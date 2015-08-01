package com.dmgburg.dozor

import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class EngineWrapperTest {
    EngineWrapper htmlExtractor

    Logger log = LoggerFactory.getLogger(EngineWrapperTest)
    @Before
    void onInit() {
        htmlExtractor = new DzzzrWrapper("http://google.com/")
    }

    @Test
    void "first test"() {
        def http = new EncounterKsRepository().ks

    }
}
