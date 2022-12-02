grammar Interpreter;

program
    :decla+
    EOF
;
type
    :TYPE
;
decla
    :type ID LPAREN paramdecla RPAREN body
;
paramdecla
    :(type ID (COMMA type ID)*)?
;
body
    :LBRACE vardecla* ene RBRACE
;
vardecla
    :type ID Assign expr SEMICOLON
;
block
    :LBRACE ene RBRACE
;
ene
    :expr (SEMICOLON expr)*
;
expr
    :	ID                                  #Indentifier
    |	INTLIT                              #Int
    |	BOOLLIT                             #Boolean
    |	ID Assign expr                      #Assignment
    |	LPAREN expr binop expr RPAREN       #Parens
    |	ID LPAREN args RPAREN               #FunctionCall
    |	block                               #Blocks
    |	IF expr THEN block ELSE block       #IfStatement
    |	WHILE expr DO block                 #WhileLoop
    |	REPEAT block UNTIL expr             #RepeatLoop
    |	PRINT expr                          #Print
    |	SPACE                               #Space
    |	NEWLINE                             #Newline
    |	SKIPP                               #Skip
;
args
    :(expr (COMMA expr)*)?
;
binop
    :Equals
    |LessThan
    |MoreThan
    |LessThanEq
    |MoreThanEq
    |AND
    |OR
    |XOR
    |Plus
    |Minus
    |Mult
    |Div
;

//parens/braces
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
SEMICOLON: ';';
COMMA: ',';

//operations
Equals : '==';
LessThan: '<';
MoreThan: '>';
LessThanEq: '<=';
MoreThanEq: '>=';
AND: '&';
OR: '|';
XOR: '^';
Plus: '+';
Minus: '-';
Div: '/';
Mult: '*';
Assign: ':=';

//while, repeat, if
IF: 'if';
THEN: 'then';
ELSE: 'else';
WHILE: 'while';
DO: 'do';
REPEAT: 'repeat';
UNTIL: 'until';
PRINT:'print';
SKIPP: 'skip';
SPACE: 'space';
NEWLINE: 'newline';

//types
//any type in antlr
BOOLLIT : 'true'|'false';
INTLIT : [0] | [1-9][0-9]* ;
TYPE :	'int' | 'bool' | 'unit' ;
ID : [a-zA-Z]([a-zA-Z0-9_])*;

WS : [ \n\r\t]+ -> skip ;