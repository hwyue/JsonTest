package com.hwy.ui;

import javax.swing.JFrame;

import com.hwy.cache.CacheUtils;

public class FaceBase extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7346016230764209340L;

	public FaceBase() {
		CacheUtils.loadCache();
	}
}
