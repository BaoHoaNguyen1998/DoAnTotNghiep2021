package com.iuh.ABCStore.controller.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Sets;

import com.iuh.ABCStore.model.DiaChi;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.LoaiTaiKhoan;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.repository.LoaiTaiKhoanRepository;
import com.iuh.ABCStore.services.EmailSenderService;
import com.iuh.ABCStore.services.ILoaiTaiKhoanService;
import com.iuh.ABCStore.services.INguoiDungService;
import com.iuh.ABCStore.services.ITaiKhoanService;

@Controller
public class QuanLyTaiKhoanController {

	@Autowired
	private ILoaiTaiKhoanService iLoaiTaiKhoanService;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private INguoiDungService nguoiDungRepository;

	@Autowired
	private ITaiKhoanService taiKhoanRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping("/dang-ky-nguoi-ban")
	public String enterRegisterNguoiBan(Model model) {
		TaiKhoan taiKhoan = new TaiKhoan();
		NguoiDung khachHang = new NguoiDung();
		DiaChi lh = new DiaChi();
		Set<DiaChi> danhSachLienHe = new HashSet<DiaChi>();
		danhSachLienHe.add(lh);
		khachHang.setDanhSachLienHe(danhSachLienHe);
		lh.setNguoiDung(khachHang);
		taiKhoan.setNguoiDung(khachHang);
		khachHang.setTaiKhoan(taiKhoan);
		model.addAttribute("khachHang", khachHang);
		model.addAttribute("lienHe", lh);
		model.addAttribute("lienHe1", lh);
		return "dang-ky-nguoi-ban";
	}

	@RequestMapping(value = "/dang-ky-nguoi-ban", method = RequestMethod.POST)
	public ModelAndView createNewUserNguoiBan(ModelAndView modelAndView, @ModelAttribute("lienHe") DiaChi lienHe,
			Model model, @ModelAttribute("khachHang") NguoiDung khachHang) {

		TaiKhoan tk = khachHang.getTaiKhoan();
		tk.setNguoiDung(khachHang);
		Set<LoaiTaiKhoan> ltk = new HashSet<LoaiTaiKhoan>();
		LoaiTaiKhoan loai = new LoaiTaiKhoan();
		loai = iLoaiTaiKhoanService.findLoaiTKbyID((long) 3);

		ltk.add(loai);
		tk.setLoaiTaiKhoan(ltk);
		tk.setMatKhau(bCryptPasswordEncoder.encode(tk.getMatKhau()));
		Date date = new Date();
		date.getClass();
		tk.setNgayDangKy(date);
		tk.setTrangThai(false);
		tk.setXacNhanEmail(UUID.randomUUID().toString());

		khachHang.setDanhSachLienHe(new HashSet<DiaChi>(Arrays.asList(lienHe)));
		lienHe.setNguoiDung(khachHang);

		khachHang.setTenShop(khachHang.getHoTen());

		TaiKhoan existingUserEmail = taiKhoanRepository.findByTenTaiKhoanEmail(tk.getTenTaiKhoanEmail());
		if (existingUserEmail != null) {
			modelAndView.addObject("message", "Tài khoản đã được đăng ký .");
			modelAndView.setViewName("/dang-ky-nguoi-ban");
		} else if (nguoiDungRepository.saveNguoiDung(khachHang) != null) {

			model.addAttribute("mess",
					"Xin chào *" + khachHang.getHoTen() + " vui lòng chờ quản lý của chúng tôi "
							+ "xác nhận tài khoản của bạn,"
							+ "Chúng tôi sẽ xác nhận và gửi thông tin đến mail của bạn trong thời gian sớm nhất");
			modelAndView.setViewName("/dang-nhap");
		} else {

			modelAndView.setViewName("/dang-ky-nguoi-ban");
		}
		return modelAndView;
	}

	@RequestMapping("/quan-ly/tai-khoan/them-tai-khoan")
	public String taoNguoiDung(Model model) {
		TaiKhoan taiKhoan = new TaiKhoan();
		NguoiDung khachHang = new NguoiDung();
		DiaChi lh = new DiaChi();
		Set<DiaChi> danhSachLienHe = new HashSet<DiaChi>();
		LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
		Set<LoaiTaiKhoan> dsloaitk = new HashSet<LoaiTaiKhoan>();

		dsloaitk.add(loaiTaiKhoan);
		danhSachLienHe.add(lh);
		khachHang.setDanhSachLienHe(danhSachLienHe);
		lh.setNguoiDung(khachHang);

		taiKhoan.setNguoiDung(khachHang);
		khachHang.setTaiKhoan(taiKhoan);
		taiKhoan.setLoaiTaiKhoan(dsloaitk);
		List<LoaiTaiKhoan> dsloaitknew = iLoaiTaiKhoanService.findAll();

		model.addAttribute("khachHang", khachHang);
		model.addAttribute("lienHe", lh);
		model.addAttribute("loaiTaiKhoan", loaiTaiKhoan);
		model.addAttribute("dsLoaiTaiKhoan", dsloaitknew);

		return "admin/them-tai-khoan-moi";
	}

