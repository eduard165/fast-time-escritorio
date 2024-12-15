package modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import modelo.ConexionHTTP;
import modelo.pojo.Asignacion;
import modelo.pojo.Mensaje;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import modelo.pojo.Unidad;
import modelo.pojo.TipoUnidad;
import utils.Constantes;

public class UnidadesDAO {

    public static Mensaje registrarUnidad(Unidad unidad) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "unidades/registrar";

        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(unidad);
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

    public static Mensaje editarUnidad(Unidad unidad) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "unidades/editar";

        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(unidad);
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(urlServicio, parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al editar la unidad: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje eliminarUnidad(int idUnidad) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "unidades/eliminar/" + idUnidad;

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
            respuesta.setContenido("Error al eliminar la unidad: " + e.getMessage());
        }
        return respuesta;
    }

    public static List<TipoUnidad> obtenerTiposDeUnidades() {
        List<TipoUnidad> tiposUnidades = null;
        String urlServicio = Constantes.URL_WS + "unidades/obtener-tipos-unidades";
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaUnidad = new TypeToken<List<TipoUnidad>>() {
                }.getType();
                tiposUnidades = gson.fromJson(respuestaWS.getContenido(), tipoListaUnidad);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tiposUnidades;
    }

    public static List<Unidad> obtenerUnidades() {
        List<Unidad> unidades = null;
        String urlServicio = Constantes.URL_WS + "unidades/obtenerTodos";
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaUnidad = new TypeToken<List<Unidad>>() {
                }.getType();
                unidades = gson.fromJson(respuestaWS.getContenido(), tipoListaUnidad);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }

    
}
