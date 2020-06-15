/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.nortegas.demo.exception.ContentNotFoundException;
import es.nortegas.demo.exception.ErrorCode;
import es.nortegas.demo.exception.ForbiddenException;
import es.nortegas.demo.persistence.entity.UserEntity;
import es.nortegas.demo.persistence.enums.EnumEntityStatus;
import es.nortegas.demo.persistence.repository.UserRepository;
import es.nortegas.demo.service.BcPasswordEncoderService;
import es.nortegas.demo.service.UserService;
import es.nortegas.demo.service.dto.AccreditedUserDTO;
import es.nortegas.demo.service.dto.CredentialsDTO;
import es.nortegas.demo.service.dto.TokenDTO;
import es.nortegas.demo.service.dto.UserDTO;
import es.nortegas.demo.service.enums.EnumStatus;
import es.nortegas.demo.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación de los servicios de negocio de gestión de usuario
 * 
 * @author NORTEGAS
 *
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final BcPasswordEncoderService bcPasswordEncoderService;
    
    @Value(value = "${nortegas.poc.token}")
    private String token;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, Mapper mapper,
            BcPasswordEncoderService bcPasswordEncoderService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.bcPasswordEncoderService = bcPasswordEncoderService;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO findById(Long id) {
        log.debug("Buscamos usuario por el id {}", id);
        Optional<UserEntity> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ContentNotFoundException(ErrorCode.OBJECT_NOT_FOUND,
                    ErrorCode.OBJECT_NOT_FOUND.getMessage());
        }
        return this.convertToObjectDto(user.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UserDTO> search(String nif, String name, String surname1, String surname2,
            String status, Pageable pageable) {
        Page<UserEntity> usersPage = this.userRepository.searchUsers(nif, name, surname1, surname2,
                status, pageable);
        
        log.debug("Recuperamos {} usuarios", usersPage.getContent().size());
        
        return usersPage.map(this::convertToObjectDto);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO create(AccreditedUserDTO userDTO) {
        
        userDTO.setId(null);
        userDTO.setStatus(EnumStatus.ACTIVO);
        
        UserEntity userEntity = this.convertToObjectEntity(userDTO);
        userEntity.setPassword(bcPasswordEncoderService.encode(userDTO.getPassword()));
        userEntity = this.userRepository.save(userEntity);
        
        log.debug("Usuario dado de alta con el Id: {}", userEntity.getId());
        return this.convertToObjectDto(userEntity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long userId) {
        Optional<UserEntity> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ContentNotFoundException(ErrorCode.OBJECT_NOT_FOUND,
                    ErrorCode.OBJECT_NOT_FOUND.getMessage());
        } else {
            user.get().setStatus(EnumEntityStatus.INACTIVO);
            user.get().setDtCancel(LocalDateTime.now());
            this.userRepository.save(user.get());
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Long userId, AccreditedUserDTO userDTO) {
        Optional<UserEntity> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ContentNotFoundException(ErrorCode.OBJECT_NOT_FOUND,
                    ErrorCode.OBJECT_NOT_FOUND.getMessage());
        } else {
            // Obtenemos el usuario de BD
            UserEntity userOld = user.get();
            
            // Obtenemos el usuario con las modificaciones
            UserEntity userNew = this.convertToObjectEntity(userDTO);
            
            List<String> attributesCompare = Arrays.asList("name", "surname1", "surname2", "email",
                    "phoneNumber", "nif", "nickname", "password");
            
            Map<String, List<String>> attributes = ServiceUtils.compareEntities(userOld, userNew,
                    attributesCompare);
            
            log.debug("Changed attributes:" + attributes);
            
            if (!attributes.isEmpty()) {
                
                // Actualizamos la entidad con los nuevos valores. Solo con los que han cambiado
                ServiceUtils.updateEntities(userOld, userNew, attributes.keySet());
                
                // Actualizamos en BD
                userNew = this.userRepository.save(userOld);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TokenDTO login(CredentialsDTO credentialsDTO) {
        TokenDTO tokenDTO = new TokenDTO();
        Optional<UserEntity> userOp = this.userRepository
                .findByNickname(credentialsDTO.getUserName());
        if (!userOp.isPresent()) {
            log.error("Usuario {} no existe en BD ", credentialsDTO.getUserName());
            throw new ForbiddenException(ErrorCode.INVALID_AUTHENTICATION,
                    ErrorCode.INVALID_AUTHENTICATION.getMessage());
        } else {
            // Se comprueba la contraseña
            if (!bcPasswordEncoderService.matches(credentialsDTO.getPassword(),
                    userOp.get().getPassword())) {
                log.error("Contraseña incorrecta del usuario {} no existe en BD",
                        credentialsDTO.getUserName());
                throw new ForbiddenException(ErrorCode.INVALID_AUTHENTICATION,
                        ErrorCode.INVALID_AUTHENTICATION.getMessage());
            }
        }
        tokenDTO.setToken(token);
        return tokenDTO;
    }
    
    /**
     * Método que permite mapear un objeto de tipo Entity a DTO
     * 
     * @param user
     *        Objeto usuario obtenido de BD
     * @return Objeto usuario DTO
     */
    private UserDTO convertToObjectDto(UserEntity user) {
        return mapper.map(user, UserDTO.class);
    }
    
    /**
     * Método que permite mapear un objeto de tipo DTO a Entity
     * 
     * @param user
     *        Objeto usuario DTO
     * @return Objeto de tipo Entity
     */
    private UserEntity convertToObjectEntity(UserDTO user) {
        return mapper.map(user, UserEntity.class);
    }
}
