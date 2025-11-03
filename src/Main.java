import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		) { 
			String line = br.readLine();
			
			int nClientes = Integer.parseInt(line);
			line = br.readLine();
			int nMsj = Integer.parseInt(line);
			line = br.readLine();
			int nFiltros = Integer.parseInt(line);
			line = br.readLine();
			int nServidores = Integer.parseInt(line);
			line = br.readLine();
			int n = Integer.parseInt(line);
			line = br.readLine();
			int m = Integer.parseInt(line);
			line = br.readLine();
			
			
			BuzonEntrada buzonE = new BuzonEntrada(n);
		    BuzonSalida buzonS = new BuzonSalida(m);
		     
		    Cliente.setTotalToProduce(nMsj);

		    List<Cliente> clientes = new ArrayList<>();

		    List<Servidor> servidores = new ArrayList<>();


		     for (Thread c : clientes) {
		         c.start();
		     }

		     for (Thread s : servidores) {
		         s.start();
		     }

		     try {
		    	 for (Thread c : clientes) {
			         c.join();
			     }
		    	 for (Thread s : servidores) {
			         s.join();
			     }
		     } catch (InterruptedException e) {
		         Thread.currentThread().interrupt();
		     }
		 }
		}


}
