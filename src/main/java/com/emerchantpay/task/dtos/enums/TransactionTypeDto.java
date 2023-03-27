package com.emerchantpay.task.dtos.enums;

public enum TransactionTypeDto {
	AUTHORIZE("authorize"),
	CHARGE("charge"),
	REFUND("refund"),
	REVERSAL("reversal");
	
	public final String label;

    private TransactionTypeDto(String label) {
        this.label = label;
    }
}
