package com.dmgburg.dozor

import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

@Slf4j
class DzzzrKsRepository implements KsRepository {
    EngineWrapper htmlExtractor

    DzzzrKsRepository() {
        htmlExtractor = new DzzzrWrapper("http://classic.dzzzr.ru/moscow/go/")
    }

    DzzzrKsRepository(EngineWrapper htmlExtractor) {
        this.htmlExtractor = htmlExtractor
    }

    @Override
    Map<String, String> getKs() {
        String html = htmlExtractor.html
        Document parsed = Jsoup.parse(html)
        parsed.select("div")
        LinkedHashMap ks = [:]
        ks.putAll(getKsList(parsed, "основные коды"))
        ks.putAll(getKsList(parsed, "бонусные коды"))
        return ks
    }

    private LinkedHashMap getKsList(Document parsed, String pattern) {
        def zadNodesIter = parsed?.select("div")?.attr("class", "zad")?.find {
            it.text().contains("$pattern:")
        }?.childNodes()?.iterator()
        if (!zadNodesIter) {
            return [:]
        }
        def ks = ["$pattern": ""]
        def node = zadNodesIter.next()
        while (!node.text().contains("$pattern:")) {
            node = zadNodesIter.next()
        }
        fillKs(node, ks, zadNodesIter)
        ks
    }

    static void fillKs(Node node, LinkedHashMap<String, String> ks, Iterator<Node> zadNodesIter) {
        def i = 1
        def ksString = getKsString(node)
        if (ksString) {
            for (String kss : ksString.split(",")) {
                ks.put("${i++}", kss)
            }
        }
        while (zadNodesIter.hasNext()) {
            node = zadNodesIter.next()
            if (node instanceof Element) {
                i++
            } else {
                ksString = normalize(node.text())
                if (ksString) {
                    for (String kss : ksString.split(",")) {
                        if (kss) {
                            ks.put("${i++}", kss)
                        }
                    }
                }
            }
        }
    }

    static String getKsString(Node node) {
        normalize(node.text().split(":")[1])
    }

    static String normalize(String ksString) {
        ksString.replace("\\s", "").replace(" ", "")
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
