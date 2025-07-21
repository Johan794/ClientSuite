package com.seek.TalentSuite.application.exception;

import com.seek.TalentSuite.application.exception.custom.ApplicationError;
import com.seek.TalentSuite.application.exception.custom.ApplicationException;
import com.seek.TalentSuite.application.exception.custom.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

import static com.seek.TalentSuite.application.exception.util.ErrorManager.createDetail;
import static com.seek.TalentSuite.application.exception.util.ErrorManager.sendDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationError> handleApplicationException(ApplicationException exception){
        return ResponseEntity.status(exception.getError().getStatus()).body(exception.getError());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationError> handleMissArgumentException(MethodArgumentNotValidException exception){
        ApplicationError error = ApplicationError.builder().status(HttpStatus.BAD_REQUEST).details(List.of(sendDetails(createDetail("Check your field's: "+exception.getMessage(), ErrorCode.ERROR_MISSING_ARGUMENT)))).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApplicationError> handleAuthException(AuthenticationException exception){
        ApplicationError error = ApplicationError.builder().status(HttpStatus.UNAUTHORIZED).details(List.of(sendDetails(createDetail("error while login in check your credentials or register", ErrorCode.ERROR_LOGIN)))).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApplicationError> handleRuntimeException(RuntimeException exception){
        ApplicationError error = ApplicationError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).details(List.of(sendDetails(createDetail(exception.getMessage(), ErrorCode.RUNTIME_ERROR)))).build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
