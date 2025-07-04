import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ConfiguracionProduccion cp = new ConfiguracionProduccion();
       
        LocalDateTime locaDate = LocalDateTime.now();
		System.out.println("Inicio: "+locaDate);

        cp.cargarConfiguracion("input.txt");//Insertar ruta de archivo de prueba
        cp.run();

        locaDate = LocalDateTime.now();
		System.out.println("Fin: "+locaDate);
    }
}
