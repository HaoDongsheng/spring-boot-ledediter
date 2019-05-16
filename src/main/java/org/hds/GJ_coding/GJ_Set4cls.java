package org.hds.GJ_coding;

public class GJ_Set4cls
{
    private int id;
    /// <summary>
    /// set4id--1byte
    /// </summary>

    private int version;
    /// <summary>
    /// set4版本号--1byte
    /// </summary>

    private int width;
    /// <summary>
    /// 宽--2byte
    /// </summary>

    private int height;
    /// <summary>
    /// 高--2byte
    /// </summary>

    private String projectnumber;
    /// <summary>
    /// 项目编号--20byte
    /// </summary>

    private String adserverip;
    /// <summary>
    /// ad广告服务器ip--20byte
    /// </summary>

    private String adserverport;
    /// <summary>
    /// ad广告服务器端口--6byte
    /// </summary>

    private String gpsserverip;
    /// <summary>
    /// gps服务器ip--20byte
    /// </summary>

    private String gpsserverport;
    /// <summary>
    /// gps服务器端口--6byte
    /// </summary>

    private int gpsmode;
    /// <summary>
    /// GPS登陆方式 0-tcp,1-udp
    /// </summary>

    private String APN = "";
    /// <summary>
    /// apn--15byte
    /// </summary>

    private String APNname;
    /// <summary>
    /// apn名称--15byte
    /// </summary>

    private String APNpassword;
    /// <summary>
    /// apn密码--15byte
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

	public int get_width() {
		return width;
	}

	public void set_width(int width) {
		this.width = width;
	}

	public int get_height() {
		return height;
	}

	public void set_height(int height) {
		this.height = height;
	}

	public String get_projectnumber() {
		return projectnumber;
	}

	public void set_projectnumber(String projectnumber) {
		this.projectnumber = projectnumber;
	}

	public String get_adserverip() {
		return adserverip;
	}

	public void set_adserverip(String adserverip) {
		this.adserverip = adserverip;
	}

	public String get_adserverport() {
		return adserverport;
	}

	public void set_adserverport(String adserverport) {
		this.adserverport = adserverport;
	}

	public String get_gpsserverip() {
		return gpsserverip;
	}

	public void set_gpsserverip(String gpsserverip) {
		this.gpsserverip = gpsserverip;
	}

	public String get_gpsserverport() {
		return gpsserverport;
	}

	public void set_gpsserverport(String gpsserverport) {
		this.gpsserverport = gpsserverport;
	}

	public int get_gpsmode() {
		return gpsmode;
	}

	public void set_gpsmode(int gpsmode) {
		this.gpsmode = gpsmode;
	}

	public String get_APN() {
		return APN;
	}

	public void set_APN(String APN) {
		this.APN = APN;
	}

	public String get_APNname() {
		return APNname;
	}

	public void set_APNname(String APNname) {
		this.APNname = APNname;
	}

	public String get_APNpassword() {
		return APNpassword;
	}

	public void set_APNpassword(String APNpassword) {
		this.APNpassword = APNpassword;
	}
}
