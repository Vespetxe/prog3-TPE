import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Greedy {

    private Solucion mejorSolucion;

    public Greedy() {
        this.mejorSolucion = null;
    }

    public Solucion algoritmoVoraz(ArrayList<Maquina> maquinas, int piezasTotales) {
        if (mejorSolucion == null) {
            mejorSolucion = new Solucion();
        }
        greedy(maquinas, piezasTotales);
        return mejorSolucion;
    }

    private void greedy(ArrayList<Maquina> maquinas, int piezasTotales) {
        // los candidatos son todas las maquinas
        // estrategia: ordenar lista de maquinas de mayor a menor cantidad de piezas que
        // produce
        // elegir en ese orden la maquina que produzca mas piezas y no se pase del total
        // el objetivo seria elegir la cantidad minima de maquinas que logre producir el
        // total de piezas
        // puede no encontrar solucion (puede ser que no tenga solucion pero hay que
        // aclararlo

        Collections.sort(maquinas, Comparator.reverseOrder());
        int contador = 0;
        Solucion solucionGreedy = new Solucion();
        Iterator<Maquina> itMaquina = maquinas.iterator();
        Maquina m = itMaquina.next();
        while (itMaquina.hasNext() && solucionGreedy.suma() < piezasTotales) {
            contador++;
            //intentar poner todas las veces la maquina y recien cuando me paso del total voy con la proxima
            // este algoritmo no permite repetir maquinas
            if (solucionGreedy.suma() + m.getPiezas() <= piezasTotales) {
                solucionGreedy.agregarMaquina(m);
            } else {
                m = itMaquina.next();
            }

        }
        if (solucionGreedy.suma() == piezasTotales) { // encuentra solucion cuando se iguala la suma de piezas totales
                                                      // de la solucion construida con la suma de piezas totales a
                                                      // producir
            mejorSolucion = solucionGreedy;
            mejorSolucion.setEstadosGenerados(contador);
        } else {
            mejorSolucion = null; // que devuelva null significa que no encontro solucion
        }
    }

}
