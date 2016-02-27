package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.dzzzr.DzzzrKsRepository
import com.dmgburg.dozor.handlers.CodeTryHandler
import com.dmgburg.dozor.handlers.KsHandler

class DzzzrPlugin extends Plugin{
    DzzzrPlugin() {
        super()
        DzzzrKsRepository repository = new DzzzrKsRepository()
        handlers = [new KsHandler(LocalApi.instance, repository), new CodeTryHandler(repository)]
    }
}
