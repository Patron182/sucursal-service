package com.fravega.sucursal.exception;

import org.springframework.http.HttpStatus;

public class BranchOfficeException extends RuntimeException{
    private HttpStatus status;

    public BranchOfficeException(){
        super(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BranchOfficeException(BranchOfficeErrorCode error){
        super(error.getMessage());
        this.status = HttpStatus.valueOf(error.getCode());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
