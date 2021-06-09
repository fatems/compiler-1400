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
	 Map<TokenType, String> regEx;

	/** List of tokens as they appear in the input source */
	 List<Token> result;

	 String src;
	 
	 String oldestStackElement;
	 Stack<String> stack = new Stack<String>();
	 
	 String SymbolResult;
	 
	 //used for printing symbol table
	 
	 
	/**
	 * Initializes a newly created {@code Lexer} object
	 */
	public Lexer() {
		stack.push("");
		regEx = new TreeMap<TokenType, String>();
		launchRegEx();
		result = new ArrayList<Token>();
	}

	/**
	 * Performs the tokenization of the input source code.
	 * 
	 * @param source
	 *            string to be analyzed
	 * @throws AnalyzerException
	 *             if lexical error exists in the source
	 * 
	 */
	public void tokenize(String source) throws AnalyzerException {
		int position = 0;
		Token token = null;
		do {
			token = separateToken(source, position);
			if (token != null) {
				position = token.getEnd();
				result.add(token);
			}
		} while (token != null && position != source.length());
		if (position > source.length()) {
			throw new AnalyzerException("Lexical error at position # "+ position, position);

		}
	}

	/**
	 * Returns a sequence of tokens
	 * 
	 * @return list of tokens
	 */
	public List<Token> getTokens() {
		return result;
	}

	/**
	 * Returns a sequence of tokens without types {@code BlockComment},
	 * {@code LineComment} , {@code NewLine}, {@code Tab}, {@code WhiteSpace}
	 * 
	 * @return list of tokens
	 */
	public List<Token> getFilteredTokens() {
		List<Token> filteredResult = new ArrayList<Token>();
		for (Token t : this.result) {
			if (!t.getTokenType().isAuxiliary()) {
				filteredResult.add(t);
			}
		}
		return filteredResult;
	}

	/**
	 * Scans the source from the specific index and returns the first separated
	 * token
	 * 
	 * @param source
	 *            source code to be scanned
	 * @param fromIndex
	 *            the index from which to start the scanning
	 * @return first separated token or {@code null} if no token was found
	 * 
	 */
	private Token separateToken(String source, int fromIndex) {
		int ii=0;
		if (fromIndex < 0 || fromIndex >= source.length()) {
			throw new IllegalArgumentException("Illegal index in the input stream!");
		}
		for (TokenType tokenType : TokenType.values()) {
			//System.out.println(tokenType);
			System.out.println(ii +"the stack's peek is: "+stack.peek());
			if(stack.lastElement()=="Int"||
					stack.peek()=="Double"||
					stack.peek()=="Float"||
					stack.peek()=="String"
					) {
				ii++;
				if(tokenType.toString().equals("Identifier")) {
					Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
							Pattern.DOTALL);
					Matcher m = p.matcher(source );
					
					if (m.matches()) {
						String lexema = m.group(1);
						//
						stack.push(tokenType.toString());
							
							System.out.println("this is what lexemais : "+lexema);
						return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
					
					}
					
				}
			}
			else if(stack.peek().equals("Identifier")) {
				
				///////// alternative : find ig
				if(tokenType.toString().equals("Assignment")) {
					Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
							Pattern.DOTALL);
					Matcher m = p.matcher(source );
					
					if (m.matches()) {
						String lexema = m.group(1);
						//
						stack.push(tokenType.toString());
						
						
						return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
					
					}
					
				}
			}
			
			else if(stack.peek().equals("Assignment")) {
				
				///////// alternative : find ig
				if(tokenType.toString().equals("IntValue") && oldestStackElement =="Int" ) {
					Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
							Pattern.DOTALL);
					Matcher m = p.matcher(source );
					
					if (m.matches()) {
						String lexema = m.group(1);
						System.out.println(lexema);
						stack.push(tokenType.toString());
						
						
						printStack();
						
						return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
					
					}
					
				}
				else if(tokenType.toString().equals("StringValue") &&  oldestStackElement =="String") {
					Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
							Pattern.DOTALL);
					Matcher m = p.matcher(source );
					
					if (m.matches()) {
						String lexema = m.group(1);
						//
						stack.push(tokenType.toString());
						
						
						printStack();
						
						return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
					
					}
					
				}
				else if(tokenType.toString().equals("FloatDouble") && ( oldestStackElement =="Float"|| oldestStackElement =="Double")) {
					Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
							Pattern.DOTALL);
					Matcher m = p.matcher(source );
					
					if (m.matches()) {
						String lexema = m.group(1);
						//
						stack.push(tokenType.toString());
						
						printStack();
						
						return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
					
					}
					
				}
				else {
					stack.push("Undefined");
					printStack();
				}
				
			}
			
			else {
				Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
						Pattern.DOTALL);
				
				Matcher m = p.matcher(source );
				
				
