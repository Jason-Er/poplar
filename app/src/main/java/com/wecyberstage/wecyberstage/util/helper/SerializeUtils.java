package com.wecyberstage.wecyberstage.util.helper;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {

    public static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String string = byteArrayOutputStream.toString("ISO-8859-1");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return string;
    }
    public static Object serializeToObject(String str) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return object;
    }

    public static String parcelObject2String(Parcelable stu) {
        // 1.序列化
        Parcel p = Parcel.obtain();
        stu.writeToParcel(p, 0);
        byte[] bytes = p.marshall();
        p.recycle();

        // 2.编码
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        return str;
    }

    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // this is extremely important!
        return parcel;
    }

    public static <T> T unmarshall(String str, Parcelable.Creator<T> creator) {
        // 1.解码
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        // 2.反序列化
        Parcel parcel = unmarshall(bytes);
        return creator.createFromParcel(parcel);
    }
}
