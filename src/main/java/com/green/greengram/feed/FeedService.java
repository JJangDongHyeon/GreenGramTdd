package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.feed.model.*;
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
        if(file == null){
            return FeedPostRes.builder()
                    .feedId(p.getFeedId())
                    .build();
        }
        FeedPicPostDto dto = FeedPicPostDto.builder()
                            .feedId(p.getFeedId()).build();

        try{
            String path = String.format("feed/%d",p.getFeedId());
            customFileUtils.makeFolders(path);
            for(MultipartFile pic : file){
                String saveFileName = customFileUtils.makeRandomFileName(pic);
                dto.getFileNames().add(saveFileName);
                String target = String.format("%s/%s",path,saveFileName);
                customFileUtils.transferTo(pic,target);
            }
        } catch (Exception e){
                e.printStackTrace();
            throw new RuntimeException("Feed 등록 오류");
        }
        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(dto.getFileNames())
                .build();
    }


    public List<FeedGetRes> getFeed(FeedGetReq p){
        List<FeedGetRes> result = mapper.getFeed(p);

        for(FeedGetRes pic : result){
            List<String> pics = mapper.getFeedPicsByFeedId(pic.getFeedId());
            pic.setPics(pics);
        }
        return result;



    }
}
