package com.ss.camper.common.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {

    private List<T> content;

    @JsonProperty("count")
    private long totalElements;

    @JsonProperty("size")
    private long size;

    @JsonProperty("currentPage")
    private long number;

    @JsonProperty("totalPage")
    private long totalPages;

    public long getNumber() {
        return number + 1;
    }

}
