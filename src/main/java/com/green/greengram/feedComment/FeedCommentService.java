package com.green.greengram.feedComment;

import com.green.greengram.common.GlobalConst;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public long postFeedComment(FeedCommentPostReq p){
        int affectedRow = mapper.insFeedComment(p);
        return p.getFeedCommentId();

    }
    public int deleteFeedComment(FeedCommentDelReq p){
        return mapper.deleteFeedComment(p);
    }

    public List<FeedCommentGetRes> getFeedComment(long feedId){
        List<FeedCommentGetRes> list = mapper.getFeedComment(feedId);
        for(int i = 0 ; i <GlobalConst.COMMENT_SIZE_PER_FEED -1 ; i++){
            list.remove(0);
        }

            return mapper.getFeedComment(feedId);
    }
}
