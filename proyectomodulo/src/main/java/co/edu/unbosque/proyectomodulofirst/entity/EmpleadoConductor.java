package co.edu.unbosque.proyectomodulofirst.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Entity;

/**
 * Entidad que representa a un empleado conductor.
 * Extiende Empleado añadiendo el tipo de vehículo asignado al conductor.
 */
@Entity
public class EmpleadoConductor extends Empleado {

    private String tipoVehiculo;

    public EmpleadoConductor() {
        super();
    }

    /**
     * Constructor que inicializa todos los campos del empleado conductor.
     *
     * @param nombre nombre del empleado conductor
     * @param edad edad del empleado conductor
     * @param fechaInicio fecha de inicio del empleado conductor
     * @param tipoVehiculo tipo de vehículo asignado al conductor
     */
    public EmpleadoConductor(String nombre, int edad, LocalDateTime fechaInicio, String tipoVehiculo) {
        super(nombre, edad, fechaInicio);
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    @Override
    public String toString() {
        return "EmpleadoConductor [tipoVehiculo=" + tipoVehiculo + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(tipoVehiculo);
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
        EmpleadoConductor other = (EmpleadoConductor) obj;
        return Objects.equals(tipoVehiculo, other.tipoVehiculo);
    }
}