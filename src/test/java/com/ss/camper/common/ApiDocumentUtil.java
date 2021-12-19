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
        FieldDescriptor[] responseFields = new FieldDescriptor[] {
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
        };
        return responseFields;
    }

    public static FieldDescriptor[] defaultResponseFields(FieldDescriptor ... fieldDescriptors) {
        FieldDescriptor[] responseFields = defaultResponseFields();
        if (fieldDescriptors.length > 0) {
            FieldDescriptor[] newResponseFields = Arrays.copyOf(responseFields, (responseFields.length + 1 + fieldDescriptors.length));
            newResponseFields[responseFields.length] = fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과 데이터");
            int idx = responseFields.length + 1;
            for (FieldDescriptor descriptor : fieldDescriptors) {
                newResponseFields[idx] = descriptor;
                idx++;
            }
            responseFields = newResponseFields;
        }
        return responseFields;
    }

}
