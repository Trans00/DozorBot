package com.dmgburg.dozor.requests

abstract class AbstructRequest implements Request {

    @Override
    public String toString() {
        return "AbstructRequest{" +
                "methodName='" + methodName + '\'' +
                ", parameters=" + parameters.toMapString() +
                '}';
    }
}
