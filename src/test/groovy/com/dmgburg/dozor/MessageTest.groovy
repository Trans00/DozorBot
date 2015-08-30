package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Message
import org.junit.Before
import org.junit.Test

class MessageTest {

    Message message
    
    @Before
    void setUp(){
        message = new Message()
    }
    
    @Test
    void "should return command string when message contains command only"(){
        message.text = " /test  "
        assert message.getCommand() == "test"
    }

    @Test
    void "should return empty string when message doesn't contain command"(){
        message.text = "some Text with /command"
        assert message.getCommand() == ""
    }

    @Test
    void "should return command string when message contains command and text"(){
        message.text = "/test some Text with /command"
        assert message.getCommand() == "test"
    }

    @Test
    void "should return empty string when message contains command only"(){
        message.text = " /test  "
        assert message.getArgument() == ""
    }

    @Test
    void "should return entire string when message doesn't contain command"(){
        message.text = "some Text with /command"
        assert message.getArgument() == "some Text with /command"
    }

    @Test
    void "should return command arguments when message contains command and text"(){
        message.text = "/test some Text with /command"
        assert message.getArgument() == "some Text with /command"
    }
}
