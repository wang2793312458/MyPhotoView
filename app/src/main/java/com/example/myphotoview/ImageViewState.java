package com.example.myphotoview;

import android.graphics.PointF;

import java.io.Serializable;

public class ImageViewState implements Serializable {
    private float scale;
    private float centerX;
    private float centerY;
    private int orientation;

    public ImageViewState(float scale, PointF center, int orientation) {
        this.scale = scale;
        this.centerX = center.x;
        this.centerY = center.y;
        this.orientation = orientation;
    }

    public float getScale() {
        return this.scale;
    }

    public PointF getCenter() {
        return new PointF(this.centerX, this.centerY);
    }

    public int getOrientation() {
        return this.orientation;
    }

}
