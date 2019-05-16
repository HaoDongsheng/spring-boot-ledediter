package org.hds.GJ_coding;

public class GJ_SetLedcls {
	
    private String _Area_ProvinceName = "";//地区 省份
    private String _Area_CityName = "";//地区 城市名称
    private double _Area_Longitude;//地区 经度
    private double _Area_Latitude;//地区 纬度
    private String _LedModel = "";    

    private int m_version;
    /// <summary>
    /// 版本号--2byte
    /// </summary>

    private byte m_test;
    /// <summary>
    /// 测试状态--1byte 置1测试状态
    /// </summary>

    private int m_width;
    /// <summary>
    /// 宽--2byte
    /// </summary>

    private int m_height;
    /// <summary>
    /// 高--2byte
    /// </summary>

    private byte m_setBrightnessMode;
    /// <summary>
    /// 设置亮度调节方式，0固定亮度，1按时间调节亮度，2经纬度自动调节亮度，
    /// </summary>

    private byte m_fixedBrightnessValue;
    /// <summary>
    /// 固定亮度值--1byte  0-15级
    /// </summary>

    private String[] m_BrightnessTimeArray = new String[8];
    /// <summary>
    /// 按时间调节亮度的时间点集合，精确到分，（HH*60+MM）/ 10 --8byte
    /// </summary>

    private byte[] m_BrightnessValueArray = new byte[8];
    /// <summary>
    /// 按时间调节亮度的亮度值集合。--8byte
    /// </summary>

    private byte m_BootstrapperWaitingTime;
    /// <summary>
    /// 引导程序等待升级的超时时间(秒)--1byte
    /// </summary>

    private byte m_setZhaoMingMode;
    /// <summary>
    /// 设置照明灯方式--1byte 0：按设定时间开关。1：常关。2：常开。
    /// </summary>

    private String m_ZhaoMingTimeStart;
    /// <summary>
    /// 照明灯开启时间--2byte，时分
    /// </summary>

    private String m_ZhaoMingTimeEnd;
    /// <summary>
    /// 照明灯结束时间--2byte，时分
    /// </summary>

    private int m_LinkTime;
    /// <summary>
    /// 心跳时间--2byte。
    /// </summary>

    private int m_ReceiveDelay;
    /// <summary>
    /// 多包回信间隔--2byte。单位毫秒
    /// </summary>
    
    private int m_MaxReceiveLen;
    /// <summary>
    /// 多包回信最大包长--2byte。
    /// </summary>

    private String m_DefaulText;
    /// <summary>
    /// 默认标语--8byte。
    /// </summary>

    private String m_AlarmText;
    /// <summary>
    /// 报警标语--8byte。
    /// </summary>

    private byte m_Energy;
    /// <summary>
    /// 是否节能--1byte。0节能，1非节能
    /// </summary>

    private byte m_HDR;
    /// <summary>
    /// HDR模式--1byte
    /// </summary>    

    private byte m_Showversion;
    /// <summary>
    /// 开机是否显示版本，非0显示版本。--1byte
    /// </summary>    

    private int m_Flag;
    /// <summary>
    /// 屏参id
    /// </summary>    

	public String get_Area_ProvinceName() {
		return _Area_ProvinceName;
	}

	public void set_Area_ProvinceName(String _Area_ProvinceName) {
		this._Area_ProvinceName = _Area_ProvinceName;
	}

	public String get_Area_CityName() {
		return _Area_CityName;
	}

	public void set_Area_CityName(String _Area_CityName) {
		this._Area_CityName = _Area_CityName;
	}

	public double get_Area_Longitude() {
		return _Area_Longitude;
	}

	public void set_Area_Longitude(double _Area_Longitude) {
		this._Area_Longitude = _Area_Longitude;
	}

	public double get_Area_Latitude() {
		return _Area_Latitude;
	}

	public void set_Area_Latitude(double _Area_Latitude) {
		this._Area_Latitude = _Area_Latitude;
	}

	public String get_LedModel() {
		return _LedModel;
	}

	public void set_LedModel(String _LedModel) {
		this._LedModel = _LedModel;
	}

	public int getM_version() {
		return m_version;
	}

	public void setM_version(int m_version) {
		this.m_version = m_version;
	}

