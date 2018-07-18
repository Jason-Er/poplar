package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.Role;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.helper.MaskChoose;
import com.wecyberstage.wecyberstage.view.helper.MaskImageButton;
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
        return items.get(position) instanceof Role;
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
        Role role = (Role) items.get(position);
        Mask mask = role.mask;
        ((MaskChoose)holder.itemView).init(mask);
    }

}
