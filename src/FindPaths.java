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
			
			// YOUR CODE HERE: call shortestPath and print
			// out the result
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

		Collection<Vertex> v = new ArrayList<Vertex>();
		while(s.hasNext())
			v.add(new Vertex(s.next()));

		try {
			s = new Scanner(new File(f2));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f2);
			System.exit(2);
		}

		Collection<Edge> e = new ArrayList<Edge>();
		/** variable to iterate through e **/
		int index = 0;
		while(s.hasNext()) {
			try {
				Vertex a = new Vertex(s.next());
				Vertex b = new Vertex(s.next());
				int w = s.nextInt();
				e.add(new Edge(a,b,w));
//				for (int i = 0; i < v.size(); i++) {
//					if (((ArrayList<Vertex>) v).get(i).equals(a)) {
//						(((ArrayList<Vertex>) v).get(i)).addEdge(((ArrayList<Edge>) e).get(index));
//					}
//				}
				index++;
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}

		return new MyGraph(v,e);
	}
}
