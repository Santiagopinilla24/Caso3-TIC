import java.util.Random;

public class FiltroSpam extends Thread {
    private BuzonEntrada buzonEntrada;
    private BuzonSalida buzonSalida;
    private BuzonCuarentena buzonCuarentena;
    private int totalClientes;

    private static int clientesTerminados = 0;
    private static boolean finEnviado = false;
    private static Object lock = new Object();

    private Random random = new Random();

    public FiltroSpam(String name, BuzonEntrada buzonEntrada, BuzonSalida buzonSalida, 
                      BuzonCuarentena buzonCuarentena, int totalClientes) {
        super(name);
        this.buzonEntrada = buzonEntrada;
        this.buzonSalida = buzonSalida;
        this.buzonCuarentena = buzonCuarentena;
        this.totalClientes = totalClientes;
    }

    @Override
    public void run() {
        try {
            while (getClientesTerminados() < totalClientes) {
                Mensaje mensaje = buzonEntrada.get();

                if (mensaje.esFin()) {
                    incrementarClientesTerminados();
                    System.out.println("[" + getName() + "]: recibió FIN de cliente");

                    if (getClientesTerminados() >= totalClientes) {
                        synchronized (lock) {
                            if (!finEnviado) {
                                enviarMensajesFin();
                                finEnviado = true;
                            }
                        }
                    }
                } else if (mensaje.esInicio()) {
                    System.out.println("[" + getName() + "]: recibió INICIO de cliente");
                } else {
                    procesarMensaje(mensaje);
                }
            }

            // Revisión de seguridad final
            synchronized (lock) {
                if (!finEnviado) {
                    Thread.sleep(100);
                    if (!finEnviado) {
                        enviarMensajesFin();
                        finEnviado = true;
                    }
                }
            }

            System.out.println("[" + getName() + "]: finalizado");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void procesarMensaje(Mensaje mensaje) throws InterruptedException {
        if (mensaje.esSpam()) {
            int tiempoCuarentena = 2 + random.nextInt(3);
            mensaje.setTiempoCuarentena(tiempoCuarentena);
            buzonCuarentena.put(mensaje);
            System.out.println("[" + getName() + "]: envió " + mensaje.getId() +
                    " a cuarentena por " + tiempoCuarentena + "s");
        } else {
            buzonSalida.put(mensaje);
            System.out.println("[" + getName() + "]: envió " + mensaje.getId() + " a buzón de salida");
        }
    }

    private void enviarMensajesFin() throws InterruptedException {
        // Espera activa si el contador no coincide
        if (getClientesTerminados() < totalClientes) {
            System.out.println("[" + getName() + "]: ERROR - Contador inconsistente. Esperando...");
            while (getClientesTerminados() < totalClientes) {
                Thread.sleep(100);
            }
        }

        Thread.sleep(500);

        int intentos = 0;
        int maxIntentos = 30;
        while (!buzonCuarentena.isEmpty() && intentos < maxIntentos) {
            Thread.sleep(1000);
            intentos++;
        }

        buzonSalida.put(new Mensaje(false, true));
        System.out.println("[" + getName() + "]: envió FIN a buzón de salida");

        buzonCuarentena.terminarSistema();
        System.out.println("[" + getName() + "]: envió FIN a cuarentena");

        buzonEntrada.terminarSistema();
        System.out.println("[" + getName() + "]: terminó sistema de entrada");
    }

    // Métodos sincronizados para proteger las variables estáticas compartidas
    private static synchronized void incrementarClientesTerminados() {
        clientesTerminados++;
    }

    private static synchronized int getClientesTerminados() {
        return clientesTerminados;
    }

    public static synchronized void reiniciarEstado() {
        clientesTerminados = 0;
        finEnviado = false;
    }
}