import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This data structure class is the representation of the graph
 * that represents a map with towns and roads connecting these towns. 
 * @author nanaa
 */
public class TownGraph implements GraphInterface<Town, Road> {

    /**
     * Towns in the graph
     */
    private Set<Town> towns = new HashSet<>();
    
    /**
     * Roads in the graph based on the towns
     */
    private Set<Road> roads = new HashSet<>();
    
    /**
     * List of shortest path from town A to town B
     */    
    private ArrayList<String> shortestPath = new ArrayList<>();
    
    private Town destination;
    
    private int finishTown;
    
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        Road road = null;
        for (Road r : roads) {
            if (r.contains(sourceVertex) && r.contains(destinationVertex)) {
                road = r;
           }
        }
        return road;
    }

    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, 
            int weight, String description) {
        
        if (sourceVertex == null || destinationVertex == null) {
            throw new NullPointerException();
        }
        
        if (!towns.contains(sourceVertex) || !towns.contains(destinationVertex)) { 
            throw new IllegalArgumentException();
        }
        
        Road road = new Road(sourceVertex, destinationVertex, weight, description);
        roads.add(road);
        
        return road;
    }

    @Override
    public boolean addVertex(Town v) {
        
        if (v == null) {
            throw new NullPointerException();
        }
        
        if (!towns.contains(v)) {
            towns.add(v);
            return true;
        }
        
        return false;
    }

    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        for (Road r : roads) {
            if (r.contains(sourceVertex) && r.contains(destinationVertex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsVertex(Town v) {
        return towns.contains(v);
    }

    @Override
    public Set<Road> edgeSet() {
        return roads;
    }

    @Override
    public Set<Road> edgesOf(Town vertex) {
        Set<Road> edges = new HashSet<>();
        for (Road r : roads) {
            if (r.contains(vertex)) {
                edges.add(r);
            }
        }
        return edges;
    }

    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, 
            int weight, String description) {
        
        if (sourceVertex == null || destinationVertex == null || description == null) {
            throw new NullPointerException();
        }
        
        if (!towns.contains(sourceVertex) || !towns.contains(destinationVertex)) { 
            throw new IllegalArgumentException();
        }
        
        Road road = null;
        for (Road r : roads) {
            if (r.contains(sourceVertex) && r.contains(destinationVertex) &&
                    r.getWeight() == weight && r.getName().equals(description)) {
                road = r;
            }
        }
        return roads.remove(road) ? road : null;
    }

    @Override
    public boolean removeVertex(Town v) {
        return towns.remove(v);
    }

    @Override
    public Set<Town> vertexSet() {
        return towns;
    }

    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        destination = destinationVertex;
        dijkstraShortestPath(sourceVertex);
        String shortPath = "";
        int totalMiles = 0;
        for (int idx = 0; idx < shortestPath.size() - 1; idx++) {
            Town source = new Town(shortestPath.get(idx));
            Town dest = new Town(shortestPath.get(idx + 1));
            Road road = getEdge(source, dest);
            totalMiles += road.getWeight();
            shortPath += source + " via " + road.getName() + " to " + dest 
                    + " " + road.getWeight() + " miles;";
        }
        shortestPath.clear();
        for (String step : shortPath.split(";")) {
            shortestPath.add(step);
        }
        shortestPath.add("Total miles: " + totalMiles + " miles");
        return shortestPath;
    }

    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        shortestPath.clear();
        Town[] rowsAndCols = new Town[towns.size()];
        int s = 0;
        for (Town t : towns) {
            rowsAndCols[s] = new Town(t);
            s++;
        }        
        int[][] adjacencyMatrix = new int[towns.size()][towns.size()];       
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (i == j || !containsEdge(rowsAndCols[i], rowsAndCols[j])) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    int weight = getEdge(rowsAndCols[i], rowsAndCols[j]).getWeight();
                    adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = weight;
                }
            }
        }
        
        int startTown = 0;
        for (Town t : rowsAndCols) {
            if (!t.equals(sourceVertex)) {
                startTown++;
            } else {
                break;
            }
        }
        
        finishTown = 0;
        for (Town t : rowsAndCols) {
            if (!t.equals(destination)) {
                finishTown++;
            } else {
                break;
            }
        }
        
        int numTowns = adjacencyMatrix[0].length; 
        
        int[] smallestWeights = new int[numTowns];
        
        boolean[] added = new boolean[numTowns];
        
        for (int townIdx = 0; townIdx < numTowns; townIdx++) {
            smallestWeights[townIdx] = Integer.MAX_VALUE;
            added[townIdx] = false;
        }
        
        smallestWeights[startTown] = 0;
        
        int[] parents = new int[numTowns];
        
        parents[startTown] = -1;
        
        for (int i = 1; i < numTowns; i++) {
            int nearestTown = -1;
            int smallestWeight = Integer.MAX_VALUE;
            for (int townIdx = 0; townIdx < numTowns; townIdx++) {
                if (!added[townIdx] && 
                        smallestWeights[townIdx] < smallestWeight) {
                    nearestTown = townIdx;
                    smallestWeight = smallestWeights[townIdx];
                }
            }
            added[nearestTown] = true;
            for (int townIdx = 0; townIdx < numTowns; townIdx++) {
                int roadDist = adjacencyMatrix[nearestTown][townIdx]; 
                if (roadDist > 0 && 
                        ((smallestWeight + roadDist) < smallestWeights[townIdx])) {
                    parents[townIdx] = nearestTown;
                    smallestWeights[townIdx] = smallestWeight + roadDist;
                }
            }           
        }
        populatePathArrayList(finishTown, parents); 
    }
    
    /**
     * Populate town with the order of towns to go from source to destination
     * @param currentVertex index of destination
     * @param parents indexes of towns in shortest path
     */
    private void populatePathArrayList(int currentVertex, int[] parents) {
        
        if (currentVertex == -1) { 
            return; 
        } 
        populatePathArrayList(parents[currentVertex], parents); 
        int townIdx = 0;
        for (Town t : towns) {
            if (townIdx == currentVertex) {
                shortestPath.add(t.getName()); 
            }
            townIdx++;
        }
    } 

}
