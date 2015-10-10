package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.DzzzrKsRepository
import com.dmgburg.dozor.handlers.KsHandler

class DzzzrPlugin extends Plugin{
    DzzzrPlugin() {
        super([new KsHandler(ksRepository: new DzzzrKsRepository())])
    }
}
