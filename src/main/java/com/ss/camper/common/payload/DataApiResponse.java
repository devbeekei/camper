package com.ss.camper.common.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class DataApiResponse<T> extends ApiResponse {
    private T result;
}
