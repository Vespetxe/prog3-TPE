import java.util.LinkedList;
import java.util.List;

public class Greedy {
    
    public Solucion hacerGreedy(List<Maquina> maquinas, int piezasTotales){
        Solucion solucion = new Solucion();
        int sumaPiezas =0;
        ordenar(maquinas);
        while(sumaPiezas<piezasTotales){
            Maquina m = seleccionar(maquinas);
            if(sumaPiezas+ m.getPiezas()<=piezasTotales){
                solucion.agregarMaquina(m);
            }
        }
        if(sumaPiezas==piezasTotales){
            return solucion;
        }else return null; // no hay solucion
    }







    // estrategia: ordenar lista de maquinas de mayor a menor cantidad de piezas que produce
    // elegir en ese orden la maquina que produzca mas piezas y no se pase del total
    // el objetivo seria elegir la cantidad minima de maquinas que logre producir el total de piezas

    // podria contar el resto de piezas q me quedan

    //tener cuidado con el greedy puede no encontrar solucion (puede ser que no tenga solucion pero hay que aclararlo)


}
