// Christin Scott
import java.util.Comparator;

/**
 * This class compares two vertices to determine which has the lowest path cost from the source vertex.
 * @author Christin Scott
 *
 */
public class VertexComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex arg0, Vertex arg1) {
		int pathCostArg0 = arg0.getPathCost();
		int pathCostArg1 = arg1.getPathCost();
		if (pathCostArg0 == pathCostArg1) {
			return 0;
		} else if (pathCostArg0 < pathCostArg1) {
			return -1;
		} else if (pathCostArg0 > pathCostArg1) {
			return 1;
		}
		return Integer.getInteger(null);
	}
}





