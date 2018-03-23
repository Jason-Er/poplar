package com.wecyberstage.wecyberstage.view.compose;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.KeyFrame;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mike on 2018/3/17.
 */

public class KeyFrameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private KeyFrame keyframe;
    private List<ItemViewInfo> dataset;

    class ItemViewInfo {
        private KeyFrameCardViewType cardViewType;
        private Object object;

        public ItemViewInfo(KeyFrameCardViewType cardViewType, Object object) {
            this.cardViewType = cardViewType;
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        int getType() {
            return cardViewType.ordinal();
        }
    }

    class RoleViewHolder extends RecyclerView.ViewHolder {
        RoleCardView view;
        public RoleViewHolder(View v) {
            super(v);
            view = (RoleCardView)v;
        }
    }

    class StageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_stage_image)
        ImageView background;
        StageCardView view;

        public StageViewHolder(View v) {
            super(v);
            view = (StageCardView)v;
            ButterKnife.bind(this, v);
        }
    }

    class PropViewHolder extends RecyclerView.ViewHolder {

        public PropViewHolder(View itemView) {
            super(itemView);
        }
    }

    class LineViewHolder extends RecyclerView.ViewHolder {

        public LineViewHolder(View v) {
            super(v);

        }
    }

    public KeyFrame getKeyframe() {
        return keyframe;
    }

    public void setKeyframe(@NonNull KeyFrame keyframe) {
        this.keyframe = keyframe;
        dataset = new ArrayList<>();
        dataset.add(new ItemViewInfo(KeyFrameCardViewType.STAGE, keyframe.settingURL));
        for (KeyFrame.RoleInfo roleInfo : keyframe.roleInfoList) {
            dataset.add(new ItemViewInfo(KeyFrameCardViewType.ROLE, roleInfo));
        }
        for (KeyFrame.PropInfo propInfo : keyframe.propInfoList) {
            dataset.add(new ItemViewInfo(KeyFrameCardViewType.PROP, propInfo));
        }
        for (KeyFrame.LineInfo lineInfo : keyframe.lineInfoList) {
            dataset.add(new ItemViewInfo(KeyFrameCardViewType.LINE, lineInfo));
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View v;
        switch (KeyFrameCardViewType.values()[viewType]) {
            case STAGE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_stage, parent, false);
                vh = new StageViewHolder(v);
                break;
            case ROLE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_role, parent, false);
                vh = new RoleViewHolder(v);
                break;
            case LINE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_line, parent, false);
                vh = new LineViewHolder(v);
                break;
            case PROP:
                break;
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StageViewHolder) {
            String imageURL = (String) dataset.get(position).getObject();
            View view = ((StageViewHolder) holder).view;
            Glide.with(view.getContext()).load(imageURL).into(((StageViewHolder) holder).background);
        } else if (holder instanceof RoleViewHolder) {
            KeyFrame.RoleInfo role = (KeyFrame.RoleInfo) dataset.get(position).getObject();
            ((RoleViewHolder) holder).view.roleInfo = role;
        } else if (holder instanceof LineViewHolder) {
            // TODO: 2018/3/18 show line position here
        } else if (holder instanceof PropViewHolder) {
            // TODO: 2018/3/22 show prop position here
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null? 0 : dataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).getType();
    }
}
