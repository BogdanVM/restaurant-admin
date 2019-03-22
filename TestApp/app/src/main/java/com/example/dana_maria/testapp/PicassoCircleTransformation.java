package com.example.dana_maria.testapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.squareup.picasso.Transformation;

public class PicassoCircleTransformation implements Transformation {
    private static final String[] CONTENT_ORIENTATION = new String[] {
            MediaStore.Images.ImageColumns.ORIENTATION
    };

    final Context context;
    final Uri uri;

    public PicassoCircleTransformation(Context context, Uri uri) {
        this.context = context;
        this.uri = uri;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int exifRotation = getExifOrientation(context, uri);
            if(exifRotation != 0) {
                Matrix matrix = new Matrix();
                matrix.preRotate(exifRotation);

                Bitmap rotated = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                        source.getHeight(), matrix, true);

                if(rotated != source) {
                    source.recycle();
                }

                source = rotated;
            }
        }

        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }

    public static int getExifOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri, CONTENT_ORIENTATION, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
