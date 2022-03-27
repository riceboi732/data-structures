import java.util.*;
import java.io.*;

/**
 * CountryDisplayer.java
 * This class sorts countries and prints out the result in the form of a text or a graph
 */

public class CountryDisplayer {

    // instance variables of CountryDisplayer
    private String filePath; //User inputted file to read
    private String indicator; //indicator used to sort the countries
    private boolean isGreatestToLeast; // check the way we sort
    private SortedLinkedList list;

    //constructor that takes in 3 parameters
    public CountryDisplayer(String filePath, String indicator, boolean isGreatestToLeast){
        this.filePath = filePath;
        this.indicator = indicator;
        this.isGreatestToLeast = isGreatestToLeast;
        this.list = null;
    }

    public void loadCountries(String filePath){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String[] countryArray;
        Country country;

        //scans each line, makes country, and adds  to list
        while(textScan.hasNextLine()){
            line = textScan.nextLine();
            countryArray = line.split(",");
            country = new Country(countryArray);
            this.list.add(country);
        }
    }
    /**
     * Prints a text version of the countries (listed in the same format as in HW4)
     */
    public void displayCountriesAsText(){
      for (int i = 0; i < this.list.size(); i++) {
            System.out.println(this.list.get(i).getIndicator("name")  + "," +
            this.list.get(i).getIndicator("CO2Emissions") + "," + "," +
            this.list.get(i).getIndicator("AccessToElectricity") + "," +
            this.list.get(i).getIndicator("RenewableEnergy") + "," +
            this.list.get(i).getIndicator("ProtectedAreas") + "," +
            this.list.get(i).getIndicator("PopulationGrowth") + "," +
            this.list.get(i).getIndicator("PopulationTotal") + "," +
            this.list.get(i).getIndicator("UrbanPopulationGrowth"));
      }
    }

