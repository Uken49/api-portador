package com.example.apiportador.presentation.handler;

import com.example.apiportador.presentation.handler.exception.ClientDoesNotCorrespondToCreditAnalysisException;
import com.example.apiportador.presentation.handler.exception.ClientWithIDAlreadyExistsException;
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

        problemDetail.setTitle(title);
        problemDetail.setType(URI.create(""));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException.class)
    public ProblemDetail clientDoesNotCorrespondToCreditAnalysisExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException cdnctcae) {
        return builderProblemDetail("Portador não cadastrado", HttpStatus.BAD_REQUEST,cdnctcae.getMessage());
    }

    @ExceptionHandler(ClientWithIDAlreadyExistsException.class)
    public ProblemDetail clientWithIDAlreadyExistsExceptionHandler(ClientWithIDAlreadyExistsException cwiaee) {
        return builderProblemDetail("Portador não cadastrado", HttpStatus.CONFLICT, cwiaee.getMessage());
    }


}
