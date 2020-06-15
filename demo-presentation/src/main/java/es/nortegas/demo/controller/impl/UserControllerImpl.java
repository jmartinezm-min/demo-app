/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.controller.impl;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import es.nortegas.demo.controller.UserController;
import es.nortegas.demo.service.UserService;
import es.nortegas.demo.service.dto.AccreditedUserDTO;
import es.nortegas.demo.service.dto.CredentialsDTO;
import es.nortegas.demo.service.dto.TokenDTO;
import es.nortegas.demo.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * Api de servicios comun para todos los componentes del proyecto.
 * 
 * Va a definir los métodos para publicar
 * 
 * @author NORTEGAS
 *
 */
@RestController
@Slf4j
public class UserControllerImpl implements UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "userId") Long userId) {
        log.info("Recuperamos la información del usuario: {}", userId);
        return ResponseEntity.ok(userService.findById(userId));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Page<UserDTO>> findAll(
            @RequestParam(value = "nif", required = false) String nif,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname1", required = false) String surname1,
            @RequestParam(value = "surname2", required = false) String surname2,
            @RequestParam(value = "status", required = false) String status,
            @PageableDefault(size = Integer.MAX_VALUE, sort = "surname1") Pageable pageable) {
        log.info("Obtenemos la lista de usuarios");
        return ResponseEntity
                .ok(userService.search(nif, name, surname1, surname2, status, pageable));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDTO> create(@Valid @RequestBody AccreditedUserDTO userDTO) {
        log.info("Damos de alta al usuario {}", userDTO.getName());
        UserDTO user = this.userService.create(userDTO);
        
        URI uri = MvcUriComponentsBuilder
                .fromMethodName(UserController.class, "findById", user.getId())
                .buildAndExpand(user.getId()).toUri();
        
        return ResponseEntity.created(uri).build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDTO> delete(@PathVariable(name = "userId") Long userId) {
        log.info("Damos de baja al usuario {}", userId);
        this.userService.delete(userId);
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDTO> update(@PathVariable(name = "userId") Long userId,
            @RequestBody AccreditedUserDTO userDTO) {
        log.info("Modificamos la información del usuario {}", userId);
        this.userService.update(userId, userDTO);
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<TokenDTO> session(@Valid @RequestBody CredentialsDTO credentialsDTO) {
        log.info("Obtenemosm el token del usuario {}", credentialsDTO.getUserName());
        TokenDTO token = this.userService.login(credentialsDTO);
        
        return ResponseEntity.ok(token);
    }
    
}
