package co.edu.unbosque.proyectomodulofirst.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Entity;

/**
 * Entidad que representa a un empleado manipulador de paquetes.
 * Extiende Empleado añadiendo el tipo de paquete que maneja el manipulador.
 */
@Entity
public class EmpleadoManipulador extends Empleado {

    private String tipoDePaquete;

    public EmpleadoManipulador() {
        super();
    }

    /**
     * Constructor que inicializa todos los campos del empleado manipulador.
     *
     * @param nombre nombre del empleado manipulador
     * @param edad edad del empleado manipulador
     * @param fechaInicio fecha de inicio del empleado manipulador
     * @param tipoDePaquete tipo de paquete que maneja el manipulador
     */
    public EmpleadoManipulador(String nombre, int edad, LocalDateTime fechaInicio, String tipoDePaquete) {
        super(nombre, edad, fechaInicio);
        this.tipoDePaquete = tipoDePaquete;
    }

    /**
     * Constructor que inicializa únicamente el tipo de paquete del manipulador.
     *
     * @param tipoDePaquete tipo de paquete que maneja el manipulador
     */
    public EmpleadoManipulador(String tipoDePaquete) {
        super();
        this.setTipoDePaquete(tipoDePaquete);
    }

    public String getTipoDePaquete() { return tipoDePaquete; }
    public void setTipoDePaquete(String tipoDePaquete) { this.tipoDePaquete = tipoDePaquete; }

    @Override
    public String toString() {
        return "EmpleadoManipulador [tipoDePaquete=" + tipoDePaquete + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(tipoDePaquete);
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
        EmpleadoManipulador other = (EmpleadoManipulador) obj;
        return Objects.equals(tipoDePaquete, other.tipoDePaquete);
    }
}