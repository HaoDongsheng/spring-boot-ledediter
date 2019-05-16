package org.hds.GJ_coding;

//查询信息列表类（播放列表+广告列表）
public class GJ_INFOlistcls {
	private int[] mplaylist = new int[] { };
    private int[] madvlist = new int[] { };
	public int[] getMplaylist() {
		return mplaylist;
	}
	public void setMplaylist(int[] mplaylist) {
		this.mplaylist = mplaylist;
	}
	public int[] getMadvlist() {
		return madvlist;
	}
	public void setMadvlist(int[] madvlist) {
		this.madvlist = madvlist;
	}
}
