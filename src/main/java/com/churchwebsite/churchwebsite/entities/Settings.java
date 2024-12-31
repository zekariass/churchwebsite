package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int settingId;
    private String settingName;
    private String settingDescription;
    private int settingValueInt;
    private String settingValueChar;
    private double settingValueDouble;

    public Settings() {}

    public Settings(String settingName, String settingDescription, int settingValueInt, String settingValueChar, double settingValueDouble) {
        this.settingName = settingName;
        this.settingDescription = settingDescription;
        this.settingValueInt = settingValueInt;
        this.settingValueChar = settingValueChar;
        this.settingValueDouble = settingValueDouble;
    }

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingDescription() {
        return settingDescription;
    }

    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }

    public int getSettingValueInt() {
        return settingValueInt;
    }

    public void setSettingValueInt(int settingValueInt) {
        this.settingValueInt = settingValueInt;
    }

    public String getSettingValueChar() {
        return settingValueChar;
    }

    public void setSettingValueChar(String settingValueChar) {
        this.settingValueChar = settingValueChar;
    }

    public double getSettingValueDouble() {
        return settingValueDouble;
    }

    public void setSettingValueDouble(double settingValueDouble) {
        this.settingValueDouble = settingValueDouble;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settingId=" + settingId +
                ", settingName='" + settingName + '\'' +
                ", settingDescription='" + settingDescription + '\'' +
                ", settingValueInt='" + settingValueInt + '\'' +
                ", settingValueChar='" + settingValueChar + '\'' +
                ", settingValueChar='" + settingValueDouble + '\'' +
                '}';
    }
}
