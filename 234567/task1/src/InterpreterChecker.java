import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import java.util.*;
@SuppressWarnings("ALL")
public class InterpreterChecker extends AbstractParseTreeVisitor<InterpreterTypes> implements InterpreterVisitor<InterpreterTypes> {
    //k: function name v: return type
    private final Map<String, InterpreterTypes> fuctionDefinitions = new HashMap<>();
    //k: function name, v: type
    private final Map<String, InterpreterTypes> variables = new HashMap<>();
    //k: function name, v: argument types
    private final Map<String, InterpreterTypes[]> parameters = new HashMap<>();
    @Override
    public InterpreterTypes visitProgram(InterpreterParser.ProgramContext ctx) {
        for (int i = 0; i < ctx.decla().size(); ++i) {
            if (fuctionDefinitions.containsKey(ctx.decla().get(i).ID().getText())){throw new InterpreterTypeException().duplicatedFuncError();}
            fuctionDefinitions.put(ctx.decla(i).ID().getText(), visit(ctx.decla(i).type()));}
        if (!fuctionDefinitions.containsKey("main")){throw new InterpreterTypeException().noMainFuncError();}
        else {if (fuctionDefinitions.get("main") != InterpreterTypes.INT){throw new InterpreterTypeException().mainReturnTypeError();}}
        for (int i = 0; i < ctx.decla().size(); ++i) {
            variables.clear();
            InterpreterTypes[] parameterTypes = new InterpreterTypes[ctx.decla(i).paramdecla().ID().size()];
            for (int j = 0; j < ctx.decla(i).paramdecla().ID().size(); j++) {
                parameterTypes[j] = visit(ctx.decla(i).paramdecla().type(j));
                if (parameterTypes[j] == InterpreterTypes.UNIT) {throw new InterpreterTypeException().unitVarError();}
                if (fuctionDefinitions.containsKey(ctx.decla(i).paramdecla().ID(j).getText())) {throw new InterpreterTypeException().clashedVarError();}
                if (variables.containsKey(ctx.decla(i).paramdecla().ID(j).getText())) {throw new InterpreterTypeException().duplicatedVarError();}
                if (parameterTypes[j] == null){throw new InterpreterTypeException().argumentError();}
                variables.put(ctx.decla(i).paramdecla().ID(j).getText(), parameterTypes[j]);}
            parameters.put(ctx.decla(i).ID().getText(), parameterTypes);}
        for (int i = 0; i < ctx.decla().size(); ++i){
            if (visit(ctx.decla(i)) != fuctionDefinitions.get(ctx.decla(i).ID().getText())){throw new InterpreterTypeException().functionBodyError();}
            else{ visit(ctx.decla(i));}}
        return null;}
    @Override
    public InterpreterTypes visitDecla(InterpreterParser.DeclaContext ctx) {
        variables.clear();
        for (int i = 0; i < ctx.paramdecla().type().size(); i++) {variables.put(ctx.paramdecla().ID(i).getText(), visit(ctx.paramdecla().type(i)));}
        return visit(ctx.body());}
    @Override
    public InterpreterTypes visitVardecla(InterpreterParser.VardeclaContext ctx) {
        if (ctx.type().getText().equals("unit")) {throw new InterpreterTypeException().unitVarError();}
        if (fuctionDefinitions.containsKey(ctx.ID().getText())) {throw new InterpreterTypeException().clashedVarError();}
        if (variables.containsKey(ctx.ID().getText())) {throw new InterpreterTypeException().duplicatedVarError();}
        if (visit(ctx.expr()) != visit(ctx.type())) {throw new InterpreterTypeException().assignmentError();}
        variables.put(ctx.ID().getText(), visit(ctx.type()));
        visit(ctx.expr());
        return null;}
    @Override
    public InterpreterTypes visitBody(InterpreterParser.BodyContext ctx) {
        ctx.vardecla().forEach(this::visit);
        if (visit(ctx.ene()) == null){throw new InterpreterTypeException().loopBodyError();}
        return visit(ctx.ene());}
    @Override
    public InterpreterTypes visitBlock(InterpreterParser.BlockContext ctx) {
        return visit(ctx.ene());}
    @Override
    public InterpreterTypes visitEne(InterpreterParser.EneContext ctx) {
        if (ctx.expr().size() == 0) {return null;}
        ctx.expr().forEach(this::visit);
        return visit(ctx.expr(ctx.expr().size()-1));}
    @Override
    public InterpreterTypes visitIndentifier(InterpreterParser.IndentifierContext ctx) {
        if (fuctionDefinitions.containsKey(ctx.ID().getText())) {throw new InterpreterTypeException().clashedVarError();}
        if (!variables.containsKey(ctx.ID().getText())){throw new InterpreterTypeException().undefinedVarError();}
        return variables.get(ctx.ID().getText());}
    @Override
    public InterpreterTypes visitAssignment(InterpreterParser.AssignmentContext ctx) {
        if (visit(ctx.expr()) == InterpreterTypes.UNIT){throw new InterpreterTypeException().unitVarError();}
        if (!variables.containsKey(ctx.ID().getText())){throw new InterpreterTypeException().undefinedVarError();}
        if (variables.get(ctx.ID().getText()) != visit(ctx.expr())){throw new InterpreterTypeException().assignmentError();}
        return visit(ctx.expr());}
    @Override
    public InterpreterTypes visitIfStatement(InterpreterParser.IfStatementContext ctx) {
        if (visit(ctx.expr()) != InterpreterTypes.BOOL){throw new InterpreterTypeException().conditionError();}
        if (visit(ctx.block(0)) != visit(ctx.block(1))) {throw new InterpreterTypeException().branchMismatchError();}
        return visit(ctx.block(0));}
    @Override
    public InterpreterTypes visitPrint(InterpreterParser.PrintContext ctx) {
        if (ctx.expr().getText().equals("space") || ctx.expr().getText().equals("newline")){return InterpreterTypes.UNIT;}
        if (visit(ctx.expr())==InterpreterTypes.INT){return InterpreterTypes.UNIT;}
        else{throw new InterpreterTypeException().printError();}}
    @Override
    public InterpreterTypes visitSpace(InterpreterParser.SpaceContext ctx) {return InterpreterTypes.UNIT;}
    @Override
    public InterpreterTypes visitNewline(InterpreterParser.NewlineContext ctx) {return InterpreterTypes.UNIT;}
    @Override
    public InterpreterTypes visitSkip(InterpreterParser.SkipContext ctx) {return InterpreterTypes.UNIT;}
    @Override
    public InterpreterTypes visitBlocks(InterpreterParser.BlocksContext ctx) {return visit(ctx.block());}
    @Override
    public InterpreterTypes visitRepeatLoop(InterpreterParser.RepeatLoopContext ctx) {
        if (visit(ctx.expr()) != InterpreterTypes.BOOL){throw new InterpreterTypeException().conditionError();}
        if (visit(ctx.block()) == null){throw new InterpreterTypeException().loopBodyError();}
        return visit(ctx.block());}
    @Override
    public InterpreterTypes visitParens(InterpreterParser.ParensContext ctx) {
        return switch (ctx.binop().getText()){
            case "==" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) ){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().comparisonError();}}
            case "+" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.INT;}else{throw new InterpreterTypeException().arithmeticError();}}
            case "-"  -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.INT;}else{throw new InterpreterTypeException().arithmeticError();}}
            case "*" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.INT;}else{throw new InterpreterTypeException().arithmeticError();}}
            case "/" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.INT;}else{throw new InterpreterTypeException().arithmeticError();}}
            case "<" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().comparisonError();}}
            case ">" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().comparisonError();}}
            case "<=" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().comparisonError();}}
            case ">=" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.INT){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().comparisonError();}}
            case "^" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.BOOL){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().logicalError();}}
            case "&" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.BOOL){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().logicalError();}}
            case "|" -> {if(visit(ctx.expr(0)) == visit(ctx.expr(1)) && visit(ctx.expr(0)) == InterpreterTypes.BOOL){yield InterpreterTypes.BOOL;}else{throw new InterpreterTypeException().logicalError();}}
            default -> null;};}
    @Override
    public InterpreterTypes visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        if (!fuctionDefinitions.containsKey(ctx.ID().getText())){throw new InterpreterTypeException().undefinedFuncError();}
        if (parameters.get(ctx.ID().getText()).length != ctx.args().expr().size()){throw new InterpreterTypeException().argumentNumberError();}
        for (int i = 0; i < ctx.args().expr().size(); i++){if(visit(ctx.args().expr(i)) != parameters.get(ctx.ID().getText())[i]){throw new InterpreterTypeException().argumentError();}}
        return fuctionDefinitions.get(ctx.ID().getText());}
    @Override
    public InterpreterTypes visitWhileLoop(InterpreterParser.WhileLoopContext ctx) {
        if (visit(ctx.expr()) != InterpreterTypes.BOOL){throw new InterpreterTypeException().conditionError();}
        return visit(ctx.block());}
    @Override
    public InterpreterTypes visitInt(InterpreterParser.IntContext ctx) {return InterpreterTypes.INT;}
    @Override
    public InterpreterTypes visitBoolean(InterpreterParser.BooleanContext ctx) {return InterpreterTypes.BOOL;}
    @Override
    public InterpreterTypes visitType(InterpreterParser.TypeContext ctx) {return Enum.valueOf(InterpreterTypes.class, ctx.TYPE().getText().toUpperCase());}
    //GRAVE YARD
    @Override
    public InterpreterTypes visitArgs(InterpreterParser.ArgsContext ctx) {return null;}
    @Override
    public InterpreterTypes visitBinop(InterpreterParser.BinopContext ctx) {return null;}
    @Override
    public InterpreterTypes visitParamdecla(InterpreterParser.ParamdeclaContext ctx){return null;}}