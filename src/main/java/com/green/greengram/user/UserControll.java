package com.green.greengram.user;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.SignInPostReq;
import com.green.greengram.user.model.SignInRes;
import com.green.greengram.user.model.SignUpPostReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 컨트롤러", description = "sign-up , sign-in")
public class UserControll {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입" , description = "프로필 사진은 필수가 아님")
    public ResultDto<Integer> postUser(@RequestPart(required = false) MultipartFile file,
                                       @RequestPart SignUpPostReq p){
       int result =  service.postUser(file, p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("회원가입 완료")
                .resultData(result).build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "회원 인증" )
    public ResultDto<SignInRes> postSignIn(@RequestBody SignInPostReq p){
        SignInRes result = service.postSignIn(p);

        return ResultDto.<SignInRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("로그인 완료")
                .resultData(result).build();
    }
}
