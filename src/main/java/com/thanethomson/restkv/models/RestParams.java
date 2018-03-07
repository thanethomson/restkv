package com.thanethomson.restkv.models;

import lombok.Data;

@Data
public class RestParams {

    /**
     * The timeout, in milliseconds, for all kinds of interactions with the key/value store.
     */
    private Long timeout;

    /**
     * The maximum length of any value that can be put into the store.
     */
    private Long maxValueLength;

    /**
     * Which content type to return by default.
     */
    private String contentType;

    /**
     * Are we running the server in debug mode? This controls whether or not exception stack
     * traces will be returned if they occur during key/value store interaction.
     */
    private Boolean debug = true;

}
