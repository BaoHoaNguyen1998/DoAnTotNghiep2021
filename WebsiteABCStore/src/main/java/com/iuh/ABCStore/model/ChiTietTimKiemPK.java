package com.iuh.ABCStore.model;

import java.io.Serializable;

import javax.persistence.Embeddable;



@Embeddable

public class ChiTietTimKiemPK implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String timKiem;
	private String nguoiDung;
	public ChiTietTimKiemPK(String timKiem, String nguoiDung) {
		super();
		this.timKiem = timKiem;
		this.nguoiDung = nguoiDung;
	}
	public ChiTietTimKiemPK() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nguoiDung == null) ? 0 : nguoiDung.hashCode());
		result = prime * result + ((timKiem == null) ? 0 : timKiem.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietTimKiemPK other = (ChiTietTimKiemPK) obj;
		if (nguoiDung == null) {
			if (other.nguoiDung != null)
				return false;
		} else if (!nguoiDung.equals(other.nguoiDung))
			return false;
		if (timKiem == null) {
			if (other.timKiem != null)
				return false;
		} else if (!timKiem.equals(other.timKiem))
			return false;
		return true;
	}
	public String getTimKiem() {
		return timKiem;
	}
	public void setTimKiem(String timKiem) {
		this.timKiem = timKiem;
	}
	public String getNguoiDung() {
		return nguoiDung;
	}
	public void setNguoiDung(String nguoiDung) {
		this.nguoiDung = nguoiDung;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
