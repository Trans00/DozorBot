package domain

/**
 * Created by Denis on 28.06.2015.
 */
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
