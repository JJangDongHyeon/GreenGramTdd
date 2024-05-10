package com.green.greengram.feed.model;

import com.green.greengram.feedComment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@ToString
@Getter
@Setter//json으로 바꿀때 builder패턴을 쓰고싶다 위에꺼랑 이거 추가
public class FeedGetRes {//Res는 무조건 json 응답은 무조건 json
    private long feedId;
    private long writerId;
    private String writerNm;
    private String writerPic;
    private String contents;
    private String location;
    private String createdAt;
    private int isFav;

    private List<String> pics;
    private List<FeedCommentGetRes> comments;
    private int isMoreComment;


}
