import java.util.LinkedList;
import java.util.Queue;

public class BuzonSalida {
    private int capacidad;
    private Queue<Mensaje> queue;
    private boolean sistemaTerminado = false;
    private int servidoresTerminados = 0;
    private int totalServidores;
    
    public BuzonSalida(int capacity) {
        this.capacidad = capacity;
        this.queue = new LinkedList<>();
    }
    
    public void setTotalServidores(int totalServidores) {
        this.totalServidores = totalServidores;
    }
    
    public synchronized void put(Mensaje m) throws InterruptedException {
        while (queue.size() == capacidad && !sistemaTerminado) {
            System.out.println("[" + Thread.currentThread().getName() + "]: espera porque el buzón de salida está lleno");
            wait();
        }
        
        if (!sistemaTerminado) {
            queue.offer(m);
            System.out.println("[" + Thread.currentThread().getName() + "]: guarda en el buzón de salida el mensaje " );
            notifyAll();
        }
    }
    
    public synchronized Mensaje get() throws InterruptedException {
        while (queue.isEmpty() && !sistemaTerminado) {
            wait();
        }
        
        if (sistemaTerminado && queue.isEmpty()) {
            servidoresTerminados++;
            return new Mensaje(false, true);
        }
        
        Mensaje m = queue.peek();
        
        if (m != null && m.esFin()) {
            servidoresTerminados++;

            if (servidoresTerminados == totalServidores) {
                sistemaTerminado = true;
                queue.clear();
                notifyAll();
            }
            return new Mensaje(false, true);
        } else {
            m = queue.poll();
            System.out.println("[" + Thread.currentThread().getName() + "]: sacó del buzón de salida el mensaje " + m.getId());
        }
        
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