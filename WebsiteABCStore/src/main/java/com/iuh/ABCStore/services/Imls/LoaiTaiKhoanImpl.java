package com.iuh.ABCStore.services.Imls;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuh.ABCStore.model.LoaiTaiKhoan;
import com.iuh.ABCStore.repository.LoaiTaiKhoanRepository;
import com.iuh.ABCStore.services.ILoaiTaiKhoanService;
@Service
@Transactional
public class LoaiTaiKhoanImpl implements ILoaiTaiKhoanService{
	
	@Autowired
	private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

	@Override
	public List<LoaiTaiKhoan> findAll() {
		// TODO Auto-generated method stub
		return loaiTaiKhoanRepository.findAll();
	}

	@Override
	public LoaiTaiKhoan findLoaiTKbyID(long id) {
		// TODO Auto-generated method stub.
		return loaiTaiKhoanRepository.findById(id).get();
	}

	@Override
	public LoaiTaiKhoan findByTenLoaiTaiKhoan(String tenloaiTaiKhoan) {
		// TODO Auto-generated method stub
		return loaiTaiKhoanRepository.findByTenLoaiTaiKhoan(tenloaiTaiKhoan);
	}

}
