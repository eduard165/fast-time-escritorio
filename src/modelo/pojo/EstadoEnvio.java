package modelo.pojo;

public class EstadoEnvio {

    private Integer idEstadoEnvio;
    private String nombre;

    public EstadoEnvio() {
    }

    public EstadoEnvio(Integer idEstadoEnvio, String nombre) {
        this.idEstadoEnvio = idEstadoEnvio;
        this.nombre = nombre;
    }

    public Integer getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(Integer idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;  // Esto hará que el ComboBox solo muestre el nombre
    }
}
