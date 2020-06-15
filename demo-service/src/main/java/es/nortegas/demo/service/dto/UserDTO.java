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
 * Bean con la información básica de un usuario
 * 
 * @author NORTEGAS
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends BaseDTO {
    
    private static final long serialVersionUID = 8105382316354746356L;
    
    private Long id;
    
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String name;
    
    @Size(max = 50)
    private String surname1;
    
    @Size(max = 50)
    private String surname2;
    
    @Size(max = 255)
    private String email;
    
    @Size(max = 15)
    private String phoneNumber;
    
    @NotNull
    @NotEmpty
    @Size(max = 11)
    private String nif;
    
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String nickname;
}
