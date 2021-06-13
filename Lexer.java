import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class Lexer {



	 String src;
	 
	 String oldestStackElement="";
	 String oldestStackElementTokenType="";
	 Stack<String> stack = new Stack<String>();
	 Stack<String> tokenstack= new Stack<String>();
	 Stack<String> undefinedTypes = new Stack<String>();
	 Stack<String> undefinedTokenTypes = new Stack<String>();
	 int symTableColIndex=0;
	 int ii=0;
	 Map<String, String[]> SymbolTable;
	 
	 
	 //used for printing symbol table
	 String SymbolResult="";
	 
	 ArrayList<Token> result;
	 
	 public Lexer() {
		 SymbolTable=new TreeMap<String, String[]>();
	 }
	 

	 
	
	
	

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
		undefinedTypes.push(" ");
		undefinedTokenTypes.push(" ");
		int navigate=0;
		
		String id1="";
		String id2="";
		

		//System.out.println("stack peek"+tokenstack.peek().toString());
		
		for(Token token : getFilteredTokens()) {
			
			
			navigate++;
		

			if(token.getTokenType().toString() == "VARTYPE") {
				if(oldestStackElementTokenType == "VARTYPE" && symTableColIndex<3 ) {
					int j = 3 - symTableColIndex;
					for(int i=0; i<j ;i++) {
						stack.push("undefined");
					}
					
					printStack(stack, symTableColIndex);
					emptyStack(stack);

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
				id1 = token.getLexeme();
				stack.push(token.getLexeme());
				tokenstack.push(token.getTokenType().toString());
				symTableColIndex++;
			}
			/////////////////////
			else if (
					token.getTokenType().toString()=="IDENTIFIER"&&
					!id1.equals(token.getLexeme()) ) {
				if(undefinedTypes.peek()==" ") {
					undefinedTypes.push("undefined");
					undefinedTypes.push(token.getLexeme());
					undefinedTokenTypes.push(token.getTokenType().toString());
					id2= token.getLexeme();
					ii=2;
				}
				
				
	
			}
			else if (
				//	tokenstack.peek().toString()=="IDENTIFIER" &&
					token.getTokenType().toString()=="IDENTIFIER" &&
					id1.equals(token.getLexeme())) {
				
					if(tokenstack.peek().toString()==" " ) {
						//already pushed in stack
						stack.push(oldestStackElement);
						stack.push(token.getLexeme());
						tokenstack.push(oldestStackElementTokenType);
						tokenstack.push(token.getTokenType().toString());
						
					}
					symTableColIndex=2;
					id2=token.getLexeme();
					
					
	
			}
			
			
			else if (
					undefinedTokenTypes.peek().toString()=="IDENTIFIER" &&
					token.getTokenType().toString()=="ASSIGNMENTOP" 
					) {
				
				undefinedTokenTypes.push(token.getTokenType().toString());
				
				
	
			}
			else if(
					undefinedTokenTypes.peek().toString().equals("ASSIGNMENTOP") &&
					(token.getTokenType().toString()=="NUMBER"||
					token.getTokenType().toString()=="FLOATDOUBLENUMBER"||
					token.getTokenType().toString()=="BOOLEANLITERAL"||
					token.getTokenType().toString()=="STRINGVALUE"||
					token.getTokenType().toString()=="CHARVALUE") &&
					!id1.equals(id2))
			{
				ii++;
				undefinedTokenTypes.push(token.getTokenType().toString());
				undefinedTypes.push(token.getLexeme());
				printStack(undefinedTypes, ii);
				emptyStack(undefinedTokenTypes);
			}
			
			
			////////////////////////////////////
			
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
					printStack(stack, symTableColIndex);
					emptyStack(tokenstack);
				}
			 }
		
			
			if(navigate > getFilteredTokens().size()-1 ) {
				int j = 3 - symTableColIndex;
				int k = 3- ii;
				System.out.println("this is k: "+k);
				if(j<3) {
					for(int i=0; i<j ;i++) {
						stack.push("undefined");
					}
					printStack(stack, symTableColIndex);
					
					emptyStack(tokenstack);
				}
				if(k<3) {
					for(int i=0; i<k ;i++) {
						undefinedTypes.push("undefined");
					}
					printStack(undefinedTypes, ii);
					
					emptyStack(undefinedTokenTypes);
				}
				
			}
			

		}
	}
	

	
	public void printStack(Stack<String> st, int symIndex) {
		symIndex=0;
		Stack<String> temp = new Stack<String>();
		String arg1;
		
		SymbolResult += "(";
		while (st.empty() == false)
		  {
		    temp.push(st.peek());
		    st.pop();
		    
		  }  
		while(temp.empty() == false) 
		{
			String s = temp.peek();
			
			if(s != " " && s != "=") {
				SymbolResult += s;
				SymbolResult += ",";
			}
			
			
			temp.pop();
		}
		//delete the extra "," at the end
		SymbolResult=SymbolResult.substring(0, SymbolResult.length() - 1); 
		
		
		
		
		SymbolResult += ")\n";
		st.push(" ");
		
		
		
		
	}
	public void emptyStack(Stack<String> s ) {
		while(s.empty()== false) {
			s.pop();
		}
		s.push(" ");
		//String lastElement1, String lastElement2
//		lastElement1="";
//		lastElement2="";
	}

	
}
