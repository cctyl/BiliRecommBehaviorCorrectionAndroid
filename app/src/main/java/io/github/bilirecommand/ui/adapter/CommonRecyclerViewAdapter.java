package io.github.bilirecommand.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @param <H> ViewHolder 类，需要自己创建一个
 * @param <D> 数据类型
 */
public class CommonRecyclerViewAdapter<H extends CommonRecyclerViewAdapter.InnerViewHolder<D>, D> extends RecyclerView.Adapter<H> {
    private List<D> data;
    private int resourceId;
    private Class<H> viewHolderClass;
    private Context context;
    public CommonRecyclerViewAdapter(List<D> data, int resourceId, Class<H> viewHolderClass, Context context) {
        this.data = data;
        this.resourceId = resourceId;
        this.viewHolderClass = viewHolderClass;
        this.context = context;
    }

    public List<D> getData() {
        return data;
    }

    public Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(resourceId, parent, false);
        try {
            Constructor<H> constructor = viewHolderClass.getConstructor(View.class,CommonRecyclerViewAdapter.class);
            H h = constructor.newInstance(view,this);
            return h;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("viewHolder create error");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        holder.setViewData(data.get(position), data,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static abstract class InnerViewHolder<D> extends RecyclerView.ViewHolder {
        protected CommonRecyclerViewAdapter adapter;
        public InnerViewHolder(@NonNull View itemView,CommonRecyclerViewAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            saveViewIntoHolder(itemView);
        }



        /**
         * 将itemView内部的控件保存到Holder中
         *
         * @param itemView
         */
        protected abstract void saveViewIntoHolder(View itemView);
        /**
         * 应当在该方法中，给Holder内的控件设置数据
         *
         * @param d
         * @param data
         */
        protected abstract void setViewData(D d, List<D> data,int position);
    }
}
