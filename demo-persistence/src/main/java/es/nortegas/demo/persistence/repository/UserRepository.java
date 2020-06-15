/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.nortegas.demo.persistence.entity.UserEntity;

/**
 * Interfaz que define el acceso a la tabla de USERS
 * 
 * @author NORTEGAS
 *
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    @Query(value = "SELECT u FROM UserEntity u "
            + "WHERE (:name IS NULL OR UPPER(u.name) LIKE '%'||UPPER(CAST(:name AS text))||'%') "
            + "AND (:nif IS NULL OR UPPER(u.nif) LIKE '%'||UPPER(CAST(:nif AS text))||'%') "
            + "AND (:surname1 IS NULL OR UPPER(u.surname1) LIKE '%'||UPPER(CAST(:surname1 AS text))||'%') "
            + "AND (:surname2 IS NULL OR UPPER(u.surname2) LIKE '%'||UPPER(CAST(:surname2 AS text))||'%') "
            + "AND (:status IS NULL OR UPPER(u.status) LIKE '%'||UPPER(CAST(:status AS text))||'%') ", countQuery = "SELECT count(*) FROM UserEntity u "
                    + "WHERE (:name IS NULL OR UPPER(u.name) LIKE '%'||UPPER(CAST(:name AS text))||'%') "
                    + "AND (:nif IS NULL OR UPPER(u.nif) LIKE '%'||UPPER(CAST(:nif AS text))||'%') "
                    + "AND (:surname1 IS NULL OR UPPER(u.surname1) LIKE '%'||UPPER(CAST(:surname1 AS text))||'%') "
                    + "AND (:surname2 IS NULL OR UPPER(u.surname2) LIKE '%'||UPPER(CAST(:surname2 AS text))||'%') "
                    + "AND (:status IS NULL OR UPPER(u.status) LIKE '%'||UPPER(CAST(:status AS text))||'%') ")
    Page<UserEntity> searchUsers(String nif, String name, String surname1, String surname2,
            String status, Pageable pageable);
    
    Optional<UserEntity> findByNickname(String nickName);
    
}