	@RequestMapping(value = "/quan-ly/tai-khoan/them-tai-khoan", method = RequestMethod.POST)
	public String xuLyTaoNguoiDungMoi(@ModelAttribute("lienHe") DiaChi lienHe, Model model,
			@ModelAttribute("khachHang") NguoiDung khachHang, @RequestParam("idLoai") String id) {

		TaiKhoan tk = khachHang.getTaiKhoan();
		tk.setNguoiDung(khachHang);

		Set<LoaiTaiKhoan> ltk = new HashSet<LoaiTaiKhoan>();
		LoaiTaiKhoan loai = new LoaiTaiKhoan();
		loai = iLoaiTaiKhoanService.findLoaiTKbyID(Long.parseLong(id));
		ltk.add(loai);
		tk.setLoaiTaiKhoan(ltk);
		tk.setMatKhau(bCryptPasswordEncoder.encode(tk.getMatKhau()));
		Date date = new Date();
		date.getClass();
		tk.setNgayDangKy(date);
		tk.setTrangThai(true);
		khachHang.setDanhSachLienHe(new HashSet<DiaChi>(Arrays.asList(lienHe)));
		lienHe.setNguoiDung(khachHang);
		khachHang.setTenShop(khachHang.getHoTen());
		TaiKhoan existingUserEmail = taiKhoanRepository.findByTenTaiKhoanEmail(tk.getTenTaiKhoanEmail());
		if (existingUserEmail != null) {
			model.addAttribute("error", "Email đã được đăng ký!!");
			return "redirect:/quan-ly/tai-khoan/them-tai-khoan";
		} else if (nguoiDungRepository.saveNguoiDung(khachHang) == null) {
			model.addAttribute("error", "Đăng ký thất bại !!!");
			return "redirect:/quan-ly/tai-khoan/them-tai-khoan";
		}
		model.addAttribute("error", "Đăng ký thành công !!!");
		return "redirect:/quan-ly/tai-khoan/them-tai-khoan";
	}

	@RequestMapping(value = "/quan-ly/tai-khoan/nguoi-ban-can-chap-nhan", method = RequestMethod.GET)
	public String listNguoiBanCanChapNhan(Model model,
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
		LoaiTaiKhoan loaiTaiKhoan = iLoaiTaiKhoanService.findLoaiTKbyID((long) 3);
		Page<TaiKhoan> pageTaiKhoan = taiKhoanRepository.findAllTaiKhoansByloaiTaiKhoanAndTrangThai(pageable,
				loaiTaiKhoan, false);
		if (pageTaiKhoan.isEmpty()) {
			model.addAttribute("error", "Danh sách tài khoản rỗng!!");
		}
		int totalPage = pageTaiKhoan.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listNguoiBanFalse",
				taiKhoanRepository.findAllTaiKhoansByloaiTaiKhoanAndTrangThai(pageable, loaiTaiKhoan, false));

