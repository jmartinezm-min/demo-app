/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.nortegas.demo.service.dto.AccreditedUserDTO;
import es.nortegas.demo.service.dto.CredentialsDTO;
import es.nortegas.demo.service.dto.TokenDTO;
import es.nortegas.demo.service.dto.UserDTO;

/**
 * Definición de los servicios de negocio de gestión de usuario
 * 
 * @author NORTEGAS
 *
 */
public interface UserService {
    
    /**
     * Obtiene el detalle de un usuario.
     * 
     * @param id
     *        Identificador del usuario
     * @return Detalle con toda la información del usuario
     * @throws Exception
     */
    UserDTO findById(Long id);
    
    /**
     * Obtiene la lista de usuarios que corresponden con los filtros pasados como argumentos
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
     *        Estado ACTIVO o INACTIVO
     * @param pageable
     *        Opciones de página
     * @return Lista de los usuarios que coinciden con los filtros indicados
     */
    public Page<UserDTO> search(final String nif, final String name, final String surname1,
            final String surname2, final String status, final Pageable pageable);
    
    /**
     * Permite dar de alta un usuario en el sistema.
     * 
     * @param userDTO
     *        Detalles del usuario a dar de alta
     * 
     * @return Detalle del usuario con el id de BD informado
     */
    UserDTO create(AccreditedUserDTO userDTO);
    
    /**
     * Da de baja lógica al usuario indicado por su identificador.
     * 
     * @param userId
     *        Identificador del usuario a dar de baja
     */
    void delete(Long userId);
    
    /**
     * Método que permite actualizar la información de un usuario
     * 
     * @param userId
     *        Identificador del usuario a modificar
     * @param userDTO
     *        Información del usuario a modificar
     */
    void update(Long userId, AccreditedUserDTO userDTO);
    
    TokenDTO login(CredentialsDTO credentialsDTO);
}
