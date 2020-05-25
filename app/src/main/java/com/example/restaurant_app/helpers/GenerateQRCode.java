package com.example.restaurant_app.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author Jonas Mitschke
 * @content class for QR-Code generation & saving
 */
public class GenerateQRCode {

/**
 *
 * @author   Jonas Mitschke
 * @content  generate QR-Code
 * @param    context    plain text password, which needs to be encrypted
 * @param    text       the text, which will be contained in the qr-code
 * @param    picName    name of the qr-code-file
 * @return   generation & saving worked or not
 */
    public static boolean generateCode(Context context, String text, String picName){
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 500, 500);

            String path = Environment.getExternalStorageDirectory().toString();
            File file = new File(path, picName + ".jpg");
            OutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
