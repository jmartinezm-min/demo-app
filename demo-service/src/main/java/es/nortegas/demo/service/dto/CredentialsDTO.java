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
 * Bean con la información de autenticación
 * 
 * @author NORTEGAS
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CredentialsDTO implements Serializable {
    
    private static final long serialVersionUID = 8205382316354746356L;
    
    private String userName;
    private String password;
}
