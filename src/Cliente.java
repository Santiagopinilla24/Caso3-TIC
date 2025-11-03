import java.util.Random;

public class Cliente extends Thread {
    private BuzonEntrada buzonE;
    private int mensajesAProducir;
    private int clienteId;
    private static int nextClienteId = 1;
    private Random random = new Random();
    
    public Cliente(String name, BuzonEntrada buzonE, int mensajesAProducir) {
        super(name);
        this.buzonE = buzonE;
        this.mensajesAProducir = mensajesAProducir;
        this.clienteId = nextClienteId++;
    }

    private Mensaje producirMensaje(int numeroMensaje) {
        int mensajeId = clienteId * 1000 + numeroMensaje;
        boolean esSpam = random.nextDouble() < 0.1; 
        System.out.println("[" + getName() + "]: produce mensaje " + mensajeId);
        return new Mensaje(mensajeId, clienteId, esSpam);
    }

    @Override
    public void run() {
        try {
            buzonE.put(new Mensaje(true, false));
            System.out.println("[" + getName() + "]: envió mensaje INICIO");
            
            for (int i = 0; i < mensajesAProducir; i++) {
                Mensaje mensaje = producirMensaje(i + 1);
                buzonE.put(mensaje);
                Thread.sleep(random.nextInt(500));
            }

            buzonE.put(new Mensaje(false, true));
            System.out.println("[" + getName() + "]: envió mensaje FIN");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}