package com.iuh.ABCStore.services.Imls;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.repository.ChiTietHoaDonRepository;
import com.iuh.ABCStore.services.IChiTietHoaDonService;

@Service
@Transactional
public class ChiTietHoaDonImpl implements IChiTietHoaDonService{

	@Autowired
	private ChiTietHoaDonRepository chiTietHoaDonRepository;
	
	@Override
	public boolean save(ChiTietHoaDon ct) {
		
		chiTietHoaDonRepository.save(ct);
		return true;
	}

	@Override
	public List<ChiTietHoaDon> findAllByHoaDon(HoaDon hoadon) {
		// TODO Auto-generated method stub
		return chiTietHoaDonRepository.findAllByHoaDon(hoadon);
	}

	@Override
	public void delete(ChiTietHoaDon cthd) {
		// TODO Auto-generated method stub
		chiTietHoaDonRepository.delete(cthd);
	}

	@Override
	public ChiTietHoaDon findByHoaDonAndSanPham(HoaDon hd, SanPham sp) {
		// TODO Auto-generated method stub
		return chiTietHoaDonRepository.findByHoaDonAndSanPham(hd, sp);
	}


	
}
