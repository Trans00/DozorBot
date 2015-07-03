package com.dmgburg.dozor.domain

class Group {
    int id
    String title


    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
