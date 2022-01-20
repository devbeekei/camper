package com.ss.camper.blog.ui.payload;

import com.ss.camper.blog.application.dto.BlogDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ModifyBlogPayload {

    @ToString
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String title;
        private String introduction;

        public BlogDTO convertBlogDTO() {
            return BlogDTO.builder()
                    .title(title)
                    .introduction(introduction)
                    .build();
        }
    }

}
