package com.dmgburg.dozor.requests

/**
 * Created by Denis on 28.06.2015.
 */
interface Request {
    public String getMethodName()
    public Map<String,String> getParameters()
}