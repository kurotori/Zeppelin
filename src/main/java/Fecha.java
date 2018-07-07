/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.text.SimpleDateFormat;  
import java.util.Date;
/**
 *
 * @author Sebastián
 */
public class Fecha {
    
    public String Hoy(){
        SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
        Date fecha = new Date();
        
        String resultado=formato.format(fecha).toString();   //java.time.LocalDate.now().toString();
        return resultado;
    }
}
