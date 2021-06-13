public enum TokenType {
	
	
	LOGICALOR("\\|\\|"),
	LOGICALAND("\\&\\&"),
	
	

	BLOCKCOMMENT("/\\*\\s*.*\\s*\\*/"),
	LINECOMMENT("//(.*?)[\\r$]?\\n"),
	FLOATDOUBLENUMBER("[+-]?([0-9]+[.][0-9]+)"),
	NUMBER("[+-]?[0-9]+"),
	
	PLUSPLUSOP("\\+\\+"),
	MINUSMINUSOP("\\-\\-"),
	PLUSOP("\\+"),
	MINUSOP("\\-"),
	DIVOP("\\/"),
	MULTIPLYOP("\\*"),
	MODOP("\\%"),
	CLASS("class"),
	
	EQUALEQUALOP("\\=\\="),
	ASSIGNMENTOP("\\="),
	EXCLAIMEQUAL("\\!\\="),
	GREATEREQUALOP("\\>\\="),
	LESSEQUALOP("\\<\\="),
	GREATEROP("\\>"),
	LESSEROP("\\<"),
	OPENBRACE("\\("),
	CLOSEBRACE("\\)"),
	OPENCURLYBRACE("\\{"),
	CLOSECURLYBRACE("\\}"),
	COLON("\\,"),
	SEMICOLON("\\;"),
	
	
	

	SKIP("[ \t\f\r\n]+"),
	VARTYPE("char|int|double|float|boolean|String|long"),
	KEYWORD("main|for|if|else if|else|then|while|elsif|switch|case|break|static|void|public|default|continue|return"),
	BOOLEANLITERAL("true|false"),
	IDENTIFIER("[a-zA-Z-0-9_\\+\\-]{1}[0-9a-zA-Z_]{0,31}"),
	CHARVALUE("\'.\'"),
	STRINGVALUE("\".*\"");
	//add charvalue to conditions
	
	private final String pattern;
//
    

    private TokenType(String pattern) {
      this.pattern = pattern;
    }
    
    public String getPattern()
    {
    	return pattern;
    }
    public boolean isAuxiliary() { return this == SKIP; }

			 
}
