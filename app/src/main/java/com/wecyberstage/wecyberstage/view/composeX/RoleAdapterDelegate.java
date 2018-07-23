package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.view.helper.MaskChoose;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoleAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {
    public RoleAdapterDelegate(int viewType) {
        super(viewType);
    }

    class RoleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mask_choose_frame)
        ViewGroup masksFrame;

        public RoleViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof StageRole;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.role_mask_choose, parent, false);
        return new RoleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Log.i("RoleAdapterDelegate","onBindViewHolder position:"+position);
        StageRole stageRole = (StageRole) items.get(position);
        Mask mask = stageRole.mask;
        ((MaskChoose)holder.itemView).init(mask);
    }

}
