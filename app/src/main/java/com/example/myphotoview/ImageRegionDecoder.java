package com.example.myphotoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;

public interface ImageRegionDecoder {
    Point init(Context var1, Uri var2) throws Exception;

    Bitmap decodeRegion(Rect var1, int var2);

    boolean isReady();

    void recycle();
}
