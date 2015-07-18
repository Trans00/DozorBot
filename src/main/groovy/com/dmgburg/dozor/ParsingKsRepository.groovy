package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild

class ParsingKsRepository implements KsRepository{
    EngineWrapper htmlExtractor

    ParsingKsRepository() {
        htmlExtractor = new EngineWrapperImpl()
    }

    ParsingKsRepository(EngineWrapper htmlExtractor) {
        this.htmlExtractor = htmlExtractor
    }

    @Override
    Map<Integer, String> getKs() {
        NodeChild html = htmlExtractor.html
        List<NodeChild> nodes = html."**".findAll{
            it.@class.toString().contains("zad")
        }

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
