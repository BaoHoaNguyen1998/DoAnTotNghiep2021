package com.iuh.ABCStore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iuh.ABCStore.model.DanhMuc;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;


@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
	
	List<SanPham> findTop10ByNguoiDung(NguoiDung nguoiDung);
	
	Page<SanPham> findAllSanPhamsByNguoiDungAndTrangThai(Pageable pageable,NguoiDung nguoiDung,String trangThai);
	
	Page<SanPham> findAllSanPhamsByTrangThai(Pageable pageable,String trangThai);
	
	Page<SanPham> findAllSanPhamsByDanhMuc(Pageable pageable, String id);
	
	@Query("SELECT sp FROM SanPham sp WHERE CONCAT(sp.tenSanPham, ' ', sp.donGia, ' ', sp.namSanXuat, ' ', sp.thuongHieu) LIKE %?1%")
	Page<SanPham> timKiem(Pageable pageable,String keyword);
	
	@Query("SELECT sp FROM SanPham sp WHERE CONCAT(sp.tenSanPham, ' ', sp.donGia, ' ', sp.namSanXuat, ' ', sp.thuongHieu,' ', sp.danhMuc.tenDanhMuc ) LIKE %?1%")
	Page<SanPham> timKiemMoi(Pageable pageable,String keyword);
}
