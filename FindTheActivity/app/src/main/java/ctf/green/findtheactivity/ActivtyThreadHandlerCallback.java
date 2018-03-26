package ctf.green.findtheactivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by giglf on 18-3-14.
 */

public class ActivtyThreadHandlerCallback implements Handler.Callback {

    Handler mBase;
    static Class<Object> classToLoad = null;


    public ActivtyThreadHandlerCallback(Handler base){
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what){
            case 100:
                handleLaunchActivity(msg);
                break;
        }

        mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {

        Object obj = msg.obj;

        try{
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent)intent.get(obj);

            String replacePackage = Util.base64decode("Y3RmLmdyZWVuLmZpbmR0aGVhY3Rpdml0eS5jaGVjaw==");//ctf.green.findtheactivity.check
            ComponentName componentName = new ComponentName(replacePackage, getCheckClass(raw.getIntExtra("Key", 0)).getName());
            raw.setComponent(componentName);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private Class<Object> getCheckClass(int xorkey){

        if(classToLoad != null) return classToLoad;

        Context context = MainActivity.ContextUtil.getContext();

        File tmpdir = new File(context.getDir(Util.base64decode("ZGV4"), Context.MODE_PRIVATE), Util.base64decode("NTExNjUzOWU0ZmFmZTRmZjYwOTllM2YxM2ZiNTliYjYuZGV4"));//5116539e4fafe4ff6099e3f13fb59bb6.dex
        File optDir = context.getDir(Util.base64decode("b3V0ZGV4"), Context.MODE_PRIVATE);
        BufferedInputStream bufInput = null;
        OutputStream dexWriter = null;

        try {

            bufInput = new BufferedInputStream(context.getAssets().open(Util.base64decode("NTExNjUzOWU0ZmFmZTRmZjYwOTllM2YxM2ZiNTliYjY=")));//5116539e4fafe4ff6099e3f13fb59bb6
            dexWriter = new BufferedOutputStream(new FileOutputStream(tmpdir));

            byte[] xorArr = new byte[4];
            ByteBuffer.wrap(xorArr).order(ByteOrder.LITTLE_ENDIAN).putInt(xorkey);
            byte[] filename = Util.base64decode("NTExNjUzOWU0ZmFmZTRmZjYwOTllM2YxM2ZiNTliYjY=").getBytes();//5116539e4fafe4ff6099e3f13fb59bb6

            for(int i=0;i<filename.length;i++){
                filename[i] ^= xorArr[i%4];
            }

            byte[] buf = new byte[1024*8];
            int len;
            ARC4 arc4 = new ARC4(filename);
            while((len = bufInput.read(buf, 0, 8*1024)) > 0){
                arc4.translate(buf);
                dexWriter.write(buf, 0, len);
            }
            dexWriter.close();
            bufInput.close();


            PathClassLoader pathClassLoader = (PathClassLoader) context
                    .getClassLoader();
            // 通过ClassLoader拿到PathList
            Object pathList = Util.getPathList(pathClassLoader);
            // 通过PahList拿到Elements数组
            Object contextElements = Util.getDexElements(pathList);

            DexClassLoader dexClassLoader = new DexClassLoader(tmpdir.getAbsolutePath(), optDir.getAbsolutePath(), null, context.getClassLoader());
            classToLoad = (Class<Object>)dexClassLoader.loadClass(Util.base64decode("Y3RmLmdyZWVuLmZpbmR0aGVhY3Rpdml0eS5jaGVjay5DaGVja0FjdGl2aXR5"));//ctf.green.findtheactivity.check.CheckActivity

            Object dexPathList = Util.getPathList(dexClassLoader);

            Object dexElements = Util.getDexElements(dexPathList);
            Object newElements = Util.combineArray(contextElements, dexElements);

            Util.setField(pathList, pathList.getClass(), "dexElements", newElements);

            tmpdir.delete();
            optDir.delete();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return classToLoad;
    }



}
