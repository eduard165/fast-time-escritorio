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
import java.util.List;
import modelo.ConexionHTTP;
import modelo.pojo.Cliente;
import modelo.pojo.Envio;
import modelo.pojo.Mensaje;
import modelo.pojo.Paquete;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import utils.Constantes;

/**
 *
 * @author eduar
 */
public class PaquetesDAO {
    public static List<Paquete> obtenerPaquetes() {
        List<Paquete> paquetes = null;
        String url = Constantes.URL_WS + "paquetes/todos";
        RespuestaHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaPaquete = new TypeToken<List<Paquete>>() {
                }.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paquetes;
    
    }
     public static Mensaje eliminarPaquete(Integer idPaquete) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "paquetes/eliminar" ;
        try {
            
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUT(urlServicio,"idPaquete=" + idPaquete);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la solicitud de eliminación: " + e.getMessage());
        }
        return respuesta;
    }
      public static Mensaje registrarPaquete(Paquete paquete) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "paquetes/registrar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(paquete);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(urlServicio, parametrosJSON);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la solicitud: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje editarPaquete(Paquete paquete) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "paquetes/actualizar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(paquete);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(urlServicio, parametrosJSON);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la solicitud de edición: " + e.getMessage());
        }
        return respuesta;
    }
}
