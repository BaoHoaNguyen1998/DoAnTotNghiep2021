package com.iuh.ABCStore.services.Imls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.repository.NguoiDungRepository;
import com.iuh.ABCStore.services.INguoiDungService;

@Service
@Transactional
public class NguoiDungServiceImpl implements INguoiDungService {

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Override
	public Optional<NguoiDung> findNguoiDungById(String nguoiDungid) {
		return nguoiDungRepository.findById(nguoiDungid);
	}

	@Override
	public NguoiDung saveNguoiDung(NguoiDung nguoiDung) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.save(nguoiDung);
	}

	@Override
	public NguoiDung updateNguoiDung(NguoiDung nguoiDung) {
		
		return nguoiDungRepository.save(nguoiDung);
	}
	
	@Override
	public boolean delete(NguoiDung nguoiDung) {
		nguoiDungRepository.delete(nguoiDung);
		return true;
	}

	


}
