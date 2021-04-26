package com.fravega.sucursal.services.controllers;

import com.fravega.sucursal.exception.BranchOfficeErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<BranchOfficeErrorResponse> handleError() {
        BranchOfficeErrorResponse error = new BranchOfficeErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setErrorMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
