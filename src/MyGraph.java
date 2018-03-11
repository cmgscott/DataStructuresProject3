// Christin Scott
import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {

	/** value of infinity **/
	private static final int INFINITY = Integer.MAX_VALUE;
	/** the collection of vertices **/
	ArrayList<Vertex> vertices;

	/** the collection of edges **/
	ArrayList<Edge> edges;
	
	/** adacency matrix **/
	int[][] adjacencyMatrix;



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

		if (v == null || e == null) {
			throw new IllegalArgumentException("null");
		}
		
		vertices = (ArrayList<Vertex>) v;
		edges = (ArrayList<Edge>) e;

		// list implementation of graph builder
		Vertex startingVertex = vertices.get(0);
		Vertex currentVertex = startingVertex;
		int count = 0;
		Collection<Vertex> unknownVertices = new ArrayList<Vertex>(vertices.size());
		// add all edges to collection for individual vertex
		while (count < vertices.size()) {
			currentVertex = vertices.get(count);
			for (int i = 0; i < edges.size(); i++) {
				if (!vertices.contains(edges.get(i).getSource()) 
						|| !vertices.contains(edges.get(i).getDestination())) {
					throw new IllegalArgumentException("Edge contains vertex not in list of vertices");
				}
				if (edges.get(i).getWeight() < 0) {
					throw new IllegalArgumentException("Weight of path is negative");
				}
				if (edges.get(i).getSource().getLabel().equals(currentVertex.getLabel())) {
//					if (!visitedVertices.contains(currentVertex)) {
					currentVertex.addEdge(((ArrayList<Edge>) e).get(i));
					unknownVertices.add(currentVertex);
//					}
				}
			}
			count++;
		}
		
		// matrix implementation of graph builder
		adjacencyMatrix = new int[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				adjacencyMatrix[i][j] = INFINITY;
			}
		}
		
		for (int i = 0; i < vertices.size(); i++) {
			ArrayList<Edge> theEdges = (ArrayList<Edge>) vertices.get(i).getEdges();
			for (int j = 0; j < theEdges.size(); j++) {
			int row = vertices.indexOf(theEdges.get(j).getSource());
			int column = vertices.indexOf(theEdges.get(j).getDestination());
			adjacencyMatrix[row][column] = theEdges.get(j).getWeight();
			}
		}

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

		return vertices;

	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {

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

		/** comparator object to compare path costs of two vertices **/
		VertexComparator comparer = new VertexComparator();
		/** list of unknown vertices **/
		List<Vertex> unknownVertices = new ArrayList<Vertex>();
		/** list of known vertices. the intersection of known and unknown should be disjoint **/
		List<Vertex> knownVertices = new ArrayList<Vertex>();
		/** the current vertex being analyzed **/
		Vertex currentVertex = vertices.get(vertices.indexOf(a));
		/** current edge of vertex being evaluated **/
		Edge currentEdge = new Edge(currentVertex, currentVertex, 0);
		/** list of vertices on path to be passed to Path constructor **/
		List<Vertex> path = new LinkedList<Vertex>();

		// see initial vertex path to 0
		currentVertex.setPathCost(0);

		// loop to set all other vertices to virtual infinity
		// adds vertex to list of unknown vertices
		for (int i = 0; i < vertices.size(); i++) {
			if (!vertices.get(i).equals(a)) {
				vertices.get(i).setPathCost(INFINITY);
				unknownVertices.add(vertices.get(i));
			}
		}
		while (!unknownVertices.isEmpty()) { // while there are vertices w/ unknown distances
			if (currentVertex != null) {
				// iterate through all the vertex's edges
				for (int i = 0; i < currentVertex.getEdges().size(); i++) { 
					currentEdge = ((ArrayList<Edge>) currentVertex.getEdges()).get(i);
					// the current total path cost of the vertex + the cost to the 
					// destination vertex of the edge at index i
					int tempPath = currentVertex.getPathCost() + currentEdge.getWeight(); 
					// if the temp path to the destination vertex is less than it's current 
					// path cost (initially infinity)
					if (unknownVertices.indexOf(currentEdge.getDestination()) >= 0 
							&& tempPath < unknownVertices.get(
									unknownVertices.indexOf(currentEdge.getDestination())).getPathCost()) { 
						if (!currentEdge.getDestination().equals(a)) { // don't want to circle back
							// index of the destination vertex
							int indexOfDest = unknownVertices.indexOf(currentEdge.getDestination()); 
							// is -1 if destination is a known vertex
							if (indexOfDest >= 0) { 
								// set new path cost to destination
								unknownVertices.get(indexOfDest).pathCost = tempPath; 
								// set the previous vertex in the destination vertex
								unknownVertices.get(indexOfDest).lastVertex = currentVertex; 
							} 
						}
					}
				}
			}
			unknownVertices.sort(comparer);
			// the vertex is no longer unknown
			unknownVertices.remove(currentVertex); 
			// the vertex is known
			knownVertices.add(currentVertex);
			// sets next vertex to lowest path cost from source as long as there are vertices to be evaluated
			if (!unknownVertices.isEmpty())
				currentVertex = unknownVertices.get(0); 
		}

		currentVertex = knownVertices.get(knownVertices.indexOf(b));
		// iterate through last vertices until null (or until starting node is reached)
		while (currentVertex != null) { 
			path.add(currentVertex);
			currentVertex = currentVertex.lastVertex;
		}
		Collections.reverse(path);
		if (knownVertices.get(knownVertices.indexOf(b)).pathCost + 100000 < 0) {
//			throw new IllegalArgumentException("Vertices are unconnected");
			return null;
		}
		resetVertices();
		return new Path(path, knownVertices.get(knownVertices.indexOf(b)).pathCost);

	}

	private void resetVertices() {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).setLastVertex(vertices.get(i));
		}
		
	}
}
