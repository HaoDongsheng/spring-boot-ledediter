package org.hds.GJ_coding;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
//脚本类
public class GJ_ADscriptcls {
	private int m_adid;/// 广告id 4byte  
    private int m_crc;/// 脚本内容crc2byte   
    private byte m_adtype;/// 广告类型1byte   
    private int m_basemapwidth;/// 底图宽2byte    
    private int m_basemapheight;/// 底图高2byte    
    private List<BufferedImage> m_basemalist;/// 底图数据   
    private byte m_transparency;/// 透明度，对所有显示项有效。  
    private byte m_framemode;/// 边框样式    
    private byte m_framespeed;/// 边框速度   
    private byte m_HDRmode;/// 播放模式hdr    
    private int m_playtime;/// 播放播放时长   
    private List<GJ_ADitemcls> m_aditem = new ArrayList<GJ_ADitemcls>();/// 显示项集合
    private byte m_basemalistmode;

    
	public byte getM_basemalistmode() {
		return m_basemalistmode;
	}
	public void setM_basemalistmode(byte m_basemalistmode) {
		this.m_basemalistmode = m_basemalistmode;
	}
	public int getM_adid() {
		return m_adid;
	}
	public void setM_adid(int m_adid) {
		this.m_adid = m_adid;
	}
	public int getM_crc() {
		return m_crc;
	}
	public void setM_crc(int m_crc) {
		this.m_crc = m_crc;
	}
	public byte getM_adtype() {
		return m_adtype;
	}
	public void setM_adtype(byte m_adtype) {
		this.m_adtype = m_adtype;
	}
	public int getM_basemapwidth() {
		return m_basemapwidth;
	}
	public void setM_basemapwidth(int m_basemapwidth) {
		this.m_basemapwidth = m_basemapwidth;
	}
	public int getM_basemapheight() {
		return m_basemapheight;
	}
	public void setM_basemapheight(int m_basemapheight) {
		this.m_basemapheight = m_basemapheight;
	}
	public List<BufferedImage> getM_basemalist() {
		return m_basemalist;
	}
	public void setM_basemalist(List<BufferedImage> m_basemalist) {
		this.m_basemalist = m_basemalist;
	}
	public byte getM_transparency() {
		return m_transparency;
	}
	public void setM_transparency(byte m_transparency) {
		this.m_transparency = m_transparency;
	}
	public byte getM_framemode() {
		return m_framemode;
	}
	public void setM_framemode(byte m_framemode) {
		this.m_framemode = m_framemode;
	}
	public byte getM_framespeed() {
		return m_framespeed;
	}
	public void setM_framespeed(byte m_framespeed) {
		this.m_framespeed = m_framespeed;
	}
	public byte getM_HDRmode() {
		return m_HDRmode;
	}
	public void setM_HDRmode(byte m_HDRmode) {
		this.m_HDRmode = m_HDRmode;
	}
	public int getM_playtime() {
		return m_playtime;
	}
	public void setM_playtime(int m_playtime) {
		this.m_playtime = m_playtime;
	}
	public List<GJ_ADitemcls> getM_aditem() {
		return m_aditem;
	}
	public void setM_aditem(List<GJ_ADitemcls> m_aditem) {
		this.m_aditem = m_aditem;
	}   
    
}
