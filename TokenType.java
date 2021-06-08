
	public enum TokenType {
		
	
		Int,
		
		
		
		
		
		//change the position later
		Identifier,
		
		
		
		
		
		
		
		
		Double,
		
		String,
		
		Float,
		IntValue,
		
		While,
		
		For,
		
		If,
		
		Then,
		
		ElseIF,
		
		Else,
		
		EqualEqual,
		
		NotEqual,
		
		Assignment,
		
		Null,
		Semicolon,
		Comma,
		
		PlusPlus,
		
		MinusMinus,
		
		Plus,

		Minus,
		
		Multiply,

		Divide,
		
		Mod,
		
		And,
		
		Or,
		
		
		
		

		GreaterOrEqual,
		
		LessOrEqual,
		
		Greater,

		Less,
		
		
		
		Break,
		
		
		
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


