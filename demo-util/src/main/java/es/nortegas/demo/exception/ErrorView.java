/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados. 
 * 
 ******************************************************************************/

package es.nortegas.demo.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase para mostrar mensaje de error en la llamada de servicios REST
 * 
 * @author NORTEGAS
 *
 */
@Data
@AllArgsConstructor
public class ErrorView implements Serializable {

	private static final long serialVersionUID = -8643686987756944916L;

	/**
	 * Mensaje con el error a mostrar
	 */
	private String errorMessage;
	
	/**
     * Mensaje con el error a mostrar
     */
    private String errorCode;
	
	
}
