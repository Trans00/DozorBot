package com.dmgburg.dozor

import com.github.igorsuhorukov.phantomjs.PhantomJsDowloader
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities

@Singleton
class PhantomJSWraper {
    public static String phantomJsPath = PhantomJsDowloader.getPhantomJsPath()
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"

    public WebDriver getDriver(){
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath)
        def DesiredCapabilities settings = new DesiredCapabilities()
        settings.setJavascriptEnabled(true)
        settings.setCapability("takesScreenshot", true)
        settings.setCapability("userAgent", USER_AGENT)
        settings.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath)
        return  new PhantomJSDriver()
    }
}
