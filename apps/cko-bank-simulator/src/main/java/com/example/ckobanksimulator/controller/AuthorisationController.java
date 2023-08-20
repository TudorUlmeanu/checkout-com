package com.example.ckobanksimulator.controller;

import com.example.ckobanksimulator.objects.Status;
import com.example.ckobanksimulator.objects.paymentRequest.PaymentRequest;
import com.example.ckobanksimulator.objects.paymentResponse.Links;
import com.example.ckobanksimulator.objects.paymentResponse.PaymentResponse;
import com.example.ckobanksimulator.objects.paymentResponse.RiskFactor;
import com.example.ckobanksimulator.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/authorisation")
public class AuthorisationController {

    private final Logger logger = LoggerFactory.getLogger(AuthorisationController.class);

    private final TransactionService transactionService;

    public AuthorisationController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> authorisation(@Valid @RequestBody PaymentRequest paymentRequest) {

        try {
            final String correlationId = transactionService.processAuthorisation(paymentRequest);
            final PaymentResponse response = new PaymentResponse(Status.AUTHORISED.name(), correlationId, setLinks(correlationId));

            logger.info("Authorisation transaction processed");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (final Exception e) {
            final List<RiskFactor> riskFactors = new ArrayList<>();
            riskFactors.add(new RiskFactor("not_checked", "error", e.getMessage()));
            final PaymentResponse response = new PaymentResponse(Status.REFUSED.name(), riskFactors);

            logger.error("Authorisation transaction failed: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PaymentResponse> handleValidationErrors(final MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        final PaymentResponse response = new PaymentResponse(Status.REFUSED.name(), getRiskFactors(errors));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private List<RiskFactor> getRiskFactors(List<String> errors) {
        final List<RiskFactor> riskFactors = new ArrayList<>();

        errors.forEach(risk -> riskFactors.add(new RiskFactor("not_checked", "error", risk)));
        return riskFactors;
    }

    private Links setLinks(String correlationId) throws MalformedURLException {
        Links links = new Links();
        links.addSettle(correlationId);
        links.addCancel(correlationId);
        links.addRefund(correlationId);
        links.addEvents(correlationId);

        return links;
    }
}