package com.rhahn.myworldtrip.DataHandler;

import android.content.Context;

import com.rhahn.myworldtrip.Data.MyWorldtripData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * {@link Datapersistance} class for loading and saving {@link MyWorldtripData}
 *
 * @author Robin Hahn
 */
public class Datapersistance {
    private static String fileName = "worldtripData";

    /**
     * Saves {@link MyWorldtripData} into the phone
     *
     * @param data    {@link MyWorldtripData} to save
     * @param context current context
     * @return true if save is successful
     */
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

    /**
     * Loads and returns {@link MyWorldtripData} saved on the phone
     *
     * @param context current context
     * @return MyWorldtripData saved on the phone if no data found return null
     */
    public static MyWorldtripData loadData(Context context) {
        FileInputStream fileInputStream;
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
