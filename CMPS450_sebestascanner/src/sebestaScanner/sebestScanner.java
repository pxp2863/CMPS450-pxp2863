package sebestaScanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class sebestScanner {
static int charClass;
static String lexeme;
static char nextChar;
static int token;
static int nextToken;

static FileReader inputStream = null;
static final int LETTER = 0;
static final int DIGIT = 1;
static final int UNKNOWN = 99;
static final int EOF = -1;

static final int INT_LIT = 10;
static final int IDENT =11;
static final int ASSIGN_OP =20;
static final int ADD_OP =21;
static final int SUB_OP =22;
static final int MULT_OP= 23;
static final int DIV_OP =24;
static final int LEFT_PAREN =25;
static final int RIGHT_PAREN =26;

static int lookup(char ch) {
switch (ch) {
case '(':
addChar();
nextToken = LEFT_PAREN;
break;
case ')':
addChar();
nextToken = RIGHT_PAREN;
break;
case '+':
addChar();
nextToken = ADD_OP;
break;
case '-':
addChar();
nextToken = SUB_OP;
break;
case '*':
addChar();
nextToken = MULT_OP;
break;
case '/':
addChar();
nextToken = DIV_OP;
break;
default:
addChar();
nextToken = EOF;
break;
}
return nextToken;
}

static void addChar() {
lexeme += nextChar;
}

static void getChar(){
	
	try {
		nextChar=(char)inputStream.read();
	} catch (IOException e) {
		System.out.println("Reading error. ");
	}
	if(nextChar==-1)
		charClass = EOF;
	else { if (Character.isLetter(nextChar))
		charClass = LETTER;
	      else if (Character.isDigit(nextChar)) 
	    	  charClass = DIGIT;
	      else charClass = UNKNOWN;
		
	}
}
static void getNonBlank() {
while (Character.isWhitespace(nextChar))
getChar();
}

static int lex() {
lexeme="";	
getNonBlank();
switch (charClass) {
/* Parse identifiers */
case LETTER:
addChar();
getChar();
while (charClass == LETTER || charClass == DIGIT) {
addChar();
getChar();
}
nextToken = IDENT;
break;
/* Parse integer literals */
case DIGIT:
addChar();
getChar();
while (charClass == DIGIT) {
addChar();
getChar();
}
nextToken = INT_LIT;
break;
/* Parentheses and operators */
case UNKNOWN:
lookup(nextChar);
getChar();
break;
/* EOF */
case EOF:
nextToken = EOF;
lexeme = "EOF";
break;
}
System.out.println("Next token is: "+nextToken+" Next lexeme is: "+lexeme);
return nextToken;
}


	public static void main(String[] args) {
		try{ inputStream = new FileReader("front.in");
		     getChar();
		     do{
		    	 lex();
		     } while(nextToken!=EOF);				
		} catch (FileNotFoundException e){
			System.out.println("File open error. ");
			System.out.println("Directory: "+ System.getProperty("user.dir"));
		} finally {
			if (inputStream!= null){
				try {
					inputStream.close();
				} catch (IOException e){
					System.out.println("File close error. ");
				}
			}
		}

	}

}
