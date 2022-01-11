package com.ss.camper.common.payload;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

@Getter
@ToString
public class PagingRequest {

    private final static int DEFAULT_SIZE = 10;
    private final static int MAX_SIZE = 50;

    private int size;
    private int page;

    public PagingRequest(int size, int page) {
        this.setSize(size);
        this.setPage(page);
    }

    public PagingRequest() {
        this.size = DEFAULT_SIZE;
        this.page = 1;
    }

    public void setSize(int size) {
        this.size = Math.min(size, MAX_SIZE);
    }

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public PageRequest getPageable() {
        return PageRequest.of(page, size);
    }

}
