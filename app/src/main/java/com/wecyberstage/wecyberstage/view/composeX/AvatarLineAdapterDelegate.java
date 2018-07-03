package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.PopupChooseAvatar;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AvatarLineAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    final private OnStartDragListener startDragListener;
    // below just for popupWindow
    private PopupWindow popupWindow;
    private ImageButton closeButton;

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
        return items.get(position) instanceof ComposeScript.AvatarLine;
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
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull final RecyclerView.ViewHolder holder) {
        Log.i("ComposeX", "onBindViewHolder");
        ((AvatarLineViewHolder) holder).dialogue.setText(((ComposeScript.AvatarLine) items.get(position)).getLine().dialogue);
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
                PopupChooseAvatar chooseAvatar = new PopupChooseAvatar(v);
                chooseAvatar.show();
                return false;
            }
        });
    }

}
