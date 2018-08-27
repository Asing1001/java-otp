package com.odde.securetoken;

public interface Token {
    String getRandom(String account);

    class AuthLogger extends com.odde.securetoken.AuthLogger {

    }
}
