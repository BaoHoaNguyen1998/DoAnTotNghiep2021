package com.iuh.ABCStore.model;

import java.math.BigDecimal;

public class TongTienStartEnd {

	private String start;
	private String end;
	private BigDecimal value;
	
	private double tong;

	public TongTienStartEnd() {
		super();
	}
	

	public double getTong() {
		return tong;
	}


	public void setTong(double tong) {
		this.tong = tong;
	}


	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MyItemsStartEnd [start=" + start + ", end=" + end + ", value=" + value + "]";
	}

}
