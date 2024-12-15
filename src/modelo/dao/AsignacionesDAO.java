/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import modelo.ConexionHTTP;
import modelo.pojo.Asignacion;
import modelo.pojo.Colaborador;
import modelo.pojo.Mensaje;
import modelo.pojo.Unidad;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import utils.Constantes;

/**
 *
 * @author eduar
 */
public class AsignacionesDAO {
    
    public static Mensaje registrarAsignacion(Asignacion asignacion) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "asignaciones/registrar";
        Gson gson2 = new Gson();
            String parametrosJSON2 = gson2.toJson(asignacion);
            System.out.println(parametrosJSON2);
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(asignacion);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(urlServicio, parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al registrar la unidad: " + e.getMessage());
        }
        return respuesta;
    }
    public static Mensaje editarAsignacion(Asignacion asignacion) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "asignaciones/editar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(asignacion);
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
    
    public static Asignacion obtenerAsignacion(int idUnidad) {
        Asignacion asignacion = null;
        String urlServicio = Constantes.URL_WS + "asignaciones/buscar-asignacion/" + idUnidad;
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {

                asignacion = gson.fromJson(respuestaWS.getContenido(), Asignacion.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return asignacion;
    }
}
