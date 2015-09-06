package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.KsRepositoryImpl
import com.dmgburg.dozor.handlers.Handler
import com.dmgburg.dozor.handlers.KsHandler
import com.dmgburg.dozor.handlers.KsNewHandler
import com.dmgburg.dozor.handlers.PassedHandler

class ManualKsPlugin extends Plugin{
    ManualKsPlugin() {
        super([new KsHandler(ksRepository: KsRepositoryImpl.instance),
               new KsNewHandler(KsRepositoryImpl.instance),
               new PassedHandler(KsRepositoryImpl.instance)])
    }
}
