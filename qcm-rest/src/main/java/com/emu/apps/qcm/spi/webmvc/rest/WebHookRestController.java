package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.api.models.WebHook;
import com.emu.apps.qcm.domain.ports.WebHookServicePort;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.WEBHOOKS;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + WEBHOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "WebHook")
public class WebHookRestController {

    private final WebHookServicePort webHookServicePort;

    public WebHookRestController(WebHookServicePort webHookServicePort) {
        this.webHookServicePort = webHookServicePort;
    }

    @GetMapping()
    @Timer
    public Iterable <WebHook> getWebhooks(Pageable pageable) {
        return webHookServicePort.getWebHooks(pageable, AuthentificationContextHolder.getUser());
    }

    @PostMapping
    @ResponseBody
    public WebHook saveWebHook(@RequestBody @Valid WebHook webHookDto) {
        return webHookServicePort.saveWebHook(webHookDto, AuthentificationContextHolder.getUser());
    }

    @PutMapping
    @ResponseBody
    public WebHook updateWebHook(@RequestBody @Valid WebHook webHookDto) {
        return webHookServicePort.saveWebHook(webHookDto, AuthentificationContextHolder.getUser());
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public WebHook getWebHookByUuid(@PathVariable("uuid") String uuid) {
        return webHookServicePort.getWebHookByUuid(uuid);
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadById(@PathVariable("uuid") String uuid) {
        webHookServicePort.deleteWebHookByUuid(uuid);
    }


}
