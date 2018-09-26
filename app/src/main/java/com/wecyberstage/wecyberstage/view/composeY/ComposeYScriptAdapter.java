package com.wecyberstage.wecyberstage.view.composeY;

import android.app.Activity;
import android.os.Message;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class ComposeYScriptAdapter extends ListDelegationAdapter
        implements ItemTouchHelperAdapter, SaveStatesInterface, RegisterBusEventInterface {

    final String START_TAG = "ComposeYArrayStart";
    final String END_TAG = "ComposeYArrayEnd";
    final UpdateStagePlayInterface updateStagePlayInterface;
    List<Long> listStart = new ArrayList<>();
    List<Long> listEnd = new ArrayList<>();

    // for apache poi read and replace
    String parenthesesREGEX = "([（\\(][^\\)）]+[）\\)])|([\\[【][^\\]】]+[\\]】])";
    String splitREGEX = "(：|:)";
    Pattern pattern = Pattern.compile(parenthesesREGEX);


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
    public void onResponseEvent(FooterEditMainEvent event) {
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
                                    for (String retval : strArr) {
                                        Log.d("ComposeYScriptAdapter","read from word:" + retval.trim());
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
                                // TODO: 9/19/2018 update dataSet
                                // notifyDataSetChanged();
                            }
                        });
                        msg.sendToTarget();
                    }
                }.start();


                break;
        }
    }
}
