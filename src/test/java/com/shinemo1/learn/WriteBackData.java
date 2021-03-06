package com.shinemo1.learn;

/**
 * 回写数据对象
 */
public class WriteBackData {
    private String sheetName;
    private String rowIdentifier;//行标识
    private String cellName;
    private String result;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getRowIdentifier() {
        return rowIdentifier;
    }

    public void setRowIdentifier(String rowIdentifier) {
        this.rowIdentifier = rowIdentifier;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public WriteBackData(String sheetName, String caseId, String cellName, String result) {
        this.sheetName = sheetName;
        this.rowIdentifier = caseId;
        this.cellName = cellName;
        this.result = result;
    }

    public WriteBackData() {
    }
}
