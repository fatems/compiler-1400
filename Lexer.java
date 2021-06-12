import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//import token.Token;
//import token.TokenType;

/**
 * The {@code Lexer} class represents lexical analyzer for subset of Java
 * language.
 * 
 * @author Ira Korshunova
 * 
 */
public class Lexer {

	/** Mapping from type of token to its regular expression */
	 //Map<TokenType, String> regEx;

	/** List of tokens as they appear in the input source */
	// List<Token> result;

	 String src;
	 
	 String oldestStackElement;
	 String oldestStackElementTokenType;
	 Stack<String> stack = new Stack<String>();
	 Stack<String> tokenstack= new Stack<String>();
	 int symTableColIndex=0;
	 
	 
	 //used for printing symbol table
	 String SymbolResult="";
	 
	 ArrayList<Token> result;
	 

	 
	
	
	

	//returns list of all tokens
	public ArrayList<Token> getTokens() {
		return result;
	}

	
	//returns list of tokens that are not skip kind
	  public List<Token> getFilteredTokens() { List<Token> filteredResult = new
	  ArrayList<Token>(); for (Token t : this.result) { if
	  (!t.getTokenType().isAuxiliary()) { filteredResult.add(t); } } return
	  filteredResult; }
	 

	  public ArrayList<Token> lex(String input) {
		    // The tokens to return
		     result = new ArrayList<Token>();

		    // Lexer logic begins here
		    StringBuffer tokenPatternsBuffer = new StringBuffer();
		    for (TokenType tokentype : TokenType.values())
		      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokentype.name(), tokentype.getPattern()));
		    Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

		    // Begin matching tokens
		    Matcher matcher = tokenPatterns.matcher(input);
		    while (matcher.find()) {
		      for (TokenType tokentype : TokenType.values())
		        if (matcher.group(tokentype.name()) != null) {
		          result.add(new Token(tokentype, matcher.group(tokentype.name())));
		          continue;
		        }
		    }
		    return result;
		  }
	  
	public void SymbolTable() {
		
		
		stack.push(" ");
		tokenstack.push(" ");
		int navigate=0;
		//System.out.println("stack peek"+tokenstack.peek().toString());
		
		for(Token token : getFilteredTokens()) {
			
			
			navigate++;
			//System.out.println(token.getLexeme());
		//	System.out.println("stack peek"+tokenstack.peek().toString());

			if(token.getTokenType().toString() == "VARTYPE") {
				if(oldestStackElementTokenType == "VARTYPE" && symTableColIndex<3 ) {
					int j = 3 - symTableColIndex;
					//System.out.println(j);
					for(int i=0; i<j ;i++) {
						stack.push("undefined");
					}
					
					printStack();
					
					
					
				}
				
				oldestStackElement = token.getLexeme();
				oldestStackElementTokenType= token.getTokenType().toString();
				stack.push(token.getLexeme());
				tokenstack.push(token.getTokenType().toString());
				symTableColIndex++;
			}
			else if(token.getTokenType().toString() == "IDENTIFIER" &&
					tokenstack.peek().toString()=="VARTYPE")
					
			{
				stack.push(token.getLexeme());
				tokenstack.push(token.getTokenType().toString());
				symTableColIndex++;
			}
			else if(token.getTokenType().toString() == "ASSIGNMENTOP" &&
					tokenstack.peek().toString()=="IDENTIFIER")
			{
				
				tokenstack.push(token.getTokenType().toString());
				
				
			}
			
			else if(tokenstack.peek().toString()=="ASSIGNMENTOP") 
			 {
				
				if(
						(token.getTokenType().toString()=="NUMBER" && (oldestStackElement.equals("int") || oldestStackElement.equals("long"))) ||
						(token.getTokenType().toString()=="FLOATDOUBLENUMBER" && ((oldestStackElement.equals("double")) || (oldestStackElement.equals("float"))))||
						(token.getTokenType().toString()=="BOOLEANLITERAL" && (oldestStackElement.equals("boolean") ))||
						
						(token.getTokenType().toString()=="CHARVALUE" && oldestStackElement.equals("char"))||
						(token.getTokenType().toString()=="STRINGVALUE" && oldestStackElement.equals("String"))) 
				{
					
					stack.push(token.getLexeme());
					tokenstack.push(token.getTokenType().toString());
					symTableColIndex++;
					printStack();
					
				}
			 }
			
			if(navigate > getFilteredTokens().size()-1 && oldestStackElementTokenType != "") {
				int j = 3 - symTableColIndex;
				//System.out.println(j);
				for(int i=0; i<j ;i++) {
					stack.push("undefined");
				}
				printStack();
			}
			

		}
	}
	

	
	public void printStack() {
		symTableColIndex=0;
		Stack<String> temp = new Stack<String>();
		SymbolResult += "(";
		while (stack.empty() == false)
		  {
		    temp.push(stack.peek());
		    stack.pop();
		    
		  }  
		while(temp.empty() == false) 
		{
			String s = temp.peek();
			
			if(s != " ") {
				SymbolResult += s;
				SymbolResult += ",";
			}
			
			
			temp.pop();
		}
		//delete the extra "," at the end
		SymbolResult=SymbolResult.substring(0, SymbolResult.length() - 1); 
		
		while(tokenstack.empty()== false) {
			tokenstack.pop();
		}
		stack.push(" ");
		tokenstack.push(" ");
		oldestStackElementTokenType="";
		oldestStackElement="";
		SymbolResult += ")\n";
		
		
		
		
	}

	
}
