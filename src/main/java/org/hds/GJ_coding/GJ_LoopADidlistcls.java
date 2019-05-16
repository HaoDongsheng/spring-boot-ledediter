package org.hds.GJ_coding;

public class GJ_LoopADidlistcls {

	 /**
	 * 
	 */
	private int m_ADid;
     /// <summary>
     /// 广告id 2个字节
     /// </summary>
     public int getM_ADid() {
		return m_ADid;
	}


	public void setM_ADid(int m_ADid) {
		this.m_ADid = m_ADid;
	}


	private String m_ADidlifestart;
     /// <summary>
     /// 广告生命周期开始 3字节
     /// </summary>
     public String getM_ADidlifestart() {
		return m_ADidlifestart;
	}


	public void setM_ADidlifestart(String m_ADidlifestart) {
		this.m_ADidlifestart = m_ADidlifestart;
	}


	private String m_ADidlifeend;
     /// <summary>
     /// 广告生命周期结束 3字节
     /// </summary>
	public String getM_ADidlifeend() {
		return m_ADidlifeend;
	}


	public void setM_ADidlifeend(String m_ADidlifeend) {
		this.m_ADidlifeend = m_ADidlifeend;
	}


}
