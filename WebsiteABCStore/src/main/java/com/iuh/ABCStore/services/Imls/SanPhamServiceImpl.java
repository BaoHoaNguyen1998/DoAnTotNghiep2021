package com.iuh.ABCStore.services.Imls;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.DanhMuc;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.repository.SanPhamRepository;
import com.iuh.ABCStore.services.ISanPhamService;

@Service
@Transactional
public class SanPhamServiceImpl implements ISanPhamService {

	@Autowired
	private SanPhamRepository sanPhamRepository;

	@Override
	public Boolean saveSanPham(SanPham sanPham) {
		sanPhamRepository.save(sanPham);
		return true;
	}

	@Override
	public Boolean deleteSanPham(SanPham sanPham) {
		sanPhamRepository.delete(sanPham);
		return true;
	}

	@Override
	public Optional<SanPham> findSanPhamById(String sanPhamId) {
		return sanPhamRepository.findById(sanPhamId);
	}

	@Override
	public List<SanPham> findAllSanPham() {
		return sanPhamRepository.findAll();
	}

	@Override
	public Page<SanPham> findAllSanPhamsByNguoiDung(Pageable pageable, NguoiDung nguoiDung,String trangThai) {
		return sanPhamRepository.findAllSanPhamsByNguoiDungAndTrangThai(pageable, nguoiDung,trangThai);
	}


	@Override
	public SanPham findbyId(String id) {
		return sanPhamRepository.findById(id).get();
	}
	
	@Override
	public Page<SanPham> findAllSanPhamsByTrangThai(Pageable pageable,String trangThai){
		return sanPhamRepository.findAllSanPhamsByTrangThai(pageable, trangThai);
	}


	@Override
	public Page<SanPham> findAll(Pageable pageable) {
		return sanPhamRepository.findAll(pageable);
	}

	@Override
	public Page<SanPham> timKiem(Pageable pageable,String keyword) {
		return sanPhamRepository.timKiem(pageable,keyword);
	}
	
	@Override
	public Page<SanPham> timKIemMoi(Pageable pageable,String keyword){
		return sanPhamRepository.timKiemMoi(pageable,keyword);
	}

	@Override
	public List<SanPham> saveALLSanPham(List<SanPham> dssanPham) {
		// TODO Auto-generated method stub
		return sanPhamRepository.saveAll(dssanPham);
	}

	@Override
	public Page<SanPham> findAllSanPhamsByDanhMuc(Pageable pageable, String id) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAllSanPhamsByDanhMuc(pageable, id);
	}

	
	




	
}
