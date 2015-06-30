package domain

import org.codehaus.jackson.annotate.JsonProperty

/**
 * Created by Denis on 29.06.2015.
 */
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
    Object sticker
    Object video
    Object contact
    Object location
    @JsonProperty(value = "new_chat_participant")User newChatParticipant
    @JsonProperty(value = "left_chat_participant")User leftChatParticipant
    @JsonProperty(value = "new_chat_title")String newChatTitle
    @JsonProperty(value = "new_chat_photo")Object newChatPhoto
    @JsonProperty(value = "delete_chat_photo")boolean deleteChatPhoto
    @JsonProperty(value = "group_chat_created")boolean groupChatCreated


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
