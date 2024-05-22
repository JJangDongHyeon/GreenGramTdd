package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.common.GlobalConst;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> file , FeedPostReq p){
        mapper.postFeed(p);
        if(file == null) System.out.println("비어있음");
        if(file == null){
            return FeedPostRes.builder()
                    .feedId(p.getFeedId())
                    .build();
        }
        FeedPicPostDto dto = FeedPicPostDto.builder()
                            .feedId(p.getFeedId())
                            .build();

        System.out.println("asdasdasd");

        try{
            String path = String.format("feed/%d",p.getFeedId());
            customFileUtils.makeFolders(path);
            for(MultipartFile pic : file){
                String saveFileName = customFileUtils.makeRandomFileName(pic);
                dto.getFileNames().add(saveFileName);
                String target = String.format("%s/%s",path,saveFileName);
                customFileUtils.transferTo(pic,target);

            }
                mapper.postFeedPics(dto) ;
        } catch (Exception e){
                e.printStackTrace();
            throw new RuntimeException("Feed 등록 오류");
        }
        System.out.println("성공");
        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(dto.getFileNames())
                .build();
    }


    public List<FeedGetRes> getFeed(FeedGetReq p){
        List<FeedGetRes> result = mapper.getFeed(p);
        System.out.println(result);

        for(FeedGetRes pic : result){
            //피드 하나당 포함된 사진 리스트
            List<String> pics = mapper.getFeedPicsByFeedId(pic.getFeedId());
            pic.setPics(pics);
            //
            List<FeedCommentGetRes> commentList = mapper.getFeedCommentTopBy4FeedId(pic.getFeedId());

            if(commentList.size() == GlobalConst.COMMENT_SIZE_PER_FEED){
                pic.setIsMoreComment(1);
                commentList.remove(commentList.size()-1);
            }
            pic.setComments(commentList);
        }


        return result;



    }
}
