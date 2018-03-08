package com.wecyberstage.wecyberstage.view.browse;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.PlayInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mike on 2018/3/7.
 */

public class PlayProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PlayInfo> dataset;

    public View.OnClickListener onClickCallBack = null;

    class PlayProfileCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.play_profile_poster)
        public ImageView poster;
        @BindView(R.id.play_profile_title)
        public TextView title;
        @BindView(R.id.play_profile_brief)
        public TextView brief;

        public CardView cardView;

        public PlayProfileCardViewHolder(View v) {
            super(v);
            cardView = (CardView) v;
            ButterKnife.bind(this, v);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_profile_card, parent, false);
        PlayProfileCardViewHolder vh = new PlayProfileCardViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlayProfileCardViewHolder vh = (PlayProfileCardViewHolder) holder;
        PlayInfo playInfo = dataset.get(position);
        ((PlayProfileCardView) vh.cardView).onClickCallBack = onClickCallBack;
        ((PlayProfileCardView) vh.cardView).playInfo = playInfo;
        vh.brief.setText(playInfo.briefIntro);
        vh.title.setText(playInfo.name);
    }

    @Override
    public int getItemCount() {
        return dataset == null? 0 : dataset.size();
    }

    public void setDataset(List<PlayInfo> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
}
