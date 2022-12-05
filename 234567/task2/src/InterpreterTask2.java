import org.antlr.v4.runtime.*;

import java.io.FileWriter;

public class InterpreterTask2 {

    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromStream(System.in);
        InterpreterLexer lexer = new InterpreterLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        InterpreterParser parser = new InterpreterParser(tokens);
        InterpreterParser.ProgramContext tree = parser.program();
        String stackMachineMacros = """
                    .data
                    space: .string " "
                    newline: .string "\\n"
                    .text
                    
                    .macro    PushImm        $number
                        li          t5, $number
                        sw          t5, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    PushReg        $reg
                        sw          $reg, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    PopReg        $reg
                        lw          $reg, 4(sp)
                        addi        sp, sp, 4
                    .end_macro
                    
                    .macro    Discard        $bytes
                        addi        sp, sp, $bytes
                    .end_macro
                    
                    .macro    Popt1t2
                        lw          t1, 4(sp)
                        addi        sp, sp, 4
                        lw          t2, 4(sp)
                        addi        sp, sp, 4
                    .end_macro
                    
                    .macro    Pop1
                        lw          t4, 4(sp)
                        addi        sp, sp, 4
                    .end_macro
                    
                    .macro CompGE
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        ble         t2, t1, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro CompLE
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        ble         t1, t2, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro CompL
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        blt         t1, t2, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro CompG
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        blt         t2, t1, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Plus
                        Popt1t2
                        add         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Minus
                        Popt1t2
                        sub         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Multiply
                        Popt1t2
                        mul         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Divide
                        Popt1t2
                        div         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    LogicalAnd
                        Popt1t2
                        and         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    LogicalXor
                        Popt1t2
                        xor         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                            
                    .macro    Jump        $address
                        j           $address
                    .end_macro
                    
                    .macro    JumpTrue    $address
                        lw          t1, 4(sp)
                        addi        sp, sp, 4
                        beqz        t1, exit
                        j           $address
                    exit:
                    .end_macro
                    """;
        FileWriter writer = new FileWriter("output.txt");
        InterpreterCodeGenerator codegen = new InterpreterCodeGenerator();
        String stackMachineCode = codegen.visit(tree);

        String out = stackMachineMacros + stackMachineCode;
        System.out.println(out);

        writer.write(out);
        writer.close();

    }
}