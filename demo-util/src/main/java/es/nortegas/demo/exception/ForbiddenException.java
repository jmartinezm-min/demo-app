/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.exception;

/**
 * Excepción lanzada cuando no se tienen permisos de acceso
 * 
 * @author NORTEGAS
 */
public class ForbiddenException extends DevelopmentException {
    
    private static final long serialVersionUID = 4636013430767967773L;
    
    public ForbiddenException(ErrorCode pErrorCode, Exception newOriginalException,
            Object... pArguments) {
        super(pErrorCode, newOriginalException, pArguments);
    }
    
    public ForbiddenException(ErrorCode pErrorCode, Exception newOriginalException) {
        super(pErrorCode, newOriginalException);
    }
    
    public ForbiddenException(ErrorCode pErrorCode, Object... pArguments) {
        super(pErrorCode, pArguments);
    }
    
    public ForbiddenException(ErrorCode pErrorCode) {
        super(pErrorCode);
    }
    
}
