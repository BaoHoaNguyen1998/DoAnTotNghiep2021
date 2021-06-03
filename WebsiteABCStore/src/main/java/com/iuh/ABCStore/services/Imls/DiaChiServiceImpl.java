package com.iuh.ABCStore.services.Imls;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.DiaChi;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.repository.DiaChiRepository;
import com.iuh.ABCStore.services.IDiaChiService;

@Service
@Transactional
public class DiaChiServiceImpl implements IDiaChiService {

	@Autowired
	private DiaChiRepository lienHeRepository;

	@Override
	public Page<DiaChi> findAllLienHesByNguoiDung(Pageable pageable, NguoiDung nguoiDung) {
		return lienHeRepository.findAllLienHesByNguoiDung(pageable, nguoiDung);
	}

	@Override
	public Optional<DiaChi> findLienHeById(String lienHeid) {
		// TODO Auto-generated method stub
		return lienHeRepository.findById(lienHeid);
	}

	@Override
	public DiaChi findByIdAndTrangThai(NguoiDung nguoiDung, boolean trangthai) {
		// TODO Auto-generated method stub
		return lienHeRepository.findByNguoiDungAndTrangThai(nguoiDung, trangthai);
	}

	@Override
	public DiaChi saveLienHe(DiaChi lienhe) {
		// TODO Auto-generated method stub
		return lienHeRepository.save(lienhe);
	}

	@Override
	public List<DiaChi> findAllLienHe() {
		return lienHeRepository.findAll();
	}

	@Override
	public List<DiaChi> findByNguoiDung(NguoiDung nguoiDung) {
		// TODO Auto-generated method stub
		return lienHeRepository.findByNguoiDung(nguoiDung);
	}


	@Override
	public DiaChi findById(String id) {
		return lienHeRepository.findById(id).get();
	}

	@Override
	public boolean delete(DiaChi lh) {
		lienHeRepository.delete(lh);
		return true;
	}
	
	@Override
	public DiaChi getMot(String id) {
		return lienHeRepository.getOne(id);
	}
	

}
