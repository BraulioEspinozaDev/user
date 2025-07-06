package com.bci.user.adapters.inbound.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiDocConstants {

    public static final String HTTP_CODE_200 = "200";
    public static final String HTTP_CODE_201 = "201";
    public static final String HTTP_CODE_202 = "202";
    public static final String HTTP_CODE_204 = "204";
    public static final String HTTP_CODE_207 = "207";
    public static final String HTTP_CODE_400 = "400";
    public static final String HTTP_CODE_401 = "401";
    public static final String HTTP_CODE_402 = "402";
    public static final String HTTP_CODE_404 = "404";
    public static final String HTTP_CODE_409 = "409";
    public static final String HTTP_CODE_500 = "500";
    public static final String HTTP_STATUS_200 = "Ok";
    public static final String HTTP_STATUS_201 = "Created";
    public static final String HTTP_STATUS_202 = "Accepted";
    public static final String HTTP_STATUS_204 = "No Content";
    public static final String HTTP_STATUS_207 = "Multi-Status";
    public static final String HTTP_STATUS_400 = "Bad request";
    public static final String HTTP_STATUS_401 = "Unauthorized";
    public static final String HTTP_STATUS_402 = "Payment Required";
    public static final String HTTP_STATUS_404 = "Not found";
    public static final String HTTP_STATUS_409 = "Conflict";
    public static final String HTTP_STATUS_500 = "Internal Server Error";

    public static final String LOGIN_REQUEST_EXAMPLE = """
            {
              "username": "usuario@email.com",
              "password": "password123"
            }
            """;

    public static final String LOGIN_SUCCESS_RESPONSE = """
            {
              "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsImV4cCI6MTcwNDY3MjAwMCwiaWF0IjoxNzA0NjY4NDAwfQ.example",
              "username": "usuario@email.com",
              "expiresAt": "2024-12-31T23:59:59"
            }
            """;

    public static final String LOGIN_INVALID_CREDENTIALS_RESPONSE = """
            {
              "token": null,
              "username": null,
              "expiresAt": null,
              "error": "Invalid credentials"
            }
            """;

    public static final String LOGIN_AUTHENTICATION_FAILED_RESPONSE = """
            {
              "token": null,
              "username": null,
              "expiresAt": null,
              "error": "Authentication failed"
            }
            """;

    public static final String LOGIN_INTERNAL_ERROR_RESPONSE = """
            {
              "token": null,
              "username": null,
              "expiresAt": null,
              "error": "Internal server error"
            }
            """;

    public static final String LOGOUT_SUCCESS_RESPONSE = """
            "Logout successful"
            """;

    public static final String LOGOUT_ERROR_RESPONSE = """
            "Logout fallido"
            """;

    public static final String TOKEN_VALIDATION_SUCCESS_RESPONSE = """
            "Token es valido para: usuario@email.com"
            """;

    public static final String TOKEN_EXPIRED_RESPONSE = """
            "Token es inválido o expiró"
            """;

    public static final String TOKEN_VALIDATION_FAILED_RESPONSE = """
            "Validación de token fallida"
            """;
}
