import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;


import java.util.*;
public class InterpreterCodeGenerator extends AbstractParseTreeVisitor<String> implements InterpreterVisitor<String>{
    private final Stack<HashMap<String, Integer>> registers = new Stack<>();
    private final Stack<Integer> regOffset = new Stack<>();
    private Integer labelCounter = 0;
    private final Stack<Integer> numOfArgs = new Stack<>();
    @Override
    public String visitProgram(InterpreterParser.ProgramContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (InterpreterParser.DeclaContext declaration : ctx.decla()) {
            registers.push(new HashMap<>());
            regOffset.push(10);
            labelCounter =0;
            numOfArgs.push(declaration.paramdecla().ID().size());
            sb.append(visit(declaration));
            regOffset.pop();
            registers.pop();
            numOfArgs.pop();}
        return sb.toString();}
    @Override
    public String visitDecla(InterpreterParser.DeclaContext ctx) {
        if (numOfArgs.peek() + registers.peek().size() > 22) {throw new RuntimeException("Too many local variables.");}
        StringBuilder sb = new StringBuilder();
        //TODO these are the registers that are used for the arguments
        sb.append(String.format("""
                %s:
                    lw          ra, 4(sp)
                    addi        sp, sp, 4
                """, ctx.ID().getText()));
        int regOffset = 10;
        for (int i = 0; i < numOfArgs.size(); ++i) {
            registers.peek().put(ctx.paramdecla().ID(i).getText(), i + regOffset);
            sb.append(
                    String.format("""
                                        lw          x%2d, 4(sp)
                                        addi        sp, sp, 4
                                    """,
                            i + regOffset
                    )
            );
        }

        regOffset = regOffset + numOfArgs.peek();

        sb.append("""
                    addi        sp, sp, 4
                """
        );
        //TODO come back
        sb.append(visit(ctx.body()));
    return sb.toString();}

    @Override
    public String visitParamdecla(InterpreterParser.ParamdeclaContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.ID().size(); ++i) {
            sb.append(String.format("""
                    lw          x%2d, 4(sp)   #from param
                    addi        sp, sp, 4     #from param
                """,i + regOffset.peek()));
            registers.peek().put(ctx.ID(i).getText(), i + regOffset.peek());}
        return sb.toString();}
    @Override
    public String visitBody(InterpreterParser.BodyContext ctx) {return visit(ctx.ene());}
    @Override
    public String visitVardecla(InterpreterParser.VardeclaContext ctx) {return null;}
    @Override
    public String visitBlock(InterpreterParser.BlockContext ctx) {return visit(ctx.ene());}
    @Override
    public String visitEne(InterpreterParser.EneContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.expr().size(); ++i) {
            sb.append(visit(ctx.expr(i)));
            System.out.println(ctx.expr(i).getText());}
        sb.append("""
                ret
            """);
        return sb.toString();}
    @Override
    public String visitIdentifier(InterpreterParser.IdentifierContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
                    PushReg     x%2d
                """,registers.peek().get(ctx.ID().getText())));
        return sb.toString();}
    @Override
    public String visitInt(InterpreterParser.IntContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                String.format("""
                    PushImm     %d
                """, Integer.parseInt(ctx.getText())));
        return sb.toString();}
    @Override
    public String visitBoolean(InterpreterParser.BooleanContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                String.format("""
                    PushImm     %d
                """, ctx.getText().equals("true") ? 1 : 0));
        sb.append(ctx.getText());
        return sb.toString();}
    @Override
    public String visitAssignment(InterpreterParser.AssignmentContext ctx) {
            StringBuilder sb = new StringBuilder();
            //TODO double check this
            sb.append(visit(ctx.expr()));
            sb.append(String.format("""
                    PushReg      x%2d
                """,registers.peek().get(ctx.ID().getText())));
            return sb.toString();}
    @Override
    public String visitParens(InterpreterParser.ParensContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(visit(ctx.expr(1)));
        sb.append(visit(ctx.expr(0)));
        return switch (((TerminalNode) (ctx.binop().getChild(0))).getSymbol().getType()) {
            case InterpreterParser.Plus -> {
                sb.append("""
                    Add
                """);yield sb.toString();}
            case InterpreterParser.Minus -> {
                sb.append("""
                    Sub
                """);yield sb.toString();}
            case InterpreterParser.Mult -> {
                sb.append("""
                    Mul
                """);yield sb.toString();}
            case InterpreterParser.Div -> {
                sb.append("""
                    Div
                """);yield sb.toString();}
            case InterpreterParser.Equals -> {
                sb.append("""
                    Eql
                """);yield sb.toString();}
            case InterpreterParser.LessThan -> {
                sb.append("""
                    Leq
                """);yield sb.toString();}
            case InterpreterParser.MoreThan -> {
                sb.append("""
                    Geq
                """);yield sb.toString();}
            case InterpreterParser.LessThanEq -> {
                sb.append("""
                    Lte
                """);yield sb.toString();}
            case InterpreterParser.MoreThanEq -> {
                sb.append("""
                    Gte
                """);yield sb.toString();}
            case InterpreterParser.AND -> {
                sb.append("""
                    And
                """);yield sb.toString();}
            case InterpreterParser.OR -> {
                sb.append("""
                    Or
                """);yield sb.toString();}
            case InterpreterParser.XOR -> {
                sb.append("""
                    Xor
                """);yield sb.toString();}
        default -> throw new RuntimeException();};}
    @Override
    public String visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.args().expr().size(); i++) {
            sb.append(visit(ctx.args().expr(i)));
            sb.append(String.format("""
                    PopReg      a%2d
                """,i+regOffset.peek()));}
        sb.append(String.format("""
                    jal         %s
                """, ctx.ID().getText()));
        return sb.toString();}
    @Override
    public String visitIfStatement(InterpreterParser.IfStatementContext ctx) {
        StringBuilder sb = new StringBuilder();
        String ifLabel = String.format("if_%d", labelCounter++);
        String elseLabel = String.format("else_%d", labelCounter++);
        String endifLabel = String.format("endif_%d", labelCounter++);
        sb.append(String.format("""
                %s:
                """, ifLabel));
        sb.append(visit(ctx.expr()));
        sb.append(String.format("""
                PushImm 1
                Eql
                JumpTrue   %s
                """, elseLabel));
        sb.append(visit(ctx.block(0)));
        sb.append(String.format("""
                Jump        %s
                """, endifLabel));
        sb.append(String.format("""
                %s:
                """, elseLabel));
        sb.append(visit(ctx.block(1)));
        sb.append(String.format("""
                %s:
                """, endifLabel));
        return sb.toString();}
    @Override
    public String visitWhileLoop(InterpreterParser.WhileLoopContext ctx) {
        StringBuilder sb = new StringBuilder();
        String loopLabel = String.format("While_%d", labelCounter++);
        String exitLabel = String.format("End-While_%d", labelCounter++);
        sb.append(
                String.format("""
                %s:
                """,loopLabel));
        sb.append(visit(ctx.expr()));
        sb.append(
                String.format("""
                    PushImm     1
                    LogicalXor
                    JumpTrue    %s
                """,exitLabel));
        sb.append(visit(ctx.block()));
        sb.append(
                String.format("""
                    Jump        %s
                """,loopLabel));
        sb.append(
                String.format("""
                    %s:
                    """,exitLabel));
        return sb.toString();}
    @Override
    public String visitRepeatLoop(InterpreterParser.RepeatLoopContext ctx) {
        StringBuilder sb = new StringBuilder();
        String loopLabel = String.format("Repeat_%d", labelCounter++);
        String exitLabel = String.format("End-Repeat_%d", labelCounter++);
        sb.append(visit(ctx.block()));
        sb.append(
                String.format("""
                %s:
                """,loopLabel));
        sb.append(visit(ctx.expr()));
        sb.append(
                String.format("""
                    PushImm     1
                    LogicalXor
                    JumpTrue    %s
                """,exitLabel));
        sb.append(visit(ctx.block()));
        sb.append(
                String.format("""
                    Jump        %s
                """,loopLabel));
        sb.append(
                String.format("""
                    %s:
                    """,exitLabel));
        return sb.toString();}
    @Override
    public String visitPrint(InterpreterParser.PrintContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(visit(ctx.expr()));
        sb.append("""
                Pop         x1
                li          a0, 1
                mv          a1, x1
                jal         print
            """);
        return sb.toString();}
    @Override
    public String visitSkip(InterpreterParser.SkipContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                nop
            """);
        return sb.toString();}
    @Override
    public String visitBlocks(InterpreterParser.BlocksContext ctx) {return visit(ctx.block());}
    @Override
    public String visitArgs(InterpreterParser.ArgsContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.expr().size(); i++) {
            sb.append(String.format("""
                        Push        %s
                    """, ctx.expr(i).getText()));
        }
        return sb.toString();}

    @Override
    public String visitSpace(InterpreterParser.SpaceContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                PushImm     32
                PrintChar
            """);
        return sb.toString();}
    @Override
    public String visitNewline(InterpreterParser.NewlineContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                PushImm     10
                PrintChar
            """);
        return sb.toString();}
    @Override
    public String visitType(InterpreterParser.TypeContext ctx) {return null;}
    @Override
    public String visitBinop(InterpreterParser.BinopContext ctx) {return null;}
}
