/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Clase de para activar la configuración de seguridad con autenticación BASIC de la aplicación.
 * Esta activación se hace al anotar esta clase como {@link Configuration} y
 * {@link EnableWebSecurity}. Para permitir la seguridad por medio de anotaciones en las clases se
 * realiza mediante {@link EnableGlobalMethodSecurity}
 * 
 * 
 * @author NORTEGAS
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityBasicConfig extends SecurityBasicConfig {
    
}
