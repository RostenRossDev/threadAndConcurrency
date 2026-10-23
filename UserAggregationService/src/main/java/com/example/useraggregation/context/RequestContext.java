package com.example.useraggregation.context;

import java.util.concurrent.Callable;

/**
 * Contexto inmutable ligado a un ámbito, no una variable global mutable.
 * ScopedValue es una API final en Java 25; no necesita preview.
 */
public final class RequestContext {
    public static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    private RequestContext() {}

    public static <T> T withRequestId(String id, ScopedValue.CallableOp<T, Exception> action) {

        try {
            return ScopedValue.where(REQUEST_ID, id).call(action);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
