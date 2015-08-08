package com.dmgburg.dozor

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class EncounterKsRepository implements KsRepository{
    EngineWrapper wrapper

    EncounterKsRepository(String baseUrl, String username, String password) {
        wrapper = new EncounterWrapper()
        wrapper.login(baseUrl,username,password)
    }

    EncounterKsRepository(EngineWrapper wrapper){
        this.wrapper = wrapper

    }
    @Override
    void setKs(List<String> ks) {
        throw new UnsupportedOperationException("Can't set KS on parsing repository")
    }

    @Override
    Map<Integer, String> getKs() {
        String html = getHtml()
        Document parsed = Jsoup.parse(html)
        parsed?.select("div")?.attr("class","cols")?.each {
            for(Element child :it.childNodes()){
                println child.text()
            }
        }
        return [:]
    }

    @Override
    void removeKs(int number) {
        throw new UnsupportedOperationException("Can't remove KS on parsing repository")
    }

    String getHtml() {
        return wrapper.html
    }

    String getCookieString(){
        StringBuilder sb = new StringBuilder()
        cookies.each {String key, String value ->
            sb.append("$key=$value; ")
        }
        return sb.toString()
    }
}
