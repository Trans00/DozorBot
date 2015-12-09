package com.dmgburg.dozor

import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

@Slf4j
class DzzzrKsRepository implements KsRepository {
    DzzzrWrapper dzzzrWrapper
    def nodeToSearch

    DzzzrKsRepository() {
        dzzzrWrapper = new DzzzrWrapper()
    }

    DzzzrKsRepository(DzzzrWrapper dzzzrWrapper) {
        this.dzzzrWrapper = dzzzrWrapper
    }

    @Override
    Map<String, String> getKs() {
        String html = dzzzrWrapper.html
        Document parsed = Jsoup.parse(html)
        LinkedHashMap ks = [:]
        ks.putAll(getKsList(parsed, "основные коды"))
        ks.putAll(getKsList(parsed, "бонусные коды"))
        nodeToSearch = null
        return ks
    }

    private LinkedHashMap getKsList(Document parsed, String pattern) {
        def zadNodesIter
        def ks = [:]
        if(!nodeToSearch) {
            zadNodesIter = parsed?.select("div")?.attr("class", "zad")?.find {
                it.text().contains("$pattern:")
            }?.childNodes()?.iterator()
            if (!zadNodesIter) {
                return [:]
            }
        } else {
            zadNodesIter = nodeToSearch.childNodes().iterator()
        }
        def node = zadNodesIter.next()
        nodeToSearch = node.parentNode()
        while (!node.text().contains("$pattern:") && zadNodesIter.hasNext()) {
            node = zadNodesIter.next()
        }
        fillKs(node, ks, zadNodesIter, pattern)
        ks
    }

    static void fillKs(Node node, LinkedHashMap<String, String> ks, Iterator<Node> zadNodesIter, String  pattern) {
        def i = 1
        def ksString = getKsString(node)
        if (ksString) {
            for (String kss : ksString.split(",")) {
                ks.put("$pattern ${i++}", kss)
            }
        }
        while (zadNodesIter.hasNext()) {
            node = zadNodesIter.next()
            if (node instanceof Element) {
                if(node.tag().name=="br"){
                    break
                }
                i++
            } else {
                ksString = normalize(node.text())
                if (ksString) {
                    for (String kss : ksString.split(",")) {
                        if (kss) {
                            ks.put("$pattern ${i++}", kss)
                        }
                    }
                }
            }
        }
    }

    void setGameLogin(String gameLogin){
        dzzzrWrapper.gameLogin = gameLogin
    }

    void setGamePassword(String gamePassword){
        dzzzrWrapper.gamePassword = gamePassword
    }

    void setUserLogin(String userLogin){
        dzzzrWrapper.gameLogin = userLogin
    }

    void setUserPassword(String userPassword){
        dzzzrWrapper.gamePassword = userPassword
    }

    static String getKsString(Node node) {
        def text = node.text()
        if(text.contains(":")) {
            return normalize(text.split(":")[1])
        }
        return ""
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