		return "/admin/danh-sach-nguoi-ban-chua-chap-nhan";
	}

	@RequestMapping(value = "/quan-ly/tai-khoan/chap-nhan-mail/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> chapNhanNguoiBan( @PathVariable("id") String id) {

		try {
			TaiKhoan tk = taiKhoanRepository.findTaiKhoanByID(id);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(tk.getTenTaiKhoanEmail());
			mailMessage.setSubject("Xac nhan email tai khoan");
			mailMessage.setFrom("khuongvo50@gmail.com");
			mailMessage.setText("Nhấn vào link bên dưới để xác nhận tài khoản của bạn ");
			mailMessage.setText("http://localhost:8099/xac-nhan-email-nguoi-ban?token=" + tk.getXacNhanEmail());
			emailSenderService.sendEmail(mailMessage);
			return new ResponseEntity<Object>("Thành Công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Thất Bại", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/quan-ly/tai-khoan/chap-nhan-ban-hang/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> chapNhanNguoiBanBH( @PathVariable("id") String id) {

		try {
			TaiKhoan tk = taiKhoanRepository.findTaiKhoanByID(id);
			tk.setTrangThai(true);
			taiKhoanRepository.save(tk);
			return new ResponseEntity<Object>("Thành Công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Thất Bại", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/xac-nhan-email-nguoi-ban", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView xacNhanMailNguoiBan(ModelAndView modelAndView, @RequestParam("token") String confirmationToken,
			@ModelAttribute("taikhoan") TaiKhoan taiKhoan, Model model) {
		TaiKhoan token = taiKhoanRepository.findByXacNhanEmail(confirmationToken);
		if (token != null) {
			token.setTrangThai(true);
			taiKhoanRepository.save(token);
			modelAndView.setViewName("/dang-nhap");

		} else {
			modelAndView.addObject("mess", "Link đã hỏng @@!");
			modelAndView.setViewName("/dang-ky-nguoi-ban");
		}
		return modelAndView;
	}

	@GetMapping("/quan-ly/tai-khoan/xoa-tai-khoan/{id}")
	public ResponseEntity<Object> xoaTaiKhoan(@PathVariable("id") String id) {
		try {
			TaiKhoan taiKhoan = taiKhoanRepository.findTaiKhoanByID(id);
			taiKhoanRepository.delete(taiKhoan);
			nguoiDungRepository.delete(taiKhoan.getNguoiDung());
			return new ResponseEntity<Object>("Thành Công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Thất bại", HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping("/quan-ly/tai-khoan/khoa-tai-khoan/{id}")
	public ResponseEntity<Object> khoaTaiKhoan( @PathVariable("id") String id) {
		try {
			TaiKhoan taiKhoan = taiKhoanRepository.findTaiKhoanByID(id);
			taiKhoan.setTrangThai(false);
			taiKhoanRepository.save(taiKhoan);
			return new ResponseEntity<Object>("Thành Công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Thất bại", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/quan-ly/tai-khoan/{id}")
	public ResponseEntity<Object> chiTietTaiKhoan(@PathVariable("id") String id) {
		try {
			TaiKhoan taiKhoan = taiKhoanRepository.findTaiKhoanByID(id);
			return new ResponseEntity<Object>(taiKhoan, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Thất bại", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/quan-ly/tai-khoan/sua-tai-khoan/{id}/{loaitaikhoan}")
	public ResponseEntity<Object> capNhatTaiKhoan(@PathVariable("id") String id,@PathVariable("loaitaikhoan") String ltk) {
		try {
			TaiKhoan taiKhoan = taiKhoanRepository.findTaiKhoanByID(id);
			
			if(!(ltk.equals("admin")||ltk.equals("user")||ltk.equals("seller"))) {
				return new ResponseEntity<Object>("Thất bại", HttpStatus.BAD_REQUEST);
			}
			Set<LoaiTaiKhoan> dsltk = new HashSet<LoaiTaiKhoan>();
			dsltk.add(iLoaiTaiKhoanService.findByTenLoaiTaiKhoan(ltk));
			taiKhoan.setLoaiTaiKhoan(dsltk);
			taiKhoanRepository.save(taiKhoan);
			return new ResponseEntity<Object>("Thành Công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Thất bại", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/quan-ly/tai-khoan/danh-sach-tai-khoan", method = RequestMethod.GET)
	public String danhSachTaiKhoan(Model model,
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
		Page<TaiKhoan> pageTaiKhoan = taiKhoanRepository.findAll(pageable);
		if (pageTaiKhoan.isEmpty()) {
			model.addAttribute("error", "Danh sách tài khoản rỗng!!");
		}
		int totalPage = pageTaiKhoan.getTotalPages();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listTaiKhoan", taiKhoanRepository.findAll(pageable));

		return "/admin/danh-sach-tai-khoan";
	}

	@RequestMapping(value = { "/quan-ly/tai-khoan/tim-kiem/" })
	public String timKiemHD(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "100") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(name = "keyWord", required = false) String keyWord) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("id").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("id").descending();
		}
		int currentPage = page.orElse(1);

		Pageable pageable = PageRequest.of(currentPage - 1, size, sortable);
		Page<TaiKhoan> pageSanPham = taiKhoanRepository.timKiem(pageable, keyWord);
		if (pageSanPham.isEmpty()) {
			model.addAttribute("error", "Tài khoản tìm kiếm không có!!");
		}
		int totalPage = pageSanPham.getTotalPages();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyWord1", keyWord);
		model.addAttribute("listTaiKhoan", taiKhoanRepository.timKiem(pageable, keyWord));
		return "/admin/danh-sach-tai-khoan";
	}
}
