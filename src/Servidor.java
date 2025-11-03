import java.util.Random;

public class Servidor extends Thread {
    private BuzonSalida buzonSalida;
    private Random random = new Random();
    public Servidor(String name, BuzonSalida buzonSalida) {
        super(name);
        this.buzonSalida = buzonSalida;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Mensaje mensaje = buzonSalida.get();
                if (mensaje.esFin()) {
                    System.out.println("[" + getName() + "]: recibió FIN");
                    break;
                }
                
                System.out.println("[" + getName() + "]: procesando mensaje " + mensaje.getId());
                int tiempoProcesamiento = 500 + random.nextInt(1000); 
                Thread.sleep(tiempoProcesamiento);
                System.out.println("[" + getName() + "]: completó procesamiento de mensaje " + mensaje.getId());
            }
        
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}