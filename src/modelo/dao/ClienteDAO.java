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
import utils.Constantes;

public class ClienteDAO {

    public static Mensaje registrarCliente(Cliente cliente) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "clientes/registrar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(cliente);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(urlServicio, parametrosJSON);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al registrar el cliente: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje editarCliente(Cliente cliente) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "clientes/editar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(cliente);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(urlServicio, parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al editar el cliente: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje eliminarCliente(Integer idCliente) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "clientes/eliminar/" + idCliente;

        try {
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionDELETE(urlServicio);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al eliminar el cliente: " + e.getMessage());
        }
        return respuesta;
    }

    public static List<Cliente> obtenerClientes() {
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS + "clientes/todos";
        RespuestaHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaCliente = new TypeToken<List<Cliente>>() {}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaCliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }

}
