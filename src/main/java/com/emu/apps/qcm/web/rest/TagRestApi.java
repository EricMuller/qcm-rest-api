package com.emu.apps.qcm.web.rest;

import com.emu.apps.qcm.web.dtos.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RequestMapping(QcmApi.API_V1 +"/tags")
public interface TagRestApi {
    @GetMapping(produces = "application/json")
    @ResponseBody
    Page<TagDto> getTagsByPAge(@RequestParam(value = "search", required = false) String search, Pageable pageable, Principal principal ) throws IOException;

    @GetMapping(value = "{id}")
    @ResponseBody
    TagDto getTagById(@PathVariable("id") Long id);

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    TagDto saveTag(@RequestBody TagDto tagDto);

}
