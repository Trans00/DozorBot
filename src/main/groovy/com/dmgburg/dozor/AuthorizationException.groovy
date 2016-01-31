package com.dmgburg.dozor

class AuthorizationException  extends RuntimeException{
    AuthorizationException() {
    }

    AuthorizationException(String var1) {
        super(var1)
    }

    AuthorizationException(String var1, Throwable var2) {
        super(var1, var2)
    }

    AuthorizationException(Throwable var1) {
        super(var1)
    }

    AuthorizationException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
