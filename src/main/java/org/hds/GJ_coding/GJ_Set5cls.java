package org.hds.GJ_coding;

public class GJ_Set5cls
{
    private int id;
    /// <summary>
    /// set5id--1byte
    /// </summary>

    private int version;
    /// <summary>
    /// set5版本号--1byte
    /// </summary>

    private int LEDnumber;
    /// <summary>
    /// 屏号--2byte
    /// </summary>

    private int Terminaltype;
    /// <summary>
    /// 终端类型--2byte
    /// </summary>

    private String ESIM;
    /// <summary>
    /// ESIM（ascii）--20byte
    /// </summary>

    private String MCU;
    /// <summary>
    /// MCU号--12byte
    /// </summary> 
    
    private String DTU;
    /// <summary>
    /// DTU号--12byte
    /// </summary>

	public String get_DTU() {
		return DTU;
	}

	public void set_DTU(String dTU) {
		DTU = dTU;
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

	public int get_LEDnumber() {
		return LEDnumber;
	}

	public void set_LEDnumber(int LEDnumber) {
		this.LEDnumber = LEDnumber;
	}

	public int get_Terminaltype() {
		return Terminaltype;
	}

	public void set_Terminaltype(int Terminaltype) {
		this.Terminaltype = Terminaltype;
	}

	public String get_ESIM() {
		return ESIM;
	}

	public void set_ESIM(String ESIM) {
		this.ESIM = ESIM;
	}

	public String get_MCU() {
		return MCU;
	}

	public void set_MCU(String MCU) {
		this.MCU = MCU;
	}
}
