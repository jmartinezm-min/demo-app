package es.nortegas.demo.persistence.entity;

/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados. 
 * 
 ******************************************************************************/

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.nortegas.demo.persistence.enums.EnumEntityStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase de define los atributos genéricos para toda entidad
 * 
 * @author NORTEGAS
 *
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners({ AuditingEntityListener.class })
public abstract class BaseEntity implements Serializable{
    
	private static final long serialVersionUID = -1015082169143016743L;

	@Column(name = "ENTRY_DATE", nullable = false)
    private LocalDateTime dtEntry;
        
    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    private LocalDateTime dtModified;
    
    @Column(name = "CANCEL_DATE")
    private LocalDateTime dtCancel;
    
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EnumEntityStatus status;
    
    @PrePersist
    public void onPrePersist() {
        if (dtEntry == null) {
            this.dtEntry = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    public void onPreUpdate() {
        this.dtModified = LocalDateTime.now();
    }
}
