package com.green.greengram.user;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 컨트롤러", description = "sign-up , sign-in")
public class UserControllrImpl {
    private final UserServiceImpl service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입" , description = "프로필 사진은 필수가 아님")
    public ResultDto<Integer> postUser(@RequestPart(required = false) MultipartFile pic,
                                       @RequestPart SignUpPostReq p){
        log.info("Controllpic:{}" , pic);
        int result =  service.postUser(pic, p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("회원가입 완료")
                .resultData(result).build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "회원 인증" )
    public ResultDto<SignInRes> postSignIn(@RequestBody SignInPostReq p){
        SignInRes result = service.postSignIn(p);
        log.info("pic : {}",p);
        return ResultDto.<SignInRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("로그인 완료")
                .resultData(result).build();
    }

    @GetMapping
    public ResultDto<UserInfoGetRes> getProfileUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p){
        UserInfoGetRes userInfo = service.getProfileUserInfo(p);
        log.info("p:{}" + p);
        return ResultDto.<UserInfoGetRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(userInfo).build();
    }

//    @GetMapping
//    public ResultDto<UserInfoGetRes> getProfileUserInfoParam(@RequestParam long signedUserId , @RequestParam long profileUserId){
//        UserInfoGetRes userInfo = service.getProfileUserInfo(p);
//
//        return ResultDto.<UserInfoGetRes>builder()
//                .statusCode(HttpStatus.OK)
//                .resultMsg(HttpStatus.OK.toString())
//                .resultData(userInfo).build();
//    }

    @PatchMapping(value = "pic")
    public ResultDto<String> patchProfilePic(@ParameterObject @ModelAttribute UserProfilePatchReq p) {
        String result = service.patchProfilePic(p);

        //UserProfilePatchReq p의 객체 주소값을 주는 것임
        return ResultDto.<String>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }

}
