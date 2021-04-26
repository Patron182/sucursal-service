package com.fravega.sucursal.services.controllers;

import com.fravega.sucursal.exception.BranchOfficeErrorResponse;
import com.fravega.sucursal.exception.BranchOfficeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BranchOfficeException.class)
    @ResponseBody
    public ResponseEntity<BranchOfficeErrorResponse> handleException(BranchOfficeException ex) {
        BranchOfficeErrorResponse error = new BranchOfficeErrorResponse();
        error.setErrorCode(ex.getStatus().value());
        error.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(error, ex.getStatus());
    }
}
