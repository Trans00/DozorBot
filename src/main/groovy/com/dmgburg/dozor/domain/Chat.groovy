package com.dmgburg.dozor.domain

import org.codehaus.jackson.annotate.JsonProperty

class Chat {
    int id
    @JsonProperty(value = "first_name")String firstName
    @JsonProperty(value = "last_name")String lastName
    String username
    String title

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