    /**
     * Displays a graph with the top 10 countries (based on the sorting criteria)
     * and a second series showing the additional indicator.
     * @param secondaryIndicator indicator to show as the second series in the graph
     */
    public void displayGraph(String secondaryIndicator){
      //
    }
    /**
     * Changes the criteria for sorting
     * @param indicator indicator to use for sorting the countries Valid values are: CO2Emissions,
     *AccessToElectricity, RenewableEnergy, ProtectedAreas,
     *PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth
     * @param isGreatestToLeast whether the countries should be sorted from greatest to least
     */
    public void changeSortingCriteria(String indicator, boolean isGreatestToLeast){
        this.indicator = indicator;
        this.isGreatestToLeast = isGreatestToLeast;
        Comparator<Country> newComparator = new CountryComparator(indicator, isGreatestToLeast);
        this.list.resort(newComparator);
    }
    /**
     * Adds the given country to the data.
     * @param country country to add
     */
    public void addCountry(Country country){
        this.list.add(country);
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
    public static void main(String[] args) {
        //default information
        String filePath = "";
        String indicator = "CO2Emissions";
        boolean isGreatestToLeast = true;;
        String option = ""; // one of five options that the user picks
        String input = ""; // user's input after picking an option
        //boolean exit = false; // indicates whether the program should exit the switch case

        Scanner scanner = null; //makes scanner

        //if user provides command line argument for filePath
        try{
          filePath = args[0];
          scanner = new Scanner(System.in);
          CountryDisplayer displayer = new CountryDisplayer(filePath, indicator, isGreatestToLeast);
          Comparator<Country> comparator = new CountryComparator(indicator, true);
          displayer.list = new SortedLinkedList(comparator);

          displayer.loadCountries(filePath);

          while(!option.equals("exit")){
              System.out.println("What would you like to do? The options: 'set sorting criteria', 'add country', display text', 'display graph', and 'exit'.");
              option = scanner.nextLine();
              switch(option){
                  case "set sorting criteria":
                      boolean validIndicator = false;
                      while(validIndicator == false){
                          System.out.println("What indicator do you want to sort by?");
                          input = scanner.nextLine();
                          if (checkIndicator(input)){
                              validIndicator = true;
                              indicator = input;
                          }
                      }
                      boolean validIsGreatestToLeast = false;
                      while(validIsGreatestToLeast == false){
                          System.out.println("What sorting order do you want to sort by? Enter 'true' for least to greatest and 'false' for greatest to least");
                          input = scanner.nextLine();
                          if (input.equals("true")){
                              validIsGreatestToLeast = true;
                              isGreatestToLeast = true;
                          }
                          if (input.equals("false")){
                              validIsGreatestToLeast = true;
                              isGreatestToLeast = false;
                          }
                      }
                      displayer.changeSortingCriteria(indicator, isGreatestToLeast);
                      break;
                  case "add country":
                      boolean validCountryInput = false;
                      Country newCountry = null;
                      while(validCountryInput == false){
                          System.out.println("Type in the information for the country with each indicator separated by a comma."
                            +  "If there is no data for one of the indicators, type 'NaN'. Here's the format you must use:"
                           + "Country Name,CO2 emissions (metric tons per capita)"
                           + "Access to electricity (% of population),Renewable energy consumption (% of total final energy consumption),"
                           + "Terrestrial protected areas (% of total land area),Population growth (annual %),Population (total),Urban population growth (annual %)");
                           input = scanner.nextLine();
                           try{
                              String[] countryArray = input.split(",");
                              newCountry = new Country(countryArray);
                              validCountryInput = true;
                              displayer.addCountry(newCountry);
                           }
                           catch(Exception e){
                              System.out.println("Input was not valid. Check format and try again.");
                           }
                      }
                      break;
                  case "display text":
                        displayer.displayCountriesAsText();

                      break;
                  case "display graph":
                      String secondaryIndicator = "";
                      boolean validSecondaryIndicator = false;
                      while(validSecondaryIndicator == false){
                          System.out.println("What do you want as the secondary indicator?");
                          input = scanner.nextLine();
                          if (checkIndicator(input)){
                              validSecondaryIndicator = true;
                              secondaryIndicator = input;
                          }
                      }
                      displayer.displayGraph(secondaryIndicator);
                      break;
                  case "exit":
                      System.out.println("You have exited the CountryDisplayer.");
                      break;
              }
          }
        }
          //if user does not provide command line argument
          catch(ArrayIndexOutOfBoundsException f){
              scanner = new Scanner(System.in);
              CountryDisplayer displayer = new CountryDisplayer(filePath, indicator, isGreatestToLeast);

              while(!option.equals("exit")){
                  System.out.println("What would you like to do? The options: 'set sorting criteria', 'add country', display text', 'display graph', and 'exit'.");
                  option = scanner.nextLine();
                  switch(option){
                      case "set sorting criteria":
                          boolean validIndicator = false;
                          while(validIndicator == false){
                              System.out.println("What indicator do you want to sort by?");
                              input = scanner.nextLine();
                              if (checkIndicator(input)){
                                  validIndicator = true;
                                  indicator = input;
                              }
                          }
                          boolean validIsGreatestToLeast = false;
                          while(validIsGreatestToLeast == false){
                              System.out.println("What sorting order do you want to sort by? Enter 'true' for least to greatest and 'false' for greatest to least");
                              input = scanner.nextLine();
                              if (input.equals("true")){
                                  validIsGreatestToLeast = true;
                                  isGreatestToLeast = true;
                              }
                              if (input.equals("false")){
                                  validIsGreatestToLeast = true;
                                  isGreatestToLeast = false;
                              }
                          }
                          displayer.changeSortingCriteria(indicator, isGreatestToLeast);
                          break;
                      case "add country":
                          boolean validCountryInput = false;
                          Country newCountry = null;
                          while(validCountryInput == false){
                              System.out.println("Type in the information for the country with each indicator separated by a comma."
                                +  "If there is no data for one of the indicators, type 'NaN'. Here's the format you must use:"
                               + "Country Name,CO2 emissions (metric tons per capita)"
                               + "Access to electricity (% of population),Renewable energy consumption (% of total final energy consumption),"
                               + "Terrestrial protected areas (% of total land area),Population growth (annual %),Population (total),Urban population growth (annual %)");
                               input = scanner.nextLine();
                               try{
                                  String[] countryArray = input.split(",");
                                  newCountry = new Country(countryArray);
                                  validCountryInput = true;
                                  displayer.addCountry(newCountry);
                               }
                               catch(Exception e){
                                  System.out.println("Input was not valid. Check format and try again.");
                               }
                          }
                          break;
                      case "display text":
                          System.out.println("List is empty.");
                          break;
                      case "display graph":
                          String secondaryIndicator = "";
                          boolean validSecondaryIndicator = false;
                          while(validSecondaryIndicator == false){
                              System.out.println("What do you want as the secondary indicator?");
                              input = scanner.nextLine();
                              if (checkIndicator(input)){
                                  validSecondaryIndicator = true;
                                  secondaryIndicator = input;
                              }
                          }
                          displayer.displayGraph(secondaryIndicator);
                          break;
                      case "exit":
                          System.out.println("You have exited the CountryDisplayer.");
                          break;
                  }
              }
        }
    }
}