public class Graph {

	private final int n;
	public final GraphNode[] nodes;
	
	public Graph(int size) {
		n = size;
		nodes = new GraphNode[n];
		for (int i=0; i<n; i++){
			nodes[i]= new GraphNode(i);
		}
	}
	
	public GraphEdge addEdge(int from, int to) throws IllegalArgumentException{
		if (from >=n || to >=n) {
			throw new IllegalArgumentException();
		}
		GraphEdge edge = new GraphEdge(nodes[from],nodes[to]);
		nodes[from].addEdge(edge);
		return edge;
	}
	
	public GraphNode getNode(int i) {
		return nodes[i];
	}
	
	public int nodeCount() {
		return n;
	}
	
}



