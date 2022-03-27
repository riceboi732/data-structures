import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * CountryDisplayer.java
 * This class sorts countries and prints out the result in the form of a text or a graph
 */

public class CountryDisplayer {
    

    private static List<Country> list; //Creates a list of country objects
    private static BarChart obj; //Creates a graph object that displays the graph
    private static boolean isGraph; //See if needed to print graph or txt
    private static String fileDir; //User inputed file to read
    private static String indicator1; //indicator used to sort the countries
    private static String indicator2; //2nd indicator added to the graph if we have one
    private static boolean leastToGreatest; // check the way we sort
    private static String sortMode; //store the sortMode
    
    /**
	 * This method read the input file and assign each country to be an object with its 
     * indicator values
	 * @param filePath input the file to read
     */
    public static void loadCountries(String filePath) {
        //creating File object to open file
        File text = new File(filePath);
     
        Scanner textScan = null;
        
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        
        String line; //store each line
        
        //if it is the first line, ignore the first line and go to the next
        if(textScan.hasNextLine()){
           line = textScan.nextLine(); 
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        
        //Scan all the lines in file to create country objects
        while(textScan.hasNextLine()){
            line = textScan.nextLine();
            
            //Scan for variables between commas and create oject using variables in line
            Scanner lineScan = null; 
            
            try {
                lineScan = new Scanner(line).useDelimiter(",");
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
            
                
            
            //store the values of a country object
            String name = lineScan.next();
            Double CO2 = Double.parseDouble(lineScan.next());
            Double electricity = Double.parseDouble(lineScan.next());
            Double energy = Double.parseDouble(lineScan.next());
            Double area = Double.parseDouble(lineScan.next());
            Double populationG = Double.parseDouble(lineScan.next());
            Double populationT = Double.parseDouble(lineScan.next());
            Double urbanG = Double.parseDouble(lineScan.next());
            
            //Creating a new country object
            Country temp = new Country(name, CO2, electricity, energy, area, populationG, populationT, urbanG);
            //adding coutry objects to list of countries
            list.add(temp);
            lineScan.close();
        }
        
        textScan.close();
            
    }
    
    /**
	 * This is the method that sorts the countries based on a sortMode and indicator
	 * @param sortMode     way to sort
     * @param indicator    value to sort by
	 */
    public static void sortCountryList(boolean sortMode, String indicator) {
    
        int size = list.size();

        //if its NaN, we gonna move it to the end of the list
        for (int i = 0; i < size; i++) {

            if (Double.isNaN(list.get(i).getIndicator(indicator) ) ) {
                
                list.add(list.remove(i));

                //because values shift left in the list we have to go back one index to account for that value
                i--;
                //because after moving a NaN value the number of objects in the list we want to go through decreases by one to avoid sorting that NaN value again
                size--;

            }
        }
        //after the loop, size is the total number of non-NaN numbers in the list

        //sort the non-NaN numbers
        for (int i = 0; i < size; i++) {  

            for (int j = 0; j < size-1; j++) {

                //if sortMode = least to greatest
                if (sortMode) {
                    
                    if ( list.get(j).getIndicator(indicator) >  list.get(j+1).getIndicator(indicator) ) {
                        
                        //swap two object
                        Country temp = list.remove(j);
                        list.add(j+1,temp);
                    }
                    
                //if sortMode = greatest to least
                } else {
                    
                    if ( list.get(j).getIndicator(indicator) <  list.get(j+1).getIndicator(indicator) ) {
                        //swap two object
                        Country temp = list.remove(j);
                        list.add(j+1,temp);
                    }
                }
            }
        }
       
    }
 
    /**
	 * This is the method used to display all the sorted coutries
	 */
    public static void displayTextCountries() {
        for (Country each : list) {
            System.out.println( each.getName()+ "," + each.getIndicator("CO2Emissions")+ "," + each.getIndicator("AccessToElectricity")+ "," + each.getIndicator("RenewableEnergy")+ "," + each.getIndicator("ProtectedAreas")+ "," + each.getIndicator("PopulationGrowth")+ "," + each.getIndicator("PopulationTotal")+ "," + each.getIndicator("UrbanPopulationGrowth") );
        
        }
    }
    
    /**
	 * This is the method used to display the graph of sorted countriesi
     * @param indicator1       user inputed indicator
	 */
    public static void displayCountryGraph(String indicator1) {
        
        //change the title based on the sortMode
        String print = "greatest ";
        if(leastToGreatest) {
            print = "lowest ";
        }
        
        //if thee is less than 10 countries in the list, display all those countries
        int noOfGraphedCountries = 10;
        if (list.size()<10) {
            noOfGraphedCountries = list.size();
        }
        
         //create the graph
        BarChart graph = new BarChart("Top " + noOfGraphedCountries + " " + print + indicator1, "Country", "Value");
        
        //add value to the graph
        for (int i = 0; i < noOfGraphedCountries; i++) {
            
            graph.addValue(list.get(i).getName(), list.get(i).getIndicator(indicator1), indicator1);
            
            graph.addValue(list.get(i).getName(), list.get(i).getIndicator(indicator2), indicator2);
                
        }
        
        graph.displayChart();
    }
    
    /**
	 * This is the method to check the user input of indicators
     * @param ind       user inputed indicator   
	 */
    public static boolean checkIndicator(String ind) {
        
        switch (ind) { 
        case "CO2Emissions": 
            return true;
        case "AccessToElectricity": 
            return true;
        case "RenewableEnergy": 
            return true; 
        case "ProtectedAreas": 
            return true;  
        case "PopulationGrowth": 
            return true;
        case "PopulationTotal": 
            return true;
        case "UrbanPopulationGrowth":
            return true;
        default: 
            System.out.println("please input the right indicator format: CO2Emissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth");
            return false;
        }
    }

    /**
	 * This is the method to check if the user puts sort order in the right format
     * @param sort       user inputed sort mode   
	 */
    public static boolean checkSortOrder(String sort) {
        
         switch (sort) { 
        case "greatestToLeast":
            leastToGreatest = false;
            return true;
        case "leastToGreatest":
            leastToGreatest = true;
            return true;
        default: 
            System.out.println("please input the right sortOrder format: leastToGreatest or greatestToLeast");
            return false;
        }
    }

    /**
	 * This is the main method used to get the numbers of Integer list in the command line
	 * and generate that Integer list with its sum and the numbers of instances. 
	 * @param args
	 */
    public static void main(String[] args) {
        
        if (!(args.length == 3 || args.length == 4)) {
            
            System.out.println("you should type 3 or 4 command line arguments in the right format");
            System.out.println("to print a text, type 3 command line arugments: java -classpath .:jfreechart-1.5.0.jar CountryDisplayer [FILE_NAME] [INDICATOR] [SORT_ORDER]");
            System.out.println("to print a graph, type 4 command line arugments: java -classpath .:jfreechart-1.5.0.jar CountryDisplayer [FILE_NAME] [INDICATOR1] [SORT_ORDER] [INDICATOR2]");
            System.exit(1);
            
        } else {
        
            list = new ArrayList<Country>();
            
            fileDir = args[0];
            indicator1 = args[1];
            sortMode = args[2];
            
            //if indicator1 or sortMode are not in the correct format then exit the system 
            if ( !(checkIndicator(indicator1) && checkSortOrder(sortMode)) ) {
                System.exit(1);
            }
            
            if (args.length == 4) {
                indicator2 = args[3];
                
                checkIndicator(indicator2);
                
                //if indicator2 is not in the correct format then exit the system 
                if ( !(checkIndicator(indicator2)) ) {
                    System.exit(1);
                }
                
                isGraph = true;
            }
            
            loadCountries(fileDir);
            sortCountryList(leastToGreatest, indicator1);
            
            if(isGraph) {
                displayCountryGraph(indicator1);
            } else {
                displayTextCountries();
            } 
            
        }
         
    }
}