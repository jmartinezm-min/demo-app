/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.nortegas.demo.exception.ConflictExcepcion;
import es.nortegas.demo.exception.ContentNotFoundException;
import es.nortegas.demo.exception.DevelopmentException;
import es.nortegas.demo.exception.ErrorView;
import es.nortegas.demo.exception.ForbiddenException;

/**
 * Manejador de excepciones para el API
 * 
 * @author NORTEGAS
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    /**
     * Handler which catch DetailedErrorExceptions and returns a JSON which the detailed information
     * that the API wants to share with the client.
     * 
     * @param ex
     * @return JSON {"error": "descriptive error message"}
     */
    @ExceptionHandler(DevelopmentException.class)
    public ResponseEntity<Object> detailedErrorExceptionHandler(DevelopmentException ex) {
        
        return new ResponseEntity<Object>(
                new ErrorView(ex.getErrorCode().getMessage(), ex.getErrorCode().getCode()),
                HttpStatus.PRECONDITION_FAILED);
    }
    
    /**
     * Generic handler which translates all retrieved exceptions to 500 error with empty body,
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void genericExceptionHandler() {
    }
    
    /**
     * Handler to translate all retrieved exceptions related with content not found in 404 errors,
     */
    @ExceptionHandler(ContentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void notFoundExceptionHandler() {
    }
    
    /**
     * Handler to translate all retrieved exceptions related with forbiden in 403 errors,
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void forbiddenHandler() {
    }
    
    /**
     * Handler which translates Spring Data integrity violation in a 409 error,
     */
    @ExceptionHandler({ DataIntegrityViolationException.class, ConflictExcepcion.class })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void dataIntegrityExceptionHandler() {
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorView("Invalid input", HttpStatus.PRECONDITION_FAILED.toString()), headers,
                HttpStatus.PRECONDITION_FAILED, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorView("Invalid input", HttpStatus.PRECONDITION_FAILED.toString()), headers,
                HttpStatus.PRECONDITION_FAILED, request);
    }
}
