package com.wecyberstage.wecyberstage.view.browse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.StagePlayInfo;

import java.util.List;

/**
 * Created by mike on 2018/3/7.
 */

public class BrowseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StagePlayInfo> stagePlayInfo;

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_stageplayposter, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StagePlayPoster) holder.itemView).setStagePlayInfo(stagePlayInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return stagePlayInfo == null? 0 : stagePlayInfo.size();
    }

    public void setStagePlayInfo(List<StagePlayInfo> stagePlayInfo) {
        this.stagePlayInfo = stagePlayInfo;
        notifyDataSetChanged();
    }
}
