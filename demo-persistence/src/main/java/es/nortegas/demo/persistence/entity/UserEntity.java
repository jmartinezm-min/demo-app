/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Clase que denine la tabla USERS de la BD
 * 
 * @author NORTEGAS
 *
 */
@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "SQ_USERS", sequenceName = "SQ_USERS", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity {
    
    private static final long serialVersionUID = -7748708067287506269L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USERS")
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;
    
    @Column(name = "SURNAME1", length = 50)
    private String surname1;
    
    @Column(name = "SURNAME2", length = 50)
    private String surname2;
    
    @Column(name = "EMAIL", length = 255)
    private String email;
    
    @Column(name = "PHONE_NUMBER", length = 15)
    private String phoneNumber;
    
    @Column(name = "NIF", nullable = false, length = 11)
    private String nif;
    
    @Column(name = "NICKNAME", nullable = false, length = 255)
    private String nickname;
    
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;
    
}
