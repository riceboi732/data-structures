import java.io.*;
import java.util.*;
import java.util.Iterator;

public class PathFinder { 
    
    private List<String> nameArticles; //store the name of articles with index is vertex int
    private UnweightedGraph graph; //store the graph that contains articles with its links
    private String startRandom; //store random start vertex
    private String endRandom; //store random end vertex
    private String interRandom; //store the random intermediate vertex
    
    /**
     * Constructs a PathFinder that represents the graph with nodes (vertices) specified as in
     * nodeFile and edges specified as in edgeFile.
     * @param nodeFile name of the file with the node names
     * @param edgeFile name of the file with the edge names
     */
    public PathFinder(String nodeFile, String edgeFile){
        
        nameArticles = new ArrayList<String>(); 
        graph = new MysteryUnweightedGraphImplementation();
        Scanner scanner = null;
        
        //open nodeFile and store every single article as a vertex in graph
        try {
            //scan nodeFile
            scanner = new Scanner(new File(nodeFile));
            
            String line = null;
            
            if(scanner.hasNextLine()){
                line = scanner.nextLine();
            } else{
                System.out.println("the file " + nodeFile + " is empty");
                System.exit(1);
            }
                
            //skip the comment part
            while (!line.equals("")){
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else{
                    System.out.println("the file " + nodeFile + " is not in the correct format");
                    System.exit(1);
                }
            }
            
            //add article to both list nameArticle and graph
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                
                nameArticles.add(line);
                graph.addVertex();
            } 
            
            if(nameArticles.isEmpty()){
                System.out.println("there are no articles in the file: " + nodeFile);
                System.exit(1);
            }
            
            scanner.close();
    
        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + nodeFile + ", was not found.");
            System.exit(1);
        }
        
        //open edgeFile and link articles in graph
        try {
            //scan edgeFile
            scanner = new Scanner(new File(edgeFile));
            String line = null;
            
            if(scanner.hasNextLine()){
                line = scanner.nextLine();
            } else{
                System.out.println("the file " + edgeFile + " is empty");
                System.exit(1);
            }
                
            //skip the comment part
            while (!line.equals("")){
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else{
                    System.out.println("the file " + edgeFile + " is not in the correct format");
                    System.exit(1);
                }
            }
            
            //add links to articles
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                
                Scanner lineScan = null;
                try {
    
                    lineScan = new Scanner(line).useDelimiter("\t");
                    
                    String begin = lineScan.next();
                    String end = lineScan.next();
                    
                    int beginInt = 0;
                    int endInt = 0;
                    
                    //get the vertex int of articles that we try to link
                    for (String each: nameArticles) {
                        if (begin.equals(each)) {
                            beginInt = nameArticles.indexOf(each);
                        }
                        
                        if (end.equals(each)) {
                            endInt = nameArticles.indexOf(each);
                        } 
                    }
                    
                    graph.addEdge(beginInt, endInt);
                    
                } catch (Exception e) {
                    System.err.println(e);
                    System.exit(1);
                }
                
            } 
            
            if(nameArticles.isEmpty()){
                System.out.println("there are no articles in the file: " + edgeFile);
                System.exit(1);
            }
    
        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + edgeFile + ", was not found.");
            System.exit(1);
        }
        
        //get the random start, intermediate, and end vertices
        this.startRandom = nameArticles.get((int)(Math.random() * ((nameArticles.size() - 1) + 1)));
        this.endRandom = nameArticles.get((int)(Math.random() * ((nameArticles.size() - 1) + 1)));
        this.interRandom = nameArticles.get((int)(Math.random() * ((nameArticles.size() - 1) + 1)));
    }
    
    /**
     * Returns the length of the shortest path from node1 to node2. If no path exists,
     * returns -1. If the two nodes are the same, the path length is 0.
     * @param node1 name of the starting article node
     * @param node2 name of the ending article node
     * @return length of shortest path
     */
    public int getShortestPathLength(String node1, String node2){
        
        List<String> shortestPath = this.getShortestPath(node1, node2);
        
        return shortestPath.size() - 1;
    }

    /**
     * Returns a shortest path from node1 to node2, represented as list that has node1 at
     * position 0, node2 in the final position, and the names of each node on the path
     * (in order) in between. If the two nodes are the same, then the "path" is just a
     * single node. If no path exists, returns an empty list.
     * @param node1 name of the starting article node
     * @param node2 name of the ending article node
     * @return list of the names of nodes on the shortest path
     */
    public List<String> getShortestPath(String node1, String node2){
        
        //returned ArrayList of strings
        List<String> finalString = new ArrayList<String>();
        
        //stores nodes that have been visited while going through graph for breadthFirst
        List<Integer> visitedNode = new ArrayList<Integer>();
    
        //pretend to be the fake Queue for breadthFirst
        List<Integer> fakeQueue = new ArrayList<Integer>();
        
        //List of preceeded node of each visited node to keep track of the path
        //For each element(node) in visitedNode, we can access the preceeded node 
        //which has that element as a neighbor by accessing the same index in prenode.
        //for example, if we have a path graph of 1 -> 3 -> 5 ->6. 
        //We will have visitedNode containing "1, 3, 5, 6", and 1 is node1, 6 is node2.
        //preNode will contain "-2, 1, 3, 5". "-2" in preNode is the "fake preceeded node" for node1.
        List<Integer> preNode = new ArrayList<Integer>();
        
        //return a path with one node when two nodes are the same
        if( node1.equals(node2)){
            finalString.add(node1);
            
            return finalString;
        }
        
        //vertex int of node1 and node2
        int node1Int = nameArticles.indexOf(node1);
        int node2Int = nameArticles.indexOf(node2);
        
        
        //This is similar to the Breadth First Traversal Algorithm
    
        visitedNode.add(node1Int);
        fakeQueue.add(node1Int);
        
        //add the fake preNode for node 1
        preNode.add(-2);
    
        while (!fakeQueue.isEmpty()) {
            int thisNode = fakeQueue.remove(0);
            
            //get the list of neighbors for thisNode
            Iterable<Integer> listOfNeighbor = graph.getNeighbors(thisNode);
            
            
            for(int eachNeighbor: listOfNeighbor) {
                
                //this is the value to determine if we add the neighbor
                //into visitedNode or not.
                boolean isAdd = true;
                
                //if neighbor is already visited, then we will not add
                for (int each : visitedNode){
                    
                    if (each == eachNeighbor){
                        isAdd = false;
                        break;
                    }
                }
                
                //if this neighbor not has yet visited, then add to the list
                if(isAdd){
                    
                    visitedNode.add(eachNeighbor);
                    fakeQueue.add(eachNeighbor);
                    preNode.add(thisNode);
                    
                    //when we find out our endNode, start putting the shortest path to finalString
                    if (eachNeighbor == node2Int){
                        
                        finalString.add(nameArticles.get(eachNeighbor));
                        
                        //go to the preceeded node that leads to this neighbor (tracing back the path)
                        int preNodeInt = preNode.get(visitedNode.indexOf(eachNeighbor));
                        
                        //continue to add path to finalString until preNode is -2
                        //recall that -2 is fake preNode value of Node1
                        while (preNodeInt != -2){
                            
                            finalString.add(0,nameArticles.get(preNodeInt));
                            
                            //it's just the for loop to repeat tracing back the path to find preceeded node
                            for (int each : visitedNode){
                                
                                if (each == preNodeInt){
                                    preNodeInt = preNode.get(visitedNode.indexOf(each));
                                    
                                    break;
                                }
                            }

                        }
            
                        return finalString;
                    }
                }
            }           
        }
        
    return finalString;
    }

    /**
     * Returns a shortest path from node1 to node2 that includes the node intermediateNode.
     * This may not be the absolute shortest path between node1 and node2, but should be 
     * a shortest path given the constraint that intermediateNodeAppears in the path. If all
     * three nodes are the same, the "path" is just a single node.  If no path exists, returns
     * an empty list.
     * @param node1 name of the starting article node
     * @param node2 name of the ending article node
     * @return list that has node1 at position 0, node2 in the final position, and the names of each node 
     *      on the path (in order) in between.
     */
    public List<String> getShortestPath(String node1, String intermediateNode, String node2){
        
        // get the shortest path from node1 to intermediateNode
        List<String> path1 = this.getShortestPath(node1,intermediateNode);
        
        //there is no path from node1 to intermediateNode to 
        //node2 if there is no path from node1 to intermediateNode.
        if(path1.isEmpty()){
            return path1;
        }
        
        //remove intermediateNode to avoid duplicating in path2 below
        path1.remove(path1.size()-1);
        
        // get the shortest path from intermediateNode to node2
        List<String> path2 = this.getShortestPath(intermediateNode,node2);
        
        //there is no path from node1 to intermediateNode to 
        //node2 if there is no path from intermediateNode to node2.
        if(path2.isEmpty()){
            return path2;
        }
        
        //combine 2 paths together
        for(String each: path2){
            path1.add(each);
        }
        
        return path1;
    }

    /**
     * check for the right format oof command line arguments.
     * print out the result of shortest path for website with or without intermediate node.
     * 
     * @param args       command line arguments
     */ 
    public static void main(String[] args) {
        
        if(args.length == 2 || args.length == 3) {
            
            PathFinder obj = new PathFinder(args[0],args[1]);

            //Store the URL decoder of vertices
            String startRandomPrint = null;
            String endRandomPrint = null;
            String interRandomPrint = null;
            
            //decode URL
            try{
                startRandomPrint = java.net.URLDecoder.decode(obj.startRandom, "UTF-8");
                endRandomPrint = java.net.URLDecoder.decode(obj.endRandom, "UTF-8");
                
            } catch (Exception e) {
                System.out.println("URL decoder error");
                System.exit(1);
            }
            
            //if there is no intermediateNode
            if(args.length == 2){
                List<String> simplePath = obj.getShortestPath(obj.startRandom, obj.endRandom);

                //if there is no path
                if(simplePath.isEmpty()){
                    System.out.println("There is no path from " + startRandomPrint 
                                       + " to " + endRandomPrint + ", and length is -1.");
                } else{
                    System.out.print("path from " + startRandomPrint 
                                       + " to " + endRandomPrint);
                    int pathLength = obj.getShortestPathLength(obj.startRandom, obj.endRandom);
                    System.out.println(" has the length of " + pathLength + ".");
                    
                    System.out.print("articles route: ");
                        
                    for(int i = 0; i <simplePath.size(); i++){
                        
                        System.out.print(simplePath.get(i));
                        
                        if (i!= simplePath.size() - 1 ){
                            System.out.print(" --> ");
                        }

                    }
                    System.out.println("");
                }
                    
            //case with intermediate node
            } else {
                
                //if args[2] is not in the right format
                if(!args[2].equals("useIntermediateNode")){
                    System.out.println("you have to use the right format for command line arguments:");
                    System.out.println("please use: java PathFinder [vertexFile] [edgeFile]");
                    System.out.println("or use: java PathFinder [vertexFile] [edgeFile] useIntermediateNode");
                    System.exit(1);
                } else {
                    
                    List<String> complexPath = obj.getShortestPath(obj.startRandom, obj.interRandom, obj.endRandom);

                    //decode URL
                    try{
                        interRandomPrint = java.net.URLDecoder.decode(obj.interRandom, "UTF-8");
                    } catch (Exception e) {
                    System.out.println("URL decoder error");
                    System.exit(1);
                    }

                    //if there is no path
                    if(complexPath.isEmpty()){
                        System.out.println("There is no path from " + startRandomPrint 
                                           + " through " + interRandomPrint + " to " + endRandomPrint + ", and length is -1.");

                    } else {
                        System.out.print("path from " + startRandomPrint
                                           + " through " + interRandomPrint + " to " + endRandomPrint);

                        int pathLength = complexPath.size() - 1;

                        System.out.println(" has the length of " + pathLength + ".");

                        System.out.print("articles route: ");

                        for(int i = 0; i <complexPath.size(); i++){

                            System.out.print(complexPath.get(i));

                            if (i!= complexPath.size() - 1 ){
                                System.out.print(" --> ");
                            }
                        }
                        System.out.println("");
                    }
                }
            }
            
        //args is not in the right format
        } else {
            System.out.println("you have to use the right format for command line arguments:");
            System.out.println("please use: java PathFinder [vertexFile] [edgeFile]");
            System.out.println("or use: java PathFinder [vertexFile] [edgeFile] useIntermediateNode");
            System.exit(1);
        }
    }
    
}
