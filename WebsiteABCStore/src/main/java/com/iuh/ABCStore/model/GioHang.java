package com.iuh.ABCStore.model;

import java.io.Serializable;

public class GioHang implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2723921689194761760L;
	private SanPham sanPham;
	private int soLuong;
	/**
	 * @param sanPham
	 * @param soLuong
	 */
	public GioHang(SanPham sanPham, int soLuong) {
		super();
		this.sanPham = sanPham;
		this.soLuong = soLuong;
	}
	/**
	 * 
	 */
	public GioHang() {
		super();
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	@Override
	public String toString() {
		return "GioHang [sanPham=" + sanPham + ", soLuong=" + soLuong + "]";
	}
	
	

}
