/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.nortegas.demo.service.dto.AccreditedUserDTO;
import es.nortegas.demo.service.dto.CredentialsDTO;
import es.nortegas.demo.service.dto.TokenDTO;
import es.nortegas.demo.service.dto.UserDTO;

/**
 * Api de servicios comun para todos los componentes del proyecto.
 * 
 * Va a definir los métodos para publicar
 * 
 * @author NORTEGAS
 *
 */
@RequestMapping(path = UserController.ROOT_USER_ENDPOINT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserController {
    
    /**
     * Endpoint raiz de los servicios expuestos
     */
    public static final String ROOT_USER_ENDPOINT_URL = "/users";
    
    public static final String USER_ID_ENDPOINT_URL = "/{userId}";
    public static final String LOGIN_ENDPOINT_URL = "/auth";
    
    /**
     * Obtiene el detalle de un usuario
     * 
     * @param userId
     *        identificador del usuario
     * @return Detalle con la información del usuario
     * @throws Exception
     */
    @GetMapping(UserController.USER_ID_ENDPOINT_URL)
    ResponseEntity<UserDTO> findById(Long userId);
    
    /**
     * Obtiene el listado de usuarios que cumplan los filtros seleccionados.
     * 
     * @param nif
     *        NIF del usuario
     * @param name
     *        Nombre del usuario
     * @param surname1
     *        Primer apellido del usuario
     * @param surname2
     *        Segundo apellido del usuario
     * @param status
     *        Indica si es activo o inactivo
     * @param pageable
     *        Opciones de página
     * 
     * @return Listado con los usuarios que coinciden con los filtros de búsqueda
     */
    @GetMapping
    ResponseEntity<Page<UserDTO>> findAll(final String nif, final String name,
            final String surname1, final String surname2, final String status,
            final Pageable pageable);
    
    /**
     * Permite dar de alta un usuario en el sistema.
     * 
     * @param userDTO
     *        Detalles del usuario a dar de alta
     * 
     * @return Devuelve en el parámetro de cabecera <b>location</b> la forma de de obtener al
     *         usuario creado
     */
    @PostMapping
    ResponseEntity<UserDTO> create(AccreditedUserDTO userDTO);
    
    /**
     * Da de baja lógica al usuario indicado por su identificador.
     * 
     * @param userId
     *        Identificador del usuario a dar de baja
     * 
     * @return Devuelve un 204 si todo ha ido bien
     */
    @DeleteMapping(UserController.USER_ID_ENDPOINT_URL)
    ResponseEntity<UserDTO> delete(Long userId);
    
    /**
     * Método que permite actualizar la información de un usuario
     * 
     * @param userId
     *        Identificador del usuario a modificar
     * @param userDTO
     *        Información del usuario a modificar
     * 
     * @return Devuelve un 204 si todo ha ido bien
     */
    @PatchMapping(UserController.USER_ID_ENDPOINT_URL)
    ResponseEntity<UserDTO> update(Long userId, AccreditedUserDTO userDTO);
    
    /**
     * Permite realizar el login de un usuario. La forma correcta de hacerlo es usando Spring
     * Security y Outh.
     * 
     * @param credentialsDTO
     *        Información de logeo en la aplicación
     * @return Token del usuario
     */
    @PostMapping(UserController.LOGIN_ENDPOINT_URL)
    ResponseEntity<TokenDTO> session(CredentialsDTO credentialsDTO);
}
