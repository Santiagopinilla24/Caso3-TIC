
public class FiltroSpam {
    
    private BuzonEntrada buzonEntrada;
    private BuzonSalida buzonSalida;
    private BuzonCuarentena buzonCuarentena;
    private int clientesTerminados = 0;
    private int totalClientes;
    
    public FiltroSpam(BuzonEntrada buzonEntrada, BuzonSalida buzonSalida, 
                     BuzonCuarentena buzonCuarentena, int totalClientes) {
        this.buzonEntrada = buzonEntrada;
        this.buzonSalida = buzonSalida;
        this.buzonCuarentena = buzonCuarentena;
        this.totalClientes = totalClientes;
    }

    
    private void mensajeFin() {

    }
}
    

