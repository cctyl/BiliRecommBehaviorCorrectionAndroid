package io.github.bilirecommand.ui;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import io.github.bilirecommand.ui.adapter.CommonRecyclerViewAdapter;
import io.github.bilirecommand.ui.holder.ReadyTaskInfoHolder;
import io.github.bilirecommand.util.JsonUtil;
import io.github.bilirecommand.util.ToastUtil;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BiliRecommendService biliRecommendService;
    private RecyclerView rv_ready_handle_task;
    private List<VideoVo> videoVoList = new ArrayList<>();
    private CommonRecyclerViewAdapter<ReadyTaskInfoHolder, VideoVo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_handle_task);
        biliRecommendService = RetrofitServiceCreator.create(BiliRecommendService.class);

        rv_ready_handle_task = findViewById(R.id.rv_ready_handle_task);
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {

        adapter = new CommonRecyclerViewAdapter(
                videoVoList,
                R.layout.item_ready_task_info,
                ReadyTaskInfoHolder.class
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_ready_handle_task.setLayoutManager(linearLayoutManager);
        rv_ready_handle_task.setAdapter(adapter);
    }

    private void initData() {

        biliRecommendService.getReadyToHandlerTask()
                .enqueue(new SimpleCallback<Result<Map<String, List<VideoVo>>>>() {
                    @Override
                    public void resp(Result<Map<String, List<VideoVo>>> body, Call<Result<Map<String, List<VideoVo>>>> call, Response<Result<Map<String, List<VideoVo>>>> response) {
                        ToastUtil.show("成功");

                        Map<String, List<VideoVo>> data = body.getData();
                        Collection<VideoVo> collect = data.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
                        videoVoList.addAll(collect);
                        //更新数据
                        adapter.notifyItemRangeInserted(0, videoVoList.size());
                    }
                });


    }
}