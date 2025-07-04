import java.util.ArrayList;

public class Backtracking {

    private int piezasTotales;
    private ArrayList<Maquina> maquinas;
    private Solucion mejorSolucion;
    

    public Backtracking(int piezasTotales, ArrayList<Maquina> maquinas) {
        this.piezasTotales = piezasTotales;
        this.maquinas = maquinas;
        this.mejorSolucion = new Solucion();
    }

    public Solucion busquedaExhaustiva() {
        if (mejorSolucion == null) {
            mejorSolucion = new Solucion();
        }
        backtracking(new Solucion(), maquinas.get(0), 0);

        return mejorSolucion;
    };
    // Se comienza en la raíz del árbol, con una solución vacía y suma de piezas =
    // 0.
    // Desde la raíz, se generan una rama por cada máquina disponible, agregando una
    // máquina a la solución parcial.
    // Desde cada nuevo nodo (solución parcial con una máquina), se vuelve a generar
    // una rama por cada máquina (posible repetición), siempre y cuando la suma de
    // piezas no supere el total requerido.
    // Este proceso continúa de forma recursiva, construyendo ramas nuevas mientras
    // no se pase del total de piezas.
    // Cada vez que se llega a un nodo donde la suma de piezas es exactamente igual
    // al total requerido, se evalúa si es la mejor solución encontrada hasta ahora.
    // Si en algún nodo la suma de piezas supera el total, no se continúa esa rama
    // (poda por restricción).

    // el estado final seran todas aquellas soluciones cuya suma de piezas sea igual
    // a las piezas totales a producir y no hayan sido podadas
    // una vez encontrada una mejor solucion (la de menos cantidad de maquinas
    // posibles) esa va a ser nuestro estado solucion.
    private void backtracking(Solucion solucionActual, Maquina maquinaActual, int pos) {
        if (solucionActual.suma() == piezasTotales) {
            if (esMejor(solucionActual, mejorSolucion)) {
                mejorSolucion.clear();
                mejorSolucion.addAll(solucionActual);
                mejorSolucion.setSuma(solucionActual.suma());

            }
        } else {
            for (int i = pos; i < maquinas.size(); i++) {
                Maquina siguiente = maquinas.get(i);
                if ((solucionActual.suma() + siguiente.getPiezas()) <= piezasTotales) {
                    if (!poda(solucionActual, siguiente)) {
                        solucionActual.setEstadosGenerados(solucionActual.getEstadosGenerados()+1);
                        solucionActual.agregarMaquina(siguiente);
                        backtracking(solucionActual, siguiente, i);
                        solucionActual.removeLast();
                    }
                }
            }
        }
    }

    private boolean poda(Solucion solucionActual, Maquina m) {
        if (!(mejorSolucion).isEmpty() && solucionActual.size() + 1 > mejorSolucion.size()) {
            return true;
        } else
            return false;
        // la solucion que voy contruyendo, no puede ser peor que la que ya tengo
    }

    private boolean esMejor(Solucion solucionActual, Solucion mejorSolucion) {
        return (mejorSolucion.isEmpty() || solucionActual.size() < mejorSolucion.size());// agregar condicion de o
    }

}
