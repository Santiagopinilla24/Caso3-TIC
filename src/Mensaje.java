public class Mensaje {
    private int id;
    private boolean esSpam;
    private boolean fin;
    private int tiempoCuarentena;
    
    public Mensaje(int id, boolean esSpam) {
        this.id = id;
        this.esSpam = esSpam;
        this.fin = false;
    }
    
    public Mensaje(boolean esFin) {
        this.fin = esFin;
    }
	public int getId() { return this.id; }
    public boolean esSpam() { return esSpam; }
    public boolean esFin() { return fin; }
    public void setTiempoCuarentena(int tiempo) { this.tiempoCuarentena = tiempo; }
    public void decrementarTiempo() { if (tiempoCuarentena > 0) tiempoCuarentena--; }
    public boolean tiempoAgotado() { return tiempoCuarentena == 0; }
}