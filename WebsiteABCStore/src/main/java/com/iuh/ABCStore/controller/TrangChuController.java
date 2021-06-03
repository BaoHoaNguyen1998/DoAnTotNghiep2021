package com.iuh.ABCStore.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iuh.ABCStore.model.ChiTietTimKiem;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.model.TimKiem;
import com.iuh.ABCStore.repository.ChiTietTimKiemRepository;
import com.iuh.ABCStore.repository.DanhMucRepository;
import com.iuh.ABCStore.repository.NguoiDungRepository;
import com.iuh.ABCStore.repository.TaiKhoanRepository;
import com.iuh.ABCStore.repository.TimKiemRepository;
import com.iuh.ABCStore.services.IDanhMucService;
import com.iuh.ABCStore.services.INguoiDungService;
import com.iuh.ABCStore.services.ISanPhamService;

@Controller
public class TrangChuController {

	@Autowired
	private ISanPhamService sanPhamService;

	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	@Autowired
	private ISanPhamService sanPhamRepository;

	@Autowired
	private IDanhMucService danhMucRepository;

	@Autowired
	private TimKiemRepository timKiemRe;

	@Autowired
	private DanhMucRepository danhMuc;

	@Autowired
	private ChiTietTimKiemRepository chiTietTimKiemRepository;

	@Autowired
	private INguoiDungService nguoiDungService;

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	private ChiTietTimKiem ct;

	@RequestMapping("/chinh-sach")
	public String chinhSach() {
		return "chinh-sach";
	}

//	@RequestMapping("/thongtin")
//	public String thongtinPage() {
//		return "thongtin";
//	}

	@RequestMapping(value = { "/", "/trang-chu" })
	public String listSanPham(Model model) {

		Sort sortable1 = null;
		sortable1 = Sort.by("soLuongBan").descending();
		Pageable pageable1 = PageRequest.of(0, 20, sortable1);

		Sort sortable2 = null;
		sortable2 = Sort.by("ngayTao").descending();
		Pageable pageable2 = PageRequest.of(0, 20, sortable2);

		Sort sortable3 = null;
		sortable3 = Sort.by("id").descending();
		Pageable pageable3 = PageRequest.of(0, 23, sortable3);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		if (taiKhoan != null) {
			NguoiDung nguoiDung = taiKhoan.getNguoiDung();
			LocalDateTime end = LocalDateTime.now();
			LocalDateTime start = end.withDayOfMonth(1);

			List<ChiTietTimKiem> listctChiTietTimKiems = chiTietTimKiemRepository
					.findAllChiTietTimKiemsByNguoiDungAndThoiGianTimKiemBetween(nguoiDung, start, end);
			if (listctChiTietTimKiems == null && listctChiTietTimKiems.isEmpty()) {

				ChiTietTimKiem cttk = listctChiTietTimKiems.get(listctChiTietTimKiems.size() - 1);

//				model.addAttribute("listSanPhamDanhMuc", sanPhamService.timKIemMoi(pageable1, cttk.getTimKiem().getKeyWord()));
				model.addAttribute("listSanPhamSLBan",
						sanPhamService.findAllSanPhamsByTrangThai(pageable1, "Đã Xác Nhận"));
				model.addAttribute("listSanPhamSPMoi",
						sanPhamService.findAllSanPhamsByTrangThai(pageable2, "Đã Xác Nhận"));

				model.addAttribute("listSanPham", sanPhamService.timKIemMoi(pageable1, cttk.getTimKiem().getKeyWord()));

			} else {
				model.addAttribute("listSanPham", sanPhamService.findAllSanPhamsByTrangThai(pageable3, "Đã Xác Nhận"));
				model.addAttribute("listSanPhamSLBan",
						sanPhamService.findAllSanPhamsByTrangThai(pageable1, "Đã Xác Nhận"));
				model.addAttribute("listSanPhamSPMoi",
						sanPhamService.findAllSanPhamsByTrangThai(pageable2, "Đã Xác Nhận"));
			}

		} else {
			model.addAttribute("listSanPham", sanPhamService.findAllSanPhamsByTrangThai(pageable3, "Đã Xác Nhận"));
			model.addAttribute("listSanPhamSLBan", sanPhamService.findAllSanPhamsByTrangThai(pageable1, "Đã Xác Nhận"));
			model.addAttribute("listSanPhamSPMoi", sanPhamService.findAllSanPhamsByTrangThai(pageable2, "Đã Xác Nhận"));
		}

		return "trang-chu";
	}

