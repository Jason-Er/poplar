package com.wecyberstage.wecyberstage.view.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mike on 2018/3/5.
 */

public class Browse extends Fragment {

    @BindView(R.id.frag_browse)
    View browseRecycler;

    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_browse, container, false);
        ButterKnife.bind(this, view);

        int spanCount = 2;
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        ((RecyclerView)browseRecycler).setLayoutManager(layoutManager);
        adapter = new PlayProfileAdapter();
        ((RecyclerView)browseRecycler).setAdapter(adapter);

        ((PlayProfileAdapter) adapter).onClickCallBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        return view;
    }
}
