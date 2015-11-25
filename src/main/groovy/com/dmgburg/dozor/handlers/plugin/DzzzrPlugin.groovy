package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.DzzzrKsRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.handlers.KsHandler
import groovy.transform.CompileStatic

class DzzzrPlugin extends Plugin{
    DzzzrPlugin() {
        super([new KsHandler(LocalApi.instance, new DzzzrKsRepository())])
    }
}
