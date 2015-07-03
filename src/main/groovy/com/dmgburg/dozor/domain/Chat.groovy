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
        if (firstName != chat.firstName) return false
        if (lastName != chat.lastName) return false
        if (title != chat.title) return false
        if (username != chat.username) return false

        return true
    }

    int hashCode() {
        int result
        result = id
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0)
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0)
        result = 31 * result + (username != null ? username.hashCode() : 0)
        result = 31 * result + (title != null ? title.hashCode() : 0)
        return result
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
