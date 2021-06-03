package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@IdClass(ChiTietTimKiemPK.class)

public class ChiTietTimKiem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private TimKiem timKiem;
	
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nguoi_dung_id", referencedColumnName = "id")
	private NguoiDung nguoiDung;
	
	
	private String trangThai;
	private int soLuong;
	
	private LocalDateTime thoiGianTimKiem;
	
	public ChiTietTimKiem() {
		super();
	}

	@Override
	public String toString() {
		return "ChiTietTimKiem [timkiem=" + timKiem.getId() + ", nguoiDung=" + nguoiDung.getId() + "]";
	}

	public TimKiem getTimKiem() {
		return timKiem;
	}

	public void setTimKiem(TimKiem timKiem) {
		this.timKiem = timKiem;
	}

	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public LocalDateTime getThoiGianTimKiem() {
		return thoiGianTimKiem;
	}

	public void setThoiGianTimKiem(LocalDateTime thoiGianTimKiem) {
		this.thoiGianTimKiem = thoiGianTimKiem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

	
}
