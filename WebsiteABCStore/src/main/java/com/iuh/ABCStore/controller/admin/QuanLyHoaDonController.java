package com.iuh.ABCStore.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.services.IChiTietHoaDonService;
import com.iuh.ABCStore.services.IHoaDonService;

@Controller
public class QuanLyHoaDonController {

	@Autowired
	private IHoaDonService ihoaDon;
	
	@Autowired
	private IChiTietHoaDonService ichitiet;

	@GetMapping("/quan-ly/hoa-don/hoa-don-giao-hang")
	public String listHoaDonGiaoHang(Model model,
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

		Page<HoaDon> pageHoaDon = ihoaDon.findAllHoaDonsByTrangThai(pageable, "Đang Giao Hàng");
		if(pageHoaDon.isEmpty()) {
			model.addAttribute("error", "Danh sách hóa đơn rỗng!!");
		}
		int totalPage = pageHoaDon.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listHoaDon", ihoaDon.findAllHoaDonsByTrangThai(pageable, "Đang Giao Hàng"));
		return "/admin/danh-sach-hoa-don-giao-hang";
	}

	@RequestMapping(value = "/quan-ly/hoa-don/hoan-thanh/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> hoanTatHoaDon(Model mode, @PathVariable("id") String id) {

		HoaDon hd = ihoaDon.findByHoaDonId(id).get();
		System.err.println(hd);
		if (hd != null) {
			hd.setTrangThai("Đã Thanh Toán");
			if(ihoaDon.saveHoaDon(hd) != null) {
				return new ResponseEntity<Object> ("Hoàn thành",HttpStatus.OK);
			}
		}
		return new ResponseEntity<Object> ("Thất bại",HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping(value = "/quan-ly/hoa-don/xoa-hoa-don/{id}")
	public ResponseEntity<Object> deleteCustomer(
			@PathVariable String id) {
		try {
			ihoaDon.deleteHoaDonById(id);
			return new ResponseEntity<Object>("Ok", HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Object>("Xóa hóa đơn thất bại", HttpStatus.BAD_REQUEST);
		}
		
	}


	@GetMapping("/quan-ly/hoa-don/danh-sach-hoa-don")
	public String listHoaDon(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "25") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
			) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);
		
		Page<HoaDon> pageHoaDon = ihoaDon.findAll(pageable);
		
		if(pageHoaDon.isEmpty()) {
			model.addAttribute("error", "Danh sách hóa đơn rỗng!!");
		}
		int totalPage = pageHoaDon.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listHoaDon", ihoaDon.findAll(pageable));
		return "/admin/danh-sach-hoa-don";
	}

	
	@RequestMapping(value = { "/quan-ly/hoa-don/tim-kiem/"})
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
		Page<HoaDon> pageSanPham = ihoaDon.timKiem(pageable, keyWord);
		if(pageSanPham.isEmpty()) {
			model.addAttribute("error", "Hóa đơn tìm kiếm không có!!");
		}	
		int totalPage = pageSanPham.getTotalPages();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyWord1", keyWord);
		model.addAttribute("listHoaDon", ihoaDon.timKiem(pageable, keyWord));
		return "/admin/danh-sach-hoa-don";
	}
	
	@RequestMapping(value = "/quan-ly/hoa-don/{id}",method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<HoaDon> view(  @PathVariable("id") String id) {
		
		try {
			HoaDon hd = ihoaDon.findByHoaDonId(id).get();
			return new ResponseEntity<HoaDon> (hd,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<HoaDon> (HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@RequestMapping(value = "/quan-ly/hoa-don/sua-hoa-don/{id}/{trangthai}",method = RequestMethod.GET)
	public  ResponseEntity<Object> suaHoaDon(@PathVariable String id,@PathVariable String trangthai  ) {
		try {
			HoaDon hd = ihoaDon.findByHoaDonId(id).get();
			hd.setTrangThai(trangthai);
			ihoaDon.saveHoaDon(hd);
			return new ResponseEntity<Object> ("Thành công!!",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object> ("Thất bại!!!",HttpStatus.BAD_REQUEST);
		}
	}
}
