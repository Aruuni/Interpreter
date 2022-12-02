/*
    This version is a prototype, and is provided as-is: it should be tailored to better fit the coursework.
    I disclaim any warranty (expressed or implied) that it is fit for purpose, or is in any other way well-written.
    --- JRNB (2021-09-14, ca 17.15 BST)

    Feel free to modify the interfaces, arguments, etc., but please keep the generated output identical.
    --- Hsi-Ming Ho
*/
//@SuppressWarnings("ALL")
public class InterpreterTypeException extends RuntimeException{
    private String msg;
    public InterpreterTypeException()
    {
        super();
    }
    public String report()
    {
        return msg;
    }
    public InterpreterTypeException invalidSyntax(){msg = "invalid syntax used";return this;}
    public InterpreterTypeException noMainFuncError(){msg = "No main function defined";return this;    }
    public InterpreterTypeException mainReturnTypeError(){msg = "Wrong return type declared for main function";return this;}
    public InterpreterTypeException duplicatedFuncError(){msg = "Duplicated function names";return this;}
    public InterpreterTypeException duplicatedVarError(){msg = "Duplicated variable names";return this;}
    public InterpreterTypeException clashedVarError(){msg = "A variable name clashed with a function name";return this;}
    public InterpreterTypeException unitVarError(){msg = "Variable of unit type";return this;}
    public InterpreterTypeException undefinedFuncError(){msg = "Unknown function name";return this;}
    public InterpreterTypeException undefinedVarError(){msg = "Unknown variable name";return this;}
    public InterpreterTypeException comparisonError(){msg = "Invalid operand in integer comparison";return this;}
    public InterpreterTypeException arithmeticError(){msg = "Invalid operand in arithmetic expression";return this;}
    public InterpreterTypeException logicalError(){msg = "Invalid operand in Boolean expression";return this;}
    public InterpreterTypeException branchMismatchError(){msg = "Mismatched expression types in if expression";return this;}
    public InterpreterTypeException conditionError(){msg = "Invalid condition in if expression or loop";return this;}
    public InterpreterTypeException loopBodyError(){msg = "Invalid last expression type in loop body";return this;}
    public InterpreterTypeException functionBodyError(){msg = "Invalid return value type";return this;}
    public InterpreterTypeException assignmentError(){msg = "Incompatible types in assignment";return this;}
    public InterpreterTypeException argumentNumberError(){msg = "Invalid # of arguments in invocation";return this;}
    public InterpreterTypeException argumentError(){msg = "Invalid argument type in invocation";return this;}
    public InterpreterTypeException printError(){msg = "Invalid expression in print statement";return this;}}