
package modelo.dao;

import java.net.HttpURLConnection;
import modelo.ConexionHTTP;
import modelo.pojo.respuestasPojos.RespuestaColaborador;
import modelo.pojo.respuestasPojos.RespuestaHTTP;
import utils.Constantes;
import com.google.gson.Gson;

public class LoginDAO {
     public static RespuestaColaborador iniciarSesion(String numeroPersonal, String password){
        RespuestaColaborador respuestaLogin = new RespuestaColaborador();
        String urlServicio = Constantes.URL_WS+"login/colaborador";
        String parametros = String.format("numeroPersonal=%s&password=%s", numeroPersonal, password);
        RespuestaHTTP respuestaWS = ConexionHTTP.peticionPOST(urlServicio, parametros);
        if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuestaLogin = gson.fromJson(respuestaWS.getContenido(), RespuestaColaborador.class);
        }else{
            respuestaLogin.setError(true);
            respuestaLogin.setContenido("Lo sentimos, hubo un error al procesar la autenticación, por favor intentelo más tarde");
        }
        return respuestaLogin;
    
    }
}
