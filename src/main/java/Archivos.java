



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;


public class Archivos {
    
    File[] listaArchivos=new File[0];
    /**
     * Define la lista de archivos interna para su posterior proceso.
     * @param archivos Un array de archivos
     */
    public void DefListaArchivos(File[] archivos){
        listaArchivos=archivos;
    }
    
    /**
     * Devuelve la lista interna de archivos, que contiene los archivos para
     * procesar
     * @return El objeto listaArchivos
     */
    public File[] ListaArchivos(){
        return listaArchivos;
    }
    
    /**
     * Devuelve el número de archivos en la lista de archivos interna
     * @return La cantidad de archivos en la lista interna
     */
    public int CantArchivos(){
        return listaArchivos.length;
    }
    /**
     * Vuelve a inicializar la lista interna de archivos, eliminando todos los
     * archivos de la lista
     */
    public void LimpiarLista(){
        listaArchivos=new File[0];
    }
    

    /**
     * Obtiene y muestra en un JTextAerea la lista de archivos de un directorio
     * y la guarda en un array de tipo File
     * @param directorio El objeto File con la ruta al directorio
     * @param AreaTexto  El area de texto donde se deben mostrar los archivos del directorio
     * @return Una lista/array con los archivos del directorio
     */
    public File[] VerArchivos(File directorio,JTextArea AreaTexto){
        String resultado="";
        File[] archivos;
        if(directorio.isDirectory()){
            //list all files on directory
            archivos=directorio.listFiles();
            
            String[] files = directorio.list();
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
        AreaTexto.append("\n"+resultado);
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
        GoogleDrive drive = new GoogleDrive();
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
