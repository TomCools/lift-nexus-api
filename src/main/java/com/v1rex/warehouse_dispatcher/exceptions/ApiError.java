package com.v1rex.warehouse_dispatcher.exceptions;


import java.time.LocalDateTime;

public record ApiError(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {}
