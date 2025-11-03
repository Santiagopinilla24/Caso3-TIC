import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
    private int capacidad;
    private Queue<Mensaje> queue;
    private boolean sistemaTerminado = false;
    
    public BuzonEntrada(int capacity) {
        this.capacidad = capacity;
        this.queue = new LinkedList<>();
    }
    
    public synchronized void put(Mensaje m) throws InterruptedException {
        while (queue.size() == capacidad && !sistemaTerminado) {
            System.out.println("[" + Thread.currentThread().getName() + "]: espera porque el buzón de entrada está lleno");
            wait();
        }
        
        if (!sistemaTerminado) {
            queue.offer(m);
            System.out.println("[" + Thread.currentThread().getName() + "]: guarda en el buzón de entrada el mensaje " + m.getId());
            notifyAll(); 
        }
    }
    
    public synchronized Mensaje get() throws InterruptedException {
        while (queue.isEmpty() && !sistemaTerminado) {
            wait();
        }
        
        if (queue.isEmpty() && sistemaTerminado) {
            return new Mensaje(false, true); 
        }
        
        Mensaje m = queue.poll();
        System.out.println("[" + Thread.currentThread().getName() + "]: sacó del buzón de entrada el mensaje " + m.getId());
        
        notifyAll(); 
        return m;
    }
    
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public synchronized void terminarSistema() {
        sistemaTerminado = true;
        notifyAll();
    }
}