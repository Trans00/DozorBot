package com.dmgburg.dozor.domain

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
