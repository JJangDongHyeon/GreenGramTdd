package com.green.greengram.feedFavorite;

import com.green.greengram.feed.FeedMapper;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedFavoriteService {
    private final FeedFavoriteMapper mapper;

    public int toggleFavorite(FeedFavoriteToggleReq p){
        int delresult = mapper.delFeedFavorite(p);

        if(delresult == 0) {
           int intResult = mapper.insFeedFavorite(p);
            return intResult;
        }
        return 0;

    }
        /* 방법론(1)
            select로 레코드 있는지 확인
            레코드가 있으면 delete
            레코드가 없으면 insert

            방법론(2)
            일단 insert >> 에러가 터지면 delete
            try catch

            방법론(3)
            일단 delete >> 1이 넘어옴(삭제가 됐다는 뜻) > return 0
            delete >> 0이 넘어옴 >> insert return 1;
         */

}
