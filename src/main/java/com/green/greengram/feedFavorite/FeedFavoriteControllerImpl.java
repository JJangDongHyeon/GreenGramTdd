package com.green.greengram.feedFavorite;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("api/feed/favorite")
@RequiredArgsConstructor
public class FeedFavoriteControllerImpl {
    private final FeedFavoriteServiceImpl service;
    //전송 데이터 적음, 데이터 노출되면 안되는 것(post나 put 으로ㅓ 해야함)
    //feed의 pk값은 노출되어도 상관없음
    @GetMapping
    @Operation(summary = "좋아요" , description = "Toggle 처리")
    public ResultDto<Integer> toggleFavorite(@ModelAttribute FeedFavoriteToggleReq p){
        int result = service.toggleFavorite(p);
        //result == 0 >> 좋아요 취소 (좋아요 >비좋아요) : 좋아요 취소
        //result == 1 >> 좋아요 상태 (비좋아요 > 좋아요) : 좋아요 처리
        System.out.println("p:" + result);
        String favorite = result == 0 ?  "좋아요가 취소 되었습니다." : "좋아요!!";
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(favorite)
                .resultData(result)
                .build();

    }
}
