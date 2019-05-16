package org.hds.GJ_coding;
/// 协议版本
public enum GJ_xieyiVersionEnum {	
	one(1);
	
	private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	GJ_xieyiVersionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static GJ_xieyiVersionEnum getEnum(int val) {
    	GJ_xieyiVersionEnum returnEnum =null;
        switch (val) {
		case 1:
			returnEnum = GJ_xieyiVersionEnum.one;
			break;

		default:
			returnEnum = GJ_xieyiVersionEnum.one;
			break;
		}
        
        return returnEnum;
    }
}
