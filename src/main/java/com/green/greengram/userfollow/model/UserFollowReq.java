package com.green.greengram.userfollow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class UserFollowReq {
@JsonProperty("from_user_id")//url에서 대문자 안뜨게 하기 위해 제이슨으로 쓸때 해줌 swagger에서
    private long fromUserId;
@JsonProperty("to_user_id")//url에서 대문자 안뜨게 하기 위해
    private long toUserId;

    @ConstructorProperties({"from_user_id", "to_user_id"})
    public UserFollowReq(long fromUserId, long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
