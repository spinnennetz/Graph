	public class GraphNode {
		
		private final int index;
		public final SimpleLinkedList<GraphEdge> incidence;
		
		public GraphNode (int index) {
			this.index = index;
			incidence = new SimpleLinkedList<GraphEdge>();
		}
		
		public void addEdge (GraphEdge edge) {
			incidence.add(edge);
		}
		
		public SimpleLinkedList<GraphEdge> getEdges() {
			return incidence;
		}
		
		public int id() {
			return index;
		}
		
		public GraphEdge getci(int index) {
			int i=0;
			while (i<index) {
				incidence.advance();
				i++;
			}
			return incidence.getCurrent();
		}
		
		public int incidence_length() {
			return incidence.length();
		}
	}