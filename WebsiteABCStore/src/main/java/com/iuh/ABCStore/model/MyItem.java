package com.iuh.ABCStore.model;

public class MyItem {
	
	 private String time;
	    private Object value;

	    public String getTime() {
	        return time;
	    }

	    public void setTime(String time) {
	        this.time = time;
	    }

	    public Object getValue() {
	        return value;
	    }

	    public void setValue(Object value) {
	        this.value = value;
	    }

		@Override
		public String toString() {
			return "MyItem [time=" + time + ", value=" + value + "]";
		}

	    

}
