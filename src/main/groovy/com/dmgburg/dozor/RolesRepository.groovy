package com.dmgburg.dozor

import com.dmgburg.dozor.domain.User

interface RolesRepository {

    void addPendingRequest(User user)
    void authentificate(int id, boolean auth)
    boolean getAuthentificated(int id)
}