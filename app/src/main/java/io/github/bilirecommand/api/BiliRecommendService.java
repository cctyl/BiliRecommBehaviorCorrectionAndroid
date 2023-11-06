package io.github.bilirecommand.api;

import java.util.List;
import java.util.Map;

import io.github.bilirecommand.entity.Result;
import io.github.bilirecommand.entity.VideoVo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BiliRecommendService {

    @GET("/bili-task/ready2handle")
    Call<Result<Map<String, List<VideoVo>>>> getReadyToHandlerTask();


}
