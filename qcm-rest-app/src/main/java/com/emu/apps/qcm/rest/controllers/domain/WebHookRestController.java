package com.emu.apps.qcm.rest.controllers.domain;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.webhook.WebHookRepository;
import com.emu.apps.qcm.domain.model.webhook.WebhookId;
import com.emu.apps.qcm.rest.controllers.domain.mappers.QcmResourceMapper;
import com.emu.apps.qcm.rest.controllers.domain.resources.WebHookResource;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.DOMAIN_API;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.WEBHOOKS;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = DOMAIN_API + WEBHOOKS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "WebHook")
@Validated
public class WebHookRestController {

    private final WebHookRepository webHookRepository;

    private final QcmResourceMapper qcmResourceMapper;

    public WebHookRestController(WebHookRepository webHookServicePort, QcmResourceMapper qcmResourceMapper) {
        this.webHookRepository = webHookServicePort;
        this.qcmResourceMapper = qcmResourceMapper;
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Page <WebHookResource> getWebhooks(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return qcmResourceMapper.webhookToWebhookResources(webHookRepository.getWebHooks(pageable,  PrincipalId.of(getPrincipal())));
    }

    @PostMapping
    @ResponseBody
    public WebHookResource saveWebHook(@RequestBody @Valid WebHookResource webHookResource) {
        var webhook = qcmResourceMapper.webhookResourceToModel(webHookResource);
        return qcmResourceMapper.webhookToWebhookResources(webHookRepository.saveWebHook(webhook, PrincipalId.of(getPrincipal())));
    }

    @PutMapping(value = "/{uuid}")
    @ResponseBody
    public WebHookResource updateWebHook(@PathVariable("uuid") String uuid, @RequestBody @Valid WebHookResource webHookResource) {
        var webhook = qcmResourceMapper.webhookResourceToModel(webHookResource);
        return qcmResourceMapper.webhookToWebhookResources(webHookRepository.saveWebHook(webhook, PrincipalId.of(getPrincipal())));
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public WebHookResource getWebHookByUuid(@PathVariable("uuid") String uuid) {
        return qcmResourceMapper.webhookToWebhookResources(webHookRepository.getWebHookOfId(new WebhookId(uuid)));
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWebhookByUuid(@PathVariable("uuid") String uuid) {
        webHookRepository.deleteWebHookOfId(new WebhookId(uuid));
    }


}
