package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class UserInfoGetReq {
    @Schema(name = "signed_user_id", defaultValue = "15"  , description = "로그인한 사용자")
    private long signedUserId;
    @Schema(name = "profile_user_id", defaultValue = "15"  , description = "프로필 사용자") //swager에 표시해주는 필드명만 바꿔줌 실직적으로는 constructorProperty
    private long profileUserId;


    //써주기~
//하나 바꿀땐 바인드 파람 여러개 바꿀땐 컨스트럭터프로퍼티(생성자)
    @ConstructorProperties({"signed_user_id", "profile_user_id"})
    public UserInfoGetReq(long signedUserId , long profileUserId){
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}
