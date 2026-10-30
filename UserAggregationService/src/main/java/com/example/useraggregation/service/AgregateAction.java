package com.example.useraggregation.service;

import com.example.useraggregation.context.RequestContext;
import com.example.useraggregation.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AgregateAction implements ScopedValue.CallableOp<List<User>, Exception> {
    private UserAggregationService service;
    private List<Integer> ids;
    public AgregateAction(UserAggregationService service, List<Integer> ids) {
        this.service  = service;
        this.ids = ids;
    }

    @Override
    public List<User> call() throws Exception {

        String reqId = RequestContext.REQUEST_ID.get();   // ✅ hilo actual → ámbito abierto → funciona

        List<CompletableFuture<User>> futures = new ArrayList<>();

        for (int id : ids) {
            // Un ExecutorService común no propaga automáticamente ScopedValue.
            // Pasamos requestId explícitamente a cada tarea asíncrona.
            futures.add(CompletableFuture.supplyAsync(() -> service.loadUser(id, reqId), service.getExecutor()));
        }

        // allOf espera a que terminen todas. Si una falla, join propaga la excepción.
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        return futures.stream().map(CompletableFuture::join).toList();
    }
}
