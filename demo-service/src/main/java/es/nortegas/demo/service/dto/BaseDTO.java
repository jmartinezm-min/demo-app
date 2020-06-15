/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados. 
 * 
 ******************************************************************************/

package es.nortegas.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.nortegas.demo.service.enums.EnumStatus;
import lombok.Data;

/**
 * Bean con información comun a todos los DTOs
 * 
 * @author NORTEGAS
 *
 */
@Data
public abstract class BaseDTO implements Serializable{       

	private static final long serialVersionUID = -7748708062287506269L;
	
    private LocalDateTime dtEntry;
    private LocalDateTime dtModified;
    private LocalDateTime dtCancel;
    private EnumStatus status;
}
