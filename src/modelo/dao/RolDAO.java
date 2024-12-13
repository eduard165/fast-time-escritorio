/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionHTTP;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import modelo.pojo.Rol;
import utils.Constantes;

/**
 *
 * @author eduar
 */
public class RolDAO {
     public static List<Rol> obtenerRol(){
        List<Rol> respuestaRol = new ArrayList<>();
        String urlServicio = Constantes.URL_WS+"rol/obtener-roles";
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);
        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type listaRoles = new TypeToken<List<Rol>>() {
            }.getType();
            respuestaRol = gson.fromJson(respuestaWS.getContenido(), listaRoles);
        }
        return respuestaRol;
    }
     
    
}
