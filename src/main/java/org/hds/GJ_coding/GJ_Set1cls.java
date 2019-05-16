package org.hds.GJ_coding;

public class GJ_Set1cls
{

    private int id;

    private int version;    

    private int Showversion;    

    private int LinkTime;    

    private int MaxReceiveLen;        

    private int BootstrapperWaitingTime;    

    private int test;    

    private int Energy;    

    private int DTULink;    
    
    /// <summary>
    /// set1id--1byte
    /// </summary>
    public int get_id() {
		return id;
	}
	public void set_id(int id) {
		this.id = id;
	}
	/// <summary>
    /// set1版本号--1byte
    /// </summary>
	public int get_version() {
		return version;
	}
	public void set_version(int version) {
		this.version = version;
	}
	/// <summary>
    /// 开机是否显示版本，非0显示版本。--1byte
    /// </summary>
	public int get_Showversion() {
		return Showversion;
	}
	public void set_Showversion(int Showversion) {
		this.Showversion = Showversion;
	}
	/// <summary>
    /// 上报状态心跳时间--1byte。
    /// </summary>
	public int get_LinkTime() {
		return LinkTime;
	}
	public void set_LinkTime(int LinkTime) {
		this.LinkTime = LinkTime;
	}
	/// <summary>
    /// 多包回信最大包长--2byte。
    /// </summary>
	public int get_MaxReceiveLen() {
		return MaxReceiveLen;
	}
	public void set_MaxReceiveLen(int MaxReceiveLen) {
		this.MaxReceiveLen = MaxReceiveLen;
	}
	/// <summary>
    /// 引导程序等待升级的超时时间(秒)--1byte
    /// </summary>
	public int get_BootstrapperWaitingTime() {
		return BootstrapperWaitingTime;
	}
	public void set_BootstrapperWaitingTime(int BootstrapperWaitingTime) {
		this.BootstrapperWaitingTime = BootstrapperWaitingTime;
	}
	/// <summary>
    /// 测试状态--1byte 置1测试状态
    /// </summary>
	public int get_test() {
		return test;
	}
	public void set_test(int test) {
		this.test = test;
	}
	/// <summary>
    /// 是否节能--1byte。0节能，1非节能
    /// </summary>
	public int get_Energy() {
		return Energy;
	}
	public void set_Energy(int Energy) {
		this.Energy = Energy;
	}
    /// <summary>
    /// DTU心跳--1byte。0节能，1非节能
    /// </summary>
	public int get_DTULink() {
		return DTULink;
	}
	public void set_DTULink(int DTULink) {
		this.DTULink = DTULink;
	}
}