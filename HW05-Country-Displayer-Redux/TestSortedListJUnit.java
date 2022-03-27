import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Comparator;

/**
 * Tests for an implementation of a SortedList.
 */
public class TestSortedListJUnit {
    /** 
     * Creates a list of 19 countries to use in testing.
     */
    public static List<Country> makeTestCountryList() {
        List<Country> countries = new ArrayList<Country>();
        Random rand = new Random();
       
        for(int i = 0; i < 20; i++) {
            int randValue = rand.nextInt(50);
            if (i == 0) {
                randValue = 20-i;//keep this the same to have an identical country
            }
            countries.add(new Country(new String[]{Integer.toString(i), Integer.toString(i), Integer.toString(i), 
                Integer.toString(randValue),Integer.toString(100-i),Integer.toString(7),
                    Integer.toString(7),Integer.toString(7),Integer.toString(7),Integer.toString(7)}));
        }
        return countries;
    }
    /**
     * Checks if a list is sorted. Relies on the implementations
     * of size and get working properly.
     */
    public static boolean isSorted(Comparator<Country> comparator, SortedList<Country> list) {
        boolean sorted = true;
        for(int i = 0; i < list.size()-1; i++) {
            Country cur = list.get(i);
            Country next = list.get(i+1);
            if(comparator.compare(cur, next) > 0) {
                //cur comes before next but comparator says it should be strictly after
                return false;
            }
        }
        return sorted;
    }


    /**
     * Tests for add and contains.
     */
     @Test
    public void containsAndAddTest() {
        List<Country> countries = makeTestCountryList();
        List<Country> identicalCountries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        
        assertFalse("Contains should always return false when called on an empty", list.contains(countries.get(0)));
        list.add(countries.get(0));
        assertTrue("Contains should return true when called using a country that was just added", list.contains(countries.get(0)));
        assertTrue("Contains should return true when called using a country that is .equals to a country that was just added", list.contains(identicalCountries.get(0)));
    }

    /**
     * Tests for the add and remove methods. Also 
     * does some testing of size.
     */
     @Test
    public void removeAddTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        assertFalse("Removing from an empty list should return false", list.remove(countries.get(0)));
        
        list.add(countries.get(0));
        assertTrue("Removing the item that was just added should return true", list.remove(countries.get(0)));
        assertEquals("After all items are removed, list size should be 0", 0, list.size());

        list.add(countries.get(0));
        list.add(countries.get(0));
        list.add(countries.get(0));
        assertEquals("After three of the same item have been added, size should be 3", 3, list.size());
        list.remove(countries.get(0));
        assertEquals("After removing an item, only one of the copies should be removed", 2, list.size());
        list.remove(countries.get(0));
        list.remove(countries.get(0));
        assertEquals("After all items are removed, list size should be 0", 0, list.size());


        //Add multiple things, make sure they're sorted.
        list.add(countries.get(2));
        list.add(countries.get(1));
        list.add(countries.get(0));
        assertTrue("After multiple adds, list should be sorted",
        isSorted(co2Comparator, list));
        
