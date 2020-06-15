/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bean con la información de credenciales del usuario
 * 
 * @author NORTEGAS
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccreditedUserDTO extends UserDTO {
    
    private static final long serialVersionUID = 8105382316354746356L;
    
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 255)
    private String password;
}
