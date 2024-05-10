package com.green.greengram.feedFavorite.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedFavoriteToggleReq {
    private long feedId;
    private long userId;
    //이거 두개 있으면 insert와 delete 둘다 쓸수 있음
}
