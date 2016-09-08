/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.nostra13.universalimageloader.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;

/**
 * ImageAware which provides needed info for processing of original image but do nothing for displaying image. It's
 * used when user need just load and decode image and get it in {@linkplain
 * com.nostra13.universalimageloader.core.listener.ImageLoadingListener#onLoadingComplete(String, View,
 * Bitmap) callback}.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.9.0
 */
public class NonViewAware implements ImageAware {

	protected final String imageUri;
	protected final ImageSize imageSize;
	protected final ViewScaleType scaleType;

	public NonViewAware(ImageSize imageSize, ViewScaleType scaleType) {
		this(null, imageSize, scaleType);
	}

	public NonViewAware(String imageUri, ImageSize imageSize, ViewScaleType scaleType) {
		if (imageSize == null) throw new IllegalArgumentException("imageSize must not be null");
		if (scaleType == null) throw new IllegalArgumentException("scaleType must not be null");

		this.imageUri = imageUri;
		this.imageSize = imageSize;
		this.scaleType = scaleType;
	}

	@Override
	public int getWidth() {
		return imageSize.getWidth();
	}

	@Override
	public int getHeight() {
		return imageSize.getHeight();
	}

	@Override
	public ViewScaleType getScaleType() {
		return scaleType;
	}

	@Override
	public View getWrappedView() {
		return null;
	}

	@Override
	public boolean isCollected() {
		return false;
	}

	@Override
	public int getId() {
		return TextUtils.isEmpty(imageUri) ? super.hashCode() : imageUri.hashCode();
	}

	@Override
	public boolean setImageDrawable(Drawable drawable) { // Do nothing
		return true;
	}

	@Override
	public boolean setImageBitmap(Bitmap bitmap) { // Do nothing
		return true;
	}
}