package com.example.finalwork.Helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public class AssetsUtils {

    public static final Bitmap[] readBitmapFolderFromAssets(Context context, String strDir) {
        String[] arrSTrFileName = null;
        AssetManager am = context.getResources().getAssets();

        //获取指定文件夹中的所有资源图片的名称
        try {
            arrSTrFileName = context.getAssets().list(strDir);
        } catch (IOException e) {
        }
        //判断是否存在图片
        if (arrSTrFileName.length == 0)
            return null;
        //通过循环将每一张图片加到图片组中
        Bitmap[] arrBmp = new Bitmap[arrSTrFileName.length];
        for (int i = 0; i < arrBmp.length; i++) {
            arrBmp[i] = readBitmapFromAssets(am, strDir + "/" + arrSTrFileName[i]);
        }
        return arrBmp;

    }
//读取某个文件夹下的图片

    /**
     * 读取assets文件夹内的指定图片资源
     *
     * @param am
     * @param strFileName : 图片名称(含后缀)
     * @return
     */

    public static final Bitmap readBitmapFromAssets(AssetManager am, String strFileName) {

        Bitmap bmp = null;
        try {
            //1.通过流将资源加载
            InputStream is = am.open(strFileName);
            //2.解析这个流文件生成图片对象
            bmp = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.e("sysout", "read error [" + strFileName + "]");
        }
        return bmp;
    }
}
