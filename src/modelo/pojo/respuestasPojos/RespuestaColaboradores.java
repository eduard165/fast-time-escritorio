package modelo.pojo.respuestasPojos;

import java.util.List;
import modelo.pojo.Colaborador;
import modelo.pojo.Colaborador;

public class RespuestaColaboradores {

    private boolean error;
    private String contenido;
    private List<Colaborador> colaboradores;

    public RespuestaColaboradores() {
    }

    public RespuestaColaboradores(boolean error, String contenido, List<Colaborador> colaboradores) {
        this.error = error;
        this.contenido = contenido;
        this.colaboradores = colaboradores;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }


}
