package com.dmgburg.dozor

import groovy.transform.CompileStatic
import groovy.util.slurpersupport.NodeChild

class ParsingKsRepository implements KsRepository {
    EngineWrapper htmlExtractor

    ParsingKsRepository() {
        htmlExtractor = new EngineWrapperImpl("http://classic.dzzzr.ru/moscow/go/")
    }

    ParsingKsRepository(EngineWrapper htmlExtractor) {
        this.htmlExtractor = htmlExtractor
    }

    @Override
    Map<Integer, String> getKs() {
        NodeChild result = htmlExtractor.html
        def zads = result."**".findAll {
            it.@class.toString().contains("zad")
        }

        def level
        for (NodeChild it : zads) {
            if (it.text().contains("основные коды:")) {
                level = it
                break
            }
        }

        def map = [:]
        level."**".each { NodeChild it ->
            if (it.text().contains("основные коды:")) {
                List<String> ks = []
                Iterator<NodeChild> iter = it.childNodes()
                while (iter.hasNext()) {
                    NodeChild next = iter.next()
                    if (next.text().contains("основные коды")) {
                        break
                    }
                }
                while (iter.hasNext()) {
                    String next = iter.next().trim()
                    if (next ==~ /((\d(\+)?)|null)/) {
                        ks << next
                    } else {
                        break
                    }
                }
                def i = 1
                if (map.isEmpty()) {
                    ks.each { String single ->
                        map.put(i++, single)
                    }
                }
            }
        }
        map
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
