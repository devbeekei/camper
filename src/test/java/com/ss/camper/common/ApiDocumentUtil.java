package com.ss.camper.common;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ApiDocumentUtil {

    public static OperationRequestPreprocessor getDocumentRequest() {
        // Request Spec을 정렬해서 출력해줌
        return preprocessRequest(prettyPrint());
    }

    public static OperationResponsePreprocessor getDocumentResponse() {
        // Response Spec을 정렬해서 출력해줌
        return preprocessResponse(prettyPrint());
    }

    public static FieldDescriptor[] defaultResponseFields() {
        return new FieldDescriptor[] {
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
        };
    }

    public static FieldDescriptor[] dataResponseFields(FieldDescriptor ... fieldDescriptors) {
        FieldDescriptor[] responseFields = defaultResponseFields();
        if (fieldDescriptors.length > 0) {
            FieldDescriptor[] newResponseFields = Arrays.copyOf(responseFields, (responseFields.length + 1 + fieldDescriptors.length));
            newResponseFields[responseFields.length] = fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터");
            int idx = responseFields.length + 1;
            for (FieldDescriptor descriptor : fieldDescriptors) {
                newResponseFields[idx] = descriptor;
                idx++;
            }
            responseFields = newResponseFields;
        }
        return responseFields;
    }

    public static FieldDescriptor[] pagingResponseFields(FieldDescriptor ... fieldDescriptors) {
        FieldDescriptor[] responseFields = defaultResponseFields();
        if (fieldDescriptors.length > 0) {
            FieldDescriptor[] newResponseFields = Arrays.copyOf(responseFields, (responseFields.length + 5 + fieldDescriptors.length));
            newResponseFields[responseFields.length] = fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터 목록");
            int idx = responseFields.length + 1;
            for (FieldDescriptor descriptor : fieldDescriptors) {
                newResponseFields[idx] = descriptor;
                idx++;
            }
            responseFields = newResponseFields;
            newResponseFields[idx] = fieldWithPath("count").type(JsonFieldType.NUMBER).description("결과 데이터 수");
            newResponseFields[++idx] = fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지에 보일 데이터 수");
            newResponseFields[++idx] = fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지");
            newResponseFields[++idx] = fieldWithPath("totalPage").type(JsonFieldType.NUMBER).description("총 페이지");
        }
        return responseFields;
    }

}
