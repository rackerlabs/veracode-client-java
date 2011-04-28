package com.rackspace.api.clients.veracode;

/**
 * Created by IntelliJ IDEA.
 * User: john.madrid
 * Date: 4/27/11
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class VeracodeApiException extends Exception {
    public VeracodeApiException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public VeracodeApiException(String s) {
        super(s);
    }
}
