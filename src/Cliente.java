
public class Cliente extends Thread {
	private BuzonEntrada buzonE;
	 private static int totalToProduce;
	 private static int producedCount = 0;

	 public Cliente(String name, BuzonEntrada buzonE) {
	     super(name);
	     this.buzonE = buzonE;
	 }

	 public static void setTotalToProduce(int total) {
	     totalToProduce = total;
	 }

	 private Mensaje produce() {
	     producedCount++;
	     int msjNombre = producedCount;
	     System.out.println("[" + getName() + "]: produce el item " + msjNombre);
	     return new Mensaje(msjNombre, false);
	 }

	 @Override
	 public void run() {
	     try {
	         while (producedCount < totalToProduce) {
	             synchronized (Cliente.class) {
	                 if (producedCount >= totalToProduce) {
	                     break;
	                 }
	                 Mensaje mensaje = produce();
	                
	                 buzonE.put(mensaje);
	                 
	                 System.out.println("[" + getName() + "]: se duerme hasta que su item " + mensaje.getId() + " sea procesado");
	           
	                 
	                 System.out.println("[" + getName() + "]: se despierta porque su item " + mensaje.getId() + " fue procesado");
	             }
	         }
	         System.out.println("[" + getName() + "]: finalizado");
	     } catch (InterruptedException e) {
	         Thread.currentThread().interrupt();
	     }
	 }

}
