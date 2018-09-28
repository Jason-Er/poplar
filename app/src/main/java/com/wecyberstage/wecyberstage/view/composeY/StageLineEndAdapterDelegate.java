package com.wecyberstage.wecyberstage.view.composeY;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class StageLineEndAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface<List<Object>> {

    private OnStartDragListener onStartDragListener;

    public StageLineEndAdapterDelegate(int viewType) {
        super(viewType);
    }

    class StageLineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.composeLine_dragHandle)
        View handleView;
        @BindView(R.id.composeY_stageLine_end_image)
        ImageView mask;
        @BindView(R.id.composeY_stageLine_end_text)
        TextView dialogue;

        public StageLineViewHolder(View v) {
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.composey_stageline_end, parent, false);
        return new StageLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull final RecyclerView.ViewHolder holder) {
        Log.d("LineEndAdapter", "onBindViewHolder position: "+position + " dialogue: "+ ((ComposeYItemDto) items.get(position)).getStageLine().dialogue);
        ((StageLineViewHolder) holder).dialogue.setText(((ComposeYItemDto) items.get(position)).getStageLine().dialogue);
        ((StageLineViewHolder) holder).handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        if(((ComposeYItemDto) items.get(position)).getStageLine().getMaskGraph() != null)
            Glide.with(holder.itemView.getContext()).load(((ComposeYItemDto) items.get(position)).getStageLine().getMaskGraph().graphURL).into(((StageLineViewHolder) holder).mask);
    }

    public void setOnStartDragListener(OnStartDragListener onStartDragListener) {
        this.onStartDragListener = onStartDragListener;
    }
}
