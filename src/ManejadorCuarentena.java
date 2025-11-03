import java.util.Random;

public class ManejadorCuarentena extends Thread {
    private BuzonCuarentena buzonCuarentena;
    private BuzonSalida buzonSalida;
    private Random random = new Random();
    private boolean terminado = false;
    
    public ManejadorCuarentena(BuzonCuarentena buzonCuarentena, BuzonSalida buzonSalida) {
        super("ManejadorCuarentena");
        this.buzonCuarentena = buzonCuarentena;
        this.buzonSalida = buzonSalida;
    }

    @Override
    public void run() {
        try {
            while (!terminado) {
                Mensaje mensaje = buzonCuarentena.get();
                
                if (mensaje.esFin()) {
                    terminado = true;
                    break;}
                if (mensaje != null && !mensaje.esFin()) {
                    procesarMensajeCuarentena(mensaje);
                }
                Thread.sleep(100);
            }
            System.out.println("[" + getName() + "]: Finalizado");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private void procesarMensajeCuarentena(Mensaje mensaje) throws InterruptedException {
        mensaje.decrementarTiempo();
        System.out.println("[" + getName() + "]: mensaje " + mensaje.getId() + " tiempo restante: " + mensaje.getTiempoCuarentena() + "s");
    
        int numAleatorio = random.nextInt(21) + 1;
        if (numAleatorio % 7 == 0) {
            System.out.println("[" + getName() + "]: descarta mensaje " + mensaje.getId() + " por malicioso (número: " + numAleatorio + ")");
            buzonCuarentena.removerMensaje(mensaje);
            return;
        }
        
        if (mensaje.tiempoAgotado()) {
            System.out.println("[" + getName() + "]: mensaje " + mensaje.getId() + " completó cuarentena, enviando a salida");
            buzonSalida.put(mensaje);
            buzonCuarentena.removerMensaje(mensaje);
        }
    }
}