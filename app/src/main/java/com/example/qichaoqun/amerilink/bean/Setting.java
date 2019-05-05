package com.example.qichaoqun.amerilink.bean;

/**
 * @author qichaoqun
 * @date 2018/10/7
 */
public class Setting {
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public Setting(String flag, String setting) {
        this.flag = flag;
        this.setting = setting;
    }

    private String flag;
    private String setting;
}
