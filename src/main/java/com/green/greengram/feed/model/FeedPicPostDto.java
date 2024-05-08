package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class FeedPicPostDto {
    private long feedId; //10
    @Builder.Default // 이거해줘야 리스트에도 들어감 안하면 null값
    private List<String> fileNames = new ArrayList();
    //a.jpg , b.jpg , c.jpg
}
