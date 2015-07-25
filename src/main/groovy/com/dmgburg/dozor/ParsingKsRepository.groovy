package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ParsingKsRepository implements KsRepository {
    EngineWrapper htmlExtractor
    Logger log = LoggerFactory.getLogger(ParsingKsRepository)

    ParsingKsRepository() {
        htmlExtractor = new EngineWrapperImpl("http://classic.dzzzr.ru/moscow/go/")
    }

    ParsingKsRepository(EngineWrapper htmlExtractor) {
        this.htmlExtractor = htmlExtractor
    }

    @Override
    Map<Integer, String> getKs() {
        NodeChild result = htmlExtractor.html."**".find{
            it.@class == "zad" && it.text().contains("основные коды:")
        }
        result.children().each{NodeChild it ->
            log.info(it.text())
        }
        result
    }

    @Override
    void removeKs(int number) {
        throw new UnsupportedOperationException("Can't remove KS on parsing repository")
    }


    @Override
    void setKs(List<String> ks) {
        throw new UnsupportedOperationException("Can't set KS on parsing repository")
    }
}
