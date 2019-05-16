package org.hds.GJ_coding;
/// 指令代号
public enum GJ_CommandWordEnum {
	alst(0),qlst(1),aadv(2),qadv(3),dadv(4),qver(5),blak(6),ligt(7),qled(8),rled(9),setl(10),sstt(11),
    ddtt(12),alar(13),unar(14),updd(15),dele(16),powe(17),rest(18),rrtt(19),dlst(20),seto(21),qset(22),
    ooll(23),oobb(24),ccbb(25),PWON(26),TIME(27),ALAR(28),UALM(29),DRIV(30),QRIV(31),EVAL(32),QSTR(33),
    QVES(34),UPDG(35),qinf(36),set1(37),set2(38),set3(39),set4(40),set5(41),set6(42),qss1(43),qss2(44),
    qss3(45),qss4(46),qss5(47),qss6(48);
	
	private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	GJ_CommandWordEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static GJ_CommandWordEnum getEnum(String val) {
    	GJ_CommandWordEnum returnEnum =null;
        switch (val) {
		case "alst":
			returnEnum = GJ_CommandWordEnum.alst;
			break;
		case "qlst":
			returnEnum = GJ_CommandWordEnum.qlst;
			break;
		case "aadv":
			returnEnum = GJ_CommandWordEnum.aadv;
			break;
		case "qadv":
			returnEnum = GJ_CommandWordEnum.qadv;
			break;
		case "dadv":
			returnEnum = GJ_CommandWordEnum.dadv;
			break;
		case "qver":
			returnEnum = GJ_CommandWordEnum.qver;
			break;
		case "blak":
			returnEnum = GJ_CommandWordEnum.blak;
			break;
		case "ligt":
			returnEnum = GJ_CommandWordEnum.ligt;
			break;
		case "qled":
			returnEnum = GJ_CommandWordEnum.qled;
			break;
		case "rled":
			returnEnum = GJ_CommandWordEnum.rled;
			break;
		case "setl":
			returnEnum = GJ_CommandWordEnum.setl;
			break;
		case "sstt":
			returnEnum = GJ_CommandWordEnum.sstt;
			break;
		case "ddtt":
			returnEnum = GJ_CommandWordEnum.ddtt;
			break;
		case "alar":
			returnEnum = GJ_CommandWordEnum.alar;
			break;
		case "unar":
			returnEnum = GJ_CommandWordEnum.unar;
			break;
		case "updd":
			returnEnum = GJ_CommandWordEnum.updd;
			break;
		case "dele":
			returnEnum = GJ_CommandWordEnum.dele;
			break;
		case "powe":
			returnEnum = GJ_CommandWordEnum.powe;
			break;
		case "rest":
			returnEnum = GJ_CommandWordEnum.rest;
			break;
		case "rrtt":
			returnEnum = GJ_CommandWordEnum.rrtt;
			break;
		case "dlst":
			returnEnum = GJ_CommandWordEnum.dlst;
			break;
		case "seto":
			returnEnum = GJ_CommandWordEnum.seto;
			break;
		case "qset":
			returnEnum = GJ_CommandWordEnum.qset;
			break;
		case "ooll":
			returnEnum = GJ_CommandWordEnum.ooll;
			break;
		case "oobb":
			returnEnum = GJ_CommandWordEnum.oobb;
			break;
		case "ccbb":
			returnEnum = GJ_CommandWordEnum.ccbb;
			break;
		case "PWON":
			returnEnum = GJ_CommandWordEnum.PWON;
			break;
		case "TIME":
			returnEnum = GJ_CommandWordEnum.TIME;
			break;
		case "ALAR":
			returnEnum = GJ_CommandWordEnum.ALAR;
			break;
		case "UALM":
			returnEnum = GJ_CommandWordEnum.UALM;
			break;
		case "DRIV":
			returnEnum = GJ_CommandWordEnum.DRIV;
			break;
		case "QRIV":
			returnEnum = GJ_CommandWordEnum.QRIV;
			break;
		case "EVAL":
			returnEnum = GJ_CommandWordEnum.EVAL;
			break;
		case "QSTR":
			returnEnum = GJ_CommandWordEnum.QSTR;
			break;
		case "QVES":
			returnEnum = GJ_CommandWordEnum.QVES;
			break;
		case "UPDG":
			returnEnum = GJ_CommandWordEnum.UPDG;
			break;
		case "qinf":
			returnEnum = GJ_CommandWordEnum.qinf;
			break;
		case "set1":
			returnEnum = GJ_CommandWordEnum.set1;
			break;
		case "set2":
			returnEnum = GJ_CommandWordEnum.set2;
			break;
		case "set3":
			returnEnum = GJ_CommandWordEnum.set3;
			break;
		case "set4":
			returnEnum = GJ_CommandWordEnum.set4;
			break;
		case "set5":
			returnEnum = GJ_CommandWordEnum.set5;
			break;
		case "set6":
			returnEnum = GJ_CommandWordEnum.set6;
			break;
		case "qss1":
			returnEnum = GJ_CommandWordEnum.qss1;
			break;
		case "qss2":
			returnEnum = GJ_CommandWordEnum.qss2;
			break;
		case "qss3":
			returnEnum = GJ_CommandWordEnum.qss3;
			break;
		case "qss4":
			returnEnum = GJ_CommandWordEnum.qss4;
			break;
		case "qss5":
			returnEnum = GJ_CommandWordEnum.qss5;
			break;
	    case "qss6":
			returnEnum = GJ_CommandWordEnum.qss6;
			break;
		}		
        
        return returnEnum;
    }
}
