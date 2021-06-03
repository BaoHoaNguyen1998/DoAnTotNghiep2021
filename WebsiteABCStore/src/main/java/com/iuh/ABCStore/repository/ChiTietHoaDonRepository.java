package com.iuh.ABCStore.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.SanPham;

public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon,String>{
//	select c.hoa_don_id,c.san_pham_id,c.tong_tien,
//	h.ngay_lap,s.nguoi_dung_id from
//	chi_tiet_hoa_don c join hoa_don h on c.hoa_don_id = h.id join san_pham s 
//	on c.san_pham_id = s.id;
	@Query("select c from ChiTietHoaDon c join HoaDon h on c.hoaDon = h.id join SanPham s on c.sanPham = s.id where h.ngayLap BETWEEN :start AND :end")
	public List<ChiTietHoaDon> findChiTiet(@Param("start")LocalDateTime start,@Param("end")LocalDateTime end);
	
	List<ChiTietHoaDon> findAllByHoaDon(HoaDon hoadon);
	
	ChiTietHoaDon findByHoaDonAndSanPham(HoaDon hd, SanPham sp);
}
