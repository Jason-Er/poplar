package com.wecyberstage.wecyberstage.view.message;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;

public class MainActivityEvent {
    private Object data;
    private String message;

    public MainActivityEvent(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    static public class FileEvent {
        public Uri uri;
        public Handler handler;
        public ContentResolver resolver;

        public FileEvent(Uri uri, ContentResolver resolver, Handler handler) {
            this.uri = uri;
            this.resolver = resolver;
            this.handler = handler;
        }
    }
}
