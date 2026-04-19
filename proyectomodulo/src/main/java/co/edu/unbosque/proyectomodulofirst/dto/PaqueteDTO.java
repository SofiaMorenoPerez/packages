package co.edu.unbosque.proyectomodulofirst.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import co.edu.unbosque.proyectomodulofirst.enums.TipoDePaquete;

/**
 * DTO que representa un paquete dentro del sistema de envíos.
 * Contiene la información del remitente, los empleados asignados,
 * las direcciones de origen y destino, y los datos del envío.
 */
public class PaqueteDTO {

    private long id;
    private long idUsuario;
    private long idConductor;
    private long idManipulador;
    private String ciudadDeOrigen;
    private String ciudadDeDestino;
    private String direccionDeOrigen;
    private String direccionDeDestino;
    private LocalDateTime fechaEnvio;
    private TipoDePaquete tipo;
    private double peso;
    private int maxHoras;
    private int tiempo;

    public PaqueteDTO() {
    }

    /**
     * Constructor que inicializa los campos principales del paquete.
     *
     * @param idUsuario identificador del usuario que realiza el envío
     * @param idConductor identificador del conductor asignado al envío
     * @param idManipulador identificador del manipulador asignado al envío
     * @param ciudadDeOrigen ciudad de origen del paquete
     * @param ciudadDeDestino ciudad de destino del paquete
     * @param direccionDeOrigen dirección de origen del paquete
     * @param direccionDeDestino dirección de destino del paquete
     * @param tipo tipo de paquete según el enum TipoDePaquete
     * @param peso peso del paquete en kilogramos
     */
    public PaqueteDTO(long idUsuario, long idConductor, long idManipulador, String ciudadDeOrigen,
            String ciudadDeDestino, String direccionDeOrigen, String direccionDeDestino,
            TipoDePaquete tipo, double peso) {
        super();
        this.idUsuario = idUsuario;
        this.idConductor = idConductor;
        this.idManipulador = idManipulador;
        this.ciudadDeOrigen = ciudadDeOrigen;
        this.ciudadDeDestino = ciudadDeDestino;
        this.direccionDeOrigen = direccionDeOrigen;
        this.direccionDeDestino = direccionDeDestino;
        this.tipo = tipo;
        this.peso = peso;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }
    public long getIdConductor() { return idConductor; }
    public void setIdConductor(long idConductor) { this.idConductor = idConductor; }
    public long getIdManipulador() { return idManipulador; }
    public void setIdManipulador(long idManipulador) { this.idManipulador = idManipulador; }
    public String getCiudadDeOrigen() { return ciudadDeOrigen; }
    public void setCiudadDeOrigen(String ciudadDeOrigen) { this.ciudadDeOrigen = ciudadDeOrigen; }
    public String getCiudadDeDestino() { return ciudadDeDestino; }
    public void setCiudadDeDestino(String ciudadDeDestino) { this.ciudadDeDestino = ciudadDeDestino; }
    public String getDireccionDeOrigen() { return direccionDeOrigen; }
    public void setDireccionDeOrigen(String direccionDeOrigen) { this.direccionDeOrigen = direccionDeOrigen; }
    public String getDireccionDeDestino() { return direccionDeDestino; }
    public void setDireccionDeDestino(String direccionDeDestino) { this.direccionDeDestino = direccionDeDestino; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public TipoDePaquete getTipo() { return tipo; }
    public void setTipo(TipoDePaquete tipo) { this.tipo = tipo; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public int getMaxHoras() { return maxHoras; }
    public void setMaxHoras(int maxHoras) { this.maxHoras = maxHoras; }
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    @Override
    public String toString() {
        return "PaqueteDTO [id=" + id + ", idUsuario=" + idUsuario + ", idConductor=" + idConductor
                + ", idManipulador=" + idManipulador + ", ciudadDeOrigen=" + ciudadDeOrigen
                + ", ciudadDeDestino=" + ciudadDeDestino + ", direccionDeOrigen=" + direccionDeOrigen
                + ", direccionDeDestino=" + direccionDeDestino + ", fechaEnvio=" + fechaEnvio
                + ", tipo=" + tipo + ", peso=" + peso + ", maxHoras=" + maxHoras + ", tiempo=" + tiempo + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciudadDeDestino, ciudadDeOrigen, direccionDeDestino, direccionDeOrigen,
                fechaEnvio, id, idConductor, idManipulador, idUsuario, maxHoras, peso, tiempo, tipo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaqueteDTO other = (PaqueteDTO) obj;
        return Objects.equals(ciudadDeDestino, other.ciudadDeDestino)
                && Objects.equals(ciudadDeOrigen, other.ciudadDeOrigen)
                && Objects.equals(direccionDeDestino, other.direccionDeDestino)
                && Objects.equals(direccionDeOrigen, other.direccionDeOrigen)
                && Objects.equals(fechaEnvio, other.fechaEnvio) && id == other.id
                && idConductor == other.idConductor && idManipulador == other.idManipulador
                && idUsuario == other.idUsuario && maxHoras == other.maxHoras
                && Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso)
                && tiempo == other.tiempo && tipo == other.tipo;
    }
}