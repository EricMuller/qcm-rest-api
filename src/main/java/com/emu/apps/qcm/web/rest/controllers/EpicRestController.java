package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.EpicService;
import com.emu.apps.qcm.services.entity.epics.Epic;
import com.emu.apps.qcm.web.rest.dtos.EpicDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.mappers.EpicMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping("/api/v1/epics")
@Api(value = "epic-store", description = "All operations ", tags = "Epic")
public class EpicRestController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EpicService epicService;

    @Autowired
    private EpicMapper epicMapper;


    @ApiOperation(value = "Find a epics by ID", response = EpicDto.class, tags = "Epic", nickname = "getEpicById")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public EpicDto getEpic(@PathVariable("id") Long id) {
        return epicMapper.modelToDto(epicService.findById(id));
    }

    @ApiOperation(value = "Find all epics", responseContainer = "List", response = EpicDto.class, nickname = "getEpics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @PreAuthorize("true")
    public Iterable<EpicDto> getCategories() {
        return epicMapper.modelsToDtos(epicService.findAll());

    }

    @ApiOperation(value = "Save a epic", response = EpicDto.class, nickname = "saveEpic")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public EpicDto saveEpic(@RequestBody EpicDto epicDto) {
        Epic epic = epicMapper.dtoToModel(epicDto);
        return epicMapper.modelToDto(epicService.save(epic));
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    public ResponseEntity<?> handleAllException(Exception e) throws IOException {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
