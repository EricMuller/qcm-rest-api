/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c)  2019 qcm-rest-api
 * Author  Eric Muller
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.services.entity.upload.Upload;
import com.emu.apps.qcm.web.dtos.UploadDto;
import com.emu.apps.shared.metrics.Timer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Profile("webmvc")
@CrossOrigin
@RequestMapping(value = QcmApi.API_V1 + "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UploadRestApi {
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/{fileType}", headers = "Content-Type=multipart/form-data")
    UploadDto uploadFile(@PathVariable("fileType") String fileType,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("async") Boolean async,
                         Principal principal) throws IOException;

    @GetMapping()
    @Timer
    Iterable <UploadDto> getUploads(Pageable pageable, Principal principal);

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/{id}/import")
    UploadDto importFile(@PathVariable("id") Long uploadId, Principal principal) throws IOException;

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteUploadById(@PathVariable("id") long id);

    @GetMapping(value = "/{id}")
    @ResponseBody
    UploadDto getUploadById(@PathVariable("id") long id);
}