        //Some remove checks.
        List<Integer> orderToRemove  = Arrays.asList(0, 1, 2);
        for(int i = 0; i < orderToRemove.size(); i++) {
            assertTrue("Removing an item that is present should return true",
                list.remove(countries.get(orderToRemove.get(i))));
            assertEquals("After removing, list should have one fewer item",
                2, list.size());
            assertTrue("After removing an item, list should still be sorted",
                isSorted(co2Comparator,list)); 
            list.add(countries.get(orderToRemove.get(i)));
        }
        
    }

    /**
     * Test the remove method that takes the index
     * to remove as a parameter.
     */
    @Test
    public void removeAtPositionTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        
        list.add(countries.get(0));
        Country removedCountry = list.remove(0);
        assertEquals("Return value for return at position should be the removed country", countries.get(0), removedCountry);
        assertEquals("After removing single item in list, size should be 0", 0, list.size());

        //removing something in the middle and seeing if things to the right slide over
        list.add(countries.get(2));
        list.add(countries.get(1));
        list.add(countries.get(0));

        removedCountry = list.remove(1);
        assertEquals("Return value for return at position should be the removed country", countries.get(1), removedCountry);
        assertEquals("After removing, list should have one fewer item",
                2, list.size());
        assertTrue("After removing an item, list should still be sorted",
                isSorted(co2Comparator,list)); 
        removedCountry = list.remove(1);
        assertEquals("Return value for return at position should be the removed country", countries.get(2), removedCountry);
        assertEquals("After removing, list should have one fewer item",
                1, list.size());
        assertTrue("After removing an item, list should still be sorted",
                isSorted(co2Comparator,list));
        
        
        removedCountry = list.remove(0);
        assertEquals("Return value for return at position should be the removed country", countries.get(0), removedCountry);
        assertEquals("After removing, list should have one fewer item",
                0, list.size());                
    }

    /**
     * Test edge cases for remove at a position.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds1() {
        List<Country> countries = makeTestCountryList();
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        list.remove(0);
    }

        /**
     * Test edge cases for remove at a position.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds2() {
        List<Country> countries = makeTestCountryList();
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        list.remove(-1);
    }


    /**
     * Test the clear method (also relies on size and add).
     */
     @Test
    public void clearTest() {
        List<Country> countries = makeTestCountryList();
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        list.add(countries.get(0));
        list.add(countries.get(1));
        assertEquals("Size should return the number of items in the list", 2, list.size());
        list.clear();
        assertEquals("Size should be zero after clear", 0, list.size());
        list.add(countries.get(2));
        assertEquals("Size should update properly even after clear", 1, list.size());
    }

    /**
     * Test the toArray method.
     */
    @Test
    public void toArrayTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        for(int i = 0; i < countries.size(); i++) {
            list.add(countries.get(i));
        }
        list.remove(countries.get(0));
        Object[] array = list.toArray();
        assertEquals("Array should be exactly the length of the number of items", countries.size() - 1,
                     array.length);
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue("Ordering of the array from toArray should be sorted", co2Comparator.compare((Country) array[i], (Country) array[i+1]) <= 0);
        }
    }

    /**
     * Tests isEmpty to make sure a newly initialized list isEmpty and that after adding,
     * list is not empty. Also does some testing of the relationship between clear and isEmpty.
     */
    @Test
    public void isEmptyTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        assertTrue("Newly created list should be empty", list.isEmpty());
        
        list.add(countries.get(0));
        assertFalse("List after add should not be empty", list.isEmpty());
                
        list.clear();
        assertTrue("Just cleared list should be empty", list.isEmpty());

        list.add(countries.get(0));
        assertFalse("List after add should not be empty", list.isEmpty());
    }
   
    /**
     * Tests that the size method always returns one larger value after an add.
     */
    @Test
    public void sizeTest() {
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
        assertEquals("Newly created list should have size = 0", 0, list.size());

        for(int i=0; i<500; i++) {
            list.add(new Country(new String[]{Integer.toString(i), Integer.toString(i), 
                Integer.toString(20-i),Integer.toString(100-i),Integer.toString(7),
                    Integer.toString(7),Integer.toString(7),Integer.toString(7),Integer.toString(7)}));  
            assertEquals("Adding should always increase size by 1", i+1, list.size());
  
        }
    }

    /**
     *Tests add. Some reliance on get and size to check if the list is sorted
     * when adding.
     */
    @Test
    public void addTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
                
        for(int i = countries.size()-1; i >= 0; i--) {
            Country country = countries.get(i);
            list.add(country);
            assertTrue("List should be sorted after adding an item", isSorted(co2Comparator,list));

        }
    }


    /**
     * Tests the resort method. Note that this just checks for correctness in sorted order.
     * It does not check that you aren't making new objects (although you shouldn't do so and the
     * graders will be checking this).
     */
    @Test
    public void resortTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);
                
        for(int i = countries.size()-1; i >= 0; i--) {
            Country country = countries.get(i);
            list.add(country);
        }

        CountryComparator co2ComparatorGL = new CountryComparator("CO2Emissions", true);//greatest to least
        list.resort(co2ComparatorGL);
        assertTrue("List should be sorted after re-sorting", isSorted(co2ComparatorGL,list));

        CountryComparator electricityComparator = new CountryComparator("AccessToElectricity", true);//greatest to least
        list.resort(electricityComparator);
        assertTrue("List should be sorted after re-sorting", isSorted(electricityComparator,list));
                
        
    }

    /**
     * Tests the getPosition method. Some reliance on add.
     */
    @Test
    public void getPositionTest() {
        List<Country> countries = makeTestCountryList();
        
        CountryComparator co2Comparator = new CountryComparator("CO2Emissions", false);//Least to greatest
        SortedList<Country> list = new SortedLinkedList(co2Comparator);

        assertEquals("getPosition for any object on an empty list should be -1", -1,
                     list.getPosition(countries.get(0)));

        list.add(countries.get(0));
        list.add(countries.get(0));
        assertEquals("getPosition should return first position when duplicate item is present", 0,
                     list.getPosition(countries.get(0)));
        list = new SortedLinkedList(co2Comparator);

        for(int i = countries.size()-1; i >= 0; i--) {
            Country country = countries.get(i);
            list.add(country);
        }
        assertTrue("After adding many countries, list should be sorted", isSorted(co2Comparator, list));
        for (int i = 0; i < countries.size(); i++) {
            assertEquals("getPosition should return index in list", i,
                     list.getPosition(countries.get(i)));
        }
              
    }
}