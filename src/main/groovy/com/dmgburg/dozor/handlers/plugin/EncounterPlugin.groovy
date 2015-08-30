package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.EncounterKsRepository
import com.dmgburg.dozor.handlers.Handler
import com.dmgburg.dozor.handlers.KsHandler

class EncounterPlugin extends Plugin{

    EncounterPlugin() {
        super([new KsHandler(new EncounterKsRepository())])
    }
}
