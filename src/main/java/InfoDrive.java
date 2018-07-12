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
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import java.io.IOException;
import java.security.GeneralSecurityException;

/*
 * Copyright (C) 2018 UTU-CETP
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Sebastián
 * @version 0.5
 */
public class InfoDrive {
    GoogleDrive drive = new GoogleDrive();
    
    /**
     * 
     * @return La capacidad usada de la cuenta (en Drive) y la capacidad total disponible
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    public String CapacidadCuenta()throws IOException, GeneralSecurityException{
        Numeros num = new Numeros();
        Drive servicio = drive.CrearServicio();
        String resultado="";
        About datosCuenta=servicio.about().get().setFields("user, storageQuota").execute();
        long capUso = (datosCuenta.getStorageQuota().getUsageInDrive());
        long capMax = datosCuenta.getStorageQuota().getLimit();
        resultado=num.BytesAGiB(capUso)+" / "+num.BytesAGiB(capMax);
        return resultado;
    }
    
     /**
     * 
     * @return El nombre (e-mail) de la cuenta asociada
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    public String NombreCuenta()throws IOException, GeneralSecurityException{
        Drive servicio = drive.CrearServicio();
        String resultado="";
        About datosCuenta=servicio.about().get().setFields("user, storageQuota").execute();
        resultado=datosCuenta.getUser().getEmailAddress();
        return resultado;
    }
    
    
    /**
     * @param busqueda Un String conteniendo un patrón de búsqueda de nombre.
     * @return Un String con una lista de directorios de Google Drive, de acuerdo a un patrón de
     * búsqueda
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    public String ListaDirectoriosDrive(String busqueda)
            throws IOException, GeneralSecurityException{
        String resultado="";
        Drive servicio = drive.CrearServicio();
         FileList result = servicio.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(name, size,mimeType)")
                .setQ("mimeType = 'application/vnd.google-apps.folder'")//("name contains '"+busqueda+"'")
                .execute();
        List<File> files = result.getFiles();
        
        if (files == null || files.isEmpty()) {
            resultado = "No se hallaron Directorios en Drive.";
        } else {
            resultado = "Directorios en Drive:\n";
            for (File file : files) {
                
                resultado= resultado + file.getName()+"\t"+file.getSize()+"\n";
            }
        }

        return resultado;
    }

}
