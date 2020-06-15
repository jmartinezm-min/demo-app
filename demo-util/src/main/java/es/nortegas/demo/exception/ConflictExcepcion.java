/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados.
 * 
 ******************************************************************************/

package es.nortegas.demo.exception;

/**
 * Excepción que define un error al producirse un conflicto
 * 
 * @author NORTEGAS
 */
public class ConflictExcepcion extends DevelopmentException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5992315134274853744L;
    
    public ConflictExcepcion(ErrorCode pErrorCode) {
        super(pErrorCode);
    }
    
    public ConflictExcepcion(ErrorCode pErrorCode, Exception newOriginalException,
            Object... pArguments) {
        super(pErrorCode, newOriginalException, pArguments);
    }
    
    public ConflictExcepcion(ErrorCode pErrorCode, Exception newOriginalException) {
        super(pErrorCode, newOriginalException);
    }
    
    public ConflictExcepcion(ErrorCode pErrorCode, Object... pArguments) {
        super(pErrorCode, pArguments);
    }
    
}