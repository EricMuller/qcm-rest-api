package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.repositories.BookmarkJdbcRepository;
import com.emu.apps.qcm.web.rest.dtos.BookmarkDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v0/bookmarks")
@Api(value="bookmark-store", description="Operations on bookmarks Store")
public class BookmarkRestController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookmarkJdbcRepository bookmarkRepository;

    @RequestMapping(value = "test",method= RequestMethod.GET)
    @ApiOperation(value = "Dummy Test ",response = String.class)
    public String test() {
        return "OK";
    }

    @RequestMapping(value = "{id}" ,method= RequestMethod.GET)
    @ApiOperation(value = "Search a bookmark with an ID",response = BookmarkDto.class)
    public BookmarkDto getBookmark(@PathVariable("id") long id) {
        logger.info("Get Bookmark");
        return bookmarkRepository.getBookmark(id);
    }

   /* @RequestMapping(value= "ids", produces = "application/json")
    public List<Bookmark> getBookmarksById(@RequestParam("ids") long[] ids) {
        LOGGER.info("Get bookmarks");
        return bookmarkRepository.getBookmarks(ids);
    }*/
    @RequestMapping(produces = "application/json", method= RequestMethod.GET)
    @ApiOperation(value = "View a list of bookmarks",responseContainer = "List", response = BookmarkDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    public List<BookmarkDto> getBookmarks() {
        return bookmarkRepository.getBookmarks();
    }
} 