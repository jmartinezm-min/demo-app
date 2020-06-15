/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/
package es.nortegas.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase de configuración de la clase de codificación de contraseñas
 * 
 * @author NORTEGAS
 *
 */
@Configuration
public class PasswordEncoderConfiguration {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
