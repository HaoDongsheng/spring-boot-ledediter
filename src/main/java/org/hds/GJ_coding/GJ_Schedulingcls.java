package org.hds.GJ_coding;

import java.util.ArrayList;
import java.util.List;
//排档类
public class GJ_Schedulingcls {
	 private byte m_mode;/// 排挡方式，0循环排挡Loop_，1是模板排挡Template_     
     private List<GJ_LoopADidlistcls> m_Loop_adidlist = new ArrayList<GJ_LoopADidlistcls>();/// 循环排挡_Loop，广告播放列表 4*nbyte 
     private int m_Template_cycle;/// 模板排挡_Template,模板周期(宽度)4*nbyte    
     private List<GJ_Templatecls> m_Template_adlist = new ArrayList<GJ_Templatecls>();/// 模板排挡_Template,播放模板
	public byte getM_mode() {
		return m_mode;
	}
	public void setM_mode(byte m_mode) {
		this.m_mode = m_mode;
	}
	public List<GJ_LoopADidlistcls> getM_Loop_adidlist() {
		return m_Loop_adidlist;
	}
	public void setM_Loop_adidlist(List<GJ_LoopADidlistcls> m_Loop_adidlist) {
		this.m_Loop_adidlist = m_Loop_adidlist;
	}
	public int getM_Template_cycle() {
		return m_Template_cycle;
	}
	public void setM_Template_cycle(int m_Template_cycle) {
		this.m_Template_cycle = m_Template_cycle;
	}
	public List<GJ_Templatecls> getM_Template_adlist() {
		return m_Template_adlist;
	}
	public void setM_Template_adlist(List<GJ_Templatecls> m_Template_adlist) {
		this.m_Template_adlist = m_Template_adlist;
	} 
     
}
