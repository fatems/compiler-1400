public class Token {


	private TokenType tokenType;

	
	private String lexeme;
	
	public Token(TokenType tokenType,String lexeme) {
		
		this.tokenType = tokenType;
		this.lexeme = lexeme;
	}


	

	
	/**
	 * Returns a string for the token 
	 * 
	 * @return a string of characters associated with this token
	 */
	public String getLexeme() {
		return lexeme;
	}


	/**
	 * Returns token's type 
	 * 
	 * @return type associated with this token
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	@Override
	public String toString() {
	      return String.format("%-10s => [%s]", tokenType.name(), lexeme);
	    }
}
