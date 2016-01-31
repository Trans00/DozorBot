package com.dmgburg.dozor

import org.jsoup.nodes.Document

interface EngineWrapper {
    public Document getHtml()
    public Document tryCode(String code)
}
