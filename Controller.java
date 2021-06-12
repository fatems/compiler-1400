
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
public class Controller {

	public static void main(String[] args) throws AnalyzerException {
		// TODO Auto-generated method stub
		String myString ="";

		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("Source.txt")));
			while(sc.hasNextLine()) {
	 			myString += sc.nextLine();
	 			myString += "\n";
	 			
	 		}
		
			
			
			//instansiate a lexer from it's class
			Lexer L = new Lexer();
			L.lex(myString);
			System.out.println(L.getFilteredTokens());
			
			System.out.println("*************************************************SYMBOL TABLE*************************************************");
			L.SymbolTable();
			System.out.println("type, name, value");
			System.out.println(L.SymbolResult);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	         
		
	}
		
 		
	

}
