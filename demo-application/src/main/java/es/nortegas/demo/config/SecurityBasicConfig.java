/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase de configuración del contexto de seguridad de las aplicaciones.
 * 
 * @author NORTEGAS
 *
 */
@Slf4j
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    
    @Bean
    public BasicAuthenticationEntryPoint basicAuthEntryPoint() {
        BasicAuthenticationEntryPoint basicAuthEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthEntryPoint.setRealmName("Basic realm");
        return basicAuthEntryPoint;
    }
    
    /**
     * Realiza la configuración de seguridad de la aplicación.
     * <p>
     * Por defecto la aplicación se configura como sigue:
     * <ul>
     * <li>Se incluye el filtro que recupera el username de la petición
     * <li>Para poder accedera cualquier url de la aplicación el cliente debe estar authenticado.
     * <li>Si no está autenticado no podrá entrar y se mostrará un mensaje error.
     * <li>No es necesario que en la petición venga el token csrf <b>(Cross-site equest forgery o
     * falsificación de petición en sitios cruzado)</b>
     * <li>Se configura el handler para mostrar el mensaje de error de acceso para la aplicación.
     * </ul>
     * Si la aplicación necesita otro tipo de configuración, solo será necesario sobreescribir este
     * método.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and().authorizeRequests()
                .antMatchers("/users/auth/**").permitAll().antMatchers("/h2-console/**").permitAll()
                .antMatchers("/health").permitAll().antMatchers("/info").permitAll().anyRequest()
                .authenticated().and().httpBasic().authenticationEntryPoint(authEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable().formLogin().disable();
    }
    
    @PostConstruct
    public void init() {
        log.info("start security basic");
    }
}
