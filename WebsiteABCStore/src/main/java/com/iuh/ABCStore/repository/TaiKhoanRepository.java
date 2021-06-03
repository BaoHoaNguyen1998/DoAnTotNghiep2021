package com.iuh.ABCStore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.LoaiTaiKhoan;
import com.iuh.ABCStore.model.TaiKhoan;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {

	TaiKhoan findByTenTaiKhoanEmail(String tenTaiKhoanEmail);

	TaiKhoan findByXacNhanEmail(String confirmationToken);

	Page<TaiKhoan> findAllTaiKhoansByloaiTaiKhoanAndTrangThai(Pageable pageable, LoaiTaiKhoan loaiTaiKhoan,
			boolean trangThai);

	List<TaiKhoan> findAllTaiKhoansByTrangThai(String trangthai);

	// count(*) FROM receipt WHERE DATE_FORMAT(receiptDate,'%Y-%m-%d') = :date")
	long countByNgayDangKy(Date ngayDangKy);

	
	@Query("SELECT tk FROM TaiKhoan tk WHERE CONCAT(tk.id, ' ', tk.tenTaiKhoanEmail) LIKE %?1%")
	Page<TaiKhoan> timKiem(Pageable pageable,String keyword);
}
