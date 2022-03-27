import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class Maze { 
    //Number of rows in the maze.
    private static int numRows;
    
    //Number of columns in the maze.
    private static int numColumns;
    
    //Grid coordinates for the starting maze square
    private static int startRow;
    private static int startColumn;
    
    //Grid coordinates for the final maze square
    private static int finishRow;
    private static int finishColumn;
    
    //stack that contains the solution of the maze
    private static Stack<MazeSquare> solutionStack;
    
    //check if user want to print out solution
    private static boolean isPrintSolution;
    
    //list of square objects
    private static List<MazeSquare> squareList;
    
    /**
     * Constructor to create an empty maze with no squares.
     */
    public Maze() {
        squareList = new ArrayList<MazeSquare>(); 
        isPrintSolution = false;
    } 
    
    /**
     * Loads the maze that is written in the given fileName.
     *
     * @return true if the file can be loaded successfully, false otherwise
     */
    public boolean load(String fileName) { 
        Scanner scanner = null;
        try {
            //Open a scanner to read the file
            scanner = new Scanner(new File(fileName));
            numColumns = scanner.nextInt();
            numRows = scanner.nextInt();
            startColumn = scanner.nextInt();
            startRow = scanner.nextInt();
            finishColumn = scanner.nextInt();
            finishRow = scanner.nextInt();
            
            //Check if the start or finish squares are out of bounds
            if(!isInRange(startRow, 0, numRows) 
                    || !isInRange(startColumn, 0, numColumns)
                    || !isInRange(finishRow, 0, numRows) 
                    || !isInRange(finishColumn, 0, numColumns)) {
                System.err.println("Beginning and/or end square is not in maze.");
                scanner.close();
                return false;
            }
       
            
            int noLine = 0; //keep track of number of row
            
            //go through every line (every row) after the initial condition statements
            while(scanner.hasNext()) {
                String line = scanner.next(); 
                noLine++;
                
                //if the number of actual rows is more than number of indicated rows, return false
                if (noLine > numRows) {
                    System.out.println("the number of actual rows is greater than the number of indicated rows");
                    return false;
                }
                
                //if the number of actual columnn is not equal to number of indicated column, return false
                if (line.length() != numColumns){
                    System.out.println("the number of actual columnn is not equal to number of indicated column");
                    return false;
                }
                
                //iterates through each char (each column in the current row) to create each square
                for(int i = 0; i < line.length(); i++) {
                    char curr = line.charAt(i);
                    
                    //if its the first row and doesn't have the top wall, exit
                    if ((noLine == 1) && !(curr == '7' || curr == '_')) {
                        System.out.println("the top wall should only be discriptor '7' or '_' for your first row to have the border");
                        return false;
                    }
                    
                    //if any square in the last column doesn't have a right wall, exit
                    if ((i == line.length() - 1) && !(curr == '7' || curr == '|')) {
                        System.out.println("the right wall should only be discriptor '7' or '|' for your last column to have the border");
                        return false;
                    }
                    
                    
                    //if char is not a valid char, then return false
                    if(!MazeSquare.isAllowedCharacter(curr)) {
                        System.out.println(curr + " is not a valid discriptor");
                        return false;
                    }
                    
                    //creates and adds square object to squareList
                    MazeSquare square = new MazeSquare(curr, noLine - 1, i);
                    squareList.add(square);
                    
                }
                
            }
            
            //if the number of actual rows is less than number of indicated rows, return false
            if(noLine < numRows){
                System.out.println("the number of actual rows is less than number of indicated rows"); 
                return false;
            }
            
            
            
        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + fileName + ", was not found. Please enter a valid file name");
            return false;
        } catch(InputMismatchException e) {
            System.err.println("Maze file not formatted correctly. Please reformat correctly.");
            scanner.close();
            return false;
        } 
        return true;
    } 
    
    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound. 
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound â‰¤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }
    
    /**
     * Prints the maze with the start and finish squares marked.
     * Print out the solution with asterisk if the user wants to.
     * @param isSolution    state TRUE if the user wants to print out the solution.
     */
    public void print(boolean isSolution) {
        
        //if user wants to print out the solution, store the stack solution to the list without the finish and start square
        List<MazeSquare> list = new ArrayList<MazeSquare>();
        if(isSolution) {
            if (solutionStack.isEmpty()){
                System.out.println("this maze has no valid solution");
                System.exit(1);
            } else {
                while(!solutionStack.isEmpty()){
                    list.add(solutionStack.pop());
                }
                
                list.remove(0);
                list.remove(list.size() - 1);
                
                if(list.isEmpty()) {
                    System.out.println("the Start square is adjacent to the Finish square");
                }
                
            }
        }

        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //we are at the second row of characters.

                        boolean ifStar = false;
                        
                        //if the user want to print out the solution, print out * to solve the maze
                        if(isSolution && !list.isEmpty()){
                            for (MazeSquare temp : list) {
                                if(temp.getRow() == row && temp.getColumn() == col) {
                                    System.out.print("  *  ");
                                    ifStar = true;
                                }
                            }
                        }
                            
                        
                        //Check if we're in the start or finish state
                        if(startRow == row && startColumn == col) {
                            System.out.print("(¯▿¯)");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                        } else if (!ifStar) {
                            System.out.print("     ");
                            ifStar = false;
                        }
                        
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } 
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //print off the bottom of the maze
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     *  @ numColumns      the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * @return  a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * @return  a String representing the bottom of a square without a horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * @return    a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * @return    a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    
    /**
     * This method will return the square object at the specific index in the list
     * based on its column and row.
     * @param row    square's row index
     * @param col    square's col index
     *
     * @return       the square in the list
     */
    public MazeSquare getMazeSquare(int row, int col) {
        int indexOfSquare = row * numColumns + col;
        return squareList.get(indexOfSquare);
    }
    
    /**
     * Create a stack of MazeSquare object that store one solution of the maze.
     * If there is more than one solution, one particular solution will be stored
     *
     * @return a stack with solution containing MazeSquare objects with the series of squares
     * from the start square at the bottom of the stack to the finish
     * square on the top of the stack. An empty stack is
     * returned if there is no solution.
     */
    public Stack<MazeSquare> getSolution() {
        Stack<MazeSquare> stack = new MysteryStackImplementation<MazeSquare>();
        
        MazeSquare temp = this.getMazeSquare(startRow, startColumn);
        MazeSquare finishSquare = this.getMazeSquare(finishRow, finishColumn);
        
        //add the start square to top of the stack
        temp.changeToVisited();// change here
        stack.push(temp);
        
        while(!stack.isEmpty()){
            
            //if the top item is finish square, the maze has been solved.
            if (stack.peek().getRow() == finishSquare.getRow() &&  stack.peek().getColumn() == finishSquare.getColumn()) {
                return stack;
            }
            
            //if the current square has no right wall and is not in the last column in the maze and the square to the right is unvisited, add the right square to the stack and change it to visited
            if (!(stack.peek().hasRightWall()) && (stack.peek().getColumn() != numColumns - 1) 
                && !(getMazeSquare(stack.peek().getRow(), stack.peek().getColumn() + 1).getVisitStatus()) ) {
                
                temp = getMazeSquare(stack.peek().getRow(), stack.peek().getColumn() + 1);
                temp.changeToVisited();
                stack.push(temp);
            
            //if the current square has no top wall and is not in the first row in the maze and the square above it is unvisited, add the square above it to the stack and change it to visited
            } else if (!(stack.peek().hasTopWall()) && (stack.peek().getRow() != 0) 
                       && !(getMazeSquare(stack.peek().getRow() - 1, stack.peek().getColumn()).getVisitStatus()) ) {
                
                temp = getMazeSquare(stack.peek().getRow() - 1,stack.peek().getColumn());
                temp.changeToVisited();
                stack.push(temp);
            
            //if the current square is not in the first column in the maze and has no left wall and the square to the left is unvisited, add the square to the left to the stack and change it to visited
            } else if ((stack.peek().getColumn() != 0) && !(getMazeSquare(stack.peek().getRow(), stack.peek().getColumn() - 1).hasRightWall()) 
                       && !(getMazeSquare(stack.peek().getRow(), stack.peek().getColumn() - 1).getVisitStatus()) )  { 
                
                temp = getMazeSquare(stack.peek().getRow(), stack.peek().getColumn() - 1);
                temp.changeToVisited();
                stack.push(temp);
            
            //if the current square is not in the last row in the maze and has no wall below it and the square to below it is unvisited, add the square below it to the stack and change it to visited
            } else if ((stack.peek().getRow() != numRows - 1) && !(getMazeSquare(stack.peek().getRow() + 1, stack.peek().getColumn()).hasTopWall()) 
                       && !(getMazeSquare(stack.peek().getRow() + 1, stack.peek().getColumn()).getVisitStatus()) )  { 
                
                temp = getMazeSquare(stack.peek().getRow() + 1, stack.peek().getColumn());
                temp.changeToVisited();
                stack.push(temp);
            
            //otherwise remove the top square from the stack
            } else {
                temp = stack.pop();
            }        
        }
        //if the stack is empty return stack with no solution (unsolvable)
        return stack;
    }

    
 
    /**
     * Given a file containing descriptors for a maze, print out the maze
     * with or without the solution
     * 
     * @param args       command line arguments
     */ 
    public static void main(String[] args) { 
        
        if(args.length == 1 || args.length == 2) {
            Maze maze = new Maze();
                    
            if(!maze.load(args[0])) {
                System.out.println("File can not be loaded");
                System.exit(1);
            } else {
                
                //print out the solution if the user wants
                if (args.length == 2){
                    
                    //if argument line is not in the right format, exit
                    if ( args[1].compareTo("--solve") != 0 ) {
                        System.out.println("the arugment line have to follow the structure:");
                        System.out.println(" +, java Maze <mazeFile> IF YOU WANT TO PRINT OUT THE MAZE WITHOUT SOLUTION");
                        System.out.println(" +, java Maze <mazeFile> --solve IF YOU WANT TO PRINT OUT THE MAZE WITH SOLUTION");
                        System.exit(1);
                    }
                    isPrintSolution = true;
                    solutionStack = maze.getSolution();
                }
                maze.print(isPrintSolution);
            }
        
        //if argument line is not in the right format, exit
        } else {
            System.out.println("the arugment line have to follow the structure:");
            System.out.println(" +, java Maze <mazeFile> IF YOU WANT TO PRINT OUT THE MAZE WITHOUT SOLUTION");
            System.out.println(" +, java Maze <mazeFile> --solve IF YOU WANT TO PRINT OUT THE MAZE WITH SOLUTION");
            System.exit(1);
        }
    } 
}