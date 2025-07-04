import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ConfiguracionProduccion {
    private int piezasTotales;
    private List<Maquina> maquinas;
    private Solucion mejorSolucion;
    private int estadosGenerados;

    public ConfiguracionProduccion() {
        this.piezasTotales=0;
        this.maquinas = new ArrayList<>();
        this.mejorSolucion=null;
        this.estadosGenerados = 0;
    }

    public void cargarConfiguracion (String archivo){
        try {
            BufferedReader br = new BufferedReader (new FileReader(archivo));
            String linea = br.readLine();
            if(linea!= null){
                piezasTotales = Integer.parseInt(linea.trim());
            }
            while((linea = br.readLine())!=null){
                String[] partes = linea.split(",");
                if (partes.length == 2){
                    String nombre = partes[0].trim();
                    int piezas = Integer.parseInt(partes[1].trim());
                    maquinas.add(new Maquina(nombre,piezas));
                }
            }
            br.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    };

    public void run (){
        System.out.println(this.piezasTotales);
        for (Maquina maquina : maquinas) {
            System.out.println(maquina.toString());
        }
        algoritmoVoraz(); 
        System.out.println("Solucion parcial por optimizacion: " + String.valueOf(mejorSolucion)  );
        System.out.println("Cantidad de piezas producidas: " + mejorSolucion.suma() );
        System.out.println("Cantidad de puestas en funcionamiento: " + mejorSolucion.getSolucion().size() );
        System.out.println("Estados Generados: " + estadosGenerados  );
       
        busquedaExhaustiva();
        System.out.println("Mejor solucion encontrada por búsqueda exaustiva: " + String.valueOf(mejorSolucion) );
        System.out.println("Cantidad de piezas producidas: " + mejorSolucion.suma() );
        System.out.println("Cantidad de puestas en funcionamiento: " + mejorSolucion.getSolucion().size() );
        System.out.println("Estados Generados: " + estadosGenerados  );
    };

    private Solucion algoritmoVoraz(){

        if (mejorSolucion==null) {
            mejorSolucion = new Solucion();
        }
        estadosGenerados = 0;
        greedy();
        return mejorSolucion;
    }
 
    private  Solucion busquedaExhaustiva(){
       if (mejorSolucion==null) {
            mejorSolucion = new Solucion();
        }
        estadosGenerados = 0;
        backtracking(new Solucion(),maquinas.get(0),0);
        
        return mejorSolucion;
    };

    private void backtracking(Solucion solucionActual,Maquina maquinaActual, int pos){
        // Se comienza en la raíz del árbol, con una solución vacía y suma de piezas = 0.
        // Desde la raíz, se generan una rama por cada máquina disponible, agregando una máquina a la solución parcial.
        // Desde cada nuevo nodo (solución parcial con una máquina), se vuelve a generar una rama por cada máquina (posible repetición), siempre y cuando la suma de piezas no supere el total requerido.
        // Este proceso continúa de forma recursiva, construyendo ramas nuevas mientras no se pase del total de piezas.
        // Cada vez que se llega a un nodo donde la suma de piezas es exactamente igual al total requerido, se evalúa si es la mejor solución encontrada hasta ahora.
        // Si en algún nodo la suma de piezas supera el total, no se continúa esa rama (poda por restricción).

        // el estado final seran todas aquellas soluciones cuya suma de piezas sea igual a las piezas totales a producir y no hayan sido podadas
        // una vez encontrada una mejor solucion (la de menos cantidad de maquinas posibles) esa va a ser nuestro estado solucion.

        if(solucionActual.suma()==piezasTotales){
            System.out.println("primer if backtraking");
            if (esMejor(solucionActual, mejorSolucion)){
                mejorSolucion = new Solucion();
                mejorSolucion.addAll(solucionActual);
                mejorSolucion.setSuma(solucionActual.suma());
            }
        }else{
            for (int i = pos; i<maquinas.size() ;i++){
                if((solucionActual.suma() + maquinas.get(i).getPiezas())<= piezasTotales){
                // que las piezas producidas hasta ahora mas la maquina a considerar no se pase del total
                    if(!poda(solucionActual, maquinas.get(i))){
                        estadosGenerados ++;
                        solucionActual.agregarMaquina(maquinas.get(i));
                        backtracking(solucionActual, maquinas.get(i), i);
                        solucionActual.removeLast();
                    }
                }
            }
        }
    }
    private boolean poda(Solucion solucionActual, Maquina m){
        if ((mejorSolucion != null && !mejorSolucion.isEmpty() )&& solucionActual.size() + 1 >= mejorSolucion.size()) {
            return true;
        }else return false; 
        // la solucion que voy contruyendo, no puede ser peor que la que ya tengo 
        
    }
    private boolean esMejor(Solucion solucionActual, Solucion mejorSolucion){
        return (mejorSolucion.isEmpty() || solucionActual.size() < mejorSolucion.size());
    }
    private void greedy(){
        // los candidatos son todas las maquinas
        // estrategia: ordenar lista de maquinas de mayor a menor cantidad de piezas que produce
        // elegir en ese orden la maquina que produzca mas piezas y no se pase del total
        // el objetivo seria elegir la cantidad minima de maquinas que logre producir el total de piezas
        // puede no encontrar solucion (puede ser que no tenga solucion pero hay que aclararlo

    
       Collections.sort(maquinas, Comparator.reverseOrder());
       Solucion solucionGreedy = new Solucion();
       Iterator<Maquina> itMaquina = this.maquinas.iterator();
        while (itMaquina.hasNext() && solucionGreedy.suma()<piezasTotales) {
            Maquina m = itMaquina.next();
            System.out.println("piezas seleccionadas: " + m.getPiezas());
            System.out.println("suma parcial: " + solucionGreedy.suma());
            if (solucionGreedy.suma()+m.getPiezas()<=piezasTotales) {
                estadosGenerados++;
                solucionGreedy.agregarMaquina(m);
            }
            
        }
        if (solucionGreedy.suma()== piezasTotales) { // encuentra solucion cuando se iguala la suma de piezas totales de la solucion construida con la suma de piezas totales a producir
            mejorSolucion=solucionGreedy;
        }else{
            mejorSolucion=null; // que devuelva null significa que no encontro solucion
        }
    }
}
