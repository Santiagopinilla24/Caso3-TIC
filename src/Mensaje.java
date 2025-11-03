public class Mensaje {
    private int id;
    private boolean esSpam;
    private boolean fin;
    private boolean inicio;
    private int tiempoCuarentena;
    private int clienteId;
    
    public Mensaje(int id, int clienteId, boolean esSpam) {
        this.id = id;
        this.clienteId = clienteId;
        this.esSpam = esSpam;
        this.fin = false;
        this.inicio = false;
    }
    
    public Mensaje(boolean esInicio, boolean esFin) {
        this.fin = esFin;
        this.inicio = esInicio;
        this.id = -1;
    }
    
    public int getId() { return this.id; }
    public boolean esSpam() { return esSpam; }
    public boolean esFin() { return fin; }
    public boolean esInicio() { return inicio; }
    public int getClienteId() { return clienteId; }
    public void setTiempoCuarentena(int tiempo) { this.tiempoCuarentena = tiempo; }
    public void decrementarTiempo() { if (tiempoCuarentena > 0) tiempoCuarentena--; }
    public boolean tiempoAgotado() { return tiempoCuarentena == 0; }
    public int getTiempoCuarentena() { return tiempoCuarentena; }
}