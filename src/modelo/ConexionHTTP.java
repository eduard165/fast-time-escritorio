/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import utils.Constantes;

/**
 *
 * @author eduar
 */
public class ConexionHTTP {

    public static RespuestaHTTP peticionGET(String url) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); 
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("GET"); 
            int codigoRespuesta = conexionHttp.getResponseCode(); 
            respuesta.setCodigoRespuesta(codigoRespuesta); 
            System.out.println("Codigo WS: " + codigoRespuesta); 

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); 
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); 
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); 
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); 
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; 
    }

    public static RespuestaHTTP peticionPOST(String url, String parametros) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); 
        try {
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("POST"); 
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Define el tipo de contenido
            conexionHttp.setDoOutput(true); // Habilita el envío de datos

            // Enviar los parámetros de la petición
            OutputStream os = conexionHttp.getOutputStream(); // habilitas el modo escritura del cuerpo de la peticion
            os.write(parametros.getBytes());
            os.flush();
            os.close();

            int codigoRespuesta = conexionHttp.getResponseCode(); // Obtiene el código de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta); // Asigna el código de respuesta

            // Verifica si la respuesta fue exitosa
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); // Asigna el contenido de la respuesta
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); // Mensaje de error en caso de fallo
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); // Error si la URL es inválida
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); // Error si hay problemas en la conexión
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; // Devuelve la respuesta de la petición POST
    }

    // Método para realizar una petición PUT a una URL
    public static RespuestaHTTP peticionPUT(String url, String parametros) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); // Objeto para almacenar la respuesta HTTP
        try {
            // Crear una conexión a la URL especificada
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("PUT"); // Establece el método de la petición como PUT
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Define el tipo de contenido
            conexionHttp.setDoOutput(true); // Habilita el envío de datos

            // Enviar los parámetros de la petición
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();

            int codigoRespuesta = conexionHttp.getResponseCode(); // Obtiene el código de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta); // Asigna el código de respuesta

            // Verifica si la respuesta fue exitosa
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); // Asigna el contenido de la respuesta
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); // Mensaje de error en caso de fallo
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); // Error si la URL es inválida
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); // Error si hay problemas en la conexión
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; // Devuelve la respuesta de la petición PUT
    }

    // Método para realizar una petición DELETE a una URL
    public static RespuestaHTTP peticionDELETE(String url, String parametros) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); // Objeto para almacenar la respuesta HTTP
        try {
            // Crear una conexión a la URL especificada
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("DELETE"); // Establece el método de la petición como DELETE
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Define el tipo de contenido
            conexionHttp.setDoOutput(true); // Habilita el envío de datos

            // Enviar los parámetros de la petición
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();

            int codigoRespuesta = conexionHttp.getResponseCode(); // Obtiene el código de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta); // Asigna el código de respuesta

            // Verifica si la respuesta fue exitosa
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); // Asigna el contenido de la respuesta
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); // Mensaje de error en caso de fallo
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); // Error si la URL es inválida
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); // Error si hay problemas en la conexión
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; // Devuelve la respuesta de la petición DELETE
    }

    public static RespuestaHTTP peticionDELETE(String url) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); // Objeto para almacenar la respuesta HTTP
        try {
            // Crear una conexión a la URL especificada
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("DELETE"); // Establece el método de la petición como DELETE
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Define el tipo de contenido

            int codigoRespuesta = conexionHttp.getResponseCode(); // Obtiene el código de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta); // Asigna el código de respuesta

            // Verifica si la respuesta fue exitosa
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); // Asigna el contenido de la respuesta
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); // Mensaje de error en caso de fallo
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); // Error si la URL es inválida
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); // Error si hay problemas en la conexión
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; // Devuelve la respuesta de la petición DELETE
    }

    // Método para realizar una petición POST a una URL
    public static RespuestaHTTP peticionPOSTJson(String url, String parametros) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); // Objeto para almacenar la respuesta HTTP
        try {
            // Crear una conexión a la URL especificada
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("POST"); // Establece el método de la petición como POST
            conexionHttp.setRequestProperty("Content-Type", "application/json"); // Define el tipo de contenido
            conexionHttp.setDoOutput(true); // Habilita el envío de datos

            // Enviar los parámetros de la petición
            OutputStream os = conexionHttp.getOutputStream(); // habilitas el modo escritura del cuerpo de la peticion
            os.write(parametros.getBytes());
            os.flush();
            os.close();

            int codigoRespuesta = conexionHttp.getResponseCode(); // Obtiene el código de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta); // Asigna el código de respuesta

            // Verifica si la respuesta fue exitosa
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); // Asigna el contenido de la respuesta
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); // Mensaje de error en caso de fallo
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); // Error si la URL es inválida
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); // Error si hay problemas en la conexión
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; // Devuelve la respuesta de la petición POST
    }

    // Método para realizar una petición PUT a una URL
    public static RespuestaHTTP peticionPUTJSON(String url, String parametros) {
        RespuestaHTTP respuesta = new RespuestaHTTP(); // Objeto para almacenar la respuesta HTTP
        try {
            // Crear una conexión a la URL especificada
            URL urlDestino = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
            conexionHttp.setRequestMethod("PUT"); // Establece el método de la petición como PUT
            conexionHttp.setRequestProperty("Content-Type", "application/json"); // Define el tipo de contenido
            conexionHttp.setDoOutput(true); // Habilita el envío de datos

            // Enviar los parámetros de la petición
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();

            int codigoRespuesta = conexionHttp.getResponseCode(); // Obtiene el código de respuesta
            respuesta.setCodigoRespuesta(codigoRespuesta); // Asigna el código de respuesta

            // Verifica si la respuesta fue exitosa
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream())); // Asigna el contenido de la respuesta
            } else {
                respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta); // Mensaje de error en caso de fallo
            }
        } catch (MalformedURLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL); // Error si la URL es inválida
            respuesta.setContenido("Error en la dirección de conexión.");
        } catch (IOException io) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION); // Error si hay problemas en la conexión
            respuesta.setContenido("Error: no se pudo realizar la solicitud correspondiente.");
        }
        return respuesta; // Devuelve la respuesta de la petición PUT
    }

    public static RespuestaHTTP peticionPUTBytes(String url, byte[] datos) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHttp = (HttpURLConnection) urlServicio.openConnection();
            conexionHttp.setRequestMethod("PUT");
            conexionHttp.setRequestProperty("Content-Type", "image/jpeg");
            conexionHttp.setDoOutput(true);

            try (OutputStream os = conexionHttp.getOutputStream()) {
                os.write(datos, 0, datos.length);
            }

            respuesta.setCodigoRespuesta(conexionHttp.getResponseCode());
            respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));

        } catch (IOException e) {
            respuesta.setCodigoRespuesta(HttpURLConnection.HTTP_INTERNAL_ERROR);
            respuesta.setContenido("Error en la petición: " + e.getMessage());
        }
        return respuesta;
    }

    // Método privado para obtener el contenido de la respuesta del servicio web
    private static String obtenerContenidoWS(InputStream inputWS) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputWS)); // Lee la respuesta
        String inputLine;
        StringBuffer respuestaEntrada = new StringBuffer(); // Acumulador de la respuesta
        while ((inputLine = in.readLine()) != null) { // Lee cada línea de la respuesta
            respuestaEntrada.append(inputLine);
        }
        in.close(); // Cierra el lector
        return respuestaEntrada.toString(); // Devuelve la respuesta completa como String
    }

}
