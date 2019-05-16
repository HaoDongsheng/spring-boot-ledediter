package org.hds.GJ_coding;

public class GJ_WorkStatecls {
	private byte mWorkVersion;//状态指令版本号0x01
    private byte mBorL;//黑亮屏 0亮屏 1黑屏
    private byte mLightValue;//亮度值
    private byte mFontState;//字库状态非0异常
    private byte mLedSetState;//屏参设置是否异常
    private int mLedSetId;//屏参id
    private int mADCount;//广告总数
    private int mListCount;//列表总数
    private GJ_PlayStateEnum mPlayState;//正在播放的状态
    private int mPlayListId;//正在播放列表id
    private int mPlayADid;//正在播放的广告id
    private String mLEDversion;//屏软件版本；
    private byte mPlayMode;//播放模式
    private byte mZhaoMingState;//照明状态        
    private byte mset0;//
    private byte mset1;//
    private byte mset2;//
    private byte mset3;//
    private byte mset4;//
    private byte mset5;//
    private byte mset6;//
	public byte getmWorkVersion() {
		return mWorkVersion;
	}
	public void setmWorkVersion(byte mWorkVersion) {
		this.mWorkVersion = mWorkVersion;
	}
	public byte getmBorL() {
		return mBorL;
	}
	public void setmBorL(byte mBorL) {
		this.mBorL = mBorL;
	}
	public byte getmLightValue() {
		return mLightValue;
	}
	public void setmLightValue(byte mLightValue) {
		this.mLightValue = mLightValue;
	}
	public byte getmFontState() {
		return mFontState;
	}
	public void setmFontState(byte mFontState) {
		this.mFontState = mFontState;
	}
	public byte getmLedSetState() {
		return mLedSetState;
	}
	public void setmLedSetState(byte mLedSetState) {
		this.mLedSetState = mLedSetState;
	}
	public int getmLedSetId() {
		return mLedSetId;
	}
	public void setmLedSetId(int mLedSetId) {
		this.mLedSetId = mLedSetId;
	}
	public int getmADCount() {
		return mADCount;
	}
	public void setmADCount(int mADCount) {
		this.mADCount = mADCount;
	}
	public int getmListCount() {
		return mListCount;
	}
	public void setmListCount(int mListCount) {
		this.mListCount = mListCount;
	}
	public GJ_PlayStateEnum getmPlayState() {
		return mPlayState;
	}
	public void setmPlayState(GJ_PlayStateEnum mPlayState) {
		this.mPlayState = mPlayState;
	}
	public int getmPlayListId() {
		return mPlayListId;
	}
	public void setmPlayListId(int mPlayListId) {
		this.mPlayListId = mPlayListId;
	}
	public int getmPlayADid() {
		return mPlayADid;
	}
	public void setmPlayADid(int mPlayADid) {
		this.mPlayADid = mPlayADid;
	}
	public String getmLEDversion() {
		return mLEDversion;
	}
	public void setmLEDversion(String mLEDversion) {
		this.mLEDversion = mLEDversion;
	}
	public byte getmPlayMode() {
		return mPlayMode;
	}
	public void setmPlayMode(byte mPlayMode) {
		this.mPlayMode = mPlayMode;
	}
	public byte getmZhaoMingState() {
		return mZhaoMingState;
	}
	public void setmZhaoMingState(byte mZhaoMingState) {
		this.mZhaoMingState = mZhaoMingState;
	}
	public byte getMset0() {
		return mset0;
	}
	public void setMset0(byte mset0) {
		this.mset0 = mset0;
	}
	public byte getMset1() {
		return mset1;
	}
	public void setMset1(byte mset1) {
		this.mset1 = mset1;
	}
	public byte getMset2() {
		return mset2;
	}
	public void setMset2(byte mset2) {
		this.mset2 = mset2;
	}
	public byte getMset3() {
		return mset3;
	}
	public void setMset3(byte mset3) {
		this.mset3 = mset3;
	}
	public byte getMset4() {
		return mset4;
	}
	public void setMset4(byte mset4) {
		this.mset4 = mset4;
	}
	public byte getMset5() {
		return mset5;
	}
	public void setMset5(byte mset5) {
		this.mset5 = mset5;
	}
	public byte getMset6() {
		return mset6;
	}
	public void setMset6(byte mset6) {
		this.mset6 = mset6;
	}
}
