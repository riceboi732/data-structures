import java.lang.*;
import java.util.*;

public class FlashcardPriorityQueue implements PriorityQueue<Flashcard>{ //implementing the PQ

    private Flashcard[] queue; //create a queue to store flashcards
    private int index; //create a index to increment and store
    
    public FlashcardPriorityQueue(){
        this.queue  = new Flashcard[50]; //set initial length for the flashcard queue, will increase later if more space is needed
        this.index = 0;
    }

    public void add(Flashcard item){ //add flashcards to queue
        if(index == 0){ //"base case" if the flashcard is the first index in the queue
            this.queue[0] = item; //set queue index to the flashcard item
            this.index += 1; //increment the index to continue inserting flashcards
        }

        else{ //all other cases where it is not the first flashcard
            int current = this.index + 1; //continue incrementation
            int parent = current / 5; //one parent has at most five children with priority from left to right
            this.queue[current] = item; //set current equal to the flashcard item

            while(current != 1 && queue[current].compareTo(this.queue[parent]) < 0){ //min heap implmentation, when child index < parent index, switch to make sure min heap.

                Flashcard temp1 = queue[parent]; //store parent index in a temp
                queue[parent] = queue[current]; //set parent equal to current index that it it being compared to
                queue[current] = temp1; //set current index to the temp (what used to be the parent) index
                current = parent; //set current as parent
                parent = current / 5; //make sure every parent has at most 5 children

            }
            this.index ++; //increment index
        }

        if(this.index == queue.length - 1){ //handling "out-of-space" problem, double size to accomondate more cards
            Flashcard[] temp2 = new Flashcard[queue.length * 2]; //create a temp queue that is double in size
            for(int i = 0; i < this.index + 1; i++){
                temp2[i] = this.queue[i]; //import all previous entries into temp
            }
            this.queue = temp2; //set the new queue to the temp, creating a queue that is double in size with all the previous entries still in priority order
        }

    }

   public Flashcard poll(){ //polls the first item determined 
    if(!isEmpty()){ //if queue is not empty
        //swap the last Flashcard to the head
        Flashcard returnCard = this.queue[1]; //set the card to be polled to be the first index
        this.queue[0] = null; //set (delete) first index to null
        this.queue[0] = this.queue[index]; //set the new first index to the first index in the new queue
        this.queue[index] = null; //delete the old index, creating a new queue that is one index shorter with the second index in the first place
        index--; //decrement index

        return returnCard; //returns the first index of the queue
      }
      else{
          throw new NoSuchElementException("Queue is empty"); //catching empty queues
      }
    }

  public Flashcard peek(){
      if(isEmpty()){
          throw new NoSuchElementException("Queue is empty"); //catching empty queues
      }
      else{
          return this.queue[1]; //returning the first index
      }
  }

    public boolean isEmpty(){//if the first index of the queue is null it is empty
        if(this.queue[1] == null){
            return true;
        }
        else{
            return false;
        }
    }

    public void clear() {
    }