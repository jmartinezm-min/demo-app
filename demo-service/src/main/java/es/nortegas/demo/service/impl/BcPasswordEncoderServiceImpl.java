/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.nortegas.demo.exception.DevelopmentException;
import es.nortegas.demo.exception.ErrorCode;
import es.nortegas.demo.service.BcPasswordEncoderService;

/**
 * Implementación de los servicios de codificación y maching de contraseñas
 * 
 * @author NORTEGAS
 *
 */
@Service
public class BcPasswordEncoderServiceImpl implements BcPasswordEncoderService {
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public BcPasswordEncoderServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public String encode(String plainPassword) {
        String hashedPassword = "";
        
        try {
            //Encoded with BCryptPasswordEncoder
            hashedPassword = passwordEncoder.encode(plainPassword);
            
        } catch (Exception e) {
            throw new DevelopmentException(ErrorCode.INVALID_VALUE);
        }
        return hashedPassword;
    }
    
    @Override
    public boolean matches(String plaintPass, String encodedPass) {
        return passwordEncoder.matches(plaintPass, encodedPass);
    }
    
}
