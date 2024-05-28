package com.green.greengram.feedFavorite;

import com.green.greengram.feedFavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedFavoriteMapper {
    int insFeedFavorite(FeedFavoriteToggleReq p);
    int delFeedFavorite(FeedFavoriteToggleReq p);
    List<FeedFavoriteEntity> selFeedFavoriteForTest(FeedFavoriteToggleReq p);
}
