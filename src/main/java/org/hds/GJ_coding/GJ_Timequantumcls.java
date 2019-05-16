package org.hds.GJ_coding;

//时间段类
public class GJ_Timequantumcls {
	private String m_playstart;/// 播放开始时间 3byte	    
	private String m_playend;/// 播放结束时间 3byte  
	
	public String getM_playstart() {
		return m_playstart;
	}
	public void setM_playstart(String m_playstart) {
		this.m_playstart = m_playstart;
	}
	public String getM_playend() {
		return m_playend;
	}
	public void setM_playend(String m_playend) {
		this.m_playend = m_playend;
	}
}
