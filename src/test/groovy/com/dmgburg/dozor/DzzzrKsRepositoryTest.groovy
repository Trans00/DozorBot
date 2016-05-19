package com.dmgburg.dozor

import com.dmgburg.dozor.configs.HtmlPublishingContext
import com.dmgburg.dozor.dzzzr.DzzzrKsRepository
import com.dmgburg.dozor.dzzzr.DzzzrWrapper
import com.dmgburg.dozor.engine.TestJettyServer
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

import static com.dmgburg.dozor.configs.HtmlPublishingContext.Mode.Unit
import static com.dmgburg.dozor.configs.HtmlPublishingContext.setMode
import static org.mockito.Mockito.when

class DzzzrKsRepositoryTest {
    private static final String ADDRESS = "http://localhost:8080"
    DzzzrKsRepository repository
    DzzzrWrapper wrapper
    static TestJettyServer server
    def main = "основные коды"
    @Mock
    CredentialsRepository credentialsRepository

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
        wrapper = new DzzzrWrapper(credentialsRepository)
        repository = new DzzzrKsRepository(wrapper)
        mode = Unit
        when(credentialsRepository.url).thenReturn("$ADDRESS")
        when(credentialsRepository.login).thenReturn("login")
        when(credentialsRepository.gameLogin).thenReturn("login")
        when(credentialsRepository.password).thenReturn("password")
        when(credentialsRepository.gamePassword).thenReturn("password")
    }

    @BeforeClass
    static void start() {
        CountDownLatch latch = new CountDownLatch(1)
        server = new TestJettyServer(latch)
        server.start()
        latch.await(60, TimeUnit.SECONDS)
    }

    @AfterClass
    static void destroy() {
        server.stop()
    }

    @Test
    void "should return 1 ks from html when 1 ks exist"() {
        HtmlPublishingContext.name = "dzzzr1"
        assert repository.ks == ["основные коды 1": "null"]
    }

    @Test
    void "should return ks from html"() {
        HtmlPublishingContext.name = "dzzzr2"
        assert repository.ks == ["основные коды 1" : "1", "основные коды 2": "2", "основные коды 7": "1+",
                                 "основные коды 13": "2", "основные коды 18": "2", "основные коды 26": "null"]
    }

    @Test
    void "should return no ks from html when no ks exist"() {
        HtmlPublishingContext.name = "dzzzr3"
        assert repository.ks == [:]
    }

    @Test
    void "should return ks from html when first ks taken"() {
        HtmlPublishingContext.name = "dzzzr4"
        assert repository.ks == ["основные коды 2" : "1+", "основные коды 3": "1", "основные коды 4": "2",
                                 "основные коды 5" : "1", "основные коды 7": "3", "основные коды 8": "2+",
                                 "основные коды 9" : "2", "основные коды 10": "1", "основные коды 11": "1",
                                 "основные коды 12": "1+", "основные коды 13": "1", "основные коды 14": "1+",
                                 "основные коды 15": "1+", "основные коды 16": "1+", "основные коды 17": "1+",
                                 "основные коды 18": "1", "основные коды 19": "1+", "основные коды 20": "1",
                                 "основные коды 21": "1+", "основные коды 22": "1+", "основные коды 23": "1",
                                 "основные коды 24": "2", "основные коды 25": "1+", "основные коды 26": "1+",
                                 "основные коды 27": "1+", "основные коды 28": "1+", "основные коды 29": "1+",
                                 "основные коды 30": "1", "основные коды 31": "1+", "основные коды 32": "1+",
                                 "основные коды 33": "1", "основные коды 34": "1", "основные коды 35": "1+",
                                 "основные коды 36": "null"]
    }

    @Test
    void "should return ks after proper login"() {
        HtmlPublishingContext.name = "dzzzr1"
        mode = Dzzzr
        when(credentialsRepository.gameLogin).thenReturn("gameLogin")
        when(credentialsRepository.gamePassword).thenReturn("gamePass")
        when(credentialsRepository.login).thenReturn("userLogin")
        when(credentialsRepository.password).thenReturn("userPass")
        assert repository.ks == ["основные коды 1": "null"]
    }

    @Test(expected = AuthorizationException)
    void "should receive error when game login pass invalid"() {
        HtmlPublishingContext.name = "dzzzr1"
        mode = Dzzzr
        when(credentialsRepository.gameLogin).thenReturn("invalid")
        when(credentialsRepository.gamePassword).thenReturn("invalid")
        repository.ks
    }

    @Test
    void "should receive login page when user login invalid"() {
        HtmlPublishingContext.name = "dzzzr1"
        mode = Dzzzr
        when(credentialsRepository.gameLogin).thenReturn("gameLogin")
        when(credentialsRepository.gamePassword).thenReturn("gamePass")
        when(credentialsRepository.login).thenReturn("invalid")
        when(credentialsRepository.password).thenReturn("invalid")
        assert repository.ks == [:]
    }

    @Test
    void "test"() {
//        WebDriver driver = PhantomJSWraper.instance.getDriver()
//        driver.get("http://classic.dzzzr.ru/moscow/")
//        driver.findElement(By.cssSelector("input[type=\"text\"][name=\"login\"]")).sendKeys("golden_surfer")
//        driver.findElement(By.cssSelector("input[type=\"password\"][name=\"password\"]")).sendKeys("a123456789")
//        driver.findElement(By.cssSelector("input[type=\"submit\"][value=\"ok\"]")).click()
//        File submitted = (driver as PhantomJSDriver).getScreenshotAs(OutputType.FILE)
//        submitted.absolutePath
//        File submitted2 = (driver as PhantomJSDriver).getScreenshotAs(OutputType.FILE)
//        submitted2.absolutePath
//        Map<String, String> coockies = [:]
//        driver.manage().cookies.each{
//            coockies.put(it.name,it.value)
//        }
//
//
//        println driver.pageSource
    }
}
