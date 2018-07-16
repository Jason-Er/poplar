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
        if(mask.maskGraphList.size() <= 4) {
            ((GridLayout)((RoleViewHolder) holder).masksFrame).setColumnCount(2);
            ((GridLayout)((RoleViewHolder) holder).masksFrame).setRowCount(2);
        } else if(mask.maskGraphList.size() <= 9) {
            ((GridLayout)((RoleViewHolder) holder).masksFrame).setColumnCount(3);
            ((GridLayout)((RoleViewHolder) holder).masksFrame).setRowCount(3);
        } else if(mask.maskGraphList.size() <= 16) {
            ((GridLayout)((RoleViewHolder) holder).masksFrame).setColumnCount(4);
            ((GridLayout)((RoleViewHolder) holder).masksFrame).setRowCount(4);
        }

        // add mask to content view
        for(MaskGraph mg: mask.maskGraphList) {
            Log.i("PopupChooseMask","maskGraph ID: " + mg.id);
            int height = UICommon.dp2px((AppCompatActivity) ((RoleViewHolder) holder).masksFrame.getContext(), 48); // (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, view.getContext().getResources().getDisplayMetrics());
            int width =  UICommon.dp2px((AppCompatActivity) ((RoleViewHolder) holder).masksFrame.getContext(), 48); // (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, view.getContext().getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(height, width);
            // MaskImageButton imageButton = new MaskImageButton(((RoleViewHolder) holder).masksFrame.getContext(), this, this);
            ImageButton imageButton = new ImageButton(((RoleViewHolder) holder).masksFrame.getContext());
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // imageButton.setMaskGraph(mg);
            ((RoleViewHolder) holder).masksFrame.addView(imageButton, layoutParams);
            Glide.with(((RoleViewHolder) holder).masksFrame.getContext()).load(mg.graphURL).into(imageButton);
        }

    }

}
