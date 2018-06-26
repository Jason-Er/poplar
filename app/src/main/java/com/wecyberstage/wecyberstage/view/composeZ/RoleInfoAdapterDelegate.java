package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;

import java.util.List;

class RoleInfoAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    public RoleInfoAdapterDelegate(int viewType) {
        super(viewType);
    }

    class RoleViewHolder extends RecyclerView.ViewHolder {
        public RoleViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof KeyFrame.RoleInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_role, parent, false);
        return new RoleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        KeyFrame.RoleInfo role = (KeyFrame.RoleInfo) items.get(position);
        ((RoleCardView) holder.itemView).roleInfo = role;
    }
}
