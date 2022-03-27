import java.util.*; //import stuff
import java.io.*; //import stuff

public class CountryComparator implements Comparator<Country>{ //implements Comparator
    String indicator; //Declare string to indicate what is being compared
    boolean sort; //Declare boolean variable to indicate big/small order

    public CountryComparator(String indicator, boolean sort){ //Declare class for Comparator
        this.indicator = indicator; //establish indicator
        this.sort = sort; //establish sorting method with boolean value
    }

    public int compare(Country country1, Country country2){ //take in parameters to run method
        if (sort == false) { //case 1 if false
            if (country1.getIndicator(indicator) < country2.getIndicator(indicator)) {
              return -1; 
            }
            else if (country1.getIndicator(indicator) > country2.getIndicator(indicator)) {
              return 1; 
            }
            else if (Double.isNaN(country1.getIndicator(indicator)) || Double.isNaN(country2.getIndicator(indicator))){
                if (Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                    return country1.getName().compareTo(country2.getName());
                }
                if (Double.isNaN(country1.getIndicator(indicator)) && !Double.isNaN(country2.getIndicator(indicator))){
                    return 1;
                }
                if (!Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                    return -1;
                }
            }
            else {
                  if (country1.getName().compareTo(country2.getName()) < 0){
                      return -1; //country 1 before country 2
                  } else if (country1.getName().compareTo(country2.getName()) > 0) {
                      return 1; //country 1 after country 2
                  } else {
                      return 0; //country 1 equals country 2
                  }
            }
        }
        else { //case 2 if true
            if (country1.getIndicator(indicator) > country2.getIndicator(indicator)) {
                return -1;
            }
            else if (country1.getIndicator(indicator) < country2.getIndicator(indicator)) {
                return 1;
            }
            else {
                if (Double.isNaN(country1.getIndicator(indicator)) || Double.isNaN(country2.getIndicator(indicator))){
                    if (Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                        return country1.getName().compareTo(country2.getName());
                    }
                    if (Double.isNaN(country1.getIndicator(indicator)) && !Double.isNaN(country2.getIndicator(indicator))){
                        return 1;
                    }
                    if (!Double.isNaN(country1.getIndicator(indicator)) && Double.isNaN(country2.getIndicator(indicator))){
                        return -1;
                    }
                }
          }
              if (country1.getName().compareTo(country2.getName()) < 0){
                    return -1;
              } else if (country1.getName().compareTo(country2.getName()) > 0) {
                    return 1;
              }
          }
        return 0;
        }
}