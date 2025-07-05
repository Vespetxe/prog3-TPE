import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ConfiguracionProduccion {
    private int piezasTotales;
    private ArrayList<Maquina> maquinas;
    private Solucion mejorSolucion;

    public ConfiguracionProduccion() {
        this.piezasTotales = 0;
        this.maquinas = new ArrayList<>();
        this.mejorSolucion = null;

    }

    public void cargarConfiguracion(String archivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea = br.readLine();
            if (linea != null) {
                piezasTotales = Integer.parseInt(linea.trim());
            }
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    int piezas = Integer.parseInt(partes[1].trim());
                    maquinas.add(new Maquina(nombre, piezas));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void run() {
        
        Greedy greedy = new Greedy();
        this.mejorSolucion = greedy.algoritmoVoraz(maquinas, piezasTotales);
        if (mejorSolucion != null) {
            System.out.println("Solucion parcial por optimizacion: " + String.valueOf(mejorSolucion));
            System.out.println("Cantidad de piezas producidas: " + mejorSolucion.suma());
            System.out.println("Cantidad de puestas en funcionamiento: " + mejorSolucion.getSolucion().size());
            System.out.println("Candidatos considerados= " + mejorSolucion.getEstadosGenerados());
        } else
            System.out.println("Solucion parcial por optimizacion: No se encontro solucion.");
        
        // puedo crear un objeto mejor solucion 
        Backtracking bt = new Backtracking(piezasTotales, maquinas);
        this.mejorSolucion = bt.busquedaExhaustiva();

        System.out.println("Mejor solucion encontrada por búsqueda exaustiva: " + String.valueOf(mejorSolucion));
        System.out.println("Cantidad de piezas producidas: " + mejorSolucion.suma());
        System.out.println("Cantidad de puestas en funcionamiento: " + mejorSolucion.getSolucion().size());
        System.out.println("Estados Generados: " + mejorSolucion.getEstadosGenerados());
    };

}
