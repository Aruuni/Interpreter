// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link InterpreterParser}.
 */
public interface InterpreterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(InterpreterParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(InterpreterParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(InterpreterParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(InterpreterParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#decla}.
	 * @param ctx the parse tree
	 */
	void enterDecla(InterpreterParser.DeclaContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#decla}.
	 * @param ctx the parse tree
	 */
	void exitDecla(InterpreterParser.DeclaContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#paramdecla}.
	 * @param ctx the parse tree
	 */
	void enterParamdecla(InterpreterParser.ParamdeclaContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#paramdecla}.
	 * @param ctx the parse tree
	 */
	void exitParamdecla(InterpreterParser.ParamdeclaContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(InterpreterParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(InterpreterParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#vardecla}.
	 * @param ctx the parse tree
	 */
	void enterVardecla(InterpreterParser.VardeclaContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#vardecla}.
	 * @param ctx the parse tree
	 */
	void exitVardecla(InterpreterParser.VardeclaContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(InterpreterParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(InterpreterParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#ene}.
	 * @param ctx the parse tree
	 */
	void enterEne(InterpreterParser.EneContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#ene}.
	 * @param ctx the parse tree
	 */
	void exitEne(InterpreterParser.EneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(InterpreterParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(InterpreterParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(InterpreterParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(InterpreterParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Boolean}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(InterpreterParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Boolean}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(InterpreterParser.BooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Assignment}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(InterpreterParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Assignment}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(InterpreterParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(InterpreterParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(InterpreterParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(InterpreterParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(InterpreterParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Blocks}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBlocks(InterpreterParser.BlocksContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Blocks}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBlocks(InterpreterParser.BlocksContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(InterpreterParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(InterpreterParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(InterpreterParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(InterpreterParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RepeatLoop}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRepeatLoop(InterpreterParser.RepeatLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RepeatLoop}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRepeatLoop(InterpreterParser.RepeatLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Print}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrint(InterpreterParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrint(InterpreterParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Space}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSpace(InterpreterParser.SpaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Space}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSpace(InterpreterParser.SpaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Newline}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewline(InterpreterParser.NewlineContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Newline}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewline(InterpreterParser.NewlineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Skip}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSkip(InterpreterParser.SkipContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Skip}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSkip(InterpreterParser.SkipContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(InterpreterParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(InterpreterParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#binop}.
	 * @param ctx the parse tree
	 */
	void enterBinop(InterpreterParser.BinopContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#binop}.
	 * @param ctx the parse tree
	 */
	void exitBinop(InterpreterParser.BinopContext ctx);
}