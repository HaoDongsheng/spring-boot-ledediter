package org.hds.GJ_coding;

import java.awt.image.BufferedImage;
//图文混排类
public class GJ_PictureAndTextcls {
	private int mTextType;/// 图文类型 0 文字，1图片
    private String mTexts;/// 文字内容
    private BufferedImage mPicture;/// 图片内容
	public int getmTextType() {
		return mTextType;
	}
	public void setmTextType(int mTextType) {
		this.mTextType = mTextType;
	}
	public String getmTexts() {
		return mTexts;
	}
	public void setmTexts(String mTexts) {
		this.mTexts = mTexts;
	}
	public BufferedImage getmPicture() {
		return mPicture;
	}
	public void setmPicture(BufferedImage mPicture) {
		this.mPicture = mPicture;
	}
    
}
