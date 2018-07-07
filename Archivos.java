



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;
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
    
    
    
    
}
