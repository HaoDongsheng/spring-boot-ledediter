package org.hds.GJ_coding;

import java.util.ArrayList;
import java.util.List;
//模板类
public class GJ_Templatecls {
	private int m_adid;/// 广告id 4byte    
    private int m_adplaytime;/// 广告播放时长 4byte   
    private List<String> m_timeoffset = new ArrayList<String>();/// 广告播放时间偏移量集合 3*nbyte
    //2018-12-7 改动
    private String m_ADlifestart;
	//2018-12-7 改动
    private String m_ADlifeend;


	public int getM_adid() {
		return m_adid;
	}
	public void setM_adid(int m_adid) {
		this.m_adid = m_adid;
	}
	public int getM_adplaytime() {
		return m_adplaytime;
	}
	public void setM_adplaytime(int m_adplaytime) {
		this.m_adplaytime = m_adplaytime;
	}
	public List<String> getM_timeoffset() {
		return m_timeoffset;
	}
	public void setM_timeoffset(List<String> m_timeoffset) {
		this.m_timeoffset = m_timeoffset;
	}
	
    public String getM_ADlifestart() {
		return m_ADlifestart;
	}
	public void setM_ADlifestart(String m_ADlifestart) {
		this.m_ADlifestart = m_ADlifestart;
	}
	
	public String getM_ADlifeend() {
		return m_ADlifeend;
	}
	public void setM_ADlifeend(String m_ADlifeend) {
		this.m_ADlifeend = m_ADlifeend;
	}
    
}
