/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Memory cache with {@linkplain WeakReference weak references} to {@linkplain Bitmap bitmaps}<br />
 * <br />
 * <b>NOTE:</b> This cache uses only weak references for stored Bitmaps.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.5.3
 */
public class WeakMemoryCache extends BaseMemoryCache {
	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}
}
