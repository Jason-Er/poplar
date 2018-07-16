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
import com.wecyberstage.wecyberstage.view.helper.ComposeScriptHelper;
import com.wecyberstage.wecyberstage.view.helper.RecyclerViewEvent;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MaskLineAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    final private OnStartDragListener startDragListener;
    final private ComposeScriptHelper composeScriptHelper;

    public MaskLineAdapterDelegate(int viewType, OnStartDragListener startDragListener, ComposeScriptHelper composeScriptHelper) {
        super(viewType);
        this.startDragListener = startDragListener;
        this.composeScriptHelper = composeScriptHelper;
    }

    class ComposeLineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.composeXCardLine_dragHandle)
        ImageView dragHandle;
        @BindView(R.id.composeXCardLine_lineAvatar)
        ImageView mask;
        @BindView(R.id.composeXCardLine_lineDialogue)
        TextView dialogue;

        ComposeLineViewHolder(View v) {
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
                .inflate(R.layout.composex_maskline, parent, false);
        return new ComposeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final List<Object> items, final int position, @NonNull final RecyclerView.ViewHolder holder) {
        Log.i("ComposeX", "onBindViewHolder");
        ((ComposeLineViewHolder) holder).dialogue.setText(((ComposeLine) items.get(position)).line.dialogue);
        ((MaskLineCardView)holder.itemView).setPosition(position);
        ((ComposeLineViewHolder) holder).dragHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    startDragListener.onStartDrag(holder);
                }
                return false;
            }
        });

        ((ComposeLineViewHolder) holder).mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MaskLineAdapterDelegate","send click");
                RecyclerViewEvent event = new RecyclerViewEvent("MASK_CLICK");
                EventBus.getDefault().post(event);
            }
        });

    }

}
