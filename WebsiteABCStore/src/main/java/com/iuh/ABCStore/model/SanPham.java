package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity

public class SanPham implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	private BigDecimal donGia;

	@Column(name = "ten_san_pham", columnDefinition = "NVARCHAR(255)")
	private String tenSanPham;
	@Column(name = "mo_ta", columnDefinition = "NVARCHAR(255)")
	private String moTa;
	@Column(name = "mo_ta_chi_tiet", columnDefinition = "TEXT")
	private String moTaChiTiet;

	private int namSanXuat;
	private long soLuongBan;
	private long soLuongKho;
	private LocalDate ngayTao;
	private String trangThai;


	private String hinhAnh;
	private String hinhAnh1;
	private String hinhAnh2;
	private String hinhAnh3;
	
	@OneToMany(mappedBy = "sanPham")
	private List<DanhGia> danhGias;

	@JsonIgnore
	@OneToMany(mappedBy = "sanPham",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<ChiTietHoaDon> chiTietHoaDons;

	private String thuongHieu;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "danh_muc_id", referencedColumnName = "id")
	private DanhMuc danhMuc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nguoi_dung_id", referencedColumnName = "id")
	private NguoiDung nguoiDung;


	

	public SanPham() {
		super();
	}

	@Override
	public String toString() {
		return "SanPham [tenSanPham=" + tenSanPham + ", hinhAnh=" + hinhAnh + " _ " + hinhAnh1 + " _ " + hinhAnh2
				+ " _ " + hinhAnh3 	+ ", danhMuc=" + danhMuc.getId() + "]";
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
		SanPham other = (SanPham) obj;
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

	public BigDecimal getDonGia() {
		return donGia;
	}

	public void setDonGia(BigDecimal donGia) {
		this.donGia = donGia;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getMoTaChiTiet() {
		return moTaChiTiet;
	}

	public void setMoTaChiTiet(String moTaChiTiet) {
		this.moTaChiTiet = moTaChiTiet;
	}

	public int getNamSanXuat() {
		return namSanXuat;
	}

	public void setNamSanXuat(int namSanXuat) {
		this.namSanXuat = namSanXuat;
	}

	public long getSoLuongBan() {
		return soLuongBan;
	}

	public void setSoLuongBan(long soLuongBan) {
		this.soLuongBan = soLuongBan;
	}

	public long getSoLuongKho() {
		return soLuongKho;
	}

	public void setSoLuongKho(long soLuongKho) {
		this.soLuongKho = soLuongKho;
	}

	public LocalDate getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(LocalDate ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public String getHinhAnh1() {
		return hinhAnh1;
	}

	public void setHinhAnh1(String hinhAnh1) {
		this.hinhAnh1 = hinhAnh1;
	}

	public String getHinhAnh2() {
		return hinhAnh2;
	}

	public void setHinhAnh2(String hinhAnh2) {
		this.hinhAnh2 = hinhAnh2;
	}

	public String getHinhAnh3() {
		return hinhAnh3;
	}

	public void setHinhAnh3(String hinhAnh3) {
		this.hinhAnh3 = hinhAnh3;
	}

	public List<DanhGia> getDanhGias() {
		return danhGias;
	}

	public void setDanhGias(List<DanhGia> danhGias) {
		this.danhGias = danhGias;
	}

	public List<ChiTietHoaDon> getChiTietHoaDons() {
		return chiTietHoaDons;
	}

	public void setChiTietHoaDons(List<ChiTietHoaDon> chiTietHoaDons) {
		this.chiTietHoaDons = chiTietHoaDons;
	}

	public String getThuongHieu() {
		return thuongHieu;
	}

	public void setThuongHieu(String thuongHieu) {
		this.thuongHieu = thuongHieu;
	}

	public DanhMuc getDanhMuc() {
		return danhMuc;
	}

	public void setDanhMuc(DanhMuc danhMuc) {
		this.danhMuc = danhMuc;
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
