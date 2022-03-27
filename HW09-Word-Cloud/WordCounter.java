import java.util.*;
import java.io.*;
import java.lang.*; 
import java.util.Map.Entry;

public class WordCounter{
public static void main(String[] args) throws FileNotFoundException {
String text = args[0];
int counter = 0;
File f = new File(text); //wutheringheights text file
File g = new File("StopWords.txt");//stopwords text file
Scanner scan1 = new Scanner(f);
Scanner scan2 = new Scanner(g);
ArrayList<String> b = new ArrayList<String>();//all words
ArrayList<String> a = new ArrayList<String>();//all stop words

while (scan1.hasNext()){
    b.add(scan1.next().replaceAll("\\p{Punct}","").toLowerCase());

}
scan1.close();

while (scan2.hasNext()){
    a.add(scan2.next());
}
scan2.close();
// Gets rid of the stop.txt words
// each word in the text file is iterated through and compared to every single stop word.
for (int j = 0; j < a.size(); j++) {
  for (int k = 0; k < b.size(); k++) {
    if( b.get(k).equals(a.get(j))){
        b.remove(k); //remove stop word
    }
  }
}

HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
        for (int i = 0; i < b.size(); i++) {
            String key = b.get(i);
            int freq = freqMap.getOrDefault(key, 0);
            freqMap.put(key, ++freq);
        }

      Map<Integer, String> map = sortByValues(freqMap); 
      Set set = freqMap.entrySet();
      Iterator iterator = set.iterator();
      while(iterator.hasNext()) {
           Map.Entry me = (Map.Entry)iterator.next();
           System.out.print(me.getKey() + ": ");
           System.out.println(me.getValue());
      }

}

private static HashMap sortByValues(HashMap map) { 
       List list = new LinkedList(map.entrySet());
       // Defined Custom Comparator here
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
       });

       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
       return sortedHashMap;
  }

}