package com.example.useraggregation.model;

/** Resultado agregado del usuario. Record: objeto inmutable de datos. */
public record User(
        int id,
        String name,
        String email,
        String department,
        String requestId
) {}
