package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.dzzzr.DzzzrKsRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.handlers.KsHandler

class DzzzrPlugin extends Plugin{
    DzzzrPlugin() {
        super([new KsHandler(LocalApi.instance, new DzzzrKsRepository())])
    }
}
