package com.wecyberstage.wecyberstage.view.composeY;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.UpdateStagePlayInterface;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.SaveStatesInterface;
import com.wecyberstage.wecyberstage.view.main.FooterEditMainEvent;
import com.wecyberstage.wecyberstage.view.main.MainActivityEvent;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ComposeYScriptAdapter extends ListDelegationAdapter
        implements ItemTouchHelperAdapter, SaveStatesInterface, RegisterBusEventInterface {

    final String START_TAG = "ComposeYArrayStart";
    final String END_TAG = "ComposeYArrayEnd";
    final UpdateStagePlayInterface updateStagePlayInterface;
    List<Long> listStart = new ArrayList<>();
    List<Long> listEnd = new ArrayList<>();
    @Inject
    public ComposeYScriptAdapter(AdapterDelegatesManager<Object> delegates, UpdateStagePlayInterface updateStagePlayInterface) {
        super(delegates);
        delegatesManager
                .addDelegate(new StageLineStartAdapterDelegate(ComposeYCardViewType.START.ordinal()))
                .addDelegate(new StageLineEndAdapterDelegate(ComposeYCardViewType.END.ordinal()));
        this.updateStagePlayInterface = updateStagePlayInterface;
    }

    public void setStageScene(@NonNull StageScene stageScene) {
        dataSet = new ArrayList<>();
        for(StageLine stageLine : stageScene.stageLines) {
            handleStageLine(stageLine);
        }
        notifyDataSetChanged();
    }

    private void handleStageLine(StageLine stageLine) {
        if(stageLine != null) {
            ComposeYCardViewType viewType;
            // TODO: 2018/5/22 need further refactoring: let user on the phone be end position
            if(listStart.contains(stageLine.roleId)) {
                viewType = ComposeYCardViewType.START;
            } else if(listEnd.contains(stageLine.roleId)) {
                viewType = ComposeYCardViewType.END;
            } else if(listStart.size() > listEnd.size()) {
                listEnd.add(stageLine.roleId);
                viewType = ComposeYCardViewType.END;
            } else {
                listStart.add(stageLine.roleId);
                viewType = ComposeYCardViewType.START;
            }
            ComposeYItemDto dto = new ComposeYItemDto(viewType, stageLine);
            dataSet.add(dto);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataSet, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataSet, i, i - 1);
            }
        }
        updateStagePlayInterface.swapStageLines(fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        updateStagePlayInterface.deleteStageLine(((ComposeYItemDto)dataSet.get(position)).getStageLine());
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void saveStates(Activity activity) {
        Log.d("ComposeYScriptAdapter","unRegister");
        if(listStart.size() > 0) {
            Long[] arrayStart = listStart.toArray(new Long[listStart.size()]);
            activity.getIntent().putExtra(START_TAG, arrayStart);
        }
        if(listEnd.size() > 0) {
            Long[] arrayEnd = listEnd.toArray((new Long[listEnd.size()]));
            activity.getIntent().putExtra(END_TAG, arrayEnd);
        }
    }

    @Override
    public void restoreStates(Activity activity) {
        Log.d("ComposeYScriptAdapter","register");
        Long[] sourceArray = (Long[]) activity.getIntent().getSerializableExtra(START_TAG);
        if( sourceArray != null ) {
            listStart = Arrays.asList(sourceArray);
        }
        sourceArray = (Long[]) activity.getIntent().getSerializableExtra(END_TAG);
        if( sourceArray != null ) {
            listEnd = Arrays.asList(sourceArray);
        }
    }

    @Override
    public void register(Activity activity) {
        Log.d("ComposeYScriptAdapter","register");
        EventBus.getDefault().register(this);
    }

    @Override
    public void unRegister(Activity activity) {
        Log.d("ComposeYScriptAdapter","unRegister");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseFooterEditMainEvent(FooterEditMainEvent event) {
        Log.d("ComposeYScriptAdapter","receive footerEditMain");
        if(event.getStageLine() instanceof StageLine) {
            StageLine stageLine = null;
            try {
                stageLine = (StageLine) ((StageLine) event.getStageLine()).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            handleStageLine(stageLine);
        } else if(event.getStageLine() instanceof ArrayList) {

        }
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onResponseMainActivityEvent(MainActivityEvent event) {
        switch (event.getMessage()) {
            case "File Selected":
                Uri uri = (Uri) event.getData();
                Log.d("ComposeYScriptAdapter","uri path: " + uri.getPath());
                break;
        }
    }
}
