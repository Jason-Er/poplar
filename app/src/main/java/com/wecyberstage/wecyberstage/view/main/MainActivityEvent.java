package com.wecyberstage.wecyberstage.view.main;

import android.content.ContentResolver;
import android.net.Uri;

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
        public ContentResolver resolver;

        public FileEvent(Uri uri, ContentResolver resolver) {
            this.uri = uri;
            this.resolver = resolver;
        }
    }
}
