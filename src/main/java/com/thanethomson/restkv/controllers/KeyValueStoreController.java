package com.thanethomson.restkv.controllers;

import com.google.common.net.HttpHeaders;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.thanethomson.restkv.models.RestParams;
import com.thanethomson.restkv.storage.KeyValueStore;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
public class KeyValueStoreController {

    private static final Logger logger = LoggerFactory.getLogger(KeyValueStoreController.class);

    private final KeyValueStore keyValueStore;
    private final ListeningExecutorService executorService;
    private final RestParams restParams;
    private final MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();

    @Autowired
    public KeyValueStoreController(
            KeyValueStore keyValueStore,
            ListeningExecutorService executorService,
            RestParams restParams
    ) {
        this.keyValueStore = keyValueStore;
        this.executorService = executorService;
        this.restParams = restParams;
        responseHeaders.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(restParams.getContentType()));
    }

    @RequestMapping(
            path = "/**",
            method = RequestMethod.GET
    )
    public DeferredResult<ResponseEntity<String>> getValue(HttpServletRequest request) {
        String key = extractKey(request);
        return handleFuture(keyValueStore.get(key), key, HttpStatus.NOT_FOUND);
    }

    private String extractKey(HttpServletRequest request) {
        String path = request.getRequestURI();
        // strip any leading slashes
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    @RequestMapping(
            path = "/**",
            method = RequestMethod.PUT
    )
    public DeferredResult<ResponseEntity<String>> putValue(@RequestBody String body, HttpServletRequest request) {
        String key = extractKey(request);
        return handleFuture(keyValueStore.put(key, body), key, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/**",
            method = RequestMethod.DELETE
    )
    public DeferredResult<ResponseEntity<String>> deleteValue(HttpServletRequest request) {
        String key = extractKey(request);
        return handleFuture(keyValueStore.delete(key), key, HttpStatus.OK);
    }

    private DeferredResult<ResponseEntity<String>> handleFuture(
            ListenableFuture<String> future,
            String keyId,
            HttpStatus statusOnNull
    ) {
        final DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>(restParams.getTimeout());
        Futures.addCallback(future, new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result == null)
                    deferredResult.setResult(new ResponseEntity<>(
                            "",
                            responseHeaders,
                            statusOnNull
                    ));
                else
                    deferredResult.setResult(new ResponseEntity<>(
                            result,
                            responseHeaders,
                            HttpStatus.OK
                    ));
            }

            @Override
            public void onFailure(Throwable t) {
                String stackTrace = ExceptionUtils.getStackTrace(t);
                deferredResult.setResult(new ResponseEntity<>(
                        restParams.getDebug() ? stackTrace : "Internal server error",
                        responseHeaders,
                        HttpStatus.INTERNAL_SERVER_ERROR
                ));
                logger.error(String.format("Error while attempting operation for key \"%s\"", keyId), t);
            }

        }, executorService);
        return deferredResult;
    }

}
