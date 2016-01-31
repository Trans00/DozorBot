package com.dmgburg.dozor.dzzzr

import com.dmgburg.dozor.KsRepository
import groovy.util.logging.Slf4j
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.select.Elements

@Slf4j
class DzzzrKsRepository implements KsRepository {
    DzzzrWrapper dzzzrWrapper

    DzzzrKsRepository() {
        dzzzrWrapper = new DzzzrWrapper()
    }

    DzzzrKsRepository(DzzzrWrapper dzzzrWrapper) {
        this.dzzzrWrapper = dzzzrWrapper
    }

    @Override
    Map<String, String> getKs() {
        Document parsed = null
        try {
            parsed = dzzzrWrapper.html
            Element mainColumn = getMainColumn(parsed)
            Element mainMission = getMainMission(mainColumn)
            Map ks = [:]
            ks.put(getMissionTitle(mainColumn), "")
            ks.putAll(getKsList(mainMission, "основные коды"))
            ks.putAll(getKsList(mainMission, "бонусные коды"))
            return ks
        } catch (Throwable t){
            log.error("Error parsing html: \n${parsed?.html()}\n", t)
            return ["Не удалось получить КС":" что-то сломалось :-("]
        }
    }

    static String getMissionTitle(Element element) {
        StringBuilder stringBuilder = new StringBuilder()
        Element title = element?.select('div.title')?.first()
        stringBuilder.append(title.childNode(0).toString())
        stringBuilder.append(title.childNode(3).childNode(0).toString())
        return stringBuilder.toString()
    }

    static Element getMainColumn(Element html) {
        Elements elements = html.select('td[width="70%"]')
        if (elements.size() > 1) {
            log.error("There are more than one main column")
        }
        elements.first()
    }

    static Element getMainMission(Element mainColumn) {
        mainColumn?.select('div.zad')?.first()
    }

    private static LinkedHashMap getKsList(Element mainMission, String pattern) {

        def ks = [:]
        def zadNodesIter = mainMission?.select('div.zad')?.first()?.childNodes()?.iterator()
        if (!zadNodesIter) {
            return [:]
        }
        def node = zadNodesIter.next()
        while (!node.text().contains("$pattern:") && zadNodesIter.hasNext()) {
            node = zadNodesIter.next()
        }
        fillKs(node, ks, zadNodesIter, pattern)
        ks
    }

    static void fillKs(Node node, LinkedHashMap<String, String> ks, Iterator<Node> zadNodesIter, String pattern) {
        def i = 1
        def ksString = getKsString(node)
        if (ksString) {
            for (String kss : ksString.split(",")) {
                ks.put("$pattern ${i++}".toString(), kss)
            }
        }
        while (zadNodesIter.hasNext()) {
            node = zadNodesIter.next()
            if (node instanceof Element) {
                if (node.tag().name == "br") {
                    break
                }
                i++
            } else {
                ksString = normalize(node.text())
                if (ksString) {
                    for (String kss : ksString.split(",")) {
                        if (kss) {
                            ks.put("$pattern ${i++}".toString(), kss)
                        }
                    }
                }
            }
        }
    }

    static String getKsString(Node node) {
        def text = node.text()
        if (text.contains(":")) {
            return normalize(text.split(":")[1])
        }
        return ""
    }

    static String normalize(String ksString) {
        ksString.replace("\\s", "").replace(" ", "")
    }

    @Override
    boolean tryCode(String code) {
        Element sysMessage = dzzzrWrapper.tryCode(code).select("div.sysmsg").first()
        StringBuilder sb = new StringBuilder()
        sysMessage.childNodes().each {
            it -> sb.append(it.toString())
                  sb.append("\n")
        }
        return sb.toString()
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
