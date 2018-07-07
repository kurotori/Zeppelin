



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;


public class Archivos {
    
    public void VerArchivos(){
        File ubicacion = new File("D:\\");
        if(ubicacion.isDirectory()){
            //list all files on directory
            String[] files = ubicacion.list();
            
            for(String s:files){
		System.out.println(s);
			}
		}
    }
    
    public String VerArchivos(File directorio){
        String resultado="";
        if(directorio.isDirectory()){
            //list all files on directory
            String[] files = directorio.list();
            resultado= "Archivos en "+directorio.getAbsolutePath()+"\n";
            resultado= resultado+"-----------------------------------\n";
            for(String s:files){
		resultado = resultado+s+"\n";
			}
		}
        return resultado;
    }
    
    public File[] VerArchivos(File directorio,JTextArea panel){
        String resultado="";
        File[] archivos;
        if(directorio.isDirectory()){
            //list all files on directory
            String[] files = directorio.list();
            archivos=directorio.listFiles();
            resultado= "Archivos en "+directorio.getAbsolutePath()+"\n";
            resultado= resultado+"-----------------------------------\n";
            for(String s:files){
		resultado = resultado+s+"\n";
			}
		}
        else{
            resultado="No es un directorio";
            archivos = new File[0];
        }
        panel.setText(resultado);
        return archivos;
    }
    
    public void ChequearDirCredenciales(JTextArea panel){
//        CodeSource codeSource = Inicio.class.getProtectionDomain().getCodeSource();
//        File jarFile = new File("\\");
//        try {
//            jarFile = new File(codeSource.getLocation().toURI().getPath());
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String jarDir = jarFile.getParentFile().getPath();
//        
        File directorio = new File("credencial");
        if(directorio.isDirectory()){
            String[] f = directorio.list();
            for(String s:f){
                File archivo = new File(directorio.getAbsolutePath()+"\\"+s);
                archivo.delete();
                panel.append("Se borró: "+archivo.getAbsolutePath()+"\n");
            }
        }
        else{
            directorio.mkdir();
        }
        
        //System.out.println(directorio.getAbsolutePath());
    }
    
    public String GetDirCredenciales(){
        //File directorio = new File(Archivos.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"\\credencial");
        File directorio = new File("credencial");
        String dir = directorio.getAbsolutePath();
        return dir;
    }
    
    public void SubirArchivo(File archivo, JTextArea panel){
        String texto="Se subirá el archivo "+archivo.getName()+" a Drive\n";
        panel.setText(texto);
        dq drive = new dq();
        //File archivo = new File("ej.pdf");
        try {
            panel.setText(texto+drive.SubirArchivo(archivo));
        } catch (IOException ex) {
            panel.setText(""+ex.getMessage());
            //Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            panel.setText(""+ex.getMessage());
            //Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
