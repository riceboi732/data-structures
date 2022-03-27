import java.util.*;
import java.io.*;

public class SortedLinkedList implements SortedList<Country> {
    private Node head;
    private Comparator<Country> comparator;
    private int size;

    private class Node{
        private Country country;
        private Node next;
        private Node(Country country){
          this(country, null);
        }
        private Node(Country country, Node nextNode){
            this.country = country;
            this.next = nextNode;
            size += 1;
        }
        private Country getCountry(){
            return country;
        }
    }
  /*
  Constructor for SortedLinkedList
  */
  public SortedLinkedList(Comparator<Country> countryComparator){
      comparator = countryComparator;
      head = null;
      size = 0;
  }
  /*
  Sets the size instance variable
  */
  public void setSize(int newSize){
      size = newSize;
  }
  /*
  gets head of SortedLinkedList
  */
  public Node getHead(){
      return head;
  }
  /*
  Sets comparator instance variable of SortedLinkedList
  */
  public void setComparator(Comparator<Country> newComparator){
      this.comparator = newComparator;
  }
  /**
   * Adds item to the list in sorted order.
   */
   public void add(Country country){
      // makes new node to be inserted
      Node newNode = new Node(country);
      // checks head node
      if (head == null)
      {
         head = newNode;
      }
      else if (comparator.compare(head.getCountry(), newNode.getCountry()) == 1){
          newNode.next = head;
          head = newNode;
      }
      // otherwise, it iterates until the correct position where node should be inserted
      else {
        Node curNode;
        curNode = head;
        // while the node to be inserted is still greater than the node being compared to, set the curNode to the next node
        while (curNode.next != null && comparator.compare(curNode.next.getCountry(), newNode.getCountry()) == -1){
              curNode = curNode.next;
        }
        // once it's in the correct position, make the new one reference the node after curNode and make curNode reference node.
        newNode.next = curNode.next;
        curNode.next = newNode;
      }
    }
  /**
   * Remove targetItem from the list, shifting everything after it up
   * one position. targetItem is considered to be in the list if
   * an item that is equal to it (using .equals) is in the list.
   * (This convention for something being in the list should be
   * followed throughout.)
   * @return true if the item was in the list, false otherwise
   */
  public boolean remove(Country targetCountry){
      Node tempNode = null;
      //checks if list is empty
      if (size == 0){
          return false;
      }
      //checks head
      else if (head.getCountry().getName().equals(targetCountry.getName())){
          Node newHead = null;
          newHead = head.next;
          head.next = null;
          head = newHead;
          this.setSize(this.size() - 1);
          return true;
      }
      //checks rest of list
      else {
          Node curNode = head;
          while (curNode.next != null) {
              if (!curNode.getCountry().getName().equals(targetCountry.getName())) {
                  tempNode = curNode;
              }
              if (curNode.getCountry().getName().equals(targetCountry.getName())) {
                  tempNode.next = curNode.next;
                  curNode.next = null;
                  this.setSize(this.size() - 1);
                  return true;
              }
              curNode = curNode.next;
          }
      }
      return false;
}
  /**
   * Remove the item at index position from the list, shifting everything
   *  after it up one position.
   * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
   */
   public Country remove(int position){
       Node tempNode = null;
       //checks if position is out of bounds
       if (position < 0 || position > size){
           throw new IndexOutOfBoundsException("Your desired position was out of bounds! Try position > 0 or position <= size!");
       }
       //goes to correct position using count
       Node curNode = head;
       int count = 0;
       while (count != position) {
           tempNode = curNode;
           curNode = curNode.next;
           count++;
       }
       //checks head
       if (position == 0) {
           head = curNode.next;
           curNode.next = null;
           this.setSize(this.size() - 1);
           return curNode.getCountry();

       } else {
           tempNode.next = curNode.next;
           curNode.next = null;
           this.setSize(this.size() - 1);
           return curNode.getCountry();
       }
   }
  /**
   * Returns the position of targetItem in the list.
   * @return the position of the item, or -1 if targetItem is not in the list
   */
  public int getPosition(Country targetCountry){
      int count = 1;
      Node curNode = head;
      Node tempNode = null;
      //increments count until it finds country. Returns -1 if it doesn't find it.
      while (!curNode.getCountry().getName().equals(targetCountry.getName())) {
          tempNode = curNode;
          curNode = curNode.next;
          count++;
          if (count == size && !curNode.getCountry().getName().equals(targetCountry.getName())) {
              return -1;
          }
      }
      return count - 1;
  }
  /**
   * Returns the item at a given index.
   * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
   */
  public Country get(int position){
      int count = 0;
      Node curNode = head;
      Node tempNode = null;
      //checks if position is out of bounds
      if (position < 0 || position > size) {
          throw new IndexOutOfBoundsException("Your desired position was out of bounds! Try position > 0 or position <= size!");
      }
      //increments count until item is found
      while (count != position) {
          tempNode = curNode;
          curNode = curNode.next;
          count++;
      }
      return curNode.getCountry();
  }

  /** Returns true if the list contains the target item. */
  public boolean contains(Country targetCountry) {
      int count = 1;
      Node curNode = head;
      //checks head
      if (curNode.getCountry().getName().equals(targetCountry.getName())) {
          return true;
      }
      //checks rest  of list
      while (!curNode.getCountry().getName().equals(targetCountry.getName())) {

          if (!curNode.getCountry().getName().equals(targetCountry.getName()) && count == size) {
              return false;
          }
          curNode = curNode.next;
          if (curNode.getCountry().getName().equals(targetCountry.getName())) {
              return true;
          }
          count++;
      }
      return false;
  }
  /** Re-sorts the list according to the given comparator.
   * All future insertions should add in the order specified
   * by this comparator.
   */
   // this almost works properly but has a few bugs
   public void resort(Comparator<Country> comparator){
    this.setComparator(comparator);

    int count = this.size();
    if (size == 0) {
        System.out.println("Cannot resort an empty list!");
        System.exit(1);
    }
    else if (size == 1){
        System.exit(1);
    }
    else {
        Node tempNode = null;
        Node curNode = head;
        for (int i = 0; i < count; i++) {
            if(curNode.next != null && comparator.compare(curNode.getCountry(), curNode.next.getCountry()) == 1){
                tempNode = curNode;
                curNode = curNode.next;
                this.remove(i);
                this.add(tempNode.getCountry());
                i = i - 1;
            }
            else{
                if(curNode.next == null){
                    break;
                }
                curNode = curNode.next;
            }
        }
    }
}

  /** Returns the length of the list: the number of items stored in it. */
  public int size(){
      return size;
  }
  /** Returns true if the list has no items stored in it. */
  public boolean isEmpty(){
      if (size == 0){
        return true;
      }
      return false;
  }
  /** Returns an array version of the list.  Note that, for technical reasons,
   * the type of the items contained in the list can't be communicated
   * properly to the caller, so an array of Objects gets returned.
   * @return an array of length length(), with the same items in it as are
   *         stored in the list, in the same order.
   */
  public Object[] toArray(){
      Country countryList[] = new Country[size];
      Node curNode = head;
      int count = 0;
      //checks if list is empty
      if (size == 0) {
          return countryList;

      }
      //otherwise adds each country to an array
      else {
            while (count != size) {
                countryList[count] = curNode.getCountry();
                curNode = curNode.next;
                count++;
            }
      }
      return countryList;
  }
  /** Returns an iterator that begins just before index 0 in this list. */
  public Iterator<Country> iterator(){
      throw new UnsupportedOperationException("iterator() not implemented!");
  }

  /** Removes all items from the list. */
  public void clear(){
      //sets head to null and size to 0.
      head = null;
      size = 0;
  }

}