package io.github.bilirecommand.ui;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.bilirecommand.R;
import io.github.bilirecommand.api.BiliRecommendService;
import io.github.bilirecommand.api.RetrofitServiceCreator;
import io.github.bilirecommand.api.SimpleCallback;
import io.github.bilirecommand.entity.Result;
import io.github.bilirecommand.entity.VideoVo;
import io.github.bilirecommand.entity.enumeration.HandleType;
import io.github.bilirecommand.ui.adapter.CommonRecyclerViewAdapter;
import io.github.bilirecommand.ui.function.ReadyTaskInfoFunction;
import io.github.bilirecommand.ui.holder.ReadyTaskInfoHolder;
import io.github.bilirecommand.util.JsonUtil;
import io.github.bilirecommand.util.ToastUtil;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ReadyTaskInfoFunction {

    private static final String TAG = "MainActivity";
    private BiliRecommendService biliRecommendService;
    private RecyclerView rv_ready_handle_task;
    private List<VideoVo> videoVoList = new ArrayList<>();
    private CommonRecyclerViewAdapter<ReadyTaskInfoHolder, VideoVo> adapter;
    private int page = 1;
    private int size = 15;
    private long lastRefreshTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_handle_task);
        biliRecommendService = RetrofitServiceCreator.create(BiliRecommendService.class);


        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("bilibili://video/1202549686?page=0");
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // 如果没有匹配的应用可以处理此deeplink，则可以显示错误提示或其他操作
        }

        rv_ready_handle_task = findViewById(R.id.rv_ready_handle_task);
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {

        adapter = new CommonRecyclerViewAdapter(
                videoVoList,
                R.layout.item_ready_task_info,
                ReadyTaskInfoHolder.class,
                this
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_ready_handle_task.setLayoutManager(linearLayoutManager);
        rv_ready_handle_task.setAdapter(adapter);
        rv_ready_handle_task.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                boolean isBottom = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0;
                Log.d(TAG, "firstVisibleItemPosition:"+firstVisibleItemPosition+",总数："+totalItemCount+",当前位置: "+(visibleItemCount + firstVisibleItemPosition)+",结果："+isBottom);
                // 判断是否滚动到底部
                if (isBottom && (System.currentTimeMillis() - lastRefreshTime >2000)) {
                    lastRefreshTime = System.currentTimeMillis();
                    // 加载下一页的逻辑
                    page++;
                    initData();
                }
            }
        });
    }

    private void initData() {
        biliRecommendService.getReadyToHandlerTask(HandleType.THUMB_UP, page, size)
                .enqueue(new SimpleCallback<Result<List<VideoVo>>>() {
                    @Override
                    public void resp(Result<List<VideoVo>> body, Call<Result<List<VideoVo>>> call, Response<Result<List<VideoVo>>> response) {
                        ToastUtil.show("成功");

                        List<VideoVo> data = body.getData();
                        videoVoList.addAll(data);
                        //更新数据
                        adapter.notifyItemRangeInserted(videoVoList.size(), videoVoList.size());
                    }
                });
    }

    @Override
    public void processSingleVideo(String id, HandleType handleType, SimpleCallback callback) {
        biliRecommendService.secondProcessSingleVideo(id, handleType, null).enqueue(callback);
    }


    @Override
    public void afterRead(String id, SimpleCallback callback) {
        biliRecommendService.afterRead(id).enqueue(callback);
    }
}