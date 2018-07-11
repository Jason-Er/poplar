package com.wecyberstage.wecyberstage.view.composeY;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ComposeLineAdapterEndDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    public ComposeLineAdapterEndDelegate(int viewType) {
        super(viewType);
    }

    class ComposeLineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageview_pic)
        ImageView avatar;
        @BindView(R.id.textview_message)
        TextView dialogue;

        public ComposeLineViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return ((ComposeYItemDto)items.get(position)).getViewType() == ComposeYCardViewType.END;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.i("ComposeX", "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.composey_maskline_right, parent, false);
        return new ComposeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Log.i("ComposeX", "onBindViewHolder");
        ((ComposeLineViewHolder) holder).dialogue.setText(((ComposeYItemDto) items.get(position)).getComposeLine().line.dialogue);
    }
}
