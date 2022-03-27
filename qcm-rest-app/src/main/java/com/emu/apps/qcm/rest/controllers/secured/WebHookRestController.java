package com.emu.apps.qcm.rest.controllers.secured;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.webhook.WebHookRepository;
import com.emu.apps.qcm.domain.model.webhook.WebhookId;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.controllers.secured.resources.WebHookResource;
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

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PROTECTED_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.WEBHOOKS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = PROTECTED_API + WEBHOOKS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "WebHook")
@Validated
public class WebHookRestController {

    private final WebHookRepository webHookRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public WebHookRestController(WebHookRepository webHookServicePort, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.webHookRepository = webHookServicePort;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    @GetMapping
    @Timer
    @PageableAsQueryParam
    public Page <WebHookResource> getWebhooks(
            @Parameter(hidden = true)
            @PageableDefault(direction = DESC, sort = {"dateModification"}) Pageable pageable) {

        return questionnaireResourceMapper.webhookToResources(webHookRepository.getWebHooks(pageable,  PrincipalId.of(getPrincipal())));
    }

    @PostMapping
    @ResponseBody
    public WebHookResource saveWebHook(@RequestBody @Valid WebHookResource webHookResource) {
        var webhook = questionnaireResourceMapper.webhookToModel(webHookResource);
        return questionnaireResourceMapper.webhookToResources(webHookRepository.saveWebHook(webhook, PrincipalId.of(getPrincipal())));
    }

    @PutMapping(value = "/{uuid}")
    @ResponseBody
    public WebHookResource updateWebHook(@PathVariable("uuid") String uuid, @RequestBody @Valid WebHookResource webHookResource) {
        var webhook = questionnaireResourceMapper.webhookToModel(webHookResource);
        return questionnaireResourceMapper.webhookToResources(webHookRepository.saveWebHook(webhook, PrincipalId.of(getPrincipal())));
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    public WebHookResource getWebHookByUuid(@PathVariable("uuid") String uuid) {
        return questionnaireResourceMapper.webhookToResources(webHookRepository.getWebHookByUuid(new WebhookId(uuid)));
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWebhookByUuid(@PathVariable("uuid") String uuid) {
        webHookRepository.deleteWebHookByUuid(new WebhookId(uuid));
    }


}
