package com.ss.camper.common.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataPagingApiResponse<T> extends ApiResponse {

    @JsonProperty("data")
    private List<T> content;

    @JsonProperty("count")
    private int totalElements;

    @JsonProperty("size")
    private int size;

    @JsonProperty("currentPage")
    private int number;

    @JsonProperty("totalPage")
    private int totalPages;

    public int getNumber() {
        return number + 1;
    }

}
