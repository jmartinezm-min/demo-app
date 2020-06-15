/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.service.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bean con la información del token del usuario
 * 
 * @author NORTEGAS
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TokenDTO implements Serializable {
    
    private static final long serialVersionUID = 8205382316354746356L;
    
    private String token;
}
