package com.iuh.ABCStore.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.services.ISanPhamService;

@Controller
public class QuanLySanPhamController {

	@Autowired
	private ISanPhamService iSanPhamService;
	


	@GetMapping("/quan-ly/san-pham/san-pham-can-duyet")
	public String listSanPhamChoDuyet(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "25") Integer size,
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

		Page<SanPham> pageSanPham = iSanPhamService.findAllSanPhamsByTrangThai(pageable, "Chưa Xác Nhận");
		int totalPage = pageSanPham.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);	
		model.addAttribute("listSanPham", iSanPhamService.findAllSanPhamsByTrangThai(pageable, "Chưa Xác Nhận"));
		// so luong mua
		return "/admin/danh-sach-san-pham-can-duyet";
	}
		
		@RequestMapping(value = "/quan-ly/san-pham/chap-nhan/{id}", method = RequestMethod.GET)
		public ResponseEntity<Object> chapNhanNguoiBan(Model mode, @PathVariable("id") String id) {

			try {
				SanPham sp = iSanPhamService.findbyId(id);
				if (sp != null) {
					sp.setTrangThai("Đã Xác Nhận");
					iSanPhamService.saveSanPham(sp);
				}
				return new ResponseEntity<Object> ("Thành Công" ,HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Object> ("Thất Bại" ,HttpStatus.BAD_REQUEST);
			}
			
		}
	
	@GetMapping("/quan-ly/san-pham/xoa-san-pham/{id}")
	public ResponseEntity<Object> xoaSanPham(@PathVariable(name = "id") String id) {

		try {
			SanPham sp = iSanPhamService.findSanPhamById(id).get();
			 iSanPhamService.deleteSanPham(sp);
			return new ResponseEntity<Object> ("Thành công",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object> ("Thất bại",HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/quan-ly/san-pham/danh-sach-san-pham")
	public String danhSachSanPham(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "25") Integer size,
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
		Page<SanPham> pageSanPham = iSanPhamService.findAll(pageable);
		int totalPage = pageSanPham.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);	
		model.addAttribute("listSanPham", iSanPhamService.findAll(pageable));
		return "/admin/danh-sach-san-pham";
	}
	
	@RequestMapping(value = { "/quan-ly/san-pham/tim-kiem/"})
	public String timKiemHD(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "100") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(name = "keyWord" ,required = false) String keyWord) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);
		
		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);
		Page<SanPham> pageSanPham = iSanPhamService.timKiem(pageable, keyWord);
		if(pageSanPham.isEmpty()) {
			model.addAttribute("error", "Sản phẩm tìm kiếm không có!!");
		}	
		int totalPage = pageSanPham.getTotalPages();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyWord1", keyWord);
		model.addAttribute("listSanPham", iSanPhamService.timKiem(pageable, keyWord));
		return "/admin/danh-sach-san-pham";
	}
}
