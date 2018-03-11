// Christin Scott
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class TestGraph {

	public static void testMissingVertexEdge() {

		// read the graph and construct a new MyGraph object
		MyGraph g = FindPaths.readGraph("MissingVertexVertex.txt","MissingVertexEdge.txt");
		codeFromMain(g, "Testing for an edge that is missing its source or destination vertex");
	}

	public static void testDoubleWeightEdge() {

		// read the graph and construct a new MyGraph object
		MyGraph g = FindPaths.readGraph("DoubleWeightVertex.txt","DoubleWeightEdge.txt");
		codeFromMain(g, "Testing for double edge with different weights");
	}

	public static void testManyCopies() {

		// read the graph and construct a new MyGraph object
		MyGraph g = FindPaths.readGraph("ManyCopiesVertex.txt","ManyCopiesEdge.txt");
		codeFromMain(g, "Testing many copies of the same vertices or edges");
	}

	public static void testUnconnectedGraph() {

		// read the graph and construct a new MyGraph object
		MyGraph g = FindPaths.readGraph("UnconnectedVertex.txt","UnconnectedEdge.txt");
		codeFromMain(g, "Testing an unconnected graph");
	}
	
	public static void testEmptyVertexFile() {
		MyGraph g = FindPaths.readGraph("noVertexVertex.txt", "noVertexEdge.txt");
		codeFromMain(g, "Testing an empty vertex.txt");
	}
	
	public static void testEmptyEdgeFile() {
		MyGraph g = FindPaths.readGraph("noEdgeVertex.txt", "noEdgeEdge.txt");
		codeFromMain(g, "Testing an empty edge.txt");
	}
	
	public static void testLargeInput() {
		MyGraph g = FindPaths.readGraph("LargeVertex.txt", "LargeEdge.txt");
		codeFromMain(g, "Testing large inputs");
	}

	private static void codeFromMain(MyGraph g, String initTestMethod) {
		@SuppressWarnings("resource")
		// create scanner for user input
		Scanner console = new Scanner(System.in);
		// create collection of vertices and edges
		Collection<Vertex> v = new ArrayList<Vertex>(FindPaths.NUM_OF_VERTICES);
		v = g.vertices();
		Collection<Edge> e = new ArrayList<Edge>(FindPaths.NUM_OF_EDGES);
		e = g.edges();
		// print out vertices and edges for debugging
		System.out.println(initTestMethod);
//		System.out.println("Vertices are "+v);
//		System.out.println("Edges are "+e);
		// loop to take user input and find the shortest path
		System.out.print("Start vertex? ");
		Vertex a = new Vertex(console.nextLine());
		if(!v.contains(a)) {
			System.out.println("no such vertex");
			System.exit(0);
		}

		System.out.print("Destination vertex? ");
		Vertex b = new Vertex(console.nextLine());
		if(!v.contains(b)) {
			System.out.println("no such vertex");
			System.exit(1);
		}

		try {
		Path path = g.shortestPath(a, b);
		System.out.println("*--------------------------------*");
		System.out.println("Results\nSource: " + a.getLabel() + " Destination: " + b.getLabel());
		for (int i = 0; i < path.vertices.size(); i++) {
			System.out.print(path.vertices.get(i).getLabel());
			if (i < path.vertices.size() - 1) {
				System.out.print(" -> ");
			}
		}
		System.out.println("\nPath cost: " + path.cost);
		System.out.println("*--------------------------------*"); 
		} catch (NullPointerException e2) {
			
			System.out.println("No path found");
			System.out.println("*--------------------------------*");
	}
		

	}
}
