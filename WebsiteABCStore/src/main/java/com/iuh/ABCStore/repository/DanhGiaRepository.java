package com.iuh.ABCStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iuh.ABCStore.model.DanhGia;


@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, String> {
	
}
