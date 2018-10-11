package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.graphics.Rect;
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
import com.wecyberstage.wecyberstage.view.message.MaskChooseEvent;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.view.common.StageLineView;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.message.MaskClickEvent;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class StageLineAdapterDelegate extends ViewTypeDelegateClass
        implements AdapterDelegateInterface<List<Object>>, RegisterBusEventInterface {

    final private OnStartDragListener startDragListener;
    final private RecyclerView.Adapter adapter;

    public StageLineAdapterDelegate(int viewType, RecyclerView.Adapter adapter, OnStartDragListener startDragListener) {
        super(viewType);
        this.startDragListener = startDragListener;
        this.adapter = adapter;
    }

    class ComposeLineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.composeXCardLine_dragHandle)
        ImageView dragHandle;
        @BindView(R.id.stageLine_mask)
        ImageView mask;
        @BindView(R.id.stageLine_dialogue)
        TextView dialogue;
        ComposeLineViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof StageLine;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.i("StageLineAdapter", "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.composex_stageline, parent, false);
        return new ComposeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final List<Object> items, final int position, @NonNull final RecyclerView.ViewHolder holder) {
        ((StageLineView)holder.itemView).setPosition(position);
        ((StageLineView)holder.itemView).setStageLine((StageLine)items.get(position));

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
                Log.i("StageLine","send click");
                int[] viewLocation = new int[2];
                v.getLocationOnScreen(viewLocation);
                Rect viewRect = new Rect(viewLocation[0], viewLocation[1], viewLocation[0] + v.getWidth(), viewLocation[1] + v.getHeight());
                MaskClickEvent event = new MaskClickEvent("MASK_CLICK", (StageLine) items.get(position), viewRect);
                EventBus.getDefault().post(event);
            }
        });

    }

    @Override
    public void register(Activity activity) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unRegister(Activity activity) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseEventBus(MaskChooseEvent event) {
        switch (event.getMessage()) {
            case "Click":
                Log.i("StageLineAdapter","Click");
                int headPosition = 0;
                for(Object object: ((ListDelegationAdapter) adapter).getDataSet()) {
                    if ( object instanceof StageLine ) {
                        break;
                    } else {
                        headPosition ++;
                    }
                }
                adapter.notifyItemChanged(headPosition + (int)event.getStageLine().ordinal -1);
                break;
        }
    }
}
