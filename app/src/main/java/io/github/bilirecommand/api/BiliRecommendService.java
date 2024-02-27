package io.github.bilirecommand.api;

import java.util.List;
import java.util.Map;

import io.github.bilirecommand.entity.Result;
import io.github.bilirecommand.entity.VideoVo;
import io.github.bilirecommand.entity.enumeration.HandleType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BiliRecommendService {

    @GET("/bili-task/ready2handle")
    Call<Result<List<VideoVo>>> getReadyToHandlerTask(@Query("handleType") HandleType handleType,
                                                      @Query("page") int page,
                                                      @Query("size") int size

                                                      );

    @PUT("/bili-task/process")
    Call<Result> secondProcessSingleVideo(@Query("id") String id,
                                          @Query("handleType") HandleType handleType,
                                          @Query("reason") String reason
                                          );


    @POST("/bili-task/after-read/{id}")
    Call<Result> afterRead(@Path("id") String id);
}
