public class MazeSquare {
    //Wall variables
    private boolean hasTopWall = false;
    private boolean hasRightWall = false;
    
    //check the visit status of this square while solving the maze
    private boolean isVisited = false;
    
    //Location of this square in a larger maze.
    private int row;
    private int col;
		
    /**
     * Constructs a new maze square with walls as configured by
     * descriptor (7 = top and right, | is just right, _ is just
     * top, and * is neither top nor right) and located at the
     * given row and column.
     */
    public MazeSquare(char descriptor, int row, int col) {
        this.row = row;
        this.col = col;
        if(descriptor == '7') {
            hasTopWall = true;
            hasRightWall = true;
        } else if(descriptor == '|') {
            hasTopWall = false;
            hasRightWall = true;
        } else if(descriptor == '_') {
            hasTopWall = true;
            hasRightWall = false;
        } else if(descriptor == '*') {
            hasTopWall = false;
            hasRightWall = false;
        } else {
            hasTopWall = false;
            hasRightWall = false;
            System.err.println("Unrecognized character for MazeSquare description: " + descriptor);		
        }
    }
		
    /**
     * Returns true if this square has a top wall.
     */
    public boolean hasTopWall() {
        return hasTopWall;
    }
		
    /**
     * Returns true if this square has a right wall.
     */
    public boolean hasRightWall() {
        return hasRightWall;
    }
		
    /**
     * Returns the row this square is in.
     */
    public int getRow() {
        return row;
    }
		
    /**
     * Returns the column this square is in.
     */
    public int getColumn() {
        return col;
    }
    
    /**
     * Changes the square to visited.
     */
    public void changeToVisited() {
        isVisited = true;
    }
    
    /**
     * get the visit status
     */
    public boolean getVisitStatus() {
        return isVisited;
    }
    
    
    /**
     * Returns true if c is a valid identifier for a maze square
     */
    public static boolean isAllowedCharacter(char c) {
        return c=='*' || c=='_' || c=='|' || c=='7';
    }
		
} 