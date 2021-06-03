package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity

public class NguoiDung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name ="id")
	private String id;

	@Column(name ="ho_ten",columnDefinition = "NVARCHAR(255)")
	private String hoTen;
	
	@Column(name ="ten_shop",columnDefinition = "NVARCHAR(256)")
	private String tenShop;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate ngaySinh;
	
	@JsonIgnore
	@OneToOne(mappedBy = "nguoiDung", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private TaiKhoan taiKhoan;

	@JsonIgnore
	@OneToMany(mappedBy = "nguoiDung",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<HoaDon> danhSachHoaDon;
	
	@JsonIgnore
	@OneToMany(mappedBy = "nguoiDung",cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
	private Set<SanPham> danhSachSPBan;
	
	@JsonIgnore
	@OneToMany(mappedBy = "nguoiDung",cascade = CascadeType.ALL)
	private Set<DiaChi> danhSachLienHe;
	
	@JsonIgnore
	@OneToMany(mappedBy = "nguoiDung",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ChiTietTimKiem> chiTietTimKiem;
	
	

	public NguoiDung() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NguoiDung(String id) {
		super();
		this.id = id;
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
		NguoiDung other = (NguoiDung) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NguoiDung [id=" + id + ", chiTietTimKiem=" + chiTietTimKiem + "]";
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

	public String getTenShop() {
		return tenShop;
	}

	public void setTenShop(String tenShop) {
		this.tenShop = tenShop;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public Set<HoaDon> getDanhSachHoaDon() {
		return danhSachHoaDon;
	}

	public void setDanhSachHoaDon(Set<HoaDon> danhSachHoaDon) {
		this.danhSachHoaDon = danhSachHoaDon;
	}

	public Set<SanPham> getDanhSachSPBan() {
		return danhSachSPBan;
	}

	public void setDanhSachSPBan(Set<SanPham> danhSachSPBan) {
		this.danhSachSPBan = danhSachSPBan;
	}

	public Set<DiaChi> getDanhSachLienHe() {
		return danhSachLienHe;
	}

	public void setDanhSachLienHe(Set<DiaChi> danhSachLienHe) {
		this.danhSachLienHe = danhSachLienHe;
	}

	public List<ChiTietTimKiem> getChiTietTimKiem() {
		return chiTietTimKiem;
	}

	public void setChiTietTimKiem(List<ChiTietTimKiem> chiTietTimKiem) {
		this.chiTietTimKiem = chiTietTimKiem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	

	
	

	

	

	

}
