package com.dmgburg.dozor.domain

import com.sun.istack.internal.Nullable
import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonProperty

@CompileStatic
class User {
    int id
    @JsonProperty(value = "first_name")String firstName
    @JsonProperty(value = "last_name") String lastName
    String username

    public String getName(){
        firstName ?: lastName?: username
    }

    Chat getChat(){
        new Chat(id,firstName,lastName,username,"")
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

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        User user = (User) o

        if (id != user.id) return false

        return true
    }

    int hashCode() {
        return id
    }
}
