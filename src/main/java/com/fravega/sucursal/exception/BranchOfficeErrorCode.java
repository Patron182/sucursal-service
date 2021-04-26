package com.fravega.sucursal.exception;

public enum BranchOfficeErrorCode {

    BRANCH_OFFICE_INVALID_ID(400, "Ingrese un id de sucursal válido"),
    BRANCH_OFFICE_INVALID_DATA(400, "Ingrese los datos de la nueva sucursal"),
    BRANCH_OFFICE_REQUIRED_ADDRESS(400, "La dirección es requerida"),
    BRANCH_OFFICE_REQUIRED_LATITUDE(400, "La latidud es requerida"),
    BRANCH_OFFICE_REQUIRED_LONGITUDE(400, "La longitud es requerida"),

    GEOLOCATION_REQUIRED(400, "La latidud y longitud son requeridas"),
    GEOLOCATION_BAD_REQUEST(400, "Error al obtener la ubicación"),

    BRANCH_OFFICE_NOT_FOUND(404, "No se encontró ninguna sucursal"),

    BRANCH_OFFICE_CREATE_ERROR(500, "Error al guardar la sucursal");

    private final int code;
    private final String message;

    BranchOfficeErrorCode(int code, String message) {
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
