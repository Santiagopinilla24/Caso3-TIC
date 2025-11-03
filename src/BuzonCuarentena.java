import java.util.LinkedList;
import java.util.Queue;

public class BuzonCuarentena {
    private Queue<Mensaje> queue;
    private boolean finRecibido = false;
    
    public BuzonCuarentena() {
        this.queue = new LinkedList<>();
    }
    
    public synchronized void put(Mensaje m) throws InterruptedException {
        if (finRecibido) {
            return;
        }
        queue.offer(m);
        System.out.println("[" + Thread.currentThread().getName() + "]: guarda en cuarentena " + m.getId() + " por " + m.getTiempoCuarentena() + "s");
        notifyAll();
    }
    
    public synchronized Mensaje get() throws InterruptedException {
        while (queue.isEmpty() && !finRecibido) {
            wait(1000);
            if (finRecibido) {
                return new Mensaje(false, true);
            }
        }
        
        if (queue.isEmpty() && finRecibido) {
            return new Mensaje(false, true);
        }
        
        return queue.peek();
    }
    
    public synchronized void removerMensaje(Mensaje m) {
        boolean removed = queue.remove(m);
        if (removed) {
            System.out.println("[ManejadorCuarentena]: remueve " + m.toString() + " de cuarentena");
        }
        notifyAll();
    }
    
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public synchronized int size() {
        return queue.size();
    }
    
    public synchronized void terminarSistema() {
        finRecibido = true;
        notifyAll();
    }
}