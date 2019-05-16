package org.hds.GJ_coding;

import java.util.ArrayList;
import java.util.List;
//播放列表类
public class GJ_Playlistcls {
	 private int m_id;/// 播放列表id 4byte
     private int m_crc;/// 播放列表的crc校验值 2byte
     private byte m_level;/// 列表的播放等级，0级最低，
     private String m_lifestart ;/// 生命周期开始 置0一直有效 3byte
     private String m_lifeend ;/// 生命周期结束 置0一直有效 3byte
     private List<GJ_Timequantumcls> m_Timequantum = new ArrayList<GJ_Timequantumcls>();/// 时间段集合
     private GJ_Schedulingcls m_Scheduling = new GJ_Schedulingcls();/// 排挡
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getM_crc() {
		return m_crc;
	}
	public void setM_crc(int m_crc) {
		this.m_crc = m_crc;
	}
	public byte getM_level() {
		return m_level;
	}
	public void setM_level(byte m_level) {
		this.m_level = m_level;
	}
	public String getM_lifestart() {
		return m_lifestart;
	}
	public void setM_lifestart(String m_lifestart) {
		this.m_lifestart = m_lifestart;
	}
	public String getM_lifeend() {
		return m_lifeend;
	}
	public void setM_lifeend(String m_lifeend) {
		this.m_lifeend = m_lifeend;
	}
	public List<GJ_Timequantumcls> getM_Timequantum() {
		return m_Timequantum;
	}
	public void setM_Timequantum(List<GJ_Timequantumcls> m_Timequantum) {
		this.m_Timequantum = m_Timequantum;
	}
	public GJ_Schedulingcls getM_Scheduling() {
		return m_Scheduling;
	}
	public void setM_Scheduling(GJ_Schedulingcls m_Scheduling) {
		this.m_Scheduling = m_Scheduling;
	}
     
}
