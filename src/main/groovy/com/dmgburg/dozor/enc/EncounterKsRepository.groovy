package com.dmgburg.dozor.enc

import com.dmgburg.dozor.EngineWrapper
import com.dmgburg.dozor.KsRepository
import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

@Slf4j
class EncounterKsRepository implements KsRepository {
    EngineWrapper wrapper

    EncounterKsRepository() {
        wrapper = new EncounterWrapper()
    }

    EncounterKsRepository(EngineWrapper wrapper) {
        this.wrapper = wrapper

    }

    @Override
    void setKs(List<String> ks) {
        throw new UnsupportedOperationException("Can't set KS on parsing repository")
    }

    @Override
    Map<String, String> getKs() {
        Map<String, String> map = [:]
        try {
            String html = getHtml()
            Document parsed = Jsoup.parse(html)
            int i = 1
            def list = []
            parsed?.body()?.getElementsByClass("container")[0]?.getElementsByClass("content")[0]?.getElementsByClass("cols-wrapper")[0]?.getElementsByClass("cols")?.each {
                it.select("p").each {
                    list << it
                }
            }
            list.each {
                Element it ->
                    if (it.child(0).className() == "color_dis") {
                        map.put("${i++}:${it.childNode(0).text}", it.child(0).text())
                    } else {
                        i++
                    }
            }
            parsed?.body()?.getElementsByClass("container")[0]?.getElementsByClass("content")[0].getElementsByTag("h3").findAll {
                Element it -> it.className() == "color_bonus" || it.className() == "color_correct"
            }.each {
                if (it.className() == "color_bonus") {
                    map.put("${i++}:${it?.childNode(0)?.text?.replaceAll("\\r", "")?.replaceAll("\\n", "")?.replaceAll("\\t", "")}".toString(), it?.nextElementSibling()?.text())
                } else {
                    map.put("${i++}:${it?.childNode(0)?.text?.replaceAll("\\r", "")?.replaceAll("\\n", "")?.replaceAll("\\t", "")}".toString(), it?.nextElementSibling()?.text())
                }
            }
        } catch (Exception e) {
            log.error("Exception during parsing: ${wrapper.html}", e)
        }
        return map
    }

    @Override
    void removeKs(int number) {
        throw new UnsupportedOperationException("Can't remove KS on parsing repository")
    }

    String getHtml() {
        return wrapper.html
    }
}
