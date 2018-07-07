




import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class Inicio {



    public static void main(String[] args) {
        Archivos a = new Archivos();
        a.VerArchivos();
        VentanaInicio v1 = new VentanaInicio();
        v1.setVisible(true);

    }

}