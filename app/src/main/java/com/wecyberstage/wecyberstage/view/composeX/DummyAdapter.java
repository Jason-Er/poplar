package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.DummyViewHolder> {

    List<String> stringList = new ArrayList<>();
    public DummyAdapter(Context context) {
        stringList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.languages)));
    }

    @Override
    public DummyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compose_x_card_line, parent, false);
        DummyViewHolder itemViewHolder = new DummyViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(DummyViewHolder holder, int position) {
        holder.textView.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class DummyViewHolder extends RecyclerView.ViewHolder{
        final TextView textView;
        public DummyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.compose_x_line);
        }
    }
}
