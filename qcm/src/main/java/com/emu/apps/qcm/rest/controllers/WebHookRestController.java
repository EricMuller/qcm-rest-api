package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.webhook.WebHookRepository;
import com.emu.apps.qcm.domain.model.webhook.WebhookId;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.WebHookResources;
import com.emu.apps.shared.annotations.Timer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.WEBHOOKS;
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

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;

    public WebHookRestController(WebHookRepository webHookServicePort, QuestionnaireResourcesMapper questionnaireResourcesMapper) {
        this.webHookRepository = webHookServicePort;
        this.questionnaireResourcesMapper = questionnaireResourcesMapper;
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Page <WebHookResources> getWebhooks(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionnaireResourcesMapper.webhookToResources(webHookRepository.getWebHooks(pageable, new PrincipalId(getPrincipal())));
    }

    @PostMapping
    @ResponseBody
    public WebHookResources saveWebHook(@RequestBody @Valid WebHookResources webHookResources) {
        var webhook = questionnaireResourcesMapper.webhookToModel(webHookResources);
        return questionnaireResourcesMapper.webhookToResources(webHookRepository.saveWebHook(webhook, getPrincipal()));
    }

    @PutMapping
    @ResponseBody
    public WebHookResources updateWebHook(@RequestBody @Valid WebHookResources webHookResources) {
        var webhook = questionnaireResourcesMapper.webhookToModel(webHookResources);
        return questionnaireResourcesMapper.webhookToResources(webHookRepository.saveWebHook(webhook, getPrincipal()));
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public WebHookResources getWebHookByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireResourcesMapper.webhookToResources(webHookRepository.getWebHookByUuid(new WebhookId(uuid)));
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWebhookByUuid(@PathVariable("uuid") String uuid) {
        webHookRepository.deleteWebHookByUuid(new WebhookId(uuid));
    }


}
