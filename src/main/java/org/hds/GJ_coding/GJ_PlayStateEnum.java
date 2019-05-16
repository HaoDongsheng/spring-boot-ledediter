package org.hds.GJ_coding;

public enum GJ_PlayStateEnum {
	测试(0),默认(1),广告(2),报警(3),异常(255); 
	
	private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	GJ_PlayStateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static GJ_PlayStateEnum getEnum(int val) {
    	GJ_PlayStateEnum returnEnum =null;
        switch (val) {
		case 0:
			returnEnum = GJ_PlayStateEnum.测试;
			break;	
		case 1:
			returnEnum = GJ_PlayStateEnum.默认;
			break;
		case 2:
			returnEnum = GJ_PlayStateEnum.广告;
			break;
		case 3:
			returnEnum = GJ_PlayStateEnum.报警;
			break;
		case 255:
			returnEnum = GJ_PlayStateEnum.异常;
			break;
		}
        
        return returnEnum;
    }
}
