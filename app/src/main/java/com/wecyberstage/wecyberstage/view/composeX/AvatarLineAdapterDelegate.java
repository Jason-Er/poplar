package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AvatarLineAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    public AvatarLineAdapterDelegate(int viewType) {
        super(viewType);
    }

    class AvatarLineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.line_avatar)
        ImageView avatar;
        @BindView(R.id.line_dialogue)
        TextView dialogue;

        public AvatarLineViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof ComposeScript.Avatar_Line;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.i("ComposeX", "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avatar_line_card_view, parent, false);
        return new AvatarLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Log.i("ComposeX", "onBindViewHolder");
        ((AvatarLineViewHolder) holder).dialogue.setText(((ComposeScript.Avatar_Line) items.get(position)).getLine().dialogue);
        ((AvatarLineCardView)holder.itemView).setPosition(position);
    }
}
