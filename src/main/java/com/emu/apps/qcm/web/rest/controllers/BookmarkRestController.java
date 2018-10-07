package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.repositories.BookmarkJdbcRepository;
import com.emu.apps.qcm.web.rest.BookmarkRestApi;
import com.emu.apps.qcm.web.rest.dtos.BookmarkDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookmarkRestController implements BookmarkRestApi {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookmarkJdbcRepository bookmarkRepository;

    @Override
    public String test() {
        return "OK";
    }

    @Override
    public BookmarkDto getBookmark(@PathVariable("id") long id) {
        logger.info("Get Bookmark");
        return bookmarkRepository.getBookmark(id);
    }

   /* @RequestMapping(value= "ids", produces = "application/json")
    public List<Bookmark> getBookmarksById(@RequestParam("ids") long[] ids) {
        LOGGER.info("Get bookmarks");
        return bookmarkRepository.getBookmarks(ids);
    }*/
    @Override
    @RequestMapping(produces = "application/json", method= RequestMethod.GET)
    public List<BookmarkDto> getBookmarks() {
        return bookmarkRepository.getBookmarks();
    }
} 