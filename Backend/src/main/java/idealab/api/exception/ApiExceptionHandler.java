package idealab.api.exception;

import idealab.api.dto.responses.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex){
        return createExceptionResponse(ErrorType.GENERAL_ERROR, ex);
    }

    @ExceptionHandler(value = IdeaLabApiException.class)
    public ResponseEntity<ExceptionResponse> handleIdeaLabApiException(IdeaLabApiException ex){
        return createExceptionResponse(ex.getErrorType(), ex);
    }

    private ResponseEntity<ExceptionResponse> createExceptionResponse(ErrorType errorType, Exception ex){
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode(errorType.getErrorCode());
        response.setErrorDescription(ex.getMessage());
        response.setErrorMessage(errorType.getErrorMessage());
        response.setExceptionClassName(ex.getClass().getCanonicalName());
        response.setErrorName(errorType.name());

        return new ResponseEntity<ExceptionResponse>(response, errorType.getResponseStatus());
    }
}
