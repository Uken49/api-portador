package com.example.apiportador.presentation.handler;

import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
import com.example.apiportador.presentation.handler.exception.CreditAnalisysNotApproved;
import com.example.apiportador.presentation.handler.exception.CreditAnalisysNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptionAdvice {

    private ProblemDetail builderProblemDetail(String title, HttpStatus status, String detail) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);

        problemDetail.setType(URI.create("https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/" + status.value()));
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException.class)
    public ProblemDetail clientDoesNotCorrespondToCreditAnalysisExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException cdnctcae) {
        return builderProblemDetail("Portador não cadastrado", HttpStatus.UNPROCESSABLE_ENTITY, cdnctcae.getMessage());
    }

    @ExceptionHandler(ClientWithIDAlreadyExistsException.class)
    public ProblemDetail clientWithIDAlreadyExistsExceptionHandler(ClientWithIDAlreadyExistsException cwiaee) {
        return builderProblemDetail("Portador não cadastrado", HttpStatus.UNPROCESSABLE_ENTITY, cwiaee.getMessage());
    }

    @ExceptionHandler(CreditAnalisysNotFoundException.class)
    public ProblemDetail creditAnalisysNotFoundExceptionHandler(CreditAnalisysNotFoundException canfe) {
        return builderProblemDetail("Análise de crédito não encontrada", HttpStatus.UNPROCESSABLE_ENTITY, canfe.getMessage());
    }

    @ExceptionHandler(CreditAnalisysNotApproved.class)
    public ProblemDetail creditAnalisysNotApprovedHandler(CreditAnalisysNotApproved cana) {
        return builderProblemDetail("Cliente sem limite de crédito", HttpStatus.UNPROCESSABLE_ENTITY, cana.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolationExceptionHandler(ConstraintViolationException cve) {
        return builderProblemDetail("Argumento inválido", HttpStatus.BAD_REQUEST, cve.getLocalizedMessage());
    }

}