//				if(p.matches("Keyword")) {
//					
//				}
				if (m.matches()) {
					String lexema = m.group(1);
					System.out.println("dummy its "+tokenType);
					if(tokenType.toString().equals("Int") ||
							tokenType.toString().equals("Double")||
							tokenType.toString().equals("Float")||
							tokenType.toString().equals("String")) {
						stack.push(tokenType.toString());
						//System.out.println("wth"+stack.peek());
						
						oldestStackElement=tokenType.toString();
						
					}
					return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
					
				}
			}

		}
			
		return null;
	}
	
//	public Token matchTokens(TokenType tokenType, int fromIndex, String source, TokenType current, TokenType last) {
//		if(tokenType.toString().equals(current) && stack.get(0).equals(last)) {
//			Pattern p = Pattern.compile(".{" + fromIndex + "}"  + regEx.get(tokenType),
//					Pattern.DOTALL);
//			Matcher m = p.matcher(source );
//			
//			if (m.matches()) {
//				String lexema = m.group(1);
//				//
//				stack.push(tokenType.toString());
//				
//				lastStackItem=lexema;
//				printStack();
//				
//				return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
//			
//			}
//			
//		}
//		return null;
//	}
	
	public void printStack() {
		Stack<String> temp = new Stack<String>();
		SymbolResult += "( ";
		while (stack.empty() == false)
		  {
		    temp.push(stack.peek());
		    stack.pop();
		    
		  }  
		while(temp.empty() == false) 
		{
			String s = temp.peek();
			System.out.println(s + "  " );
			
			SymbolResult += s;
			SymbolResult += "  ";
			
			temp.pop();
		}
		SymbolResult += ")\n";
		
		stack.push(" ");
		
		
	}

	/**
	 * Creates map from token types to its regular expressions
	 * 
	 */
	private void launchRegEx() {
		
		
		//change the place of identifier later and add .* to the first of all these
		
		
		regEx.put(TokenType.WhiteSpace, ".*( ).*");
		//regEx.put(TokenType.Keyword,".*\\b(int|double|float|string)\\b.*");
		regEx.put(TokenType.Int, ".*\\b(int)\\b.*");
		

		regEx.put(TokenType.Double, ".*\\b(double)\\b.*");
		regEx.put(TokenType.String, ".*\\b(string)\\b.*");
		

		regEx.put(TokenType.Float, ".*\\b(float)\\b.*");
		
		regEx.put(TokenType.FloatDouble, ".*\\(\\d+)\\.(\\d+)?.*");
		regEx.put(TokenType.WhiteSpace, ".*( ).*");
		regEx.put(TokenType.IntValue, ".*\\^([+-])?([0-9]+)\\$.*");
		
		regEx.put(TokenType.While, ".*\\b(while)\\b.*");
		
		regEx.put(TokenType.For, ".*\\b(for)\\b.*");
		regEx.put(TokenType.If, ".*\\b(if)\\b.*");
		regEx.put(TokenType.Then, ".*\\b(then)\\b.*");
		regEx.put(TokenType.ElseIF, ".*\\b(elsif)\\b.*");
		regEx.put(TokenType.Else, ".*\\b(else)\\b.*");
		regEx.put(TokenType.EqualEqual, ".*(==).*");
		regEx.put(TokenType.NotEqual, ".*(\\!=).*");
		regEx.put(TokenType.Assignment, ".*(=).*");
		regEx.put(TokenType.Null, ".*\\b(null)\\b.*");
		regEx.put(TokenType.Semicolon, ".*(;).*");
		regEx.put(TokenType.Comma, ".*(,).*");
		regEx.put(TokenType.PlusPlus, ".*(\\++).*");
		regEx.put(TokenType.MinusMinus, ".*(\\--).*");
		regEx.put(TokenType.Plus, ".*(\\+{1}).*");
		regEx.put(TokenType.Minus, ".*(\\-{1}).*");
		regEx.put(TokenType.Multiply, ".*(\\*).*");
		regEx.put(TokenType.Divide, ".*(/).*");
		regEx.put(TokenType.Mod, ".*(%).*");
		regEx.put(TokenType.And, "(\\&&).*");
		
		
		
		regEx.put(TokenType.Or, ".*(\\|{2}).*");
		
		
		regEx.put(TokenType.GreaterOrEqual, ".*(>=).*");
		regEx.put(TokenType.LessOrEqual, ".*(<=).*");
		regEx.put(TokenType.Greater, ".*(>).*");
		regEx.put(TokenType.Less, ".*(<).*");
		regEx.put(TokenType.Break, ".*\\b(break)\\b.*");
		
		
		regEx.put(TokenType.Static, ".*\\b(static)\\b.*");
		regEx.put(TokenType.Void, ".*\\b(void)\\b.*");
		regEx.put(TokenType.Public, ".*\\b(public)\\b.*");
		regEx.put(TokenType.Default, ".*\\b(default)\\b.*");
		regEx.put(TokenType.Continue, ".*\\b(continue)\\b.*");
		regEx.put(TokenType.Return, ".*\\b(return)\\b.*");
		regEx.put(TokenType.Long, ".*\\b(long)\\b.*");
		regEx.put(TokenType.Char, ".*\\b(char)\\b.*");
		regEx.put(TokenType.Boolean, ".*\\b(boolean)\\b.*");
		regEx.put(TokenType.Class, ".*\\b(class)\\b.*");
		regEx.put(TokenType.Switch, ".*\\b(switch)\\b.*");
		regEx.put(TokenType.Case, ".*\\b(case)\\b.*");
		regEx.put(TokenType.BlockComment, ".*(/\\*.*?\\*/).*");
		regEx.put(TokenType.LineComment, ".*(//(.*?)[\r$]?\n).*");
		
		regEx.put(TokenType.Tab, ".*(\\t).*");
		regEx.put(TokenType.NewLine, ".*(\\n).*");
		regEx.put(TokenType.CloseBrace, ".*(\\)).*");
		regEx.put(TokenType.OpenBrace, ".*(\\().*");
		regEx.put(TokenType.OpeningCurlyBrace, ".*(\\{).*");
		regEx.put(TokenType.ClosingCurlyBrace, ".*(\\}).*");
		regEx.put(TokenType.True, ".*\\b(true)\\b.*");
		regEx.put(TokenType.False, ".*\\b(false)\\b.*");
		regEx.put(TokenType.StringValue, ".*\\\"([^\"]*)\"\\.*");
		regEx.put(TokenType.Identifier, ".*\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");

//		regEx.put(TokenType.Identifier, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");


		
		
		
		
		

		
		
//		regEx.put(TokenType.DoubleConstant, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
//		regEx.put(TokenType.IntConstant, "\\b(\\d{1,9})\\b.*");
		
		
		//regEx.put(TokenType.Private, "\\b(private)\\b.*");
		
//		regEx.put(TokenType.New, "\\b(new)\\b.*");
		
		
		
		
		//regEx.put(TokenType.Point, "(\\.).*");
			
	}
}
