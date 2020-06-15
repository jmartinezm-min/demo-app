/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.service;

/**
 * Definición de los servicios de codificación y maching de password
 * 
 * @author NORTEGAS
 *
 */
public interface BcPasswordEncoderService {
    
    /**
     * Codifica una contraseña en claro usando BCryptPasswordEncoder
     * 
     * @param plainPassword
     *        Contraseña en claro
     * @return Contraseña codificada
     */
    String encode(String plainPassword);
    
    /**
     * Devuelve true si la contraseña en claro es igual a la codificada. Sino false
     * 
     * @param plaintPass
     *        Contraseña en texto en claro
     * @param encodedPass
     *        Contraseña codificada con BCryptPasswordEncoder
     * @return
     */
    boolean matches(String plaintPass, String encodedPass);
    
}
