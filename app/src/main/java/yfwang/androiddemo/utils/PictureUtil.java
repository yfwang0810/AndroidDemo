package yfwang.androiddemo.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


/**
 * Description: 生成二维码
 * Copyright  : Copyright (c) 2015
 * Author     : yfwang
 * Date       : 2016/10/27 19:07
 */

public class PictureUtil {
    public static void createPic(String code, ImageView imageView) {
        int size = code.length();
        for (int i = 0; i < size; i++) {
            int c = code.charAt(i);
            if ((19968 <= c && c < 40623)) { // 中文字符ASCII
                return;
            }
        }
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_39, 500, 250);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        imageView.setImageBitmap(bitmap);
    }





}
