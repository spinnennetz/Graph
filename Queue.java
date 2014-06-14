public class Queue<E> {
	
	private SimpleLinkedList<E> queue;
	
	public Queue() {
		this.queue = new SimpleLinkedList<E>();
	}
	
	public void enqueue(E element) {
		queue.add(element);
	}
	
	public E dequeue() {
		queue.reset();
		E first = queue.getCurrent();
		queue.removeFirst();
		return first;
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
}