package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.ports.WebHookServicePort;
import com.emu.apps.qcm.models.WebHookDto;
import com.emu.apps.shared.annotations.Timer;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.WEBHOOKS;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + WEBHOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
public class WebHookRestController {

    private final WebHookServicePort webHookServicePort;

    public WebHookRestController(WebHookServicePort webHookServicePort) {
        this.webHookServicePort = webHookServicePort;
    }

    @GetMapping()
    @Timer
    public Iterable <WebHookDto> getWebhooks(Pageable pageable) {
        return webHookServicePort.getWebHooks(pageable, AuthentificationContextHolder.getUser());
    }

    @PostMapping
    @ResponseBody
    public WebHookDto saveWebHook(@RequestBody @Valid WebHookDto webHookDto) {
        return webHookServicePort.saveWebHook(webHookDto, AuthentificationContextHolder.getUser());
    }

    @PutMapping
    @ResponseBody
    public WebHookDto updateWebHook(@RequestBody @Valid WebHookDto webHookDto) {
        return webHookServicePort.saveWebHook(webHookDto, AuthentificationContextHolder.getUser());
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public WebHookDto getWebHookByUuid(@PathVariable("uuid") String uuid) {
        return webHookServicePort.getWebHookByUuid(uuid);
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUploadById(@PathVariable("uuid") String uuid) {
        webHookServicePort.deleteWebHookByUuid(uuid);
    }


}
