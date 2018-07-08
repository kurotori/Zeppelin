

import com.google.api.services.drive.Drive;
import java.io.Console;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Inicio {
    

    
    public static void main(String[] args) {
//        Archivos a = new Archivos();
//        a.ChequearDirCredenciales();
        
        
        VentanaInicio v1 = new VentanaInicio();
        v1.setIconImage(v1.icono.getImage());
        v1.setVisible(true);
        
        

    }

}