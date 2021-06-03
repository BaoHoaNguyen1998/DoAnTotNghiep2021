package com.iuh.ABCStore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.services.INguoiDungService;
import com.iuh.ABCStore.services.ISanPhamService;

@Controller
public class ShopController {
	
	
	@Autowired
	private ISanPhamService sanPhamService;

	@Autowired
	private INguoiDungService nguoiDungService;
	
	
	@RequestMapping(value = { "/shop/{idshop}"})
	public String timKiemSp(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "24") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
			@PathVariable(name = "idshop" ,required = false) String id) {
		
		Optional<NguoiDung> nguoiDung = nguoiDungService.findNguoiDungById(id);
		
		if (!nguoiDung.isPresent()) {
			return "redirect:/404";
		}
		
		Sort sortable1 = null;

		if (sort.equals("DESC")) {
			sortable1 = Sort.by("id").descending();
		}
		
		Sort sortable2 = null;

		if (sort.equals("DESC")) {
			sortable2 = Sort.by("ngayTao").descending();
		}
		
		int currentPage = page.orElse(1);
		
		
		Pageable pageable1 = PageRequest.of(currentPage - 1, size, sortable1);
		Pageable pageable2 = PageRequest.of(0, 5, sortable2);
		
		Page<SanPham> pageSanPham = sanPhamService.findAllSanPhamsByNguoiDung(pageable1, nguoiDung.get(), "Đã Xác Nhận");
		
		
		int totalPage = pageSanPham.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listSanPham", sanPhamService.findAllSanPhamsByNguoiDung(pageable1, nguoiDung.get(), "Đã Xác Nhận"));
		model.addAttribute("listSanPhamMoi", sanPhamService.findAllSanPhamsByNguoiDung(pageable2, nguoiDung.get(), "Đã Xác Nhận"));
		model.addAttribute("nguoiDung", nguoiDung.get() );
		return "shop-san-pham";
	}


}
