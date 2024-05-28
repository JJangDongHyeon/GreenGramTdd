package com.green.greengram.userfollow;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/follow")
public class UserFollowControllerImpl implements UserFollowController{
    private final UserFollowServiceImpl service;
    @Override
    @PostMapping
    public ResultDto<Integer> postUserFollow(@RequestBody UserFollowReq p){
        int result = service.postUserFollow(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("팔로우 완료")
                .resultData(result).build();
    }
    @Override
    @DeleteMapping
    public ResultDto<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p){
        int result = service.deleteUserFollow(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("팔로우 완료")
                .resultData(result).build();
    }
}
