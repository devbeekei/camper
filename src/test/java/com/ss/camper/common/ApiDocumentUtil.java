package com.ss.camper.common;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ApiDocumentUtil {

    private static final String RESULT_FILED_NAME = "result";

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
            FieldDescriptor[] newResponseFields =
                    Arrays.copyOf(responseFields, (responseFields.length + fieldDescriptors.length + 1));

            int idx = responseFields.length;
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME).type(JsonFieldType.OBJECT).description("결과 데이터");
            for (FieldDescriptor descriptor : fieldDescriptors) {
                newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + "." + descriptor.getPath()).type(descriptor.getType()).description(descriptor.getDescription());
            }
            responseFields = newResponseFields;
        }
        return responseFields;
    }

    public static FieldDescriptor[] collectionResponseFields(FieldDescriptor ... fieldDescriptors) {
        FieldDescriptor[] responseFields = defaultResponseFields();
        if (fieldDescriptors.length > 0) {
            FieldDescriptor[] newResponseFields =
                    Arrays.copyOf(responseFields, (responseFields.length + fieldDescriptors.length + 1));

            int idx = responseFields.length;
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + "[]").type(JsonFieldType.OBJECT).description("결과 데이터");
            for (FieldDescriptor descriptor : fieldDescriptors) {
                newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + "[]." + descriptor.getPath()).type(descriptor.getType()).description(descriptor.getDescription());
            }
            responseFields = newResponseFields;
        }
        return responseFields;
    }

    public static FieldDescriptor[] pagingResponseFields(FieldDescriptor ... fieldDescriptors) {
        FieldDescriptor[] responseFields = defaultResponseFields();
        if (fieldDescriptors.length > 0) {
            FieldDescriptor[] newResponseFields =
                    Arrays.copyOf(responseFields, (responseFields.length + fieldDescriptors.length + 6));

            int idx = responseFields.length;
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME).type(JsonFieldType.OBJECT).description("결과 데이터");
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + ".content[]").type(JsonFieldType.ARRAY).description("데이터 목록");
            for (FieldDescriptor descriptor : fieldDescriptors) {
                newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + ".content[]." + descriptor.getPath()).type(descriptor.getType()).description(descriptor.getDescription());
            }
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + ".count").type(JsonFieldType.NUMBER).description("총 데이터 수");
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + ".size").type(JsonFieldType.NUMBER).description("한 페이지에 보일 데이터 수");
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + ".currentPage").type(JsonFieldType.NUMBER).description("현재 페이지");
            newResponseFields[idx++] = fieldWithPath(RESULT_FILED_NAME + ".totalPage").type(JsonFieldType.NUMBER).description("총 페이지");
            responseFields = newResponseFields;
        }
        return responseFields;
    }

}
