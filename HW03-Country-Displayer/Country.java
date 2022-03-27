/**
 * Country.java
 * This class store each country as an object with its values,
 * this comes with several methods to get and change the values.
 */

public class Country {
    private String name; //name of the country
    private double CO2; //CO2Emissions
    private double electricity; //AccessToelectricity
    private double energy; //RenewableEnergy
    private double area; //ProtectedAreas
    private double populationG; //PopulationGrowth
    private double populationT; //PopulationTotal
    private double urbanG; //UrbanPopulationGrowth
    
    /**
     * This is the constructor to create a country object with its values
     * @param name        name of the country
     * @param CO2         CO2Emissions
     * @param electricity        AccessToElectricity
     * @param energy        RenewableEnergy
     * @param area        ProtectedAreas
     * @param populationG        PopulationGrowth
     * @param populationT        PopulationTotal
     * @param urbanG         UrbanPopulationGrowth
     */
    public Country (String name, double CO2, double electricity , double energy, double area, double populationG, double populationT, double urbanG) {
        
        this.name = name;
        this.CO2 = CO2;
        this.electricity  = electricity ;
        this.energy = energy;
        this.area = area;
        this.populationG = populationG;
        this.populationT = populationT;
        this.urbanG = urbanG;
    }

    /**
	 * This method get the name
	 * @return the name of the country
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the value of the indicator
     * @param indicator   the indicator's name
     * @return            the value of that indicator
     */
    public double getIndicator(String indicator){
        switch (indicator) { 
        case "CO2Emissions": 
            return this.CO2;
        case "AccessToElectricity": 
            return this.elec;
        case "RenewableEnergy": 
            return this.energy; 
        case "ProtectedAreas": 
            return this.area; 
        case "PopulationGrowth": 
            return this.populationG;
        case "PopulationTotal": 
            return this.populationT; 
        //case "UrbanPopulationGrowth":
        default:
            return this.urbanG;
        }
    }
    
    /**
	 * This method changes the value of the indicator
     * @param indicator   the indicator being changed
	 * @param no          the new value
     */
    public void changeIndicator(String indicator, Double no){
        switch (indicator) { 
        case "CO2Emissions": 
            this.CO2 = no;
            break; 
        case "AccessToElectricity": 
            this.elec = no;
            break; 
        case "RenewableEnergy": 
            this.energy = no;
            break; 
        case "ProtectedAreas": 
            this.area = no;
            break; 
        case "PopulationGrowth": 
            this.populationG = no;
            break; 
        case "PopulationTotal": 
            this.populationT = no;
            break; 
        case "UrbanPopulationGrowth":
            this.urbanG = no;
            break;
        default: 
            System.out.println("please input the right indicator format");
            break; 
        }
    }
    
    
}