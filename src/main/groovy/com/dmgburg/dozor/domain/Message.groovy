package com.dmgburg.dozor.domain

import groovy.transform.AutoClone
import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonProperty

@CompileStatic
@AutoClone(includeFields = true)
class Message {
    @JsonProperty(value = "message_id" )int messageId
    User from
    int date
    Chat chat
    @JsonProperty(value = "forward_from") User forwardFrom
    @JsonProperty(value = "forward_date") int forwardDate
    @JsonProperty(value = "reply_to_message") Message replyToMessage
    String text
    Object audio
    Object document
    Object photo
    Sticker sticker
    Object video
    Object contact
    Object location
    @JsonProperty(value = "new_chat_participant")User newChatParticipant
    @JsonProperty(value = "left_chat_participant")User leftChatParticipant
    @JsonProperty(value = "new_chat_title")String newChatTitle
    @JsonProperty(value = "new_chat_photo")Object newChatPhoto
    @JsonProperty(value = "delete_chat_photo")boolean deleteChatPhoto
    @JsonProperty(value = "group_chat_created")boolean groupChatCreated


    String getCommand() {
        String text = normalizeText(text)
        if (!text?.startsWith("/")) {
            return ""
        }
        if (!text.contains(" ")) {
            return text.substring((1))
        }
        return text.substring(1, text.indexOf(" "))
    }

    String getArgument() {
        String text = this.text
        if (text.trim().startsWith("/")) {
            if (text.trim().contains(" ")) {
                return text.substring(text.trim().indexOf(" ") + 1)
            }
            return ""
        }
        return text.substring(0)
    }

    public Message normalize() {
        Message clone = this.clone()
        clone.text = normalizeText(text)
        clone
    }

    static String normalizeText(String txt) {
        txt?.trim()?.toLowerCase()
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", from=" + from +
                ", date=" + date +
                ", chat=" + chat +
                ", forwardFrom=" + forwardFrom +
                ", forwardDate=" + forwardDate +
                ", replyToMessage=" + replyToMessage +
                ", text='" + text + '\'' +
                ", audio=" + audio +
                ", document=" + document +
                ", photo=" + photo +
                ", sticker=" + sticker +
                ", video=" + video +
                ", contact=" + contact +
                ", location=" + location +
                ", newChatParticipant=" + newChatParticipant +
                ", leftChatParticipant=" + leftChatParticipant +
                ", newChatTitle='" + newChatTitle + '\'' +
                ", newChatPhoto=" + newChatPhoto +
                ", deleteChatPhoto=" + deleteChatPhoto +
                ", groupChatCreated=" + groupChatCreated +
                '}';
    }
}
