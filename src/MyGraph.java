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
	ArrayList<Vertex> vertices;

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
		vertices = (ArrayList<Vertex>) v;
		edges = e;

		// list implementation of graph builder
		Vertex startingVertex = vertices.get(0);
		Vertex currentVertex = startingVertex;
		int count = 0;
		Collection<Vertex> visitedVertices = new ArrayList<Vertex>(vertices.size()); // large size req.
		// add all edges to collection for individual vertex
		while (count < vertices.size()) {
			currentVertex = vertices.get(count);
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
		for (int i = 0; i < vertices.size(); i++) {
			currentVertex = ((ArrayList<Vertex>) vertices).get(i);
			denseGraphMap.put(currentVertex.getLabel(), currentVertex);
		}
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
		VertexComparator comparer = new VertexComparator();
		ArrayList<Vertex> unknownVertices = new ArrayList<Vertex>();
		List<Vertex> knownVertices = new ArrayList<Vertex>();
		Vertex currentVertex = vertices.get(vertices.indexOf(a));
		Edge currentEdge = new Edge(currentVertex, currentVertex, 0);

		//		List<Path> paths = new ArrayList<Path>(vertices.size());
		List<Vertex> path = new LinkedList<Vertex>();
		int count = 0;
		currentVertex.setPathCost(0);

		for (int i = 0; i < vertices.size(); i++) {
			if (!vertices.get(i).equals(a)) {
				vertices.get(i).setPathCost(10000); // set path to large num to simulate infinte distance
				unknownVertices.add(vertices.get(i)); // add vertex to unknown vertices
			}
		}
		boolean quit = false;
		while (!unknownVertices.isEmpty()) { // while there are vertices w/ unknown distances
			if (currentVertex != null) {
				for (int i = 0; i < currentVertex.getEdges().size(); i++) { // iterate through all the vertex's edges
					currentEdge = ((ArrayList<Edge>) currentVertex.getEdges()).get(i);
					int tempPath = currentVertex.getPathCost() + currentEdge.getWeight(); // the current total path cost of the vertex + the cost to the destination vertex of the edge at index i
//					if (currentVertex.equals(a)) {
//						currentVertex.pathCost = 0;
//						tempPath = 0 + currentEdge.getWeight();
//				    }
					if (unknownVertices.indexOf(currentEdge.getDestination()) >= 0 && tempPath < unknownVertices.get(unknownVertices.indexOf(currentEdge.getDestination())).getPathCost()) { // if the temp path to the destination vertex is less than it's current path cost (initially infinity)
						if (!currentEdge.getDestination().equals(a)) { // don't want to circle back
							System.out.println("the temp path from " + currentVertex.getLabel() + " to " + currentEdge.getDestination() + " is: " + tempPath);
							int indexOfDest = unknownVertices.indexOf(currentEdge.getDestination()); // index of the destination vertex
							if (indexOfDest >= 0) { // is -1 if destination is a known vertex
							unknownVertices.get(indexOfDest).pathCost = tempPath; // set new path cost to destination
							unknownVertices.get(indexOfDest).lastVertex = currentVertex; // set the previous vertex in the destination vertex
							} 
						}
					}
				}
			}
			unknownVertices.sort(comparer); // sorts the array so that the least path is at index 0
//			if (b.equals(currentVertex)) { // if the destination becomes a known vertex, it's path is certain
//				break; // so the loop can end
//			}
			unknownVertices.remove(currentVertex); // the vertex is no longer unknown
			knownVertices.add(currentVertex);
			if (!unknownVertices.isEmpty())
			currentVertex = unknownVertices.get(0); // next lowest path cost unknown vertex
		}

		currentVertex = knownVertices.get(knownVertices.indexOf(b));
		while (currentVertex != null) { // iterate through last vertices until null (or until starting node is reached)
			path.add(currentVertex);
			currentVertex = currentVertex.lastVertex;
		}
		return new Path(path, knownVertices.get(knownVertices.indexOf(b)).pathCost);

	}
}
