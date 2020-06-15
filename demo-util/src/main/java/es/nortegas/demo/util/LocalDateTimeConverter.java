/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados. 
 * 
 ******************************************************************************/

package es.nortegas.demo.util;

import java.time.LocalDateTime;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * Clase que nos permite convertir, usando Dozer, de un LocalDateTime
 * 
 * @author NORTEGAS
 *
 */
public class LocalDateTimeConverter implements CustomConverter {
    
    /** 
     * Cómo LocalDateTime es inmutable, simplemente pasamos la referencia del objeto
     * 
     */
	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, 
			Class<?> destinationClass, Class<?> sourceClass) {
		if (sourceFieldValue == null) {
			return null;
		}
		if (destinationClass != null) {
			if (destinationClass.getSimpleName().equalsIgnoreCase(LocalDateTime.class.getSimpleName())) {
				return sourceFieldValue;
			} else {
				throw new MappingException(new StringBuilder("Converter ")
						.append(this.getClass().getSimpleName())
						.append(" was used incorrectly. Arguments were: ")
						.append(destinationClass.getClass().getName())
						.append(" and ").append(sourceFieldValue).toString());
			}
		}
		return null;
	}
}
