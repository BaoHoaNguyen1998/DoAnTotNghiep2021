package com.iuh.ABCStore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.services.IChiTietHoaDonService;
import com.iuh.ABCStore.services.IHoaDonService;
import com.iuh.ABCStore.services.ITaiKhoanService;
import com.iuh.ABCStore.utils.ThongKeUtil;

@Controller
public class HoaDonKhachHangController {

	@Autowired
	private IHoaDonService hoaDonRepository;

	@Autowired
	private ITaiKhoanService taiKhoanRepository;

	@Autowired
	private IChiTietHoaDonService chiTietHoaDonRepository;

	@GetMapping("/khach-hang/hoa-don/hoa-don-giao-hang")
	public String listHoaDonGiaoHang(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "24") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		NguoiDung khachHang = taiKhoan.getNguoiDung();

		Page<HoaDon> pageHoaDon = hoaDonRepository.findAllHoaDonsByNguoiDungAndTrangThai(pageable, khachHang,
				"Đang Giao Hàng");

		int totalPage = pageHoaDon.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listHoaDon",
				hoaDonRepository.findAllHoaDonsByNguoiDungAndTrangThai(pageable, khachHang, "Đang Giao Hàng"));
		return "danh-sach-hoa-don-giao-hang";
	}

	@GetMapping("/khach-hang/hoa-don/hoa-don-da-huy")
	public String listHoaDonDaHuy(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "24") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		NguoiDung khachHang = taiKhoan.getNguoiDung();

		Page<HoaDon> pageHoaDon = hoaDonRepository.findAllHoaDonsByNguoiDungAndTrangThai(pageable, khachHang, "Đã Hủy");

		int totalPage = pageHoaDon.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listHoaDon",
				hoaDonRepository.findAllHoaDonsByNguoiDungAndTrangThai(pageable, khachHang, "Đã Hủy"));
		return "danh-sach-hoa-don-da-huy";
	}

	@GetMapping("/khach-hang/hoa-don/hoa-don-da-thanh-toan")
	public String listHoaDonDaThanhToan(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "24") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		NguoiDung khachHang = taiKhoan.getNguoiDung();

		Page<HoaDon> pageHoaDon = hoaDonRepository.findAllHoaDonsByNguoiDungAndTrangThai(pageable, khachHang,
				"Đã Thanh Toán");

		int totalPage = pageHoaDon.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listHoaDon",
				hoaDonRepository.findAllHoaDonsByNguoiDungAndTrangThai(pageable, khachHang, "Đã Thanh Toán"));
		return "danh-sach-hoa-don-da-thanh-toan";
	}

	@GetMapping("/khach-hang/hoa-don/huy-don-hang/{id}")
	public String huyDoaDon(Model model, @PathVariable("id") String id) {
		HoaDon hd = hoaDonRepository.findByHoaDonId(id).get();
		if (hd != null) {
		List<ChiTietHoaDon> dsct = hd.getDssp();
		for (ChiTietHoaDon ct : dsct) {
			ct.setSoLuong(0);
			chiTietHoaDonRepository.save(ct);	
		}
		
			hd.setTrangThai("Đã Hủy");
			hoaDonRepository.saveHoaDon(hd);
		}
		return "redirect:/khach-hang/hoa-don/hoa-don-giao-hang";
	}

}
