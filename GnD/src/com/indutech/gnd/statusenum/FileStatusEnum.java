package com.indutech.gnd.statusenum;

public enum FileStatusEnum {
	REJECT  (3),  //calls constructor with value 3
    APPROVED(2),  //calls constructor with value 2
    HOLD   (1)   //calls constructor with value 1
    ; // semicolon needed when fields / methods follow

    private final int fileStatusCode;

    FileStatusEnum(int fileStatusCode) {
        this.fileStatusCode = fileStatusCode;
    }
    
    public int getFileStatusCode() {
        return this.fileStatusCode;
    }
}
