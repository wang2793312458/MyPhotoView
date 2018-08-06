package com.example.myphotoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public interface ImageDecoder {
    Bitmap decode(Context var1, Uri var2) throws Exception;
}
