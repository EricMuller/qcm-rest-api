package com.emu.apps.qcm.web.rest.dtos;

import io.swagger.annotations.*;

@ApiModel(value = "Bookmark")
public class BookmarkDto {
	@ApiModelProperty(notes = "The database generated product ID")
    public long id;
	@ApiModelProperty(notes = "The title  of the Bookmark")
    public String title;
	@ApiModelProperty(notes = "The url  of the Bookmark")
    public String url;
	@ApiModelProperty(notes = "The description  of the Bookmark")
    public String description;

    public BookmarkDto(long id, String title, String url, String description) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    	
} 