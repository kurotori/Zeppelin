




import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Sebastián de los Ángeles
 * @version 1.0
 * 
 */
public class dq {
    private static final Archivos arch = new Archivos();
    private static final String APPLICATION_NAME = "RespaldoDrive";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = arch.GetDirCredenciales();//"credentials"; // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);//DRIVE_METADATA_READONLY);
    private static final String CLIENT_SECRET_DIR = "data/client_secret.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = dq.class.getResourceAsStream(CLIENT_SECRET_DIR);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();
        System.out.println("-->"+flow.getTokenServerEncodedUrl());
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private Drive CrearServicio()throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        System.out.println("Iniciando servicio");
        
        return service;
        
    }
    
    public String NombreCuenta()throws IOException, GeneralSecurityException{
        Drive servicio = CrearServicio();
        String resultado="";
        About datosCuenta=servicio.about().get().setFields("user, storageQuota").execute();
        resultado=datosCuenta.getUser().getEmailAddress();
        return resultado;
    }
    
    public String CapacidadCuenta()throws IOException, GeneralSecurityException{
        Numeros num = new Numeros();
        Drive servicio = CrearServicio();
        String resultado="";
        About datosCuenta=servicio.about().get().setFields("user, storageQuota").execute();
        long capUso = (datosCuenta.getStorageQuota().getUsageInDrive());
        long capMax = datosCuenta.getStorageQuota().getLimit();
        resultado=num.BytesAGiB(capUso)+" / "+num.BytesAGiB(capMax);
        
        return resultado;
    }
    
    
    //public String ListaArchivosDrive(String busqueda)throws IOException, GeneralSecurityException{
    public String ListaArchivosDrive()throws IOException, GeneralSecurityException{
        String resultado="";
        Drive servicio = CrearServicio();
         FileList result = servicio.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(name, size,mimeType)")
                .setQ("mimeType = 'application/vnd.google-apps.folder'")//("name contains '"+busqueda+"'")
                .execute();
        List<File> files = result.getFiles();
        
        if (files == null || files.isEmpty()) {
            resultado = "No files found.";
        } else {
            resultado = "Files:\n";
            for (File file : files) {
                
                resultado= resultado + file.getName()+"\t"+file.getSize()+"\n";
            }
        }

        return resultado;
    }
    
    

    
    public String archivos()throws IOException, GeneralSecurityException {
        String resultado="";
        
        Drive service = CrearServicio();
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(name, size)")
                .setQ("name contains 'utu'")
                .execute();
        List<File> files = result.getFiles();
        
        if (files == null || files.isEmpty()) {
            resultado = "No files found.";
        } else {
            resultado = "Files:\n";
            for (File file : files) {
                
                resultado= resultado + file.getName()+"\t"+file.getSize()+"\n";
            }
        }

        return resultado;
    }
    
    public String SubirArchivo(java.io.File archivo)throws IOException, GeneralSecurityException{
        String resultado="";
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); 
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        File archivoDrive = new File();
        
        String nombre = archivo.getName();
        String ruta = archivo.getAbsolutePath();
        
        archivoDrive.setName(nombre);
        
        java.io.File filePath = new java.io.File(ruta);
        FileContent mediaContent = new FileContent(null, filePath);
        File file = service.files().create(archivoDrive, mediaContent)
        .setFields("id")
        .execute();
        resultado="Se subió el archivo "+nombre+"\n"
                +"ID en Drive: "+file.getId();
        //System.out.println("File ID: " + file.getId());
        return resultado;
    }


    
}