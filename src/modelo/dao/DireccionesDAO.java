package modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import modelo.ConexionHTTP;
import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import modelo.pojo.respuestasPojos.RespuestaDireccion;
import modelo.pojo.Estado;
import modelo.pojo.Municipio;
import utils.Constantes;

public class DireccionesDAO {

    // Insertar Dirección Cliente
    public static Mensaje insertarDireccionCliente(Direccion direccion) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);

            System.out.println("[Insertar Dirección Cliente]: " + parametrosJSON);

            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(Constantes.URL_WS + "direcciones/registrar-cliente", parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
            System.err.println("[Error]: " + e.getMessage());
        }
        return respuesta;
    }

    // Insertar Dirección Origen
    public static Mensaje insertarDireccionOrigen(Direccion direccion) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);

            System.out.println("[Insertar Dirección Origen]: " + parametrosJSON);

            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(Constantes.URL_WS + "direcciones/registrar-origen", parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
            System.err.println("[Error]: " + e.getMessage());
        }
        return respuesta;
    }

    // Insertar Dirección Destino
    public static Mensaje insertarDireccionDestino(Direccion direccion) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);

            System.out.println("[Insertar Dirección Destino]: " + parametrosJSON);

            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOSTJson(Constantes.URL_WS + "direcciones/registrar-destino", parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
            System.err.println("[Error]: " + e.getMessage());
        }
        return respuesta;
    }

    // Editar Dirección Cliente
    public static Mensaje editarDireccionCliente(Direccion direccion) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);

            System.out.println("[Editar Dirección Cliente]: " + parametrosJSON);

            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(Constantes.URL_WS + "direcciones/editar-cliente", parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
            System.err.println("[Error]: " + e.getMessage());
        }
        return respuesta;
    }

    // Editar Dirección Origen
    public static Mensaje editarDireccionOrigen(Direccion direccion) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);

            System.out.println("[Editar Dirección Origen]: " + parametrosJSON);

            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(Constantes.URL_WS + "direcciones/editar-origen", parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
            System.err.println("[Error]: " + e.getMessage());
        }
        return respuesta;
    }

    // Editar Dirección Destino
    public static Mensaje editarDireccionDestino(Direccion direccion) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);

            System.out.println("[Editar Dirección Destino]: " + parametrosJSON);

            RespuestaHTTP respuestaWS = ConexionHTTP.peticionPUTJSON(Constantes.URL_WS + "direcciones/editar-destino", parametrosJSON);

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
            System.err.println("[Error]: " + e.getMessage());
        }
        return respuesta;
    }

    public static RespuestaDireccion buscarDireccionCliente(int idCliente) {
        String urlServicio = Constantes.URL_WS + "direcciones/buscar-cliente/" + idCliente;
        return obtenerRespuestaDireccion(urlServicio);
    }

    public static RespuestaDireccion buscarDireccionOrigen(int idEnvioOrigen) {
        String urlServicio = Constantes.URL_WS + "direcciones/buscar-origen/" + idEnvioOrigen;
        return obtenerRespuestaDireccion(urlServicio);
    }

    public static RespuestaDireccion buscarDireccionDestino(int idEnvioDestino) {
        String urlServicio = Constantes.URL_WS + "direcciones/buscar-destino/" + idEnvioDestino;
        return obtenerRespuestaDireccion(urlServicio);
    }

    public static List<Estado> obtenerEstados() {
        String urlServicio = Constantes.URL_WS + "direcciones/obtenerEstados";
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);
        List<Estado> respuesta = null;

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaEstado = new TypeToken<List<Estado>>() {
            }.getType();
            respuesta = gson.fromJson(respuestaWS.getContenido(), tipoListaEstado);
            return gson.fromJson(respuestaWS.getContenido(), tipoListaEstado);
        }
        return null;
    }

    public static List<Municipio> obtenerMunicipios(int idEstado) {
        String urlServicio = Constantes.URL_WS + "direcciones/obtenerMunicipios/" + idEstado;
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);

        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaMunicipio = new TypeToken<List<Municipio>>() {
            }.getType();
            return gson.fromJson(respuestaWS.getContenido(), tipoListaMunicipio);
        }
        return null;
    }

    private static Mensaje realizarPeticion(String urlServicio, Direccion direccion, String metodo) {
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            String parametrosJSON = gson.toJson(direccion);
            System.out.println("Objeto JSON de Direccion: " + gson.toJson(direccion));

            RespuestaHTTP respuestaWS;

            if (metodo.equals("POST")) {
                respuestaWS = ConexionHTTP.peticionPOSTJson(urlServicio, parametrosJSON);
            } else if (metodo.equals("PUT")) {
                respuestaWS = ConexionHTTP.peticionPUTJSON(urlServicio, parametrosJSON);
            } else {
                throw new UnsupportedOperationException("Método HTTP no soportado");
            }

            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);

            } else {
                respuesta.setError(true);
                respuesta.setContenido("Error en el servidor: " + respuestaWS.getCodigoRespuesta());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido("Error al procesar la dirección: " + e.getMessage());
        }
        return respuesta;
    }

    private static RespuestaDireccion obtenerRespuestaDireccion(String urlServicio) {
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionGET(urlServicio);
        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            return gson.fromJson(respuestaWS.getContenido(), RespuestaDireccion.class);
        }
        return null;
    }
}
