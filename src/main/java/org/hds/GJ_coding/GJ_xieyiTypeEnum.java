package org.hds.GJ_coding;
/// 协议类型
public enum GJ_xieyiTypeEnum {	
	GJ(1);
	
	private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	GJ_xieyiTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static GJ_xieyiTypeEnum getEnum(int val) {
    	GJ_xieyiTypeEnum returnEnum =null;
        switch (val) {
		case 1:
			returnEnum = GJ_xieyiTypeEnum.GJ;
			break;

		default:
			returnEnum = GJ_xieyiTypeEnum.GJ;
			break;
		}
        
        return returnEnum;
    }
}
