import java.util.LinkedList;
import java.util.List;

public class Solucion {
    private List<Maquina> solucion;
    private int sumaPiezas;

    public Solucion(){
        this.solucion = new LinkedList<>();
        sumaPiezas = 0;
    }

    public List<Maquina> getSolucion() {
        return solucion;
    }

    public int suma() {
        return sumaPiezas;
    }

    public void agregarMaquina (Maquina m){
        this.solucion.add(m);
        this.sumaPiezas +=m.getPiezas();
    }
    public int size(){
        return this.solucion.size();
    }
    public void clear(){
        this.solucion.clear();
    }
    public void addAll(Solucion s){
        this.solucion.addAll(s.getSolucion());
    }
    public void removeLast(){
        this.solucion.removeLast();

    }
}
