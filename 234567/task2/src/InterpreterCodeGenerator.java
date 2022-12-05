import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.swing.*;
import java.util.*;
public class InterpreterCodeGenerator extends AbstractParseTreeVisitor<String> implements InterpreterVisitor<String>{
    public  HashMap<String, Integer> registers = new HashMap<>();
    private  Integer regOffset = null;
    private Integer labelCounter = 0;
    private final Stack<Integer> numOfArgs = new Stack<>();
    //NEEDS ATTENTION
    @Override
    public String visitProgram(InterpreterParser.ProgramContext ctx) {
        //TODO check is stacks are actually needed
        StringBuilder sb = new StringBuilder();
        regOffset = 18;
        for (InterpreterParser.DeclaContext declaration : ctx.decla()) {
            numOfArgs.push(declaration.paramdecla().ID().size());
            sb.append(visit(declaration));}
        return sb.toString();}
    @Override
    public String visitDecla(InterpreterParser.DeclaContext ctx) {
        if (numOfArgs.peek() + registers.size() > 28) {throw new RuntimeException("Too many local variables.");}
        StringBuilder sb = new StringBuilder();
        //TODO make this compatible with function calls
        if(ctx.ID().getText().equals("main")){
            sb.append("""
              main:
                  lw          ra, 4(sp)
                  addi        sp, sp, 4
                """);}
        else {
            sb.append(String.format("""
                %s:
                """, ctx.ID().getText()));}
        for (int i = 0; i < ctx.paramdecla().ID().size(); ++i) {
            registers.put(ctx.paramdecla().ID(i).getText(), i + regOffset);
            sb.append(String.format("""
                    lw          x%2d, 4(sp)
                    addi        sp, sp, 4
                """,i + regOffset));}
        regOffset = regOffset + ctx.paramdecla().ID().size();
        sb.append("""
                    addi        sp, sp, 4
                """);
        sb.append(visit(ctx.body()));
        sb.append("""
                ret
            """);
    return sb.toString();}

    @Override
    public String visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        //TODO finish
        StringBuilder sb = new StringBuilder();
        sb.append("""
                    mv          t2, sp
                    sw          ra, 0(sp)
                    addi        sp, sp, 4
                    sw          t2, 0(sp)
                    addi        sp, sp, 4
                """);
        for (int i = 0; i < ctx.args().expr().size(); i++) {
            sb.append(visit(ctx.args().expr(i)));
            sb.append("""
                    addi        sp, sp, -4
                """);}
        sb.append(String.format("""
                    call        %s
                """, ctx.ID().getText()));
        sb.append("""
                    lw          sp, -4(sp)
                    lw          ra, 0(sp)
                    addi        sp, sp, 8
                """);
        //regOffset.push(regOffset.peek() + ctx.args().expr().size());
        return sb.toString();}
    //COMPLETED METHODS
    //#################################################################################################
    @Override
    public String visitVardecla(InterpreterParser.VardeclaContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(visit(ctx.expr()));
        sb.append(String.format("""
                    lw          x%2d, 4(sp)
                    addi        sp, sp, 4
                """,regOffset));
        registers.put(ctx.ID().getText(), regOffset);
        regOffset =regOffset + 1;
        return sb.toString();}
    @Override
    public String visitAssignment(InterpreterParser.AssignmentContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(visit(ctx.expr()));
            sb.append(String.format("""
                    PopReg      x%2d
                """,registers.get(ctx.ID().getText())));
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
                    Equal
                    JumpTrue   %s
                """, elseLabel));
        sb.append(visit(ctx.block(1)));
        sb.append(String.format("""
                    Jump        %s
                """, endifLabel));
        sb.append(String.format("""
                %s:
                """, elseLabel));
        sb.append(visit(ctx.block(0)));
        sb.append(String.format("""
                %s:
                """, endifLabel));
        return sb.toString();}
    @Override
    public String visitParens(InterpreterParser.ParensContext ctx) {
        //TODO fix make equal and test le, ge , rest should be fine
        StringBuilder sb = new StringBuilder();
        sb.append(visit(ctx.expr(1)));
        sb.append(visit(ctx.expr(0)));
        return switch (((TerminalNode) (ctx.binop().getChild(0))).getSymbol().getType()) {
            case InterpreterParser.Plus -> {
                sb.append("""
                    Plus
                """);yield sb.toString();}
            case InterpreterParser.Minus -> {
                sb.append("""
                    Minus
                """);yield sb.toString();}
            case InterpreterParser.Mult -> {
                sb.append("""
                    Multiply
                """);yield sb.toString();}
            case InterpreterParser.Div -> {
                sb.append("""
                    Divide
                """);yield sb.toString();}
            case InterpreterParser.Equals -> {
                sb.append("""
                    Equal
                """);yield sb.toString();}
            case InterpreterParser.LessThan -> {
                sb.append("""
                    CompL
                """);yield sb.toString();}
            case InterpreterParser.MoreThan -> {
                sb.append("""
                    CompG
                """);yield sb.toString();}
            case InterpreterParser.LessThanEq -> {
                sb.append("""
                    CompLE
                """);yield sb.toString();}
            case InterpreterParser.MoreThanEq -> {
                sb.append("""
                    CompGE
                """);yield sb.toString();}
            case InterpreterParser.AND -> {
                sb.append("""
                    LogicalAnd
                """);yield sb.toString();}
            case InterpreterParser.OR -> {
                sb.append("""
                    Or
                """);yield sb.toString();}
            case InterpreterParser.XOR -> {
                sb.append("""
                    LogicalXor
                """);yield sb.toString();}
        default -> throw new RuntimeException();};}
    @Override
    public String visitWhileLoop(InterpreterParser.WhileLoopContext ctx) {
        StringBuilder sb = new StringBuilder();
        String loopLabel = String.format("while_%d", labelCounter++);
        String exitLabel = String.format("exit_while_%d", labelCounter++);
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
    public String visitBody(InterpreterParser.BodyContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.vardecla().size(); i++) {
            sb.append(visit(ctx.vardecla(i)));}
        sb.append(visit(ctx.ene()));
        return sb.toString();}
    @Override
    public String visitPrint(InterpreterParser.PrintContext ctx) {
        StringBuilder sb = new StringBuilder();
        if(ctx.expr().getText().equals("space")){
            sb.append("""
                    la          a0, space
                    li          a7, 4
                    ecall
                """);
            return sb.toString();}
        else if(ctx.expr().getText().equals("newline")){
            sb.append("""
                    la          a0, newline
                    li          a7, 4
                    ecall
                """);
            return sb.toString();}
        else{
            sb.append(visit(ctx.expr()));
            sb.append("""
                    lw          a0, 4(sp)
                    li          a7, 1
                    ecall
                    Pop1
                """);}

        return sb.toString();}
    @Override
    public String visitSkip(InterpreterParser.SkipContext ctx) {
        return """
                    nop
                """;}
    @Override
    public String visitBlocks(InterpreterParser.BlocksContext ctx) {return visit(ctx.block());}
    @Override
    public String visitBlock(InterpreterParser.BlockContext ctx) {return visit(ctx.ene());}
    @Override
    public String visitEne(InterpreterParser.EneContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.expr().size(); ++i) {
            sb.append(visit(ctx.expr(i)));}
        return sb.toString();}
    @Override
    public String visitIdentifier(InterpreterParser.IdentifierContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
                    PushReg     x%2d
                """,registers.get(ctx.ID().getText())));
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
        return sb.toString();}
    //USELESS METHODS
    //###############################################################################################
    @Override
    public String visitArgs(InterpreterParser.ArgsContext ctx) {return null;}
    @Override
    public String visitSpace(InterpreterParser.SpaceContext ctx) {return null;}
    @Override
    public String visitParamdecla(InterpreterParser.ParamdeclaContext ctx) {return null;}
    @Override
    public String visitNewline(InterpreterParser.NewlineContext ctx) {return null;}
    @Override
    public String visitType(InterpreterParser.TypeContext ctx) {return null;}
    @Override
    public String visitBinop(InterpreterParser.BinopContext ctx) {return null;}}
