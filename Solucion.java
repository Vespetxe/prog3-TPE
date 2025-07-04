import java.util.LinkedList;
import java.util.List;

public class Solucion {
    private List<Maquina> solucion;
    private int sumaPiezas;
    private int estadosGenerados;

    public void setSuma(int suma){this.sumaPiezas = suma;}

    public Solucion(){
        this.solucion = new LinkedList<>();
        this.sumaPiezas = 0;
        this.estadosGenerados = 0;
    }

    public void setEstadosGenerados(int e){this.estadosGenerados = e;}
    public int getEstadosGenerados(){return this.estadosGenerados;}

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
        this.sumaPiezas -= this.solucion.getLast().getPiezas(); 
        this.solucion.removeLast();

    }

    public boolean isEmpty() {
        return solucion.isEmpty();
    }
    @Override
    public String toString(){
        String output="[";
        if (this!=null) {
            for (Maquina maquina : solucion) {
            output+=maquina.getNombre()+", ";
        }
        output+="]";
        }
        return output;
    }
}
