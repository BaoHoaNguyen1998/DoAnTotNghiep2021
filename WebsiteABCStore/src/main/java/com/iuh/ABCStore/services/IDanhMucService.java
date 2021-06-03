package com.iuh.ABCStore.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.DanhMuc;



@Service
@Transactional
public interface IDanhMucService {

	List<DanhMuc> findAllLoaiSanPham();
	
	List<DanhMuc> findAllNguoiDung(String id);


}
