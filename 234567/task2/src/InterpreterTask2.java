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
                    .macro    PushImm        $number
                        li          t0, $number
                        sw          t0, (sp)
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
                    
                    .macro Lte
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        ble         t1, t2, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro Gte
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        ble         t2, t1, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro Lte
                        Popt1t2
                        li          t0, 1
                        sw          t0, (sp)
                        ble         t1, t2, exit
                        sw          zero, (sp)
                    exit:
                        addi        sp, sp, -4
                    .end_macro
                    .macro    Add
                        Popt1t2
                        add         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Sub
                        Popt1t2
                        sub         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Mul
                        Popt1t2
                        mul         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Div
                        Popt1t2
                        div         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    And
                        Popt1t2
                        and         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                   
                    .macro    Eql
                            Popt1t2
                            beq         t1, t2, exit
                            sw          zero, (sp)
                            addi        sp, sp, -4
                            j           exit2
                        exit:
                            li          t0, 1
                            sw          t0, (sp)
                            addi        sp, sp, -4
                        exit2:
                    .end_macro
                    
                    .macro    Leq
                            Popt1t2
                            blt         t1, t2, exit
                            sw          zero, (sp)
                            addi        sp, sp, -4
                            j           exit2
                        exit:
                            li          t0, 1
                            sw          t0, (sp)
                            addi        sp, sp, -4
                        exit2:
                    .end_macro
                    
                    .macro    Geq
                            Popt1t2
                            blt         t2, t1, exit
                            sw          zero, (sp)
                            addi        sp, sp, -4
                            j           exit2
                        exit:
                            li          t0, 1
                            sw          t0, (sp)
                            addi        sp, sp, -4
                        exit2:
                    .end_macro
                    
                    .macro    Or
                        Popt1t2
                        or         t1, t1, t2
                        sw          t1, (sp)
                        addi        sp, sp, -4
                    .end_macro
                    
                    .macro    Xor
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
        StringBuilder code = new StringBuilder();
        writer.write(code.append(codegen.visit(tree)).toString());
        writer.close();
        System.out.println(stackMachineMacros + codegen.visit(tree));

    }
}