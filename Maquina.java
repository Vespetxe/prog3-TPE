public class Maquina implements Comparable<Maquina>{
    private String nombre;
    private int piezas;

    public Maquina(String nombre, int piezas){
        this.nombre = nombre;
        this.piezas = piezas;
    }
     public String getNombre(){return this.nombre;}
     public int getPiezas(){return this.piezas;}
   
     @Override
     public int compareTo(Maquina m) {
        return Integer.compare(this.getPiezas(), m.getPiezas());
     }
     @Override
     public String toString() {
        return "Maquina [nombre=" + nombre + ", piezas=" + piezas + "]";
     }
   
}