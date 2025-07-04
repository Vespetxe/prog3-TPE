import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ConfiguracionProduccion {
    private int piezasTotales;
    private List<Maquina> maquinas;
    private Solucion mejorSolucion;

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
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private  Solucion hacerBacktracking(){
        mejorSolucion = new Solucion();
        int mejorLimite = mejorSolucion.size();

        backtracking(mejorSolucion,maquinas.getFirst(),0);
        
        return mejorSolucion;
    }

    private void backtracking(Solucion solucionActual,Maquina maquinaActual, int pos){
        if(solucionActual.suma()==piezasTotales){
            if (esMejor(solucionActual, mejorSolucion)){
                mejorSolucion.clear();
                mejorSolucion.addAll(solucionActual);
            }
        }else{
            for (int i = pos; i<maquinas.size();i++){
                if((solucionActual.suma() + maquinas.get(i).getPiezas())<= piezasTotales){
                    if(!poda(solucionActual, maquinas.get(i))){
                        solucionActual.agregarMaquina(maquinas.get(i));
                        backtracking(solucionActual,maquinas.get(i), i);
                        solucionActual.removeLast();
                    }
                }
            }
        }
    }
    private boolean poda(Solucion solucionActual, Maquina m){
        if(!mejorSolucion.isEmpty() && solucionActual.size()+1 >mejorSolucion.size()){
            return true;
        }else return false; 
        // estaria bien poner la estrategia greedy como poda?
        // que las piezas producidas hasta ahora mas la maquina a considerar no se pase del total
        // la solucion que voy contruyendo, no puede ser peor que la que ya tengo NO ES PODA ES RESTRICCION
        return;
    }
    private boolean esMejor(Solucion solucionActual, Solucion mejorSolucion){
        return (mejorSolucion.isEmpty() || solucionActual.size() < mejorSolucion.size());//agregar condicion de o
    }
}
