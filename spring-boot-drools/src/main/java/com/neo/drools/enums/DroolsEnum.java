package com.neo.drools.enums;

/**
 * @author 咚咚锵
 * @date 2021/9/3 下午2:20
 * @description 设置DROOLS加载路径
 */
public enum DroolsEnum {

    DROOLS_ENUM1_0("drools1_0", "drools/rules1_0/"),
    DROOLS_ENUM2_0("drools2_0", "drools/rules2_0/"),
    ;

    private String rulesName;
    private String rulesPath;

    DroolsEnum(String rulesName, String rulesPath) {
        this.rulesName = rulesName;
        this.rulesPath = rulesPath;
    }

    public String getRulesName() {
        return rulesName;
    }

    public void setRulesName(String rulesName) {
        this.rulesName = rulesName;
    }

    public String getRulesPath() {
        return rulesPath;
    }

    public void setRulesPath(String rulesPath) {
        this.rulesPath = rulesPath;
    }

}
