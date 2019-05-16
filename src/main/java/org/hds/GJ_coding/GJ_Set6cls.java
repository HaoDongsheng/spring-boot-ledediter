package org.hds.GJ_coding;

public class GJ_Set6cls
{
    private int id;
    /// <summary>
    /// set6id--1byte
    /// </summary>

    private int version;
    /// <summary>
    /// set6版本号--1byte
    /// </summary>

    private String carnumber;
    /// <summary>
    /// 车牌号--16byte
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

	public String get_carnumber() {
		return carnumber;
	}

	public void set_carnumber(String carnumber) {
		this.carnumber = carnumber;
	}

} 