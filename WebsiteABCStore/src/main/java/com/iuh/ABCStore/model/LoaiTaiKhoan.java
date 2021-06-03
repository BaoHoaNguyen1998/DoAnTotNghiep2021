package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity

public class LoaiTaiKhoan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1037701521539629249L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "ten_loai_tai_khoan")
	private String tenLoaiTaiKhoan;

	@JsonIgnore
	@ManyToMany(mappedBy = "loaiTaiKhoan")
	private Set<TaiKhoan> taiKhoan;

	@Override
	public String toString() {
		return "LoaiTaiKhoan [id=" + id + ", tenLoaiTaiKhoan=" + tenLoaiTaiKhoan + ", taiKhoan=" + taiKhoan + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenLoaiTaiKhoan() {
		return tenLoaiTaiKhoan;
	}

	public void setTenLoaiTaiKhoan(String tenLoaiTaiKhoan) {
		this.tenLoaiTaiKhoan = tenLoaiTaiKhoan;
	}

	public Set<TaiKhoan> getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(Set<TaiKhoan> taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}