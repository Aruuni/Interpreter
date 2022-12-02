import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.*;
public class Interpreter extends AbstractParseTreeVisitor<Object> implements InterpreterVisitor<Object> {
    private final Stack<HashMap<String, Object >> memory = new Stack<>();
    private final Map<String, InterpreterParser.DeclaContext> functions = new HashMap<>();
    @Override
    public Object visitInt(InterpreterParser.IntContext ctx) {return Integer.parseInt(ctx.getText());}
    @Override
    public Object visitBoolean(InterpreterParser.BooleanContext ctx) {return Boolean.parseBoolean(ctx.getText());}
    public Object visitMain(InterpreterParser.ProgramContext ctx, Object[] args) {
        Object returnValue = null;
        for (int i = 0; i < ctx.decla().size(); i++) {functions.put(ctx.decla(i).ID().getText(), ctx.decla(i));}
        for (int i = 0; i < ctx.decla().size(); i++) {if(ctx.decla(i).ID().getText().equals("main")){returnValue = visitDeclaWithArgs(ctx.decla(i), args);};}
        return returnValue;}
    @Override
    public Object visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        Object returnValue = null;
        Object[] arguments = new Object[ctx.args().expr().size()];
        for (int i = 0; i < ctx.args().expr().size(); i++) {arguments[i] = visit(ctx.args().expr(i));}
        returnValue = visitDeclaWithArgs(functions.get(ctx.ID().getText()), arguments);
        memory.pop();
        return returnValue;}
    public Object visitDeclaWithArgs(InterpreterParser.DeclaContext ctx,  Object[] args) {
        memory.push(new HashMap<>());
        Object returnValue = null;
        for (int i = 0; i < ctx.paramdecla().ID().size(); i++) {memory.peek().put(ctx.paramdecla().ID(i).getText(), args[i]);}
        returnValue = visit(ctx.body());
        return returnValue;}
    @Override
    public Object visitBody(InterpreterParser.BodyContext ctx) {
        ctx.vardecla().forEach(this::visit);
        return visit(ctx.ene());}
    @Override
    public Object visitVardecla(InterpreterParser.VardeclaContext ctx) {
        memory.peek().put(ctx.ID().getText(), visit(ctx.expr()));
        return null;}
    @Override
    public Object visitBlock(InterpreterParser.BlockContext ctx) {return visit(ctx.ene());}
    @Override
    public Object visitEne(InterpreterParser.EneContext ctx) {
        Object returnValue = null;
        for (int i = 0; i < ctx.expr().size(); i++) {returnValue = visit(ctx.expr(i));}
        return returnValue;}
    @Override
    public Object visitIndentifier(InterpreterParser.IndentifierContext ctx) {return memory.peek().get(ctx.ID().getText());}
    @Override
    public Object visitAssignment(InterpreterParser.AssignmentContext ctx) {
        Object returnValue = visit(ctx.expr());
        memory.peek().put(ctx.ID().getText(), returnValue);
        return returnValue;}
    @Override
    public Object visitParens(InterpreterParser.ParensContext ctx) {
        return switch (((TerminalNode) (ctx.binop().getChild(0))).getSymbol().getType()) {
            case InterpreterParser.Plus -> (int) visit(ctx.expr(0)) + (int) visit(ctx.expr(1));
            case InterpreterParser.Minus -> (int) visit(ctx.expr(0)) - (int) visit(ctx.expr(1));
            case InterpreterParser.Mult -> (int) visit(ctx.expr(0)) * (int) visit(ctx.expr(1));
            case InterpreterParser.Div -> (int) visit(ctx.expr(0)) / (int) visit(ctx.expr(1));
            case InterpreterParser.Equals -> visit(ctx.expr(0)).equals(visit(ctx.expr(1)));
            case InterpreterParser.LessThan -> (int) visit(ctx.expr(0)) < (int) visit(ctx.expr(1));
            case InterpreterParser.MoreThan -> (int) visit(ctx.expr(0)) > (int) visit(ctx.expr(1));
            case InterpreterParser.LessThanEq -> (int) visit(ctx.expr(0)) <= (int) visit(ctx.expr(1));
            case InterpreterParser.MoreThanEq -> (int) visit(ctx.expr(0)) >= (int) visit(ctx.expr(1));
            case InterpreterParser.AND -> (boolean) visit(ctx.expr(0)) && (boolean) visit(ctx.expr(1));
            case InterpreterParser.OR -> (boolean) visit(ctx.expr(0)) || (boolean) visit(ctx.expr(1));
            case InterpreterParser.XOR -> (boolean) visit(ctx.expr(0)) ^ (boolean) visit(ctx.expr(1));
            default -> throw new InterpreterTypeException().invalidSyntax();};}
    @Override
    public Object visitBlocks(InterpreterParser.BlocksContext ctx) {return visit(ctx.block());}
    @Override
    public Object visitIfStatement(InterpreterParser.IfStatementContext ctx) {return ((boolean)visit(ctx.expr()) == true) ? visit(ctx.block(0)) : visit(ctx.block(1));}
    @Override
    public Object visitWhileLoop(InterpreterParser.WhileLoopContext ctx) {
        Object returnValue = null;
        while (visit(ctx.expr()) == Boolean.TRUE) {returnValue =visit(ctx.block());}
        return returnValue;}
    @Override
    public Object visitRepeatLoop(InterpreterParser.RepeatLoopContext ctx) {
        Object returnValue = null;
        returnValue = visit(ctx.block());
        while (visit(ctx.expr()) == Boolean.TRUE) {returnValue = visit(ctx.block());}
        return returnValue;}
    @Override
    public Object visitPrint(InterpreterParser.PrintContext ctx) {
        if (ctx.expr().getText().equals("space")){System.out.print(" ");}
        else if (ctx.expr().getText().equals("newline")) {System.out.println();}
        else {System.out.print(visit(ctx.expr()));}
        return null;}
    @Override
    public Object visitSpace(InterpreterParser.SpaceContext ctx) {return null;}
    @Override
    public Object visitNewline(InterpreterParser.NewlineContext ctx) {return null;}
    @Override
    public Object visitSkip(InterpreterParser.SkipContext ctx) {return null;}
    //GRAVE YARD
    @Override
    public Object visitProgram(InterpreterParser.ProgramContext ctx) {return null;}
    @Override
    public Object visitArgs(InterpreterParser.ArgsContext ctx) {return null;}
    @Override
    public Object visitDecla(InterpreterParser.DeclaContext ctx) {return null;}
    @Override
    public Object visitParamdecla(InterpreterParser.ParamdeclaContext ctx) {return null;}
    @Override
    public Object visitType(InterpreterParser.TypeContext ctx) {return null;}
    @Override
    public Object visitBinop(InterpreterParser.BinopContext ctx) {return null;}}
