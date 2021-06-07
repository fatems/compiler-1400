
	public enum TokenType {
		Identifier,
	
		Int,

		Double,
		
		String,
		
		Float,
		
		While,
		
		For,
		
		If,
		
		Then,
		
		Else,
		
		Assignment,
		
		Null,
		Semicolon,
		Comma,
		
		Plus,

		Minus,
		
		Multiply,

		Divide,
		
		Mod,
		
		And,
		
		Or,
		
		EqualEqual,
		
		NotEqual,
		
		Greater,

		Less,

		GreaterOrEqual,
		
		LessOrEqual,
		
		PlusPlus,
		
		MinusMinus,
		
		Break,
		
		ElseIF,
		
		Static,
		
		Void,
		
		Public,
		
		Default,
		Continue,
		Return,
		Long,
		
		Char,
		Boolean,
		Class,
		
		Switch,
		
		Case,
		
		
		
		BlockComment,

		LineComment,

		WhiteSpace,

		Tab,

		NewLine,

		CloseBrace,

		OpenBrace,

		OpeningCurlyBrace,

		ClosingCurlyBrace,

//		DoubleConstant,

//		IntConstant,
		True,
		
		False;

		

		

	

		/**
		 * Determines if this token is auxiliary
		 * 
		 * @return {@code true} if token is auxiliary, {@code false} otherwise
		 */
		public boolean isAuxiliary() {
			return this == BlockComment || this == LineComment || this == NewLine || this == Tab
					|| this == WhiteSpace;
		}
	}


