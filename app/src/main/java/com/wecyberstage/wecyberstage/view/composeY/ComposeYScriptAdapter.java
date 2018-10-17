package com.wecyberstage.wecyberstage.view.composeY;

import android.app.Activity;
import android.util.Log;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StageSceneHandle;
import com.wecyberstage.wecyberstage.view.helper.PlayControlSub2Interface;
import com.wecyberstage.wecyberstage.view.helper.SaveStatesInterface;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ComposeYScriptAdapter extends ListDelegationAdapter
        implements ItemTouchHelperAdapter, SaveStatesInterface,
        PlayControlSub2Interface, StageLineHandle, StageSceneHandle {

    private final String TAG = "ComposeYScriptAdapter";

    final String START_TAG = "ComposeYArrayStart";
    final String END_TAG = "ComposeYArrayEnd";
    final StageLineHandle updateStagePlayInterface;

    List<String> listStart = new ArrayList<>();
    List<String> listEnd = new ArrayList<>();
    private StageScene stageScene;

    @Inject
    public ComposeYScriptAdapter(AdapterDelegatesManager<Object> delegates, StageLineHandle updateStagePlayInterface, OnStartDragListener startDragListener) {
        super(delegates);
        StageLineStartAdapterDelegate stageLineStartAdapterDelegate = new StageLineStartAdapterDelegate(ComposeYCardViewType.START.ordinal());
        StageLineEndAdapterDelegate stageLineEndAdapterDelegate = new StageLineEndAdapterDelegate(ComposeYCardViewType.END.ordinal());
        delegatesManager
                .addDelegate(stageLineStartAdapterDelegate)
                .addDelegate(stageLineEndAdapterDelegate);
        stageLineStartAdapterDelegate.setOnStartDragListener(startDragListener);
        stageLineEndAdapterDelegate.setOnStartDragListener(startDragListener);
        this.updateStagePlayInterface = updateStagePlayInterface;
    }

    public void setStageScene(StageScene stageScene) {
        Log.d(TAG,"setStageScene");
        this.stageScene = stageScene;
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
            if(listStart.contains(stageLine.getRoleName())) {
                viewType = ComposeYCardViewType.START;
            } else if(listEnd.contains(stageLine.getRoleName())) {
                viewType = ComposeYCardViewType.END;
            } else if(listStart.size() > listEnd.size()) {
                listEnd.add(stageLine.getRoleName());
                viewType = ComposeYCardViewType.END;
            } else {
                listStart.add(stageLine.getRoleName());
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
        Log.d(TAG,"unRegister");
        if(listStart.size() > 0) {
            String[] arrayStart = listStart.toArray(new String[listStart.size()]);
            activity.getIntent().putExtra(START_TAG, arrayStart);
        }
        if(listEnd.size() > 0) {
            String[] arrayEnd = listEnd.toArray((new String[listEnd.size()]));
            activity.getIntent().putExtra(END_TAG, arrayEnd);
        }
    }

    @Override
    public void restoreStates(Activity activity) {
        Log.d("ComposeYScriptAdapter","register");
        String[] sourceArray = (String[]) activity.getIntent().getSerializableExtra(START_TAG);
        if( sourceArray != null ) {
            for(String str : sourceArray) {
                listStart.add(str);
            }
        }
        sourceArray = (String[]) activity.getIntent().getSerializableExtra(END_TAG);
        if( sourceArray != null ) {
            for(String str : sourceArray) {
                listEnd.add(str);
            }
        }
    }

    // region implementation of PlayControlSub2Interface
    @Override
    public void pre() {
        Log.d(TAG,"pre()");
    }

    @Override
    public void next() {
        Log.d(TAG,"next()");
    }

    @Override
    public void volume(boolean open) {
        Log.d(TAG,"volume()");
    }
    // endregion

    // region implementation of StageLineHandle, StageSceneHandle
    @Override
    public void addStageLine(StageLine stageLine) {
        boolean isNeedNotifyDataSetChanged = false;
        int position = stageScene.stageLines.indexOf(stageLine);
        if( !stageScene.stageLines.contains( stageLine ) ) {
            handleStageLine(stageLine);
            isNeedNotifyDataSetChanged = true;
        } else {
            if ( listStart.contains( stageLine.getRoleName() ) ) {
                if ( ((ComposeYItemDto) dataSet.get(position)).getViewType() != ComposeYCardViewType.START ) {
                    ((ComposeYItemDto) dataSet.get(position)).setViewType(ComposeYCardViewType.START);
                    isNeedNotifyDataSetChanged = true;
                }
            } else {
                if ( ((ComposeYItemDto) dataSet.get(position)).getViewType() != ComposeYCardViewType.END ) {
                    ((ComposeYItemDto) dataSet.get(position)).setViewType(ComposeYCardViewType.END);
                    isNeedNotifyDataSetChanged = true;
                }
            }
        }
        if(isNeedNotifyDataSetChanged) {
            notifyDataSetChanged();
        } else {
            notifyItemChanged(position);
        }
    }

    @Override
    public void updateStageLine(StageLine stageLine) {

    }

    @Override
    public void deleteStageLine(StageLine stageLine) {

    }

    @Override
    public void swapStageLines(int position1, int position2) {

    }

    @Override
    public void deleteStageSceneContent() {
        dataSet = new ArrayList<>();
        stageScene.stageLines = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void deleteStageScene() {

    }

    @Override
    public void addStageScene() {

    }
    // endregion
}
