package com.thanethomson.restkv.storage;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisKeyValueStore implements KeyValueStore {

    private final RedisCommands<String, String> redisCommands;
    private final ListeningExecutorService executorService;

    public RedisKeyValueStore(RedisCommands<String, String> redisCommands, ListeningExecutorService executorService) {
        this.redisCommands = redisCommands;
        this.executorService = executorService;
    }

    @Override
    public ListenableFuture<String> get(String key) {
        return executorService.submit(() -> redisCommands.get(key));
    }

    @Override
    public ListenableFuture<String> put(String key, String value) {
        return executorService.submit(() -> redisCommands.set(key, value));
    }

    @Override
    public ListenableFuture<String> delete(String key) {
        return executorService.submit(() -> {
            redisCommands.del(key);
            return null;
        });
    }

    @Override
    public ListenableFuture<Void> deleteAll() {
        return executorService.submit(() -> {
            redisCommands.flushdb();
            return null;
        });
    }
}
