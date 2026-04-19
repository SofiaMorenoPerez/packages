package co.edu.unbosque.proyectomodulofirst.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Entidad base que representa a un empleado del sistema.
 * Utiliza herencia de tipo JOINED para las subclases de empleado.
 * Contiene los atributos comunes compartidos por todos los tipos de empleados.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "empleado")
public class Empleado {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String nombre;
    private int edad;
    private LocalDateTime fechaInicio;

    public Empleado() {
    }

    /**
     * Constructor que inicializa los campos principales del empleado.
     *
     * @param nombre nombre del empleado
     * @param edad edad del empleado
     * @param fechaInicio fecha de inicio del empleado
     */
    public Empleado(String nombre, int edad, LocalDateTime fechaInicio) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.fechaInicio = fechaInicio;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", fechaInicio=" + fechaInicio + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(edad, fechaInicio, id, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empleado other = (Empleado) obj;
        return edad == other.edad && Objects.equals(fechaInicio, other.fechaInicio)
                && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
    }
}