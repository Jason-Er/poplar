package com.wecyberstage.wecyberstage.view.composeY;

import android.content.Context;
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
import com.wecyberstage.wecyberstage.model.ComposeScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ComposeYScriptAdapter extends RecyclerView.Adapter<ComposeYScriptAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    private final List<String> stringList = new ArrayList<>();
    private final OnStartDragListener dragListener;
    private final UtilityInterface utilityInterface;

    public ComposeYScriptAdapter(Context context, ComposeY composeY) {
        stringList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
        this.dragListener = composeY;
        this.utilityInterface = composeY;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compose_y_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        holder.textView.setText(stringList.get(position));
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("ComposeYScriptAdapter", "handleView setOnTouchListener");
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    utilityInterface.hideSoftKeyboard();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    @Override
    public void onItemMove(int from, int to) {
        Log.i("onItemMove","from: " + from + " to: "+to);
        Collections.swap(stringList, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public void onItemDismiss(int position) {
        stringList.remove(position);
        notifyItemRemoved(position);
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        // TODO: 2018/5/10  need further refactoring
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;
        final ImageView figureView;
        final ImageView handleView;
        final ViewGroup container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_item_text);
            figureView = itemView.findViewById(R.id.recycler_item_view);
            handleView = itemView.findViewById(R.id.recycler_item_handle);
            container = itemView.findViewById(R.id.recycler_item_container);
        }

    }
}
