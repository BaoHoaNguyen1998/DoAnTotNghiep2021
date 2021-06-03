package com.iuh.ABCStore.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.NguoiDung;



@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
	List<HoaDon> findAllByTrangThai(String trangThai);
	Page<HoaDon> findAllHoaDonsByTrangThai(Pageable pageable,String trangThai);
	Page<HoaDon> findAll(Pageable pageable);
	
	@Query("SELECT hd FROM HoaDon hd WHERE CONCAT(hd.id, ' ', hd.tongTien, ' ', hd.hinhThucThanhToan, ' ', hd.trangThai) LIKE %?1%")
	Page<HoaDon> timKiem(Pageable pageable,String keyword);
	
	long countAllHoaDonsByNgayLapBetween(LocalDateTime start, LocalDateTime end);
	long countByNgayLap(LocalDateTime start);

	List<HoaDon> findAllHoaDonsByNgayLapBetween(LocalDateTime start, LocalDateTime end);

	List<HoaDon> findAllHoaDonsByHinhThucThanhToanAndTrangThaiAndNgayLapBetween(String hinhThucThanhToan,String trangThai,LocalDateTime start, LocalDateTime end);

	Page<HoaDon> findAllHoaDonsByNguoiDungAndTrangThai(Pageable pageable, NguoiDung nguoiDung,String trangThai);
	
	HoaDon findHoaDonsByNguoiDungAndTrangThai(NguoiDung nguoiDung,String trangThai);

}
