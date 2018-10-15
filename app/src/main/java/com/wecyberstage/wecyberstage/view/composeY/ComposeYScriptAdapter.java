package com.wecyberstage.wecyberstage.view.composeY;

import android.app.Activity;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StagePlay;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StageSceneHandle;
import com.wecyberstage.wecyberstage.view.helper.PlayControlSub2Interface;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.SaveStatesInterface;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.message.MainActivityEvent;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;


import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class ComposeYScriptAdapter extends ListDelegationAdapter
        implements ItemTouchHelperAdapter, SaveStatesInterface, RegisterBusEventInterface,
        PlayControlSub2Interface, StageLineHandle, StageSceneHandle {

    final String START_TAG = "ComposeYArrayStart";
    final String END_TAG = "ComposeYArrayEnd";
    final StageLineHandle updateStagePlayInterface;
    private final String TAG = "ComposeYScriptAdapter";
    List<String> listStart = new ArrayList<>();
    List<String> listEnd = new ArrayList<>();
    private StageScene stageScene;
    private StagePlay stagePlay;

    // for apache poi read and replace
    String parenthesesREGEX = "([（\\(][^\\)）]+[）\\)])|([\\[【][^\\]】]+[\\]】])";
    String splitREGEX = "(：|:)";
    Pattern pattern = Pattern.compile(parenthesesREGEX);

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

    public void setStagePlay(@NonNull StagePlay stagePlay, StagePlayCursor stagePlayCursor) {
        this.stagePlay = stagePlay;
        stageScene = stagePlay.scenes.get(stagePlayCursor.getSceneOrdinal());
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
        Log.d("ComposeYScriptAdapter","unRegister");
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onResponseEvent(MainActivityEvent event) {
        switch (event.getMessage()) {
            case "File Selected":
                final MainActivityEvent.FileEvent fileEvent = (MainActivityEvent.FileEvent) event.getData();
                new Thread() {
                    @Override
                    public void run() {
                        InputStream inputStream = null;
                        try {
                            inputStream = fileEvent.resolver.openInputStream(fileEvent.uri);
                            Log.d("ComposeYScriptAdapter","Available bytes of file: " + inputStream.available());
                            XWPFDocument doc = new XWPFDocument(inputStream);
                            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                            String text = extractor.getText();
                            Matcher matcher = pattern.matcher(text);
                            String tmp = matcher.replaceAll("");
                            String[] lines = tmp.split("\n");
                            for(String line: lines) {
                                String[] strArr = line.split(splitREGEX);
                                if (strArr.length > 1) {
                                    strArr[0] = strArr[0].trim();
                                    strArr[1] = strArr[1].trim();
                                    long roleId = getStageRoleIdByName(strArr[0]);
                                    if( roleId == 0 ) {
                                        // add new stageRole
                                        StageRole stageRole = new StageRole(0, strArr[0], null);
                                        stageScene.stageRoles.add(stageRole);
                                        StageLine stageLine = new StageLine();
                                        stageLine.setStageRole(stageRole);
                                        stageLine.dialogue = strArr[1];
                                        stageScene.stageLines.add(stageLine);
                                        handleStageLine(stageLine);
                                    } else {
                                        // the stageRole is already exists
                                        StageRole stageRole = getStageRoleById(roleId);
                                        StageLine stageLine = new StageLine();
                                        stageLine.setStageRole(stageRole);
                                        stageLine.dialogue = strArr[1];
                                        stageScene.stageLines.add(stageLine);
                                        handleStageLine(stageLine);
                                    }
                                }
                            }
                            extractor.close();
                            doc.close();

                        } catch (OfficeXmlFileException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Message msg = Message.obtain(fileEvent.handler, new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        });
                        msg.sendToTarget();
                    }
                }.start();


                break;
        }
    }

    private StageRole getStageRoleById(Long id) {
        StageRole stageRole = null;
        if(stageScene != null) {
            if(stageScene.stageRoles != null) {
                for(StageRole role:stageScene.stageRoles) {
                    if(role.id == id) {
                        stageRole = role;
                        break;
                    }
                }
            }
        }
        return stageRole;
    }

    private long getStageRoleIdByName(String name) {
        long id = 0;
        if(isNameInCast(name)) {
            for(StageRole stageRole:stageScene.stageRoles) {
                if(stageRole.name.equals(name)) {
                    id = stageRole.id;
                    break;
                }
            }
        }
        return id;
    }

    private boolean isNameInCast(String name) {
        boolean status = false;
        if(stageScene != null) {
            if(stageScene.stageRoles != null) {
                for(StageRole stageRole:stageScene.stageRoles) {
                    if(stageRole.name.equals(name)) {
                        status = true;
                        break;
                    }
                }
            }
        }
        return status;
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
