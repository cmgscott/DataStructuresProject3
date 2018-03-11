// Christin Scott
import java.util.*;
import java.io.*;

/**
 * Driver program that reads in a graph and prompts user for shortests paths in the graph.
 * (Intentionally without comments.  Read through the code to understand what it does.)
 */

/**
 * This class contains the main method for the program, along with the method to read text file inputs.
 * @author Christin Scott
 *
 */
public class FindPaths {

	public static int NUM_OF_VERTICES;

	public static int NUM_OF_EDGES;
	/**
	 * The main method for the program. Requires to command line text file name arguments to run, otherwise an error 
	 * message is printed and the program exits with error.
	 * @param args the command line arguments. Should be two text file names containing the vertices and edges of the
	 *             data.
	 */
	public static void main(String[] args) {
		//		if(args.length != 2) {
		//			System.err.println("USAGE: java Paths <vertex_file> <edge_file>");
		//			System.exit(1);
		//		}

		// only uncomment one at a time
		//		TestGraph.testDoubleWeightEdge(); // execution is halted
		//		TestGraph.testManyCopies(); // executes normally
		//		TestGraph.testMissingVertexEdge(); // execution is halted
		//		TestGraph.testUnconnectedGraph(); // execution is halted
		//		TestGraph.testEmptyVertexFile(); // execution is halted
		//		TestGraph.testEmptyEdgeFile(); // executes normally
		//		TestGraph.testLargeInput(); // executes normally

		// read the graph and construct a new MyGraph object
		MyGraph g = readGraph("vertex.txt","edge.txt");

		@SuppressWarnings("resource")
		// create scanner for user input
		Scanner console = new Scanner(System.in);
		// create collection of vertices and edges
		Collection<Vertex> v = g.vertices();
		Collection<Edge> e = g.edges();
		// print out vertices and edges for debugging
		System.out.println("Vertices are "+v);
		System.out.println("Edges are "+e);
		// loop to take user input and find the shortest path
		while(true) {
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

//			try {
				// writes results to text file
//				BufferedWriter br = new BufferedWriter(new FileWriter("results.txt", true));
//				br.write("Results");
//				br.newLine();
//				br.write("Source: " + a.getLabel() + " Destination: " + b.getLabel());
//				br.newLine();
//				for (int i = 0; i < path.vertices.size(); i++) {
//					br.write(path.vertices.get(i).getLabel());
//					if (i < path.vertices.size() - 1) {
//						br.write(" -> ");
//					}
//				}
//				br.newLine();
//				br.write("Path cost: " + path.cost);
//				br.newLine();
//				br.write("----------------------------------------------------");
//				br.newLine();
//
//				br.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}

		}
	}

	/**
	 * This method reads the files from the command line arguments and creates vertices and edges for each input in the
	 * file.
	 * @param f1 the vertex text file
	 * @param f2 the edge text file
	 * @return a MyGraph object representing these inputs
	 */
	public static MyGraph readGraph(String f1, String f2) {
		Scanner s = null;
		try {
			s = new Scanner(new File(f1));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f1);
			System.exit(2);
		}

		int count = 0;
		Collection<Vertex> v = new ArrayList<Vertex>();
		while(s.hasNext()) {
			v.add(new Vertex(s.next()));
			count++;
		}
		if (count == 0) {
			throw new IllegalArgumentException("No vertices in txt file");
		}
		NUM_OF_VERTICES = count;
		try {
			s = new Scanner(new File(f2));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f2);
			System.exit(2);
		}

		count = 0;
		Collection<Edge> e = new ArrayList<Edge>();
		/** variable to iterate through e **/
		while(s.hasNext()) {
			try {
				Vertex a = new Vertex(s.next());
				Vertex b = new Vertex(s.next());
				int w = s.nextInt();
				e.add(new Edge(a,b,w));
				count++;
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}
		if (count == 0) {
			System.out.println("No edges found. All queries will find no path.");
		}
		for (int i = 0; i < e.size(); i++) {
			Edge tempEdge = ((ArrayList<Edge>) e).get(i);
			boolean isDuplicate = false;
			e.remove(tempEdge);
			if (e.contains(tempEdge)) {
				System.out.println("Duplicate edge(s) detected. Higher weight edge ignored.");
				isDuplicate = true;
			}
			e.add(tempEdge);
			if (isDuplicate) {
				break;
			}
		}
		NUM_OF_EDGES = count;
		return new MyGraph(v,e);
	}
}
