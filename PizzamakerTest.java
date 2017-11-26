package pizza_maker;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

class PizzamakerTest {

	@Test
	void test() {
	}
	
	void commandAddIngredients() {
		int precio=1;
		String c="";
		Reader in=;
		BufferedReader br=new BufferedReader(in);
		FileReader fr;
		
		HashMap<String,Integer> ingredient=new HashMap<String,Integer>();
		ingredient.put("bacon",precio); 
        HashMap<String,HashSet<String>> pizza=new HashMap<String,HashSet<String>>();
        //List<String> commandList=new ArrayList<String>();
		
		Pizzamaker.read();
	}

}
