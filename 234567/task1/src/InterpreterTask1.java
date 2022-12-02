import org.antlr.v4.runtime.*;
public class InterpreterTask1 {
    public static void main(String[] args) throws Exception{
        Object[] argsf = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("true")) {
                argsf[i] = Boolean.parseBoolean(args[i]);
            } else if (args[i].equals("false")) {
                argsf[i] = Boolean.parseBoolean(args[i]);
            }
            else{
                argsf[i] = Integer.parseInt(args[i]);
            }
        }
        CharStream input = CharStreams.fromStream(System.in);
        InterpreterLexer lexer = new InterpreterLexer(input);
        //TODO fix error listeners
        //lexer.removeErrorListeners();
        //lexer.addErrorListener(new MyANTLRErrorListener());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        InterpreterParser parser = new InterpreterParser(tokens);
        //parser.removeErrorListeners();
        //parser.addErrorListener(new MyANTLRErrorListener());
        InterpreterParser.ProgramContext tree = parser.program();
        InterpreterChecker checker = new InterpreterChecker();
        try{checker.visit(tree);}
        catch (InterpreterTypeException ex){System.out.println(ex.report());return;}
        Interpreter interpreter = new Interpreter();
        Object returnValue = interpreter.visitMain(tree, argsf);
        System.out.println();
        System.out.println("NORMAL_TERMINATION");
        System.out.println(returnValue);

    }
}
