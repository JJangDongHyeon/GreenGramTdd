package com.green.greengram.feedFavorite;

import com.green.greengram.feedFavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavoriteMapperTest {
    @Autowired
    private FeedFavoriteMapper mapper;
    final int RECORD_COUNT = 8;
    @Test
    void insFeedFavorite() {
        //셀렉트 작동 확인
        FeedFavoriteToggleReq p1 = new FeedFavoriteToggleReq();
        p1.setFeedId(0);
        p1.setUserId(0);
        List<FeedFavoriteEntity> list1 = mapper.selFeedFavoriteForTest(p1);
        assertEquals(RECORD_COUNT, list1.size() , "셀렉트가 제대로 작동 안됨!");

        //인서트 확인
        FeedFavoriteToggleReq p2 = new FeedFavoriteToggleReq();
        p2.setFeedId(1);
        p2.setUserId(2);
        int affectedRow1 = mapper.insFeedFavorite(p2);
        List<FeedFavoriteEntity> list2 = mapper.selFeedFavoriteForTest(p2);//1 , 2로 들어갔는지 검색
        List<FeedFavoriteEntity> list3 = mapper.selFeedFavoriteForTest(p1);//총 레코드 수 불러옴
        assertEquals(1,affectedRow1,"1. 인서트 제대로 안들어감!");//인서트 작동 확인
        assertEquals(1,list2.size() , "p2 값이 제대로 안들어감!");// 맞는 값이 들어갔는지 확인
        assertEquals(RECORD_COUNT+1 , list3.size() , "실제 레코가 안늘어남!");//총 레코드 수 늘었는지 확인


    }

    @Test
    void delFeedFavorite() {
        //총 레코드 수 불러오기
        FeedFavoriteToggleReq p1 = new FeedFavoriteToggleReq();
        p1.setFeedId(0);
        p1.setUserId(0);
        List<FeedFavoriteEntity> list1 = mapper.selFeedFavoriteForTest(p1);
        assertEquals(RECORD_COUNT, list1.size() , "셀렉트가 제대로 작동 안됨!");

        //삭제 검증
        FeedFavoriteToggleReq p2 = new FeedFavoriteToggleReq();
        p2.setFeedId(1);
        p2.setUserId(1);
        int affectedRow1 = mapper.delFeedFavorite(p2);
        assertEquals(1,affectedRow1,"삭제가 실행 안됨!");//딜리트 메소드가 실행되었는지 확인
        List<FeedFavoriteEntity> list2 = mapper.selFeedFavoriteForTest(p1);
        assertEquals(RECORD_COUNT - 1 , list2.size(), "레코드 수가 안 줄어들음!!");//레코드 숫자 변동 확인

        List<FeedFavoriteEntity> list3 = mapper.selFeedFavoriteForTest(p2); //p2 값으로 셀렉트
        assertEquals(0,list3.size(),"p2가 삭제 되지 않음!!!!");//실제 p2가 삭제 되었는지 확인

        //없는 값 지우기
        FeedFavoriteToggleReq p3 = new FeedFavoriteToggleReq();
        p3.setFeedId(10);
        p3.setUserId(10);
        int affectedRow2 = mapper.delFeedFavorite(p3);
        List<FeedFavoriteEntity> list4 = mapper.selFeedFavoriteForTest(p1);
        assertEquals(0,affectedRow2,"삭제가 되어버림");
    }

    @Test
    void selFeedFavoriteForTest() {
    }
}