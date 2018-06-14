package com.example.mrzheng.lanlanapp.Extra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mrzheng on 18-6-14.
 */

public class Base64AndBitmap {

    /**
     * bitmap转为base64
     * @param bitmap
     * @return base64字符串
     */
    public static String bitmapToBase64(Bitmap bitmap){
        String ans = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try{
            if(bitmap != null){
                byteArrayOutputStream = new ByteArrayOutputStream();
                //100表示不压缩
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();

                byte[] bitmapBytes = byteArrayOutputStream.toByteArray();
                ans = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;

    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return bitmap位图
     */
    public static Bitmap base64ToBitmap(String base64Data){
        byte[] bytes;
        Bitmap bitmap;
        bytes = Base64.decode(base64Data, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return bitmap;
    }
}
