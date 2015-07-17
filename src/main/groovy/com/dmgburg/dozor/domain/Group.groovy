package com.dmgburg.dozor.domain

import groovy.transform.CompileStatic

@CompileStatic
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