	public byte getM_test() {
		return m_test;
	}

	public void setM_test(byte m_test) {
		this.m_test = m_test;
	}

	public int getM_width() {
		return m_width;
	}

	public void setM_width(int m_width) {
		this.m_width = m_width;
	}

	public int getM_height() {
		return m_height;
	}

	public void setM_height(int m_height) {
		this.m_height = m_height;
	}

	public byte getM_setBrightnessMode() {
		return m_setBrightnessMode;
	}

	public void setM_setBrightnessMode(byte m_setBrightnessMode) {
		this.m_setBrightnessMode = m_setBrightnessMode;
	}

	public byte getM_fixedBrightnessValue() {
		return m_fixedBrightnessValue;
	}

	public void setM_fixedBrightnessValue(byte m_fixedBrightnessValue) {
		this.m_fixedBrightnessValue = m_fixedBrightnessValue;
	}

	public String[] getM_BrightnessTimeArray() {
		return m_BrightnessTimeArray;
	}

	public void setM_BrightnessTimeArray(String[] m_BrightnessTimeArray) {
		this.m_BrightnessTimeArray = m_BrightnessTimeArray;
	}

	public byte[] getM_BrightnessValueArray() {
		return m_BrightnessValueArray;
	}

	public void setM_BrightnessValueArray(byte[] m_BrightnessValueArray) {
		this.m_BrightnessValueArray = m_BrightnessValueArray;
	}

	public byte getM_BootstrapperWaitingTime() {
		return m_BootstrapperWaitingTime;
	}

	public void setM_BootstrapperWaitingTime(byte m_BootstrapperWaitingTime) {
		this.m_BootstrapperWaitingTime = m_BootstrapperWaitingTime;
	}

	public byte getM_setZhaoMingMode() {
		return m_setZhaoMingMode;
	}

	public void setM_setZhaoMingMode(byte m_setZhaoMingMode) {
		this.m_setZhaoMingMode = m_setZhaoMingMode;
	}

	public String getM_ZhaoMingTimeStart() {
		return m_ZhaoMingTimeStart;
	}

	public void setM_ZhaoMingTimeStart(String m_ZhaoMingTimeStart) {
		this.m_ZhaoMingTimeStart = m_ZhaoMingTimeStart;
	}

	public String getM_ZhaoMingTimeEnd() {
		return m_ZhaoMingTimeEnd;
	}

	public void setM_ZhaoMingTimeEnd(String m_ZhaoMingTimeEnd) {
		this.m_ZhaoMingTimeEnd = m_ZhaoMingTimeEnd;
	}

	public int getM_LinkTime() {
		return m_LinkTime;
	}

	public void setM_LinkTime(int m_LinkTime) {
		this.m_LinkTime = m_LinkTime;
	}

	public int getM_ReceiveDelay() {
		return m_ReceiveDelay;
	}

	public void setM_ReceiveDelay(int m_ReceiveDelay) {
		this.m_ReceiveDelay = m_ReceiveDelay;
	}

	public int getM_MaxReceiveLen() {
		return m_MaxReceiveLen;
	}

	public void setM_MaxReceiveLen(int m_MaxReceiveLen) {
		this.m_MaxReceiveLen = m_MaxReceiveLen;
	}

	public String getM_DefaulText() {
		return m_DefaulText;
	}

	public void setM_DefaulText(String m_DefaulText) {
		this.m_DefaulText = m_DefaulText;
	}

	public String getM_AlarmText() {
		return m_AlarmText;
	}

	public void setM_AlarmText(String m_AlarmText) {
		this.m_AlarmText = m_AlarmText;
	}

	public byte getM_Energy() {
		return m_Energy;
	}

	public void setM_Energy(byte m_Energy) {
		this.m_Energy = m_Energy;
	}

	public byte getM_HDR() {
		return m_HDR;
	}

	public void setM_HDR(byte m_HDR) {
		this.m_HDR = m_HDR;
	}

	public byte getM_Showversion() {
		return m_Showversion;
	}

	public void setM_Showversion(byte m_Showversion) {
		this.m_Showversion = m_Showversion;
	}

	public int getM_Flag() {
		return m_Flag;
	}

	public void setM_Flag(int m_Flag) {
		this.m_Flag = m_Flag;
	}
}
