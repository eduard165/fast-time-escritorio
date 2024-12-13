package modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.net.HttpURLConnection;
import java.util.List;
import modelo.ConexionHTTP;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import modelo.pojo.Envio;
import utils.Constantes;

public class EnviosDAO {

    public static Mensaje registrarEnvio(Envio envio) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "envios/registrar";

        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(envio);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(urlServicio, parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al registrar el envío: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje editarEnvio(Envio envio) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "envios/actualizar";

        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(envio);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(urlServicio, parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al actualizar el envío: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje asignarConductor(int idEnvio, int idColaborador) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "envios/asignarConductor";
        try {
            String parametros = "idEnvio=" + idEnvio + "&idColaborador=" + idColaborador;
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUT(urlServicio, parametros);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error al asignar conductor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al asignar conductor al envío: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje actualizarEstadoEnvio(int idEnvio, int idEstado, String descripcion) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "envios/actualizarEstado";
        try {
            String parametros = "idEnvio=" + idEnvio + "&idEstado=" + idEstado + "&descripcion=" + descripcion;
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUT(urlServicio, parametros);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error al actualizar el estado del envío: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al actualizar el estado: " + e.getMessage());
        }
        return respuesta;
    }

    public static List<Cliente> obtenerClientes() {
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS + "clientes/obtenerTodos";
        RespuestaHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaEnvio = new TypeToken<List<Envio>>() {
                }.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaEnvio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
}
