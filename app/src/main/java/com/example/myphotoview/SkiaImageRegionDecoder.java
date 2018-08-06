package com.example.myphotoview;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.List;

public class SkiaImageRegionDecoder implements ImageRegionDecoder {
    private BitmapRegionDecoder decoder;
    private final Object decoderLock = new Object();
    private static final String FILE_PREFIX = "file://";
    private static final String ASSET_PREFIX = "file:///android_asset/";
    private static final String RESOURCE_PREFIX = "android.resource://";

    public SkiaImageRegionDecoder() {
    }

    public Point init(Context context, Uri uri) throws Exception {
        String uriString = uri.toString();
        if (uriString.startsWith("android.resource://")) {
            String packageName = uri.getAuthority();
            Resources res;
            if (context.getPackageName().equals(packageName)) {
                res = context.getResources();
            } else {
                PackageManager pm = context.getPackageManager();
                res = pm.getResourcesForApplication(packageName);
            }

            int id = 0;
            List<String> segments = uri.getPathSegments();
            int size = segments.size();
            if (size == 2 && ((String) segments.get(0)).equals("drawable")) {
                String resName = (String) segments.get(1);
                id = res.getIdentifier(resName, "drawable", packageName);
            } else if (size == 1 && TextUtils.isDigitsOnly((CharSequence) segments.get(0))) {
                try {
                    id = Integer.parseInt((String) segments.get(0));
                } catch (NumberFormatException var17) {
                    ;
                }
            }

            this.decoder = BitmapRegionDecoder.newInstance(context.getResources().openRawResource(id), false);
        } else if (uriString.startsWith("file:///android_asset/")) {
            String assetName = uriString.substring("file:///android_asset/".length());
            this.decoder = BitmapRegionDecoder.newInstance(context.getAssets().open(assetName, 1), false);
        } else if (uriString.startsWith("file://")) {
            this.decoder = BitmapRegionDecoder.newInstance(uriString.substring("file://".length()), false);
        } else {
            InputStream inputStream = null;

            try {
                ContentResolver contentResolver = context.getContentResolver();
                inputStream = contentResolver.openInputStream(uri);
                this.decoder = BitmapRegionDecoder.newInstance(inputStream, false);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception var16) {
                        ;
                    }
                }

            }
        }

        return new Point(this.decoder.getWidth(), this.decoder.getHeight());
    }

    public Bitmap decodeRegion(Rect sRect, int sampleSize) {
        Object var3 = this.decoderLock;
        synchronized (this.decoderLock) {
            Options options = new Options();
            options.inSampleSize = sampleSize;
            options.inPreferredConfig = Config.RGB_565;
            Bitmap bitmap = this.decoder.decodeRegion(sRect, options);
            if (bitmap == null) {
                throw new RuntimeException("Skia image decoder returned null bitmap - image format may not be supported");
            } else {
                return bitmap;
            }
        }
    }

    public boolean isReady() {
        return this.decoder != null && !this.decoder.isRecycled();
    }

    public void recycle() {
        this.decoder.recycle();
    }
}
