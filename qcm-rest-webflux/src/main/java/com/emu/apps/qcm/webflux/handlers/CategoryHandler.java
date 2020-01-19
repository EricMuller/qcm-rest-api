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

package com.emu.apps.qcm.webflux.handlers;


import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.webflux.mappers.ReactiveCategoryMapper;
import com.emu.apps.qcm.webflux.services.ReactiveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Profile("webflux")
@RestController
@CrossOrigin
@RequestMapping(QcmReactiveApi.API_V1 + "/categories")
public class CategoryHandler {

    private ReactiveCategoryService reactiveCategoryService;

    private ReactiveCategoryMapper reactiveCategoryMapper;

    @Autowired
    public CategoryHandler(ReactiveCategoryService reactiveCategoryService, ReactiveCategoryMapper reactiveCategoryMapper) {
        this.reactiveCategoryService = reactiveCategoryService;
        this.reactiveCategoryMapper = reactiveCategoryMapper;
    }
//
//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Flux <CategoryDto> getCategories() {
//
//        return reactiveCategoryService.findAll()
//                .map(category -> categoryMapper.modelToDto(category));
//    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux <CategoryDto> getCategories() {

        return reactiveCategoryService.findAll()
                .map(category -> reactiveCategoryMapper.modelToDto(category));
    }
}
