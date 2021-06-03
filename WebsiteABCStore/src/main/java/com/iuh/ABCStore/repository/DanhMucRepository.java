package com.iuh.ABCStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iuh.ABCStore.model.DanhMuc;


@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, String> {
	List<DanhMuc> findTop10BytenDanhMuc(String ten);
	
	@Query("SELECT dm FROM   DanhMuc dm INNER JOIN NguoiDung nd ON dm.id = nd.id where nd.id = :id")
	List<DanhMuc> findAllNguoiDung(String id);
}
