package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor//json으로 바뀔때 기본생성자가 있어야함
@AllArgsConstructor//빌더도 쓸수있게

public class SignInRes {
    @Schema(example = "1" , description = "유저PK")
    private long userId;
    @Schema(example = "홍길동" ,description = "유저이름")
    private String nm;
    @Schema(example = "ec520517-0f85-44f0-ab44-b3da3b822c23.webp" ,description = "유저 프로필 이미지")
    private String pic;
}
