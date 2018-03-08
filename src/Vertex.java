import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * Representation of a graph vertex
 */
public class Vertex {
	// label attached to this vertex
	private String label;
	
	private Collection<Edge> edges;
	
	public int pathCost;
	
	public Vertex lastVertex;

	/**
	 * Construct a new vertex
	 * 
	 * @param label
	 *            the label attached to this vertex
	 */
	public Vertex(String label) {
		if (label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
		// initialize edges list
		edges = new ArrayList<Edge>();
		pathCost = Integer.MAX_VALUE;
	}

	/**
	 * Get a vertex label
	 * 
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * A string representation of this object
	 * 
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}
	
	public void addEdge(Edge theEdge) {
		edges.add(theEdge);
	}
	
	public Collection<Edge> getEdges() {
		return edges == null ? new ArrayList<Edge>() : edges;
	}

	// auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	
	public void setPathCost(int cost) {
		pathCost = cost;
	}
	
	public int getPathCost() {
		return pathCost;
	}
	
	public void setLastVertex(Vertex theVertex) {
		lastVertex = theVertex;
	}
	
	public Vertex getLastVertex() {
		return lastVertex;
	}

	// auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
			return other.label == null;
		} else {
			return label.equals(other.label);
		}
	}

}
