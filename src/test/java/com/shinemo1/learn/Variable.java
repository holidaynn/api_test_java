package com.shinemo1.learn;

public class Variable {
    private String name;
    private String value;
    public String reflactClass;
    public String reflactMethod;
    public String reflactValue;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReflactClass() {
        return reflactClass;
    }

    public void setReflactClass(String reflactClass) {
        this.reflactClass = reflactClass;
    }

    public String getReflactMethod() {
        return reflactMethod;
    }

    public void setReflactMethod(String reflactMethod) {
        this.reflactMethod = reflactMethod;
    }

    public String getReflactValue() {
        return reflactValue;
    }

    public void setReflactValue(String reflactValue) {
        this.reflactValue = reflactValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", reflactClass='" + reflactClass + '\'' +
                ", reflactMethod='" + reflactMethod + '\'' +
                ", reflactValue='" + reflactValue + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
