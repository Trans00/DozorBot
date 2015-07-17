package com.dmgburg.dozor.domain

import groovy.transform.CompileStatic

@CompileStatic
class RequestResult<T> {
    boolean ok
    T result

    @Override
    public String toString() {
        return "RequestResult{" +
                "ok=" + ok +
                ", result=" + result +
                '}';
    }
}
