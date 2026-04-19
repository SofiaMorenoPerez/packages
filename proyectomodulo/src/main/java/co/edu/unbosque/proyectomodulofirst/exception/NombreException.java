package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con el nombre.
 */
public class NombreException extends RuntimeException 
{
    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public NombreException(String mensaje) {
        super(mensaje);
    }
}