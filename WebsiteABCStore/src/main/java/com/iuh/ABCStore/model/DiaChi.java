package com.iuh.ABCStore.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;



@Entity

public class DiaChi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name ="id")
	private String id;

	@Column(name = "ho_ten", columnDefinition = "NVARCHAR(60)")
	private String hoTen;
	@Column(name = "so_dien_thoai", columnDefinition = "NVARCHAR(11)")
	private String soDienThoai;
	@Column(name = "dia_chi", columnDefinition = "TEXT")
	private String diaChi;
	private boolean trangThai;
	
	
	@ManyToOne
	@JoinColumn(name = "nguoi_dung_id" ,referencedColumnName="id")
	private NguoiDung nguoiDung;



	@Override
	public String toString() {
		return "LienHe [id=" + id + ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + ", trangThai=" + trangThai
				+ "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DiaChi other = (DiaChi) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getHoTen() {
		return hoTen;
	}



	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}



	public String getSoDienThoai() {
		return soDienThoai;
	}



	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}



	public String getDiaChi() {
		return diaChi;
	}



	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}



	public boolean isTrangThai() {
		return trangThai;
	}



	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}



	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}



	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	

}
