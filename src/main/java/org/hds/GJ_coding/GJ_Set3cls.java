package org.hds.GJ_coding;

public class GJ_Set3cls
{   
    private String Area_ProvinceName = "";//地区 省份
    private String Area_CityName = "";//地区 城市名称
    private double Area_Longitude;//地区 经度
    private double Area_Latitude;//地区 纬度
    private int SetBrightnessMode;//亮度调节方式:0-固定亮度；1-定时调节；2-自动时间亮度        

    private int id;
    /// <summary>
    /// set3id--1byte
    /// </summary>

    private int version;
    /// <summary>
    /// set3版本号--1byte
    /// </summary>

    public String get_Area_ProvinceName() {
		return Area_ProvinceName;
	}

	public void set_Area_ProvinceName(String Area_ProvinceName) {
		this.Area_ProvinceName = Area_ProvinceName;
	}

	public String get_Area_CityName() {
		return Area_CityName;
	}

	public void set_Area_CityName(String Area_CityName) {
		this.Area_CityName = Area_CityName;
	}

	public double get_Area_Longitude() {
		return Area_Longitude;
	}

	public void set_Area_Longitude(double Area_Longitude) {
		this.Area_Longitude = Area_Longitude;
	}

	public double get_Area_Latitude() {
		return Area_Latitude;
	}

	public void set_Area_Latitude(double Area_Latitude) {
		this.Area_Latitude = Area_Latitude;
	}

	public int get_SetBrightnessMode() {
		return SetBrightnessMode;
	}

	public void set_SetBrightnessMode(int SetBrightnessMode) {
		this.SetBrightnessMode = SetBrightnessMode;
	}

	public int get_id() {
		return id;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public int get_version() {
		return version;
	}

	public void set_version(int version) {
		this.version = version;
	}

	public String[] get_BrightnessTimeArray() {
		return BrightnessTimeArray;
	}

	public void set_BrightnessTimeArray(String[] BrightnessTimeArray) {
		this.BrightnessTimeArray = BrightnessTimeArray;
	}

	public byte[] get_BrightnessValueArray() {
		return BrightnessValueArray;
	}

	public void set_BrightnessValueArray(byte[] BrightnessValueArray) {
		this.BrightnessValueArray = BrightnessValueArray;
	}

	private String[] BrightnessTimeArray = new String[8];
    /// <summary>
    /// 按时间调节亮度的时间点集合，精确到分，（HH*60+MM）/ 10 --8byte
    /// </summary>

    private byte[] BrightnessValueArray = new byte[8];
    /// <summary>
    /// 按时间调节亮度的亮度值集合。--8byte
    /// </summary>

}
