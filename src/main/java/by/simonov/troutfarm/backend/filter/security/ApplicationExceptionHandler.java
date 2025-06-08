package by.simonov.troutfarm.backend.filter.security;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Hidden
@ControllerAdvice(basePackages = {"by.simonov.troutfarm.backend.controller"})
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler({AuthorizationDeniedException.class, AuthenticationException.class})
    public ResponseEntity<ProblemDetail> handleAuthorizationDenied(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> methodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                ex.getMessage()
        ));
    }

    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            DataAccessException.class
    })
    public ResponseEntity<ProblemDetail> formatException(Exception ex) {
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex) {
        log.error("Unexpected exception: ", ex);
        return ResponseEntity.internalServerError().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected exception occurred"
        ));
    }
}
