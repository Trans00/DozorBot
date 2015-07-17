package com.dmgburg.dozor.domain

import com.sun.istack.internal.Nullable
import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonProperty

@CompileStatic
class User {
    int id
    @JsonProperty(value = "first_name")String firstName
    @Nullable
    @JsonProperty(value = "last_name")
    String lastName
    @Nullable
    String username

    public String getName(){
        firstName ?: lastName?: username
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
