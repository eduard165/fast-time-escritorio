package modelo.pojo;
public class Direccion {

    private Integer idDireccion;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private String municipio;
    private String estado;
    private Integer idMunicipio;
    private Integer idCliente;
    private Integer idEnvioOrigen;
    private Integer idEnvioDestino;
    private Integer idEstado;

    public Direccion() {
    }

    public Direccion(Integer idDireccion, String calle, String numero, String colonia, String codigoPostal, String municipio, String estado, Integer idMunicipio, Integer idCliente, Integer idEnvioOrigen, Integer idEnvioDestino, Integer idEstado) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.municipio = municipio;
        this.estado = estado;
        this.idMunicipio = idMunicipio;
        this.idCliente = idCliente;
        this.idEnvioOrigen = idEnvioOrigen;
        this.idEnvioDestino = idEnvioDestino;
        this.idEstado = idEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

   

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdEnvioOrigen() {
        return idEnvioOrigen;
    }

    public void setIdEnvioOrigen(Integer idEnvioOrigen) {
        this.idEnvioOrigen = idEnvioOrigen;
    }

    public Integer getIdEnvioDestino() {
        return idEnvioDestino;
    }

    public void setIdEnvioDestino(Integer idEnvioDestino) {
        this.idEnvioDestino = idEnvioDestino;
    }

    @Override
    public String toString() {
        return "Direccion{" + "idDireccion=" + idDireccion + ", calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + ", municipio=" + municipio + ", estado=" + estado + ", idMunicipio=" + idMunicipio + ", idCliente=" + idCliente + ", idEnvioOrigen=" + idEnvioOrigen + ", idEnvioDestino=" + idEnvioDestino + ", idEstado=" + idEstado + '}';
    }

    
}
