package co.edu.unbosque.proyectomodulofirst.exception;

/**
 * Excepción personalizada para errores relacionados con el tipo de vehículo.
 */
public class TipoVehiculoException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     * 
     * @param mensaje descripción del error
     */
    public TipoVehiculoException(String mensaje) {
        super(mensaje);
    }
}