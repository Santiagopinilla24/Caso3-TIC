
public class Mensaje {
	private String nombre;
	private boolean processed;
	private Thread clienteT;

	

	 public Mensaje(String nombre, Thread clienteT) {
	     this.nombre = nombre;
	     this.processed = false;
	     this.clienteT = clienteT;
	 }
	 
	 public String getNombre() {
			return this.nombre;
		}


	 public synchronized void esperarHastaProcesado() throws InterruptedException {
	     while (!processed) {
	         wait();
	     }
	 }
	 public synchronized void procesarNotificar() {
	     this.processed = true;
	     notifyAll();
	 }

	 public Thread getClienteThread() {
	     return clienteT;
	 }

}
