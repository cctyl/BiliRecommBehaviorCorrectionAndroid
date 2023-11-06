package io.github.bilirecommand.ui.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.github.bilirecommand.Application;
import io.github.bilirecommand.R;
import io.github.bilirecommand.entity.VideoVo;
import io.github.bilirecommand.entity.enumeration.HandleType;
import io.github.bilirecommand.ui.adapter.CommonRecyclerViewAdapter;

public class ReadyTaskInfoHolder extends CommonRecyclerViewAdapter.InnerViewHolder<VideoVo> {

    private View itemRoot;
    private ImageView iv_video_cover;
    private TextView tv_up_name;
    private TextView tv_video_title;
    private TextView tv_video_desc;
    private TextView tv_reason;
    private Button bt_ignore;
    private Button bt_agree;
    private Button bt_disagree;

    public ReadyTaskInfoHolder(@NonNull View itemView, CommonRecyclerViewAdapter adapter) {
        super(itemView, adapter);
    }


    @Override
    protected void saveViewIntoHolder(View itemView) {
        this.itemRoot = itemView;
        iv_video_cover = itemView.findViewById(R.id.iv_video_cover);
        tv_up_name = itemView.findViewById(R.id.tv_up_name);
        tv_video_title = itemView.findViewById(R.id.tv_video_title);
        tv_video_desc = itemView.findViewById(R.id.tv_video_desc);
        tv_reason = itemView.findViewById(R.id.tv_reason);
        bt_ignore = itemView.findViewById(R.id.bt_ignore);
        bt_agree = itemView.findViewById(R.id.bt_agree);
        bt_disagree = itemView.findViewById(R.id.bt_disagree);
    }

    @Override
    protected void setViewData(VideoVo videoVo, List<VideoVo> data,int position) {
        HandleType reversal =null;
        AtomicReference<HandleType> finalHandleType =null;
        if (HandleType.DISLIKE.equals(videoVo.handleType)){
            itemRoot.setBackgroundResource(R.color.negative);
            tv_reason.setText("原因："+videoVo.blackReason);
            reversal = HandleType.THUMB_UP;
        }else {
            itemRoot.setBackgroundResource(R.color.positive);
            tv_reason.setText("原因："+videoVo.thumbUpReason);
            reversal = HandleType.DISLIKE;
        }

        //1.封面
        Glide.with(Application.getInstance())
                .load(videoVo.coverUrl)
                .into(iv_video_cover);

        tv_up_name.setText("UP主："+videoVo.upName);
        tv_video_title.setText("标题："+videoVo.title);
        tv_video_desc.setText("描述："+videoVo.desc);
        //忽略此视频
        bt_ignore.setOnClickListener(v -> {
           videoVo.handleType = HandleType.OTHER;
           getAdapter().notifyItemChanged(position);
        });

        //反转
        HandleType finalReversal = reversal;
        bt_disagree.setOnClickListener(v -> {
            videoVo.handleType = finalReversal;
            getAdapter().notifyItemChanged(position);
        });
    }
}
