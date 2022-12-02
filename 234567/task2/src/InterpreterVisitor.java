// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link InterpreterParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface InterpreterVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(InterpreterParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(InterpreterParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#decla}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecla(InterpreterParser.DeclaContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#paramdecla}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamdecla(InterpreterParser.ParamdeclaContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(InterpreterParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#vardecla}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardecla(InterpreterParser.VardeclaContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(InterpreterParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#ene}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEne(InterpreterParser.EneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Indentifier}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndentifier(InterpreterParser.IndentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(InterpreterParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Boolean}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(InterpreterParser.BooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Assignment}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(InterpreterParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(InterpreterParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(InterpreterParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Blocks}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlocks(InterpreterParser.BlocksContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(InterpreterParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(InterpreterParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RepeatLoop}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatLoop(InterpreterParser.RepeatLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(InterpreterParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Space}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpace(InterpreterParser.SpaceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Newline}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewline(InterpreterParser.NewlineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Skip}
	 * labeled alternative in {@link InterpreterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip(InterpreterParser.SkipContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(InterpreterParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinop(InterpreterParser.BinopContext ctx);
}