/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados. 
 * 
 ******************************************************************************/

package es.nortegas.demo.exception;

/**
 * Interfaz de la que heredaran las excepciones de la aplicación
 * 
 * @author NORTEGAS
 */
public interface GenericException {

	/**
	 * Retorna el código de error.
	 * @return Código que identifica el error producido
	 */
	ErrorCode getErrorCode();
}
