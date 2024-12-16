package modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import modelo.ConexionHTTP;
import modelo.pojo.Colaborador;
import modelo.pojo.Mensaje;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import utils.Constantes;

public class ColaboradorDAO {

    public static Mensaje registrarColaborador(Colaborador colaborador) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "colaboradores/registrar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(colaborador);
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

    public static Mensaje editarColaborador(Colaborador colaborador) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "colaboradores/editar";
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(colaborador);
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

    public static Mensaje eliminarColaborador(Integer idColaborador) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "colaboradores/eliminar/" + idColaborador;
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
            respuesta.setContenido("Error al procesar la solicitud de eliminación: " + e.getMessage());
        }
        return respuesta;
    }

    public static Colaborador obtenerFotoColaborador(Integer idColaborador) {
        Colaborador fotoBase64 = null;
        String urlServicio = Constantes.URL_WS + "colaboradores/obtener-foto/" + idColaborador;
        try {
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                fotoBase64 = gson.fromJson(respuestaWS.getContenido(), Colaborador.class);
                System.out.println("ID Colaborador: " + fotoBase64.getIdColaborador());
                System.out.println("Foto Base64: " + fotoBase64.getFotoBase64());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la foto: " + e.getMessage());
        }
        return fotoBase64;
    }

    public static Mensaje subirFotoColaborador(Integer idColaborador, byte[] fotoBytes) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "colaboradores/subir-foto/" + idColaborador;
        try {
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTBytes(urlServicio, fotoBytes);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la subida de la foto: " + e.getMessage());
        }
        return respuesta;
    }

    public static Mensaje subirFotoColaboradorNuevo(String numeroPersonal, byte[] fotoBytes) {
        Mensaje respuesta = new Mensaje();
        String urlServicio = Constantes.URL_WS + "colaboradores/subir-foto-nuevo/" + numeroPersonal;
        try {
            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTBytes(urlServicio, fotoBytes);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la subida de la foto: " + e.getMessage());
        }
        return respuesta;
    }

    public static Colaborador obtenerColaborador(int idColaborador) {
        Colaborador colaborador = null;
        String url = Constantes.URL_WS + "colaboradores/obtener-colaborador/" + idColaborador;
        RespuestaHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {

                colaborador = gson.fromJson(respuesta.getContenido(), Colaborador.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaborador;
    }

    public static List<Colaborador> obtenerColaboradores() {
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS + "colaboradores/obtenerTodos";
        RespuestaHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>() {
                }.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }

    public static List<Colaborador> obtenerConductores() {
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS + "colaboradores/obtenerConductores";
        RespuestaHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>() {
                }.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
                System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
}
