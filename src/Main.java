import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try ( 
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
        ) { 
			FiltroSpam.reiniciarEstado();
            int nClientes = Integer.parseInt(br.readLine());
            int MsjPorCliente = Integer.parseInt(br.readLine());
            int nFiltros = Integer.parseInt(br.readLine());
            int nServidores = Integer.parseInt(br.readLine());
            int capBuzonEntrada = Integer.parseInt(br.readLine());
            int capBuzonSalida = Integer.parseInt(br.readLine());
            

            BuzonEntrada buzonE = new BuzonEntrada(capBuzonEntrada);
            BuzonSalida buzonS = new BuzonSalida(capBuzonSalida);
            buzonS.setTotalServidores(nServidores);
            BuzonCuarentena buzonC = new BuzonCuarentena();

            List<Cliente> clientes = new ArrayList<>();
            for (int i = 0; i < nClientes; i++) {
                Cliente cliente = new Cliente("Cliente" + (i + 1), buzonE, MsjPorCliente);
                clientes.add(cliente);
            }
            

            List<FiltroSpam> filtros = new ArrayList<>();
            for (int i = 0; i < nFiltros; i++) {
                FiltroSpam filtro = new FiltroSpam("Filtro" + (i + 1), buzonE, buzonS, buzonC, nClientes);
                filtros.add(filtro);
            }

            ManejadorCuarentena manejador = new ManejadorCuarentena(buzonC, buzonS);

            List<Servidor> servidores = new ArrayList<>();
            for (int i = 0; i < nServidores; i++) {
                Servidor servidor = new Servidor("Servidor" + (i + 1), buzonS);
                servidores.add(servidor);
            }
            System.out.println("\n*** Iniciando sistema ***");
            
            for (FiltroSpam f: filtros) {
                f.start();
            }
            
            manejador.start();
            
            for (Servidor s: servidores) {
                s.start();
            }
            
            for (Cliente c: clientes) {
                c.start();
            }
            
            try {
                for (Cliente c: clientes) {
                    c.join();
				}
                
                for (FiltroSpam f: filtros) {
                    f.join();
                }
                manejador.join();
                for (Servidor s: servidores) {
                    s.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            System.out.println("*** Sistema Finalizado Correctamente ***");
        }
    }
}