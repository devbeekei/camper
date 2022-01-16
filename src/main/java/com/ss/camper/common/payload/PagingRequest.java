package com.ss.camper.common.payload;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

@Getter
@ToString
public class PagingRequest {

    private final static int DEFAULT_SIZE = 10;
    private final static int MAX_SIZE = 50;
    private final static int DEFAULT_PAGE = 1;

    private int size;
    private int page;

    public PagingRequest(int size, int page) {
        this.setSize(size);
        this.setPage(page);
    }

    public void setSize(int size) {
        size = size <= 0 ? DEFAULT_SIZE : size;
        this.size = Math.min(size, MAX_SIZE);
    }

    public void setPage(int page) {
        page = page <= 0 ? DEFAULT_PAGE : page;
        this.page = page <= 0 ? 1 : page;
    }

    public PageRequest getPageable() {
        return PageRequest.of(page - 1, size);
    }

}
