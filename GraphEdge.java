public class GraphEdge {
	
	private final GraphNode start;
	private final GraphNode end;
	
	public GraphEdge(GraphNode start, GraphNode end) {
		this.start = start;
		this.end = end;
	}
	
	public GraphNode getStart() {
		return start;
	}
	
	public GraphNode getEnd() {
		return end;
	}
	
}