package com.example.myphotoview;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ImageSource {
    static final String FILE_SCHEME = "file:///";
    static final String ASSET_SCHEME = "file:///android_asset/";
    private final Uri uri;
    private final Bitmap bitmap;
    private final Integer resource;
    private boolean tile;
    private int sWidth;
    private int sHeight;
    private Rect sRegion;
    private boolean cached;

    private ImageSource(Bitmap bitmap, boolean cached) {
        this.bitmap = bitmap;
        this.uri = null;
        this.resource = null;
        this.tile = false;
        this.sWidth = bitmap.getWidth();
        this.sHeight = bitmap.getHeight();
        this.cached = cached;
    }

    private ImageSource(Uri uri) {
        String uriString = uri.toString();
        if (uriString.startsWith("file:///")) {
            File uriFile = new File(uriString.substring("file:///".length() - 1));
            if (!uriFile.exists()) {
                try {
                    uri = Uri.parse(URLDecoder.decode(uriString, "UTF-8"));
                } catch (UnsupportedEncodingException var5) {
                    ;
                }
            }
        }

        this.bitmap = null;
        this.uri = uri;
        this.resource = null;
        this.tile = true;
    }

    private ImageSource(int resource) {
        this.bitmap = null;
        this.uri = null;
        this.resource = resource;
        this.tile = true;
    }

    public static ImageSource resource(int resId) {
        return new ImageSource(resId);
    }

    public static ImageSource asset(String assetName) {
        if (assetName == null) {
            throw new NullPointerException("Asset name must not be null");
        } else {
            return uri("file:///android_asset/" + assetName);
        }
    }

    public static ImageSource uri(String uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        } else {
            if (!uri.contains("://")) {
                if (uri.startsWith("/")) {
                    uri = uri.substring(1);
                }

                uri = "file:///" + uri;
            }

            return new ImageSource(Uri.parse(uri));
        }
    }

    public static ImageSource uri(Uri uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        } else {
            return new ImageSource(uri);
        }
    }

    public static ImageSource bitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        } else {
            return new ImageSource(bitmap, false);
        }
    }

    public static ImageSource cachedBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        } else {
            return new ImageSource(bitmap, true);
        }
    }

    public ImageSource tilingEnabled() {
        return this.tiling(true);
    }

    public ImageSource tilingDisabled() {
        return this.tiling(false);
    }

    public ImageSource tiling(boolean tile) {
        this.tile = tile;
        return this;
    }

    public ImageSource region(Rect sRegion) {
        this.sRegion = sRegion;
        this.setInvariants();
        return this;
    }

    public ImageSource dimensions(int sWidth, int sHeight) {
        if (this.bitmap == null) {
            this.sWidth = sWidth;
            this.sHeight = sHeight;
        }

        this.setInvariants();
        return this;
    }

    private void setInvariants() {
        if (this.sRegion != null) {
            this.tile = true;
            this.sWidth = this.sRegion.width();
            this.sHeight = this.sRegion.height();
        }

    }

    protected final Uri getUri() {
        return this.uri;
    }

    protected final Bitmap getBitmap() {
        return this.bitmap;
    }

    protected final Integer getResource() {
        return this.resource;
    }

    protected final boolean getTile() {
        return this.tile;
    }

    protected final int getSWidth() {
        return this.sWidth;
    }

    protected final int getSHeight() {
        return this.sHeight;
    }

    protected final Rect getSRegion() {
        return this.sRegion;
    }

    protected final boolean isCached() {
        return this.cached;
    }
}
