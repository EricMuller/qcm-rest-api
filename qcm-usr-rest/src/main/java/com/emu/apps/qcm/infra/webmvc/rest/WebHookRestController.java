package com.emu.apps.qcm.infra.webmvc.rest;

import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.webhook.WebHook;
import com.emu.apps.qcm.domain.models.webhook.WebhookId;
import com.emu.apps.qcm.domain.repositories.WebHookRepository;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.WEBHOOKS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + WEBHOOKS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "WebHook")
@Validated
public class WebHookRestController {

    private final WebHookRepository webHookRepository;

    public WebHookRestController(WebHookRepository webHookServicePort) {
        this.webHookRepository = webHookServicePort;
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Iterable <WebHook> getWebhooks(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return webHookRepository.getWebHooks(pageable, new PrincipalId(getPrincipal()));
    }

    @PostMapping
    @ResponseBody
    public WebHook saveWebHook(@RequestBody @Valid WebHook webHookDto) {
        return webHookRepository.saveWebHook(webHookDto, getPrincipal());
    }

    @PutMapping
    @ResponseBody
    public WebHook updateWebHook(@RequestBody @Valid WebHook webHookDto) {
        return webHookRepository.saveWebHook(webHookDto, getPrincipal());
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public WebHook getWebHookByUuid(@PathVariable("uuid") String uuid) {
        return webHookRepository.getWebHookByUuid(new WebhookId(uuid));
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWebhookByUuid(@PathVariable("uuid") String uuid) {
        webHookRepository.deleteWebHookByUuid(new WebhookId(uuid));
    }


}
