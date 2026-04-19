package co.edu.unbosque.proyectomodulofirst.entity;

import java.util.Objects;
import co.edu.unbosque.proyectomodulofirst.enums.TipoDeUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un usuario del sistema de envíos.
 * Contiene la información personal y de contacto del usuario,
 * así como su tipo y tarifa asociada.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
    private String nombre;
    private @Enumerated(EnumType.STRING) TipoDeUsuario tipo;
    private double tarifa;
    private String ciudad;
    private String direccion;
    private long telefono;

    public Usuario() {
    }

    /**
     * Constructor que inicializa los campos principales del usuario.
     *
     * @param nombre nombre del usuario
     * @param tipo tipo de usuario según el enum TipoDeUsuario
     * @param ciudad ciudad de residencia del usuario
     * @param direccion dirección del usuario
     * @param telefono número de teléfono del usuario
     */
    public Usuario(String nombre, TipoDeUsuario tipo, String ciudad, String direccion, long telefono) {
        super();
        this.nombre = nombre;
        this.tipo = tipo;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoDeUsuario getTipo() { return tipo; }
    public void setTipo(TipoDeUsuario tipo) { this.tipo = tipo; }
    public double getTarifa() { return tarifa; }
    public void setTarifa(double tarifa) { this.tarifa = tarifa; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public long getTelefono() { return telefono; }
    public void setTelefono(long telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", tarifa=" + tarifa
                + ", ciudad=" + ciudad + ", direccion=" + direccion + ", telefono=" + telefono + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciudad, direccion, id, nombre, tarifa, telefono, tipo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(ciudad, other.ciudad) && Objects.equals(direccion, other.direccion)
                && id == other.id && Objects.equals(nombre, other.nombre)
                && Double.doubleToLongBits(tarifa) == Double.doubleToLongBits(other.tarifa)
                && telefono == other.telefono && tipo == other.tipo;
    }
}