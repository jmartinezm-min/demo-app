/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.nortegas.demo.exception.ErrorView;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author NORTEGAS
 *
 */
@Component
@Slf4j
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    
    @Autowired
    private ObjectMapper mapper;
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authEx) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorView errorResponse = new ErrorView("User access.denied.exception",
                HttpStatus.FORBIDDEN.name());
        log.error("Error de autenticación del usuario");
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
        response.flushBuffer();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("ApiPoc");
        super.afterPropertiesSet();
    }
}