	@RequestMapping(value = { "/san-pham-danh-cho-ban" })
	public String danhSachSPChoBan(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "2") Optional<Integer> page,
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

		Page<SanPham> pageSanPham = sanPhamService.findAllSanPhamsByTrangThai(pageable, "Đã Xác Nhận");
		int totalPage = pageSanPham.getTotalPages();
		model.addAttribute("listSanPham", sanPhamService.findAllSanPhamsByTrangThai(pageable, "Đã Xác Nhận"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);

		return "san-pham-danh-cho-ban";
	}

	@RequestMapping(value = { "/tim-kiem" })
	public String timKiemSp(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "24") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(name = "keyWord", required = false) String keyWord) {

		if (keyWord.trim().length() == 0) {
			return "redirect:/";
		}

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);

		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);
		Page<SanPham> pageSanPham = sanPhamService.timKIemMoi(pageable, keyWord);
		TimKiem timKiem = new TimKiem();

		timKiem.setKeyWord(keyWord.toLowerCase().trim());
		timKiem.setCountKeyWord(1);

		TimKiem tkkw = timKiemRe.findTimKiemByKeyWord(keyWord.toLowerCase().trim());
		System.err.println(tkkw);

		if (tkkw != null && tkkw.getKeyWord().equals(keyWord.toLowerCase().trim())) {
			tkkw.setCountKeyWord(tkkw.getCountKeyWord() + 1);
			timKiemRe.save(tkkw);

		} else {
			if (!pageSanPham.isEmpty()) {
				timKiem.setTrangThai("Có");
			} else {
				timKiem.setTrangThai("Không");
			}
			timKiemRe.save(timKiem);
		}

		if (taiKhoan != null) {
			NguoiDung nguoiDung = taiKhoan.getNguoiDung();

			ChiTietTimKiem cttk = new ChiTietTimKiem();
			cttk.setSoLuong(1);
			LocalDateTime lc = LocalDateTime.now();
			cttk.setThoiGianTimKiem(lc);

			if (tkkw != null) {
				cttk.setTimKiem(tkkw);
				ct = chiTietTimKiemRepository.findByTimKiemAndNguoiDung(tkkw, nguoiDung);
				System.err.println(ct);

				if (ct == null) {
					ChiTietTimKiem cts = new ChiTietTimKiem();
					cts.setTimKiem(tkkw);
					cts.setSoLuong(1);
					cts.setThoiGianTimKiem(lc);
					cts.setNguoiDung(nguoiDung);
					chiTietTimKiemRepository.save(cts);
				} else {
					ct.setSoLuong(ct.getSoLuong() + 1);
					ct.setThoiGianTimKiem(lc);
					ct.setNguoiDung(ct.getNguoiDung());
					chiTietTimKiemRepository.save(ct);
				}

			} else {
				cttk.setTimKiem(timKiem);

				cttk.setNguoiDung(nguoiDung);
				chiTietTimKiemRepository.save(cttk);

			}

		}

		int totalPage = pageSanPham.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyWord1", keyWord);
		model.addAttribute("listSanPham", sanPhamService.timKIemMoi(pageable, keyWord));

		return "tim-kiem-san-pham";
	}
//	@GetMapping("/san-pham-id")
//	public List<SanPham> list(){
//		System.out.println(sanPhamService.findAllSanPham());
//		
//		return sanPhamService.findAllSanPham();
//	}
	 @RequestMapping("/san-pham-id")
	  @ResponseBody
	  public List<SanPham> testListJSON() {
	   
	    
	    return sanPhamService.findAllSanPham();
	  }
}
