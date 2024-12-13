package modelo.pojo;

public class Mensaje {

    private boolean error;
    private String contenido;

    public Mensaje() {
    }

    public Mensaje(boolean error, String contenido) {
        this.error = error;
        this.contenido = contenido;
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

}
