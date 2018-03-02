import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
	// you will need some private fields to represent the graph
	// you are also likely to want some private helper methods

	// YOUR CODE HERE
	/** value of infinity **/
	public static final int INFINITY = Integer.MAX_VALUE;
	/** the collection of vertices **/
	Collection<Vertex> vertices;
	
	/** the collection of edges **/
	Collection<Edge> edges;
	
	

	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {

		// YOUR CODE HERE
		vertices = v;
		edges = e;
		
		// list implementation of graph builder
		Vertex startingVertex = ((ArrayList<Vertex>) v).get(0);
		Vertex currentVertex = startingVertex;
		int count = 0;
		Collection<Vertex> visitedVertices = new ArrayList<Vertex>(v.size()); // large size req.
		// add all edges to collection for individual vertex
		while (count < v.size()) {
			currentVertex = ((ArrayList<Vertex>) v).get(count);
			for (int i = 0; i < e.size(); i++) {
				if (((ArrayList<Edge>) e).get(i).getSource().getLabel().equals(currentVertex.getLabel())) {
					currentVertex.addEdge(((ArrayList<Edge>) e).get(i));
					visitedVertices.add(currentVertex);
				}
			}
			count++;
		}
		
		// matrix implementation of graph builder
		Map<String, Vertex> denseGraphMap = new HashMap<String, Vertex>();
		for (int i = 0; i < v.size(); i++) {
			currentVertex = ((ArrayList<Vertex>) v).get(i);
			denseGraphMap.put(currentVertex.getLabel(), currentVertex);
		}
//		int[][] adMatrix = new int[v.size()][v.size()];
//		// initialize matrix with logical infinities;
//		for (int i = 0; i < v.size()-1; i++) {
//			for (int j = 0; j < v.size()-1; j++) {
//				adMatrix[i][j] = 0;
//			}
//		}
//		// add symmetry
//		for (int i = 0; i < v.size()-1; i++) {
//			adMatrix[i][i] = INFINITY;
//		}
//		
//		// add edges
//		for (int i = 0; i < e.size(); i++) {
//			if (v.contains(((ArrayList<Edge>) e).get(i).getSource())) {
//				int indOfSource = ((ArrayList<Vertex>) v).indexOf(((ArrayList<Edge>)e).get(i).getSource());
//				int indOfDest = ((ArrayList<Vertex>) v).indexOf(((ArrayList<Edge>)e).get(i).getDestination());
//				adMatrix[indOfSource][indOfDest] = 1;
//			}
//		}
//		
//		HashMap<Vertex, int[]> graph = new HashMap<Vertex, int[]>();
//		for (int i = 0; i < v.size(); i++) {
//			graph.put(((ArrayList<Vertex>) v).get(i), adMatrix[i]);
//		}
		// print matrix for debugging
//		for (int i = 0; i < v.size()-1; i++) {
//			for (int j = 0; j < v.size()-1; j++) {
//				System.out.print(adMatrix[i][j]);
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
		// should check that the arguments make sense and throw an appropriate exception otherwise.
		
		// edges should involve only vertices with labels that are in the vertices of the graph (no edge from or to a 
		// vertex labeled A if there is no vertex with label A)
		
		// edge weights are not negative
		
		// do not throw an exception if the collection of vertices has repeats in it
		// ignore the second one encountered as redundant information
		
		// throw an exception if the collection of edges has the same directed edge more than once with a different 
		// weight
		
		// do not throw an exception if an edge appears redundantly with the same weight ignore the redundant edge 
		// information

	}

	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {

		// YOUR CODE HERE
		return vertices;

	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {

		// YOUR CODE HERE
		return edges;

	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {

		// YOUR CODE HERE
		Collection<Edge> edges = v.getEdges();
		Collection<Vertex> returnVertices = new ArrayList<Vertex>();
		for (int i = 0; i < edges.size(); i++) {
			returnVertices.add(((ArrayList<Edge>) edges).get(i).getDestination());
		}
		return returnVertices;

	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex a, Vertex b) {

		// YOUR CODE HERE
		int returnValue = -1;
		ArrayList<Edge> edges = (ArrayList<Edge>) a.getEdges();
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getDestination().equals(b)) {
				returnValue = edges.get(i).getWeight();
			}
		}
		return returnValue;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {

		// YOUR CODE HERE (you might comment this out this method while doing
		// Part 1)
		Vertex startingNode = a;
		
		return null;

	}
}
