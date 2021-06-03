package com.iuh.ABCStore.services.Imls;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.repository.HoaDonRepository;
import com.iuh.ABCStore.services.IHoaDonService;


@Service
@Transactional
public class HoaDonServiceImpl implements IHoaDonService {

	@Autowired
	private HoaDonRepository hoadon;
	
	@Override
	public List<HoaDon> findAllByTrangThai(String trangThai) {
		return hoadon.findAllByTrangThai("Đang Giao Hàng");
	}

	@Override
	public Page<HoaDon> findAllHoaDonsByTrangThai(Pageable pageable, String trangThai) {
		// TODO Auto-generated method stub
		return hoadon.findAllHoaDonsByTrangThai(pageable, "Đang Giao Hàng");
	}

	@Override
	public Optional<HoaDon> findByHoaDonId(String idHoaDon) {
		// TODO Auto-generated method stub
		return hoadon.findById(idHoaDon);
	}

	@Override
	public HoaDon saveHoaDon(HoaDon hd) {
		// TODO Auto-generated method stub
		return hoadon.save(hd);
	}

	@Override
	public Page<HoaDon> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return hoadon.findAll(pageable);
	}
	
	@Override
	public Page<HoaDon> findAllHoaDonsByNguoiDungAndTrangThai(Pageable pageable, NguoiDung nguoiDung, String trangThai) {
		
		return hoadon.findAllHoaDonsByNguoiDungAndTrangThai(pageable, nguoiDung, trangThai);
	}

	@Override
	public void deleteHoaDonById(String id) {
		hoadon.deleteById(id);
		
	}

	@Override
	public Page<HoaDon> timKiem(Pageable pageable, String keyword) {
		// TODO Auto-generated method stub
		return hoadon.timKiem(pageable, keyword);
	}

	@Override
	public HoaDon findHoaDonsByNguoiDungAndTrangThai(NguoiDung nguoiDung, String trangThai) {
		// TODO Auto-generated method stub
		return hoadon.findHoaDonsByNguoiDungAndTrangThai(nguoiDung, trangThai);
	}

}
