package com.dmgburg.dozor.web

import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.Application
import com.dmgburg.dozor.core.KsHandlerName
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import java.util.concurrent.Executors

@Controller
@RequestMapping("/")
@Slf4j
class BotController {
    private final Application app

    BotController() {
        app = new Application()
        log.info("Going to start Bot application")
        Executors.newSingleThreadExecutor().submit(app)
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onePage(ModelMap model) throws Exception {
        model.addAttribute("plugin", app.ks);
        model.addAttribute("credentialsRepository", CredentialsRepository.instance);
        model.addAttribute("credentialsRepository", CredentialsRepository.instance);
        model.addAttribute("tryEnabled", CredentialsRepository.instance.tryEnabled.toString());
        model.addAttribute("appEnabled", CredentialsRepository.instance.applicationEnabled.toString());
        model.addAttribute("users", RolesRepositoryImpl.instance.playersByChatId.values());
        model.addAttribute("pendingRequests", RolesRepositoryImpl.instance.pendingRequestsById.values());
        return "hello";
    }

    @RequestMapping(method = RequestMethod.POST,path = "/tryEnabled")
    public String tryEnabled(@RequestParam(value="tryEnabled", required=false) String tryEnabled) throws Exception {
        CredentialsRepository.instance.tryEnabled = Boolean.valueOf(tryEnabled)
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST,path = "/addUser")
    public String addUser(@RequestParam(value="id") int id,
                          @RequestParam(value="auth") String auth) throws Exception {
        RolesRepositoryImpl.instance.authentificate(id, auth == "true");
        log.info("$id was authentificated as $auth")
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST,path = "/removeUser")
    public String removeUser(@RequestParam(value="id") int id) throws Exception {
        RolesRepositoryImpl.instance.authentificate(id, false);
        log.info("$id was removed")
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/appEnabled")
    public String appEnabled(@RequestParam(value="appEnabled", required=false) Boolean appEnabled) throws Exception {
        CredentialsRepository.instance.applicationEnabled = appEnabled
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/relogin")
    public String relogin() throws Exception {
        CredentialsRepository.instance.loginRequired = true
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST,path = "/credentials")
    public String setCredentials(
            @RequestParam(value="token", required=false) String token,
            @RequestParam(value="login", required=false) String login,
            @RequestParam(value="password", required=false) String password,
            @RequestParam(value="gamelogin", required=false) String gamelogin,
            @RequestParam(value="gamepassword", required=false) String gamepassword,
            @RequestParam(value="url", required=false) String url,
            @RequestParam(value="engine", required=false) String engine) throws Exception {
        CredentialsRepository repository = CredentialsRepository.instance
        repository.authToken = token
        repository.login = login
        repository.password = password
        repository.gameLogin = gamelogin
        repository.gamePassword= gamepassword
        repository.url= url
        app.setKs(KsHandlerName.valueOf(engine))
        println(repository.toString() + " " + app.ks)
        return "redirect:/";
    }
}
