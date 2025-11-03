import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
	 private int capacity;
	 private Queue<Mensaje> queue;
	
	 public BuzonEntrada(int capacity) {
	     this.capacity = capacity;
	     this.queue = new LinkedList<>();
	 }
	
	 public synchronized void put(Mensaje m) throws InterruptedException {
	     while (queue.size() == capacity) {
	         System.out.println("[" + Thread.currentThread().getName() + "]: espera porque el buzón de entrada está lleno");
	         wait();
	     }
	     
	     queue.offer(m);
	     System.out.println("[" + Thread.currentThread().getName() + "]: guarda en el buzón de entrada el mensaje " + m.getNombre());
	    
	     notifyAll();
	 }
	
	 public synchronized Mensaje get() throws InterruptedException {
	     while (queue.isEmpty()) {
	         wait();
	     }
	     
	     Mensaje m = queue.poll();
	     System.out.println("[" + Thread.currentThread().getName() + "]: sacó del buzón de entrada el mensaje " + m.getNombre());
	    
	     notifyAll();
	     return m;
	 }

}
