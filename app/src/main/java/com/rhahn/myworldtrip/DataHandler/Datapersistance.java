package com.rhahn.myworldtrip.DataHandler;

import android.content.Context;

import com.rhahn.myworldtrip.Data.MyWorldtripData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Datapersistance {
    static String fileName = "worldtripData";

    public static boolean saveData(MyWorldtripData data, Context context) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(data);
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static MyWorldtripData loadData(Context context) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            MyWorldtripData worldtripData = (MyWorldtripData) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return worldtripData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
