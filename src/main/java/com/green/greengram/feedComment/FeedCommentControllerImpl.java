package com.green.greengram.feedComment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feed/comment")
@RequiredArgsConstructor
public class FeedCommentControllerImpl {
    private final FeedCommentServiceImpl service;

    @PostMapping
    public ResultDto<Long> postFeedComment(@RequestBody FeedCommentPostReq p){
        long feedCommentId = service.postFeedComment(p);
        return ResultDto.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(feedCommentId)
                .build();
    }
    @GetMapping
    public ResultDto<List<FeedCommentGetRes>> getFeedComment(@RequestParam(name = "feed_id") long feedId ){
        List<FeedCommentGetRes> list = service.getFeedComment(feedId);

        return ResultDto.<List<FeedCommentGetRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(String.format("rows: %d",list.size()))
                .resultData(list)
                .build();
    }

    @DeleteMapping
    public ResultDto<Integer> deleteFeedComment(@ParameterObject @ModelAttribute FeedCommentDelReq p ){
        int result = service.deleteFeedComment(p);
        String resultMsg = result == 0 ? "삭제 실패" : "삭제 성공";

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(resultMsg)
                .resultData(result)
                .build();
    }
}
