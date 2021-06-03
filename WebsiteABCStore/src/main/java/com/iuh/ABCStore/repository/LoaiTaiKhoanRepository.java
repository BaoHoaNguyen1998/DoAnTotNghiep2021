package com.iuh.ABCStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuh.ABCStore.model.LoaiTaiKhoan;


@Repository
public interface LoaiTaiKhoanRepository extends JpaRepository<LoaiTaiKhoan, Long> {
	LoaiTaiKhoan findByTenLoaiTaiKhoan(String tenloaiTaiKhoan);
}
