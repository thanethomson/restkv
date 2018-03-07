package com.thanethomson.restkv.storage;

import com.google.common.util.concurrent.ListenableFuture;

public interface KeyValueStore {

    /**
     * Returns the value associated with the given key.
     * @param key The name of the key.
     * @return The value stored for that key, if any. If none is available, returns null.
     */
    ListenableFuture<String> get(String key);

    /**
     * Puts the given value into the store for the given key.
     * @param key The name of the key.
     * @param value The string content to insert for the given key.
     * @return If any value was previously available for that key, it will be returned.
     *     Otherwise returns null.
     */
    ListenableFuture<String> put(String key, String value);

    /**
     * Deletes the value associated with the given key.
     * @param key The name of the key.
     * @return If any value was stored at the key, returns the old value prior to deleting.
     *     Otherwise null.
     */
    ListenableFuture<String> delete(String key);

    /**
     * Deletes all of the key/value pairs in this store (if possible).
     * @return A listenable future that only returns once the operation is complete.
     */
    ListenableFuture<Void> deleteAll();

}
