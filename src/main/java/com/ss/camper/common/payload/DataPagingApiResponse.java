package com.ss.camper.common.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class DataPagingApiResponse<T> {
    private List<T> data = new ArrayList<>();
}
