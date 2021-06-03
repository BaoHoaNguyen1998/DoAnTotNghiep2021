package com.iuh.ABCStore.services.Imls;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.DanhGia;
import com.iuh.ABCStore.repository.DanhGiaRepository;
import com.iuh.ABCStore.services.IDanhGiaService;


@Service
@Transactional
public class DanhGiaImpl implements IDanhGiaService{
	
	
	
	@Autowired
	DanhGiaRepository danhGiaRepository;

	@Override
	public void save(DanhGia danhgia) {
		danhGiaRepository.save(danhgia);
	}

}
