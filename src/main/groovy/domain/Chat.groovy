package domain

import org.codehaus.jackson.annotate.JsonProperty

/**
 * Created by Denis on 29.06.2015.
 */
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
