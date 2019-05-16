package org.hds.GJ_coding;

public class GJ_LedDecodingCls {
    private GJ_xieyiTypeEnum mxieyiType;//协议类型
    private GJ_xieyiVersionEnum mxieyiVersion;//协议版本
    private int mpageall;//总包数
    private int mpage;//包数
    private byte mdataflows;//数据流向0x0A 下行，0x0B上行
    private byte mdatasource;//数据来源
    private GJ_CommandWordEnum mcommandword;//指令代号
    private byte mreceiveflag;//回信标志
    private String mledtime;//屏内时间
    private int mlednumber;//屏号
    private String mserialnumber;//流水号 "yyyy-MM-dd HH:mm:ss.fff"
    private int mdownlednumber;//指定接收屏号
    private int mreceiveidvalue;//回信id值
    private String mreceivestrvalue;//回信字符串值
    private int[] mreceivelistid;//查询播放列表返回值
    private GJ_ReceiveADlistcls[] mreceiveAdlist = new GJ_ReceiveADlistcls[] { };//查询广告列表返回值
    private GJ_WorkStatecls mWorkState = new GJ_WorkStatecls();//解析工作状态
    private GJ_SetLedcls mSetLed = new GJ_SetLedcls();//屏幕设置类
    private GJ_INFOlistcls mINFOlist = new GJ_INFOlistcls();//查询信息 广告播放列表+广告内容列表
    private GJ_Set1cls mSet1 = new GJ_Set1cls();
    private GJ_Set2cls mSet2 = new GJ_Set2cls();
    private GJ_Set3cls mSet3 = new GJ_Set3cls();
    private GJ_Set4cls mSet4 = new GJ_Set4cls();
    private GJ_Set5cls mSet5 = new GJ_Set5cls();
    private GJ_Set6cls mSet6 = new GJ_Set6cls();
	public GJ_xieyiTypeEnum getMxieyiType() {
		return mxieyiType;
	}
	public void setMxieyiType(GJ_xieyiTypeEnum mxieyiType) {
		this.mxieyiType = mxieyiType;
	}
	public GJ_xieyiVersionEnum getMxieyiVersion() {
		return mxieyiVersion;
	}
	public void setMxieyiVersion(GJ_xieyiVersionEnum mxieyiVersion) {
		this.mxieyiVersion = mxieyiVersion;
	}
	public int getMpageall() {
		return mpageall;
	}
	public void setMpageall(int mpageall) {
		this.mpageall = mpageall;
	}
	public int getMpage() {
		return mpage;
	}
	public void setMpage(int mpage) {
		this.mpage = mpage;
	}
	public byte getMdataflows() {
		return mdataflows;
	}
	public void setMdataflows(byte mdataflows) {
		this.mdataflows = mdataflows;
	}
	public byte getMdatasource() {
		return mdatasource;
	}
	public void setMdatasource(byte mdatasource) {
		this.mdatasource = mdatasource;
	}
	public GJ_CommandWordEnum getMcommandword() {
		return mcommandword;
	}
	public void setMcommandword(GJ_CommandWordEnum mcommandword) {
		this.mcommandword = mcommandword;
	}
	public byte getMreceiveflag() {
		return mreceiveflag;
	}
	public void setMreceiveflag(byte mreceiveflag) {
		this.mreceiveflag = mreceiveflag;
	}
	public String getMledtime() {
		return mledtime;
	}
	public void setMledtime(String mledtime) {
		this.mledtime = mledtime;
	}
	public int getMlednumber() {
		return mlednumber;
	}
	public void setMlednumber(int mlednumber) {
		this.mlednumber = mlednumber;
	}
	public String getMserialnumber() {
		return mserialnumber;
	}
	public void setMserialnumber(String mserialnumber) {
		this.mserialnumber = mserialnumber;
	}
	public int getMdownlednumber() {
		return mdownlednumber;
	}
	public void setMdownlednumber(int mdownlednumber) {
		this.mdownlednumber = mdownlednumber;
	}
	public int getMreceiveidvalue() {
		return mreceiveidvalue;
	}
	public void setMreceiveidvalue(int mreceiveidvalue) {
		this.mreceiveidvalue = mreceiveidvalue;
	}
	public String getMreceivestrvalue() {
		return mreceivestrvalue;
	}
	public void setMreceivestrvalue(String mreceivestrvalue) {
		this.mreceivestrvalue = mreceivestrvalue;
	}
	public int[] getMreceivelistid() {
		return mreceivelistid;
	}
	public void setMreceivelistid(int[] mreceivelistid) {
		this.mreceivelistid = mreceivelistid;
	}
	public GJ_ReceiveADlistcls[] getMreceiveAdlist() {
		return mreceiveAdlist;
	}
	public void setMreceiveAdlist(GJ_ReceiveADlistcls[] mreceiveAdlist) {
		this.mreceiveAdlist = mreceiveAdlist;
	}
	public GJ_WorkStatecls getmWorkState() {
		return mWorkState;
	}
	public void setmWorkState(GJ_WorkStatecls mWorkState) {
		this.mWorkState = mWorkState;
	}
	public GJ_SetLedcls getmSetLed() {
		return mSetLed;
	}
	public void setmSetLed(GJ_SetLedcls mSetLed) {
		this.mSetLed = mSetLed;
	}
	public GJ_INFOlistcls getmINFOlist() {
		return mINFOlist;
	}
	public void setmINFOlist(GJ_INFOlistcls mINFOlist) {
		this.mINFOlist = mINFOlist;
	}
	public GJ_Set1cls getmSet1() {
		return mSet1;
	}
	public void setmSet1(GJ_Set1cls mSet1) {
		this.mSet1 = mSet1;
	}
	public GJ_Set2cls getmSet2() {
		return mSet2;
	}
	public void setmSet2(GJ_Set2cls mSet2) {
		this.mSet2 = mSet2;
	}
	public GJ_Set3cls getmSet3() {
		return mSet3;
	}
	public void setmSet3(GJ_Set3cls mSet3) {
		this.mSet3 = mSet3;
	}
	public GJ_Set4cls getmSet4() {
		return mSet4;
	}
	public void setmSet4(GJ_Set4cls mSet4) {
		this.mSet4 = mSet4;
	}
	public GJ_Set5cls getmSet5() {
		return mSet5;
	}
	public void setmSet5(GJ_Set5cls mSet5) {
		this.mSet5 = mSet5;
	}
	public GJ_Set6cls getmSet6() {
		return mSet6;
	}
	public void setmSet6(GJ_Set6cls mSet6) {
		this.mSet6 = mSet6;
	}
}
