package com.thanethomson.restkv.storage;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryKeyValueStore implements KeyValueStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();
    private final ListeningExecutorService executorService;

    public InMemoryKeyValueStore(ListeningExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public ListenableFuture<String> get(String key) {
        return executorService.submit(() -> store.get(key));
    }

    @Override
    public ListenableFuture<String> put(String key, String value) {
        return executorService.submit(() -> store.put(key, value));
    }

    @Override
    public ListenableFuture<String> delete(String key) {
        return executorService.submit(() -> store.remove(key));
    }

    @Override
    public ListenableFuture<Void> deleteAll() {
        return executorService.submit(() -> {
            store.clear();
            return null;
        });
    }

}
