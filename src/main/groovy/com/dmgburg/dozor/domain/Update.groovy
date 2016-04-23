package com.dmgburg.dozor.domain

import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
@CompileStatic
class Update {
    int update_id
    Message message

    @Override
    public String toString() {
        return "Update{" +
                "update_id=" + update_id +
                ", message=" + message +
                '}';
    }
}
