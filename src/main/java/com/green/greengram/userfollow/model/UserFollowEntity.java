package com.green.greengram.userfollow.model;

import lombok.*;

@Getter // 마찬가지
@Setter // 새로운 메소드 만드는것
@EqualsAndHashCode//new new 해도 주소값이 달라도 안에 있는게 같으면 //오버라이딩임
@AllArgsConstructor
@ToString
public class UserFollowEntity {
    private long fromUserId;
    private long toUserId;
    private String createdAt;
}
