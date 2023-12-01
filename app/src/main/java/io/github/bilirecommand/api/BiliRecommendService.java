package io.github.bilirecommand.api;

import java.util.List;
import java.util.Map;

import io.github.bilirecommand.entity.Result;
import io.github.bilirecommand.entity.VideoVo;
import io.github.bilirecommand.entity.enumeration.HandleType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BiliRecommendService {

    @GET("/bili-task/ready2handle")
    Call<Result<List<VideoVo>>> getReadyToHandlerTask(@Query("handleType") HandleType handleType);


}
