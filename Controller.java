
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
public class Controller {

	public static void main(String[] args) throws AnalyzerException {
		// TODO Auto-generated method stub
		String myString ="";
//		Controller c = new Controller();
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("Source.txt")));
			while(sc.hasNextLine()) {
	 			myString += sc.nextLine();
	 			myString += "\n";
	 			
	 		}
			//System.out.println(myString);
			
			
			//instansiate a lexer from it's class
			Lexer L = new Lexer();
			
			//Tokenize
			L.tokenize(myString);
			
			System.out.println(L.getTokens());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	         
		
	}
	public void readModule(String string) throws FileNotFoundException {
		
 		
	}

}