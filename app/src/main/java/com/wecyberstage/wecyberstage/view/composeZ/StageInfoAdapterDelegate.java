package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class StageInfoAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    public StageInfoAdapterDelegate(int viewType) {
        super(viewType);
    }

    class StageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_stage_image)
        ImageView background;

        public StageViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object>  items, int position) {
        return items.get(position) instanceof KeyFrame.StageInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_stage, parent, false);
        return new StageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        KeyFrame.StageInfo stageInfo = (KeyFrame.StageInfo) items.get(position);
        StageViewHolder vh = (StageViewHolder) holder;
        Glide.with(holder.itemView.getContext()).load(stageInfo.settingURL).into(vh.background);
    }
}
