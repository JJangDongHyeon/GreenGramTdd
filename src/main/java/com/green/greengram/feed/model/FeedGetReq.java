package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.common.GlobalConst;
import com.green.greengram.common.model.Paging;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
public class FeedGetReq extends Paging {
//    @Parameter(name = "signed_user_id") 이거하면 제이슨에서 loginedUserId말고 이렇게 뜸
    private long loginedUserId;

    public FeedGetReq(Integer page, Integer size, @BindParam("signed_user_id") long loginedUserId) {
        super(page, size == null ? GlobalConst.FEED_PAGING_SIZE : size);
        this.loginedUserId = loginedUserId;
    }
}
