package pizza_maker;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class PizzamakerTest {
	
	private Pizzamaker pm=new Pizzamaker();
	BufferedReader br=null;
	FileReader fr=null;
	
	@Test
	public void commandAddIngredients() throws IOException {
		
		String data ="add_ingredient -name pepperoni -price 2";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
		pm.read(br,fr);
		assertEquals(1, pm.getIngredients().size());
	}
	
	@Test
	public void commandCreatePizzaIngredientsExist() throws IOException {
		
		String data ="add_ingredient -name pepperoni -price 2";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		pm.read(br,fr);
		
		assertEquals(1, pm.getIngredients().size());
		
		System.out.print("ingredients "+pm.getIngredients().size());
		
		data ="create_pizza -name margarita -list_of_ingredients pepperoni";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
	    
		pm.read(br,fr);
		assertEquals(1, pm.getPizza().size());
		
	}
	
	@Test
	public void commandCreatePizzaIngredientsNotExist() throws IOException {
		
		String data ="create_pizza -name margarita -list_of_ingredients pepperoni";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        //List<String> commandList=new ArrayList<String>();
		pm.read(br,fr);
		assertEquals(0, pm.getPizza().size());
		
		
	}
	
	@Test
	public void commandCreatePizzaAndPizzaExist() throws IOException {
		
		//add ingredient
		String data ="add_ingredient -name pepperoni -price 2";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		pm.read(br,fr);
		
		assertEquals(1, pm.getIngredients().size());
		
		//add ingredient
		data ="add_ingredient -name beacon -price 1";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
		 
		pm.read(br,fr);
		
		assertEquals(2, pm.getIngredients().size());
		
		data ="create_pizza -name margarita -list_of_ingredients pepperoni,beacon";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
	    
		pm.read(br,fr);
		assertEquals(pm.getPizza().size(), 1);
		assertEquals(true,pm.getPizza().containsKey("margarita"));
		assertEquals(2,pm.getPizza().get("margarita").size() );
		
		data ="create_pizza -name margarita -list_of_ingredients pepperoni,beacon";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        //List<String> commandList=new ArrayList<String>();
		pm.read(br,fr);
		assertEquals(1,pm.getPizza().size());
		
		//create pizza and one ingredients not exists
		data ="create_pizza -name margarita -list_of_ingredients pepperoni,tomatoe";
	    System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        //List<String> commandList=new ArrayList<String>();
		pm.read(br,fr);
		assertEquals(1,pm.getPizza().size());
		
		
	}

}
