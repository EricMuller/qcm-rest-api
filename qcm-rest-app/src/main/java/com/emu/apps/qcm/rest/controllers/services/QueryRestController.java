package com.emu.apps.qcm.rest.controllers.services;

import com.emu.apps.qcm.infra.query.QueryPort;
import com.emu.apps.qcm.infra.query.adapters.jdbc.dto.Results;
import com.emu.apps.qcm.rest.controllers.domain.resources.MessageResource;
import com.emu.apps.shared.annotations.Timer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.DOMAIN_API;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.QUERY;
import static com.emu.apps.qcm.rest.controllers.domain.resources.MessageResource.ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping(value = DOMAIN_API + QUERY, produces = {APPLICATION_JSON_VALUE})
@io.swagger.v3.oas.annotations.tags.Tag(name = "Query")
@Validated
@Timed("query")
public class QueryRestController {

    private QueryPort queryPort;

    public QueryRestController(@Qualifier("jdbcQueryDynaPort") QueryPort queryPort) {
        this.queryPort = queryPort;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @Operation(summary = "Query", description = "", tags = {"Query"})
    // @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List", content = @Content(array = @ArraySchema(schema = @Schema(name = "rows", implementation = Results.class)))), @ApiResponse(responseCode = "400", description = "Invalid input")})
    public Results getQuery(@RequestParam("id") String queryName) {

        String sql = "select * from question";

        return queryPort.executeQuery(queryName, sql);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @Timer
    @Operation(summary = "Query", description = "", tags = {"Query"})
    // @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List", content = @Content(array = @ArraySchema(schema = @Schema(name = "rows", implementation = Results.class)))), @ApiResponse(responseCode = "400", description = "Invalid input")})
    public Results postQuery(@RequestParam("id") String queryName) {

        String sql = "select * from question";

        return queryPort.executeQuery(queryName, sql);
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity <MessageResource> handleAllException(Exception e) {
        return badRequest().body(new MessageResource(ERROR, e.getMessage()));
    }


}
