package com.example.apiportador.presentation.handler;

import com.example.apiportador.presentation.handler.exception.CardHolderNotFoundException;
import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
import com.example.apiportador.presentation.handler.exception.CreditAnalysisNotApproved;
import com.example.apiportador.presentation.handler.exception.CreditAnalysisNotFoundException;
import com.example.apiportador.presentation.handler.exception.InvalidStatusValueException;
import com.example.apiportador.presentation.handler.exception.RequestedLimitGreaterThanAvailableException;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class HandlerExceptionAdvice {

    private ProblemDetail builderProblemDetail(String title, HttpStatus status, String detail) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);

        problemDetail.setType(URI.create("https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/" + status.value()));
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail methodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException matme) {

        return builderProblemDetail("Incompatibilidade no tipo de argumento", HttpStatus.BAD_REQUEST, matme.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidHandler(MethodArgumentNotValidException manve) {

        final String detail = manve.getFieldError().getField() + " " + manve.getFieldError().getDefaultMessage();

        return builderProblemDetail("Argumento inválido", HttpStatus.BAD_REQUEST, detail);
    }

    @ExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException.class)
    public ProblemDetail clientDoesNotCorrespondToCreditAnalysisExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException cdnctcae) {
        return builderProblemDetail("Portador não cadastrado", HttpStatus.UNPROCESSABLE_ENTITY, cdnctcae.getMessage());
    }

    @ExceptionHandler(ClientWithIDAlreadyExistsException.class)
    public ProblemDetail clientWithIDAlreadyExistsExceptionHandler(ClientWithIDAlreadyExistsException cwiaee) {
        return builderProblemDetail("Portador não cadastrado", HttpStatus.UNPROCESSABLE_ENTITY, cwiaee.getMessage());
    }

    @ExceptionHandler(CreditAnalysisNotFoundException.class)
    public ProblemDetail creditAnalysisNotFoundExceptionHandler(CreditAnalysisNotFoundException canfe) {
        return builderProblemDetail("Análise de crédito não encontrada", HttpStatus.UNPROCESSABLE_ENTITY, canfe.getMessage());
    }

    @ExceptionHandler(CreditAnalysisNotApproved.class)
    public ProblemDetail creditAnalysisNotApprovedHandler(CreditAnalysisNotApproved cana) {
        return builderProblemDetail("Cliente sem limite de crédito", HttpStatus.UNPROCESSABLE_ENTITY, cana.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolationExceptionHandler(ConstraintViolationException cve) {
        return builderProblemDetail("Argumento inválido", HttpStatus.BAD_REQUEST, cve.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidStatusValueException.class)
    public ProblemDetail invalidStatusValueExceptionHandler(InvalidStatusValueException isve) {
        return builderProblemDetail("Valor do status não aceito", HttpStatus.BAD_REQUEST, isve.getMessage());
    }

    @ExceptionHandler(CardHolderNotFoundException.class)
    public ProblemDetail cardHolderNotFoundExceptionHandler(CardHolderNotFoundException chnfe) {
        return builderProblemDetail("Portador não encontrado", HttpStatus.NOT_FOUND, chnfe.getMessage());
    }

    @ExceptionHandler(RequestedLimitGreaterThanAvailableException.class)
    public ProblemDetail cardHolderHasNoLimitExceptionHandler(RequestedLimitGreaterThanAvailableException chhnle) {
        return builderProblemDetail("Cartão não pôde ser criado", HttpStatus.UNPROCESSABLE_ENTITY, chhnle.getMessage());
    }
}
