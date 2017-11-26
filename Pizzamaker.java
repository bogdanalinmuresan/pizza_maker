/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizza_maker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Pizzamaker {
	
	private  HashMap<String,Integer> ingredient=new HashMap<String,Integer>();
    private  HashMap<String,HashSet<String>> pizza=new HashMap<String,HashSet<String>>();
    private  List<String> commandList=new ArrayList<String>();
    
    public HashMap<String,Integer> getIngredients(){
    		return ingredient;
    }
    
    public HashMap<String,HashSet<String>> getPizza() {
    		return pizza;
    }
    
    public List<String> getCommandsList() {
    	return commandList;
    }
    
    
    /**
     * this function execute all commands received from console
     * @param c terminal command
     */
    public void commands (String c, HashMap<String, HashSet<String>> pizza, HashMap<String,Integer> ingredient){
        
      String[] parts = c.split(" ");
      try{
        switch( parts[0] ){
          case "add_ingredient":
            ingredient.put(parts[2], Integer.parseInt(parts[4])); 
            System.out.println("The ingredient was added;");
            break;
          case "create_pizza":
            String[] part_ingredients=parts[4].split(",");
            System.out.println("part_ingredients "+part_ingredients.length);
            //first check ingredients exists
            Boolean exist_ingredients=true;
            
            for(int i=0;i<part_ingredients.length; i++){
              if (!ingredient.containsKey(part_ingredients[i])){
            	  	
                  exist_ingredients=false;
              }
            }
              //now create the pizza
            if(exist_ingredients){
                if(!pizza.containsKey(parts[2])){// if pizza do no exist
                	
                		pizza.put(parts[2], new HashSet<String>());
                    for(int i=0;i<part_ingredients.length; i++){
                    		pizza.get(parts[2]).add(part_ingredients[i]);
                    }
                    System.out.println("Pizza was added in the system");
                }else {
                	System.out.println("Pizza was not added in the system because already exists");
                }
            }else{
              System.out.println(" Not all ingredients exits so pizza can not be created"  );
            }
            break;
          case "delete_pizza":
            if(pizza.remove(parts[2])==null){
              System.out.println("no exist that pizza");
            }
            else{
              System.out.println("pizza removed correctly");
            }
            break;
          case "delete_ingredient":
            // delete ingredient
            if(ingredient.remove(parts[2])==null){
              System.out.println("that ingredients do not exist ");
            }else{
              //delete all pizza with that ingredient
              for(Iterator<Map.Entry<String, HashSet<String>>> it = pizza.entrySet().iterator();it.hasNext();){
                Map.Entry<String, HashSet<String>> entry = it.next();
                if (entry.getValue().contains(parts[2])) {
                  System.out.println(entry.getKey()+" ->has been deleted");
                   it.remove();
                }
              }  
            }
            break;
          case "search_pizza":
            //all paramaters are introduced
            if(parts.length ==5){
              for(Iterator<Map.Entry<String, HashSet<String>>> it = pizza.entrySet().iterator();it.hasNext();){
                  Map.Entry<String, HashSet<String>> entry = it.next();
                  if (entry.getValue().contains(parts[2])) {//pizza contain the ingredients
                      System.out.println(entry.getKey()+" contains "+entry.getValue().toString() );
                  } else{
                      int  count=0;
                      for(Iterator<String> it_ing=entry.getValue().iterator();it_ing.hasNext();){
                        count=count+ingredient.get(it_ing.next());
                      }
                      
                      int threshold = Integer.parseInt(parts[4]);
                      if(count !=0 && count <= threshold ){
                        System.out.println(entry.getKey()+ " contains "+entry.getValue().toString()+ " with a price of "+ count);

                      }
                  }
              } 
            }else{
              //only one parameter is introduced 
              if(parts.length == 3 ){
                //which one 
                if("-ingredientName".equals(parts[1])){
                  for(Iterator<Map.Entry<String, HashSet<String>>> it = pizza.entrySet().iterator();it.hasNext();){
                    Map.Entry<String, HashSet<String>> entry = it.next();
                      if (entry.getValue().contains(parts[2])) {//pizza contain the ingredients
                        System.out.println(entry.getKey()+" contains "+entry.getValue().toString() );
                      }
                  }
                }else{
                  if("-price_threshold".equals(parts[1])){
                    for(Iterator<Map.Entry<String, HashSet<String>>> it = pizza.entrySet().iterator();it.hasNext();){
                      Map.Entry<String, HashSet<String>> entry = it.next();
                      int  count=0;
                      for(Iterator<String> it_ing=entry.getValue().iterator();it_ing.hasNext();){
                          count=count+ingredient.get(it_ing.next());
                      }
                      int threshold = Integer.parseInt(parts[2]);
                      if(count !=0 && count <= threshold ){
                        System.out.println(entry.getKey()+ " contains "+entry.getValue().toString()+ " with a price of "+ count);
                      }  
                    }
                  }
                }
              }
            }
              break;
      }   
      }catch(InputMismatchException e){
           e.printStackTrace();
      }
    }
    
    
    
    public void read(BufferedReader br,FileReader fr) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    String input="";
		while((input=br.readLine())!=null){
	              String[] parts = input.split(" ");
	              try{
	                switch(parts[0]){
	                  case "import":
	                      br = null;
	                      fr=null;
	
	                      try{
	                        fr=new FileReader(parts[2]);
	                        br=new BufferedReader(fr);
	
	                        String sCurrentLine;
	                        while ((sCurrentLine = br.readLine()) != null) {
	                          commands(sCurrentLine, pizza, ingredient);
	                          commandList.add(sCurrentLine);
	                        }
	
	                      }catch(IOException e){
	                          e.printStackTrace();
	                      }
	                      finally{
	
	                        try{
	                            if(br!=null) br.close();
	
	                            if(fr!=null) fr.close();
	
	                        }catch(IOException e){
	                            e.printStackTrace();
	                        }
	                      }
	                      break;
	                  case "export":
	                    Writer writer = null;
	
	                    try {
	                        writer = new BufferedWriter(new OutputStreamWriter(
	                                new FileOutputStream(parts[2]), "utf-8"));
	
	                        for (String com : commandList) {
	                            writer.write(com);
	                        }
	                        
	                    } catch (IOException ex) {
	                        // report
	                    } finally {
	                        try {writer.close();} catch (Exception ex) {/*ignore*/}
	                    }
	                    break;
	
	                  case "add_ingredient":
	                    commandList.add(input);
	                    commands(input, pizza, ingredient);
	                    break;
	                  case "create_pizza":
	                    commandList.add(input);
	                    commands(input, pizza, ingredient);
	                    break;
	                  case "delete_pizza":
	                    commandList.add(input);
	                    commands(input, pizza, ingredient);
	                    break;
	                  case "delete_ingredient":
	                    commandList.add(input);
	                    commands(input, pizza, ingredient);
	                    break;
	                  case "search_pizza":
	                    commandList.add(input);
	                    commands(input, pizza, ingredient);
	                    break;
	                  
	                  default:
	                    System.out.println("Only next commands are allowed: ");
	                    System.out.println("---------------------------------------------- ");
	                    System.out.println("add_ingredient -name IngredientName -price price");
	                    System.out.println("create_pizza -name PizzaName -list_of_ingredients name1,name2,name3,...,nameN");
	                    System.out.println("delete_pizza -name PizzaName");
	                    System.out.println("delete_ingredient -name IngredientName");
	                    System.out.println("search_pizza [-ingredientName Name] [-price_threshold Price]");
	                    System.out.println("import -path filePath");
	                    System.out.println("export -fileName FileName");
	                    System.out.println("---------------------------------------------- ");
	                }
	
	              }catch(InputMismatchException e){
	                e.printStackTrace();
	              }
            

        }
    }

}