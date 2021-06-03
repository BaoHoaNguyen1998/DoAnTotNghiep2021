package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity

public class TaiKhoan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;
	
	private String tenTaiKhoanEmail;
	
	private String matKhau;
	
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "nguoi_dung_id", referencedColumnName = "id")
	private NguoiDung nguoiDung;

	@ManyToMany
	private Set<LoaiTaiKhoan> loaiTaiKhoan;

	private String xacNhanEmail;

	@Temporal(TemporalType.DATE)
	private Date ngayDangKy;

	private boolean trangThai;

	public TaiKhoan() {
		super();
	}

	public TaiKhoan(String tenTaiKhoanEmail, String matKhau) {
		super();
		this.tenTaiKhoanEmail = tenTaiKhoanEmail;
		this.matKhau = matKhau;
	}

	@Override
	public String toString() {
		return "TaiKhoan [id=" + id + ", tenTaiKhoanEmail=" + tenTaiKhoanEmail + ", matKhau=" + matKhau
				+ ", xacNhanEmail=" + xacNhanEmail + ", ngayDangKy=" + ngayDangKy + ", trangThai=" + trangThai + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenTaiKhoanEmail() {
		return tenTaiKhoanEmail;
	}

	public void setTenTaiKhoanEmail(String tenTaiKhoanEmail) {
		this.tenTaiKhoanEmail = tenTaiKhoanEmail;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}

	public Set<LoaiTaiKhoan> getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(Set<LoaiTaiKhoan> loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public String getXacNhanEmail() {
		return xacNhanEmail;
	}

	public void setXacNhanEmail(String xacNhanEmail) {
		this.xacNhanEmail = xacNhanEmail;
	}

	public Date getNgayDangKy() {
		return ngayDangKy;
	}

	public void setNgayDangKy(Date ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		if (!(obj instanceof TaiKhoan))
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	

}
