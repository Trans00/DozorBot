package com.dmgburg.dozor.domain

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
