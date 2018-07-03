package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.PopupChooseMask;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AvatarLineAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    final private OnStartDragListener startDragListener;

    public AvatarLineAdapterDelegate(int viewType, OnStartDragListener startDragListener) {
        super(viewType);
        this.startDragListener = startDragListener;
    }

    class AvatarLineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.composeXCardLine_dragHandle)
        ImageView dragHandle;
        @BindView(R.id.composeXCardLine_lineAvatar)
        ImageView avatar;
        @BindView(R.id.composeXCardLine_lineDialogue)
        TextView dialogue;

        AvatarLineViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof ComposeLine;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.i("ComposeX", "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.composex_avatarline, parent, false);
        return new AvatarLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final List<Object> items, final int position, @NonNull final RecyclerView.ViewHolder holder) {
        Log.i("ComposeX", "onBindViewHolder");
        ((AvatarLineViewHolder) holder).dialogue.setText(((ComposeLine) items.get(position)).line.dialogue);
        ((AvatarLineCardView)holder.itemView).setPosition(position);
        ((AvatarLineViewHolder) holder).dragHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    startDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        ((AvatarLineViewHolder) holder).avatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("AvatarLineAdapter","Long press");
                PopupChooseMask chooseMask = new PopupChooseMask(v, ((ComposeLine) items.get(position)).maskGraph, null);
                chooseMask.show();
                return false;
            }
        });
    }

}
