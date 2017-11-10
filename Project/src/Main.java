import java.io.File;
import java.io.IOException;
import java.util.*;

import org.json.simple.parser.ParseException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Problem p=Problem.fromJson(new File("1_1_3_3_FALSE_100_14_14.json"));
			
			p.prepareSolve();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
