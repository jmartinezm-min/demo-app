package es.nortegas.demo.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;

import es.nortegas.demo.persistence.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ServiceUtils {
    
    /**
     * Instantiates a new service utils.
     */
    private ServiceUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Compare entities.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @return the map
     */
    public static Map<String, List<String>> compareEntities(Object one, Object two) {
        
        return compareEntities(one, two, null, null);
        
    }
    
    /**
     * Compare entities.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param attributesCompare
     *        the attributes compare
     * @return the map
     */
    public static Map<String, List<String>> compareEntities(Object one, Object two,
            List<String> attributesCompare) {
        
        return compareEntities(one, two, attributesCompare, null);
        
    }
    
    /**
     * Compare entities excluding attributes.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param attributesExclude
     *        the attributes exclude
     * @return the map
     */
    public static Map<String, List<String>> compareEntitiesExcludingAttributes(Object one,
            Object two, List<String> attributesExclude) {
        
        return compareEntities(one, two, null, attributesExclude);
        
    }
    
    /**
     * Compare entities.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param attributesCompare
     *        the attributes compare
     * @param attributesExclude
     *        the attributes exclude
     * @return the map
     */
    private static Map<String, List<String>> compareEntities(Object one, Object two,
            List<String> attributesCompare, List<String> attributesExclude) {
        
        validateParams(one, two, attributesCompare, attributesExclude);
        
        Map<String, List<String>> response = new HashMap<String, List<String>>();
        
        try {
            
            // Load all fields in the class (private included)        
            Field[] attributesAll = fieldsClass(one);
            
            for (Field field : attributesAll) {
                
                //If fields are limited, check if exist in the lists
                if (isExcludeField(field, attributesCompare, attributesExclude)) {
                    continue;
                }
                
                field.setAccessible(true);
                
                // Dynamically read Attribute Name
                log.debug("ATTRIBUTE NAME: " + field.getName());
                
                Field fieldTwo;
                
                if (ArrayUtils.contains(one.getClass().getDeclaredFields(), field)) {
                    fieldTwo = two.getClass().getDeclaredField(field.getName());
                } else {
                    fieldTwo = two.getClass().getSuperclass().getDeclaredField(field.getName());
                }
                
                fieldTwo.setAccessible(true);
                log.debug(" one: " + field.get(one) + " == two: " + field.get(two));
                
                if (isDifferentValue(one, two, field)) {
                    if (field.get(one) == null) {
                        response.put(field.getName(), Arrays.asList("", field.get(two).toString()));
                    } else if (field.get(two) == null) {
                        response.put(field.getName(), Arrays.asList(field.get(one).toString(), ""));
                    } else {
                        response.put(field.getName(), Arrays.asList(field.get(one).toString(),
                                field.get(two).toString()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("compare error", e);
        }
        return response;
        
    }
    
    /**
     * Checks if is different value.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param field
     *        the field
     * @return true, if is different value
     * @throws IllegalAccessException
     *         the illegal access exception
     */
    public static boolean isDifferentValue(Object one, Object two, Field field)
            throws IllegalAccessException {
        return isBigDecimal(one, two, field)
                ? ((BigDecimal) field.get(one)).compareTo((BigDecimal) field.get(two)) != 0
                : !Objects.equals(field.get(one), field.get(two));
    }
    
    /**
     * Checks if is big decimal.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param field
     *        the field
     * @return true, if is big decimal
     * @throws IllegalAccessException
     *         the illegal access exception
     */
    public static boolean isBigDecimal(Object one, Object two, Field field)
            throws IllegalAccessException {
        return field.get(one) != null && field.get(two) != null
                && field.get(one) instanceof BigDecimal;
    }
    
    /**
     * Update entities.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param attributesCompare
     *        the attributes compare
     */
    public static void updateEntities(BaseEntity one, BaseEntity two,
            Set<String> attributesCompare) {
        validateParams(one, two);
        
        try {
            
            // Load all fields in the class (private included)        
            Field[] attributesAll = fieldsClass(one);
            
            for (Field field : attributesAll) {
                
                //If fields are limited, check if exist in the list
                if (attributesCompare != null && !attributesCompare.contains(field.getName())) {
                    continue;
                }
                
                // Dynamically set attributes
                field.setAccessible(true);
                log.debug("UPDATE ATTRIBUTE: " + field.getName() + " one: " + field.get(one)
                        + " == two: " + field.get(two));
                field.set(one, field.get(two));
                
            }
        } catch (Exception e) {
            log.error("set error", e);
        }
    }
    
    /**
     * Fields class.
     *
     * @param one
     *        the one
     * @return the field[]
     */
    private static Field[] fieldsClass(Object one) {
        Field[] attributesClass = one.getClass().getDeclaredFields();
        Field[] attributesExtends = one.getClass().getSuperclass().getDeclaredFields();
        return ArrayUtils.addAll(attributesClass, attributesExtends);
        
    }
    
    /**
     * Validate params.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     */
    private static void validateParams(Object one, Object two) {
        validateParams(one, two, null, null);
    }
    
    /**
     * Validate params.
     *
     * @param one
     *        the one
     * @param two
     *        the two
     * @param attributesCompare
     *        the attributes compare
     * @param attributesExclude
     *        the attributes exclude
     */
    private static void validateParams(Object one, Object two, List<String> attributesCompare,
            List<String> attributesExclude) {
        
        if (!one.getClass().equals(two.getClass())) {
            throw new IllegalArgumentException();
        }
        
        if (attributesCompare != null && attributesExclude != null) {
            throw new IllegalArgumentException();
        }
        
    }
    
    /**
     * Method to change a date in format LocalDateTime to String.
     *
     * @param date
     *        the date
     * @return the string
     */
    public static String changeDateToString(LocalDateTime date) {
        String formatDateTime = null;
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            formatDateTime = date.format(formatter);
        }
        
        return formatDateTime;
    }
    
    /**
     * Change date to string.
     *
     * @param date
     *        the date
     * @return the string
     */
    public static String changeDateToString(LocalDate date) {
        String formatDateTime = null;
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formatDateTime = date.format(formatter);
        }
        
        return formatDateTime;
    }
    
    /**
     * Method to change the value of a boolean to YES or NO.
     *
     * @param value
     *        the value
     * @return the string
     */
    public static String changeBooleanToString(Boolean value) {
        String valueString = null;
        if (BooleanUtils.isFalse(value)) {
            valueString = "No";
        } else {
            valueString = "Si";
        }
        
        return valueString;
    }
    
    /**
     * Checks if is exclude field.
     *
     * @param field
     *        the field
     * @param attributesCompare
     *        the attributes compare
     * @param attributesExclude
     *        the attributes exclude
     * @return true, if is exclude field
     */
    private static boolean isExcludeField(Field field, List<String> attributesCompare,
            List<String> attributesExclude) {
        
        return (attributesCompare != null && !attributesCompare.contains(field.getName()))
                || (attributesExclude != null && attributesExclude.contains(field.getName()));
        
    }
    
}
