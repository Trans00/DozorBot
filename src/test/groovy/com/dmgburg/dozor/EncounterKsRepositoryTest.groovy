package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovy.xml.StreamingMarkupBuilder
import org.junit.Before
import org.junit.Test

class EncounterKsRepositoryTest {

    EncounterKsRepository repository
    String userName ="enc_test_user"
    String password = "a123456789"

    @Before
    void setUp(){
        repository = new EncounterKsRepository(login: userName, password: password)
        NodeChild.metaClass.toXmlString = {
            def self = delegate
            new StreamingMarkupBuilder().bind {
                delegate.mkp.xmlDeclaration() // Use this if you want an XML declaration
                delegate.out << self
            }.toString()
        }
    }

    @Test
    void "should login as test user"(){
        def b = repository.html
        println b.BODY.TABLE.find {it.@id == "tableContent"}.TBODY.TR.find{it.@id =="contentRow"}.TD.TABLE.TBODY.TR.TD.find{it.@id == "tdContentLeft"}
                .DIV.find{it.@id=="boxUser"}.DIV.DIV.TABLE.TBODY.TR.TD.text()
        assert b.BODY.TABLE.find {it.@id == "tableContent"}.TBODY.TR.find{it.@id =="contentRow"}.TD.TABLE.TBODY.TR.TD.find{it.@id == "tdContentLeft"}
                .DIV.find{it.@id=="boxUser"}.DIV.DIV.TABLE.TBODY.TR.TD.text().contains(userName)
    }
}
