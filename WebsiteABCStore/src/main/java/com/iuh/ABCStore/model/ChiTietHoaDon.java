package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@IdClass(ChiTietHoaDonPK.class)

public class ChiTietHoaDon implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hoa_don_id", referencedColumnName = "id")
	private HoaDon hoaDon;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "san_pham_id", referencedColumnName = "id")
	private SanPham sanPham;

	private BigDecimal tongTien;
	private int soLuong;

	public ChiTietHoaDon() {
		super();
	}

	public ChiTietHoaDon(HoaDon hoaDon,int soLuong, SanPham sanPham) {
		super();
		this.tongTien = sanPham.getDonGia().multiply(BigDecimal.valueOf(soLuong));
		this.soLuong = soLuong;
		this.sanPham = sanPham;
		this.hoaDon = hoaDon;
	}

	public ChiTietHoaDon(HoaDon hoaDon, SanPham sanPham, BigDecimal tongTien, int soLuong) {
		super();
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
		this.tongTien = tongTien;
		this.soLuong = soLuong;
	}

	public BigDecimal getTongTienChiTietHoaDon() {
		return BigDecimal.valueOf(soLuong).multiply(tongTien);
	}
	
	@Override
	public String toString() {
		return "ChiTietHoaDon [hoaDon=" + hoaDon.getId() + ", sanPham=" + sanPham.getTenSanPham() + ", donGia=" + tongTien + ", soLuong="
				+ soLuong + "]";
	}

	/**
	 * @param sanPham
	 * @param tongTien
	 * @param soLuong
	 */
	public ChiTietHoaDon(SanPham sanPham, BigDecimal tongTien, int soLuong) {
		super();
		this.sanPham = sanPham;
		this.tongTien = tongTien;
		this.soLuong = soLuong;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hoaDon == null) ? 0 : hoaDon.hashCode());
		result = prime * result + ((sanPham == null) ? 0 : sanPham.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ChiTietHoaDon))
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		if (hoaDon == null) {
			if (other.hoaDon != null)
				return false;
		} else if (!hoaDon.equals(other.hoaDon))
			return false;
		if (sanPham == null) {
			if (other.sanPham != null)
				return false;
		} else if (!sanPham.equals(other.sanPham))
			return false;
		return true;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
