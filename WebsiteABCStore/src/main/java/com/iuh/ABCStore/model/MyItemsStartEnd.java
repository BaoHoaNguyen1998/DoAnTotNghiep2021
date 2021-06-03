package com.iuh.ABCStore.model;

public class MyItemsStartEnd {
	
	private String start;
	private String end;
    private Object value;
	public MyItemsStartEnd() {
		super();
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
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "MyItemsStartEnd [start=" + start + ", end=" + end + ", value=" + value + "]";
	}
	public int compareTo(long value1) {
		
		return 0;
	}
    
    

}
