package com.dmgburg.dozor.domain

import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonProperty

@CompileStatic
class Chat {
    int id
    @JsonProperty(value = "first_name")String firstName
    @JsonProperty(value = "last_name")String lastName
    String username
    String title
    String type

    Chat() {
    }

    Chat(int id, String firstName, String lastName, String username, String title) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.username = username
        this.title = title
    }

    public boolean isUser(){
        firstName
    }

    public boolean isGroup(){
        title
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Chat chat = (Chat) o

        if (id != chat.id) return false

        return true
    }

    int hashCode() {
        return id
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
