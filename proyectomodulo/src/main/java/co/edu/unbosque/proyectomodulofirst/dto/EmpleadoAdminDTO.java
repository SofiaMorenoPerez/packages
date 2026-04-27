package co.edu.unbosque.proyectomodulofirst.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO que representa a un empleado administrativo.
 * Extiende EmpleadoDTO añadiendo la zona asignada al administrativo.
 */
public class EmpleadoAdminDTO extends EmpleadoDTO {

    private String zonaAsignada;

    public EmpleadoAdminDTO() {
        super();
    }

    /**
     * Constructor que inicializa todos los campos del empleado administrativo.
     *
     * @param nombre nombre del empleado administrativo
     * @param edad edad del empleado administrativo
     * @param fechaInicio fecha de inicio del empleado administrativo
     * @param zonaAsignada zona asignada al empleado administrativo
     */
    public EmpleadoAdminDTO(String nombre, int edad, LocalDateTime fechaInicio, String zonaAsignada) {
        super(nombre, edad, fechaInicio);
        this.zonaAsignada = zonaAsignada;
    }

    public String getZonaAsignada() {
        return zonaAsignada;
    }

    public void setZonaAsignada(String zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }

    @Override
    public String toString() {
        return "EmpleadoAdminDTO [zonaAsignada=" + zonaAsignada + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(zonaAsignada);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmpleadoAdminDTO other = (EmpleadoAdminDTO) obj;
        return Objects.equals(zonaAsignada, other.zonaAsignada);
    }
}