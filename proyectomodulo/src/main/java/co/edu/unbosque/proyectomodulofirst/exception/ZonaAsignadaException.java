package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con la zona asignada.
 */
public class ZonaAsignadaException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public ZonaAsignadaException(String mensaje) {
        super(mensaje);
    }
}