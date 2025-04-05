package com.inventory.dto;

public class ResponseOutDto {
	 private String msg ;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResponseOutDto(String msg) {
		super();
		this.msg = msg;
	}
	 
}

