package co.edu.unbosque.proyectomodulofirst.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Entity;

/**
 * Entidad que representa a un empleado administrativo.
 * Extiende Empleado añadiendo la zona asignada al administrativo.
 */
@Entity
public class EmpleadoAdmin extends Empleado {

    private String zonaAsignada;

    public EmpleadoAdmin() {
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
    public EmpleadoAdmin(String nombre, int edad, LocalDateTime fechaInicio, String zonaAsignada) {
        super(nombre, edad, fechaInicio);
        this.zonaAsignada = zonaAsignada;
    }

    public String getZonaAsignada() { return zonaAsignada; }
    public void setZonaAsignada(String zonaAsignada) { this.zonaAsignada = zonaAsignada; }

    @Override
    public String toString() {
        return "EmpleadoAdmin [zonaAsignada=" + zonaAsignada + "]";
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
        EmpleadoAdmin other = (EmpleadoAdmin) obj;
        return Objects.equals(zonaAsignada, other.zonaAsignada);
    }
}