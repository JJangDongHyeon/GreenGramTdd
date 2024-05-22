package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.common.GlobalConst;
import com.green.greengram.common.model.Paging;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
public class FeedGetReq extends Paging {
//    @Parameter(name = "signed_user_id") 이거하면 제이슨에서 loginedUserId말고 이렇게 뜸
    @Schema(name = "signed_user_id")//schema는 스웨거에서 테스트 안할거면 안해도됨
    private long signedUserId;

    @Schema(name = "profile_user_id", description = "프로필 사용자 ID (Not Required), 프로필 화면에서 사용")
    //스웨거 문서 상에서 이 이름으로 날리라고 schema로 이름을 변경해줌
    private Long profileUserId;//이게 없으면 모든 feed를 가져오기 이걸 쓰면 프로필 사용자의 피드만 가져오기
    //이건 필수가 아니라 null이 뜰 수도 있기 때문에 Long으로 입력
    //page 와 size는 파스칼 기법 ㄴㄴ 대문자가 없기때문에 bindParam을 안써도 됨
    public FeedGetReq(Integer page, Integer size, @BindParam("signed_user_id") long signedUserId,
                                                  @BindParam("profile_user_id") Long profileUserId) {
        super(page, size == null ? GlobalConst.FEED_PAGING_SIZE : size);
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}
