package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowEntity;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//H2경량데이터를 이용하지 않고
// 원래쓰던 데이터베이스 사용한다는 의미
//@Rollback(value = false)
class UserFollowMapperTest {
//여기선 생성자를 통해 di를 받는게 안돼서 RequiredArgu 안되고 Autowired사용
    @Autowired
    private UserFollowMapper mapper;

    final int RECORD_COUNT = 4;

    @Test
    @DisplayName("유저 팔로우 insert 테스트")
    void insUserFollow() {
        UserFollowReq p1 = new UserFollowReq(0,0);
        List<UserFollowEntity> list1 = mapper.selUserFollowForTest(p1);

        UserFollowReq p2 = new UserFollowReq(1,4);
        int affectedRows = mapper.insUserFollow(p2);
        System.out.println(list1.size());
        Assertions.assertEquals(1,affectedRows);
        //스태틱 임포트해서 assertEquals(1,affectedRows)해도 사용됨

        List<UserFollowEntity> list2 = mapper.selUserFollowForTest(p1);
        assertEquals(1,list2.size()-list1.size(),"실제 insert되지 않음!");

        List<UserFollowEntity> list3 = mapper.selUserFollowForTest(p2);
        assertEquals(1,list3.size(),"p2값이 제대로 insert되지 않음!");

        assertEquals(1, list3.get(0).getFromUserId());
        assertEquals(4, list3.get(0).getToUserId());

        UserFollowReq p3 = new UserFollowReq(2,4);
        int affectedRows2 = mapper.insUserFollow(p3);
        assertEquals(1,affectedRows2);
        List<UserFollowEntity> list4 = mapper.selUserFollowForTest(p1);
        assertEquals(1,list4.size()-list2.size(),"2. 실제 insert되지 않음!");

        List<UserFollowEntity> list5 = mapper.selUserFollowForTest(p3);
        assertEquals(1,list5.size(), "p3값이 제대로 insert 되지 않음");
    }

    @Test
    void delUserFollow() {
        UserFollowReq p1 = new UserFollowReq(0,0);
        List<UserFollowEntity> list1 = mapper.selUserFollowForTest(p1);//list1.size() 4


        UserFollowReq p2 = new UserFollowReq(1,1);
        int affectedRow2 = mapper.delUserFollow(p2); //레코드 수 3
        assertEquals(1,affectedRow2,"삭제가 안됨!!");
        List<UserFollowEntity> list2 = mapper.selUserFollowForTest(p1); //list2.size()3
        assertEquals(1,list1.size()-list2.size() , "삭제 되지 않음!!");
        assertEquals(2,list2.get(0).getFromUserId());
        assertEquals(2,list2.get(0).getToUserId());

        UserFollowReq p3 = new UserFollowReq(2,2);
        mapper.delUserFollow(p3); // 레코드 수 2
        List<UserFollowEntity> list3 = mapper.selUserFollowForTest(p1); //list3.size() 2
        assertEquals(2,list3.size());//사이즈2인지 확인

        List<UserFollowEntity> list3p3 = mapper.selUserFollowForTest(p3); //p3를 셀렉했을때 뜨는지 확인
        assertEquals(0,list3p3.size());
        assertEquals(2,list1.size()-list3.size() , "삭제 되지 않음!!");
        assertEquals(3,list3.get(0).getFromUserId());
        assertEquals(3,list3.get(0).getToUserId());

        //없는 pk 삭제
        UserFollowReq p4 = new UserFollowReq(5,5);
        int affectedRow = mapper.delUserFollow(p4);
        assertEquals(0,affectedRow,"삭제 할 거 없는데 삭제됨!");
    }

    @Test
    @DisplayName("유저 팔로우 select 테스트")
    void selUserFollowForTest(){
        UserFollowReq p1 = new UserFollowReq(0,0);
        List<UserFollowEntity> list1 = mapper.selUserFollowForTest(p1);
        //1.롤백을  안시키면 결과에 영향ㅇ을 미칠 숟 ㅗ있어서 뺌
        assertEquals(4, list1.size(),"1. 레코드 수가 다르다");

        UserFollowEntity record0 = list1.get(0);
        assertEquals(1,record0.getFromUserId(), "0번레코드 from_user_id 다름");
        assertEquals(1,record0.getToUserId(), "0번레코드 to_user_id 다름");
        assertEquals(new UserFollowEntity(2,2,"2024-05-20 16:54:35"),
                        list1.get(1),"1번레코드 값이 다름" );

        // 2.from_user_id = 1
        UserFollowReq p2 = new UserFollowReq(1,0);
        List<UserFollowEntity> list2 = mapper.selUserFollowForTest(p2);
        assertEquals(1, list2.size(),"2.레코드 수가 다르다");
        assertEquals(new UserFollowEntity(1,1,"2024-05-21 11:03:50"),
                list1.get(0),"2. 0번레코드 값이 다름" );


        //3. fromUserId = 300
        UserFollowReq p3 = new UserFollowReq(300, 0);
        List<UserFollowEntity> list3 = mapper.selUserFollowForTest(p3);
        assertEquals(0,list3.size(),"3. 레코드가 넘어오면 안됨");


        //4. toUserId = 1
        UserFollowReq p4 = new UserFollowReq(0, 1);
        List<UserFollowEntity> list4 = mapper.selUserFollowForTest(p4);
        assertEquals(1, list4.size(), "4. 레코드 수가 다르다");
        assertEquals(new UserFollowEntity(1, 1, "2024-05-21 11:03:50")
                , list4.get(0), "4. 0번 레코드 값이 다름");


    }
}