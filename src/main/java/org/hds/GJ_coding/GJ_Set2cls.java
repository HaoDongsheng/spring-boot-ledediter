package org.hds.GJ_coding;

public class GJ_Set2cls
{
    private int id;
    /// <summary>
    /// set2id--1byte
    /// </summary>   

    private int version;
    /// <summary>
    /// set2版本号--1byte
    /// </summary>    

    private String DefaulText;
    /// <summary>
    /// 默认标语--8byte。
    /// </summary> 
    
    private String AlarmText;
    /// <summary>
    /// 报警标语--8byte。
    /// </summary>

    private int setZhaoMingMode;
    /// <summary>
    /// 设置照明灯方式--1byte 0：按设定时间开关。1：常关。2：常开。
    /// </summary>

    private String ZhaoMingTimeStart;
    /// <summary>
    /// 照明灯开启时间--2byte，时分
    /// </summary>

    private String ZhaoMingTimeEnd;
    /// <summary>
    /// 照明灯结束时间--2byte，时分
    /// </summary>


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

	public String get_DefaulText() {
		return DefaulText;
	}

	public void set_DefaulText(String DefaulText) {
		this.DefaulText = DefaulText;
	}

	public String get_AlarmText() {
		return AlarmText;
	}

	public void set_AlarmText(String AlarmText) {
		this.AlarmText = AlarmText;
	}

	public int get_setZhaoMingMode() {
		return setZhaoMingMode;
	}

	public void set_setZhaoMingMode(int setZhaoMingMode) {
		this.setZhaoMingMode = setZhaoMingMode;
	}

	public String get_ZhaoMingTimeStart() {
		return ZhaoMingTimeStart;
	}

	public void set_ZhaoMingTimeStart(String ZhaoMingTimeStart) {
		this.ZhaoMingTimeStart = ZhaoMingTimeStart;
	}

	public String get_ZhaoMingTimeEnd() {
		return ZhaoMingTimeEnd;
	}

	public void set_ZhaoMingTimeEnd(String ZhaoMingTimeEnd) {
		this.ZhaoMingTimeEnd = ZhaoMingTimeEnd;
	}	
}