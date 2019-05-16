package org.hds.GJ_coding;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
//显示项类
public class GJ_ADitemcls {
	 private byte m_type;/// 显示项类型  
     private Color m_fontforecolor;/// 字库文字前景色  
     private Color m_fontbackcolor;/// 字库文字背景色
     private byte m_fontforetransparency;/// 字库文字前景色透明度
     private byte m_fontbacktransparency;/// 字库文字背景色透明度
     private byte m_fontno;/// 字库号
     private int m_X1;/// 坐标x1
     private int m_Y1;/// 坐标x2
     private int m_X2;/// 坐标y1
     private int m_Y2;/// 坐标y2
     private byte m_linkmove;/// 是否联动 1,与下一个显示项一起播放。
     private byte m_playtype;/// 显示方式。
     private byte m_playspeed;/// 播放速度。
     private byte m_rollstop;/// 左滚停留方式 0,不停留，1每屏一停留。
     private double m_stoptime;/// 停留时间，精确到小数点后2位，单位是秒。（屏里4个字节。单位是毫秒）
     private byte m_looptime; /// 循环次数
     private byte m_Gamma;
     private byte[] m_GIFdata;
     private int m_rollwidth;
     private int m_rolltimelenght;
     
    public byte[] getM_GIFdata() {
		return m_GIFdata;
	}
	public void setM_GIFdata(byte[] m_GIFdata) {
		this.m_GIFdata = m_GIFdata;
	}
	public byte getM_Gamma() {
		return m_Gamma;
	}
	public void setM_Gamma(byte m_Gamma) {
		this.m_Gamma = m_Gamma;
	}
	
	private List<GJ_PictureAndTextcls> m_texts = new ArrayList<GJ_PictureAndTextcls>();/// 图文内容
	public byte getM_type() {
		return m_type;
	}
	public void setM_type(byte m_type) {
		this.m_type = m_type;
	}
	public Color getM_fontforecolor() {
		return m_fontforecolor;
	}
	public void setM_fontforecolor(Color m_fontforecolor) {
		this.m_fontforecolor = m_fontforecolor;
	}
	public Color getM_fontbackcolor() {
		return m_fontbackcolor;
	}
	public void setM_fontbackcolor(Color m_fontbackcolor) {
		this.m_fontbackcolor = m_fontbackcolor;
	}
	public byte getM_fontforetransparency() {
		return m_fontforetransparency;
	}
	public void setM_fontforetransparency(byte m_fontforetransparency) {
		this.m_fontforetransparency = m_fontforetransparency;
	}
	public byte getM_fontbacktransparency() {
		return m_fontbacktransparency;
	}
	public void setM_fontbacktransparency(byte m_fontbacktransparency) {
		this.m_fontbacktransparency = m_fontbacktransparency;
	}
	public byte getM_fontno() {
		return m_fontno;
	}
	public void setM_fontno(byte m_fontno) {
		this.m_fontno = m_fontno;
	}
	public int getM_X1() {
		return m_X1;
	}
	public void setM_X1(int m_X1) {
		this.m_X1 = m_X1;
	}
	public int getM_Y1() {
		return m_Y1;
	}
	public void setM_Y1(int m_Y1) {
		this.m_Y1 = m_Y1;
	}
	public int getM_X2() {
		return m_X2;
	}
	public void setM_X2(int m_X2) {
		this.m_X2 = m_X2;
	}
	public int getM_Y2() {
		return m_Y2;
	}
	public void setM_Y2(int m_Y2) {
		this.m_Y2 = m_Y2;
	}
	public byte getM_linkmove() {
		return m_linkmove;
	}
	public void setM_linkmove(byte m_linkmove) {
		this.m_linkmove = m_linkmove;
	}
	public byte getM_playtype() {
		return m_playtype;
	}
	public void setM_playtype(byte m_playtype) {
		this.m_playtype = m_playtype;
	}
	public byte getM_playspeed() {
		return m_playspeed;
	}
	public void setM_playspeed(byte m_playspeed) {
		this.m_playspeed = m_playspeed;
	}
	public byte getM_rollstop() {
		return m_rollstop;
	}
	public void setM_rollstop(byte m_rollstop) {
		this.m_rollstop = m_rollstop;
	}
	public double getM_stoptime() {
		return m_stoptime;
	}
	public void setM_stoptime(double m_stoptime) {
		this.m_stoptime = m_stoptime;
	}
	public byte getM_looptime() {
		return m_looptime;
	}
	public void setM_looptime(byte m_looptime) {
		this.m_looptime = m_looptime;
	}
	public List<GJ_PictureAndTextcls> getM_texts() {
		return m_texts;
	}
	public void setM_texts(List<GJ_PictureAndTextcls> m_texts) {
		this.m_texts = m_texts;
	}
	public int getM_rollwidth() {
		return m_rollwidth;
	}
	public void setM_rollwidth(int m_rollwidth) {
		this.m_rollwidth = m_rollwidth;
	}
	public int getM_rolltimelenght() {
		return m_rolltimelenght;
	}
	public void setM_rolltimelenght(int m_rolltimelenght) {
		this.m_rolltimelenght = m_rolltimelenght;
	} 
     
}
