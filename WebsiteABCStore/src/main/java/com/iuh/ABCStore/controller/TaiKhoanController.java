package com.iuh.ABCStore.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iuh.ABCStore.model.DiaChi;
import com.iuh.ABCStore.model.GioHang;
import com.iuh.ABCStore.model.LoaiTaiKhoan;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.repository.LoaiTaiKhoanRepository;
import com.iuh.ABCStore.services.EmailSenderService;
import com.iuh.ABCStore.services.IDiaChiService;
import com.iuh.ABCStore.services.INguoiDungService;
import com.iuh.ABCStore.services.ITaiKhoanService;
import com.iuh.ABCStore.utils.TinhTien;

@Controller
@ComponentScan("com.iuh.BanHangOnline.services")
public class TaiKhoanController {

	@Autowired
	private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private INguoiDungService nguoiDungRepository;

	@Autowired
	private ITaiKhoanService taiKhoanRepository;

	@Autowired
	private IDiaChiService iLienHeService;

	@Autowired
	private TinhTien tinhTien;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping("/dang-nhap")
	public String enterLoginPage(Model model) {

		return "dang-nhap";
	}

	@RequestMapping("/dang-ky")
	public String taoNguoiDung(Model model) {
		TaiKhoan taiKhoan = new TaiKhoan();
		NguoiDung khachHang = new NguoiDung();
		DiaChi lh = new DiaChi();

		Set<DiaChi> danhSachLienHe = new HashSet<DiaChi>();

		danhSachLienHe.add(lh);
		khachHang.setDanhSachLienHe(danhSachLienHe);
		lh.setNguoiDung(khachHang);

		taiKhoan.setNguoiDung(khachHang);
		khachHang.setTaiKhoan(taiKhoan);
		// model.addAttribute("taikhoan",taiKhoan);

		model.addAttribute("khachHang", khachHang);
		model.addAttribute("lienHe", lh);

		return "dang-ky";
	}

	@RequestMapping(value = "/dang-ky", method = RequestMethod.POST)
	public ModelAndView xuLyTaoNguoiDungMoi(ModelAndView modelAndView, @ModelAttribute("lienHe") DiaChi lienHe,
			Model model, @ModelAttribute("khachHang") NguoiDung khachHang) {

		TaiKhoan tk = khachHang.getTaiKhoan();
		tk.setNguoiDung(khachHang);

		Set<LoaiTaiKhoan> ltk = new HashSet<LoaiTaiKhoan>();
		LoaiTaiKhoan loai = new LoaiTaiKhoan();
		loai = loaiTaiKhoanRepository.findById((long) 2).get();
		ltk.add(loai);
		tk.setLoaiTaiKhoan(ltk);
		tk.setMatKhau(bCryptPasswordEncoder.encode(tk.getMatKhau()));
//		tk.setMatKhau(tk.getMatKhau());
		Date date = new Date();
		date.getClass();
		tk.setNgayDangKy(date);
//		tk.setTrangThai(false);
		tk.setTrangThai(true);
		tk.setXacNhanEmail(UUID.randomUUID().toString());

		khachHang.setDanhSachLienHe(new HashSet<DiaChi>(Arrays.asList(lienHe)));
		lienHe.setNguoiDung(khachHang);

		khachHang.setTenShop(khachHang.getHoTen());

		TaiKhoan existingUserEmail = taiKhoanRepository.findByTenTaiKhoanEmail(tk.getTenTaiKhoanEmail());
		if (existingUserEmail != null) {
			model.addAttribute("mess", "Email này đã được đăng ký .");
			modelAndView.setViewName("/dang-ky");
		} else if (nguoiDungRepository.saveNguoiDung(khachHang) != null) {

//			SimpleMailMessage mailMessage = new SimpleMailMessage();
//			mailMessage.setTo(tk.getTenTaiKhoanEmail());
//			mailMessage.setSubject("Xac nhan email tai khoan");
//			mailMessage.setFrom("baohoa098550675@gmail.com");
//			mailMessage.setText("Nhấn vào link bên dưới để xác nhận tài khoản của bạn ");
//			mailMessage.setText("http://localhost:8099/xac-nhan-email?token=" + tk.getXacNhanEmail());
//
//			emailSenderService.sendEmail(mailMessage);
//
//			model.addAttribute("mess", "Xin chào *" + khachHang.getHoTen() + " vui lòng đăng nhập email "
//					+ "để xác nhận tài khoản của bạn");
//			modelAndView.setViewName("/dang-nhap");
		} else {

			modelAndView.setViewName("/dang-ky");
		}

		return modelAndView;
	}
//
//	@RequestMapping(value = "/xac-nhan-email", method = { RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView xacNhanEmailDangKy(ModelAndView modelAndView, @RequestParam("token") String confirmationToken,
//			@ModelAttribute("taikhoan") TaiKhoan taiKhoan, Model model) {
//
//		TaiKhoan token = taiKhoanRepository.findByXacNhanEmail(confirmationToken);
//		System.err.println(token);
//		if (token != null) {
//			token.setTrangThai(true);
//			System.err.println(token);
//
//			taiKhoanRepository.save(token);
//
//			modelAndView.setViewName("/dang-nhap");
//
//		} else {
//			model.addAttribute("mess", "Link đã hỏng @@!");
//			modelAndView.setViewName("/dang-ky");
//		}
//		return modelAndView;
//	}

	@RequestMapping(value = "/dang-xuat", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		HashMap<String, GioHang> gioHang = (HashMap<String, GioHang>) session.getAttribute("giohang");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		HttpSession newSession = request.getSession();
		if (gioHang != null) {
			newSession.setAttribute("giohang", gioHang);
			newSession.setAttribute("tongtien", tinhTien.tongTien(gioHang));
			newSession.setAttribute("sl", gioHang.size());
		}

		return "redirect:/";
	}

	@GetMapping("/quen-mat-khau")
	public String quenMatKhau(Model model, @ModelAttribute("taiKhoan1") TaiKhoan taiKhoan1) {

		return "quen-mat-khau";
	}

	@PostMapping("/quen-mat-khau")
	public String xuLyQuenMatKhau(Model model, @ModelAttribute("taiKhoan1") TaiKhoan taiKhoan1) {

		taiKhoan1 = taiKhoanRepository.findByTenTaiKhoanEmail(taiKhoan1.getTenTaiKhoanEmail());
		if (taiKhoan1 == null) {
			System.out.println("null me r");
		} else {

			model.addAttribute("email", taiKhoan1.getTenTaiKhoanEmail());
			model.addAttribute("ten", taiKhoan1.getNguoiDung().getHoTen());
			model.addAttribute("xacnhan", "Chúng tôi đã gửi một email xác nhận đến email của bạn."
					+ "Vui lòng đăng nhập email để xác nhận ... ");

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(taiKhoan1.getTenTaiKhoanEmail());
			mailMessage.setSubject("Lay Lai Mat Khau");
			mailMessage.setFrom("baohoa0985550675@gmail.com");
			mailMessage.setText(
					"Xin chào " + taiKhoan1.getTenTaiKhoanEmail() + " vui lòng click vào link để lấy lại mật khẩu ");
			mailMessage.setText("http://localhost:8090/lay-lai-mat-khau?token=" + taiKhoan1.getXacNhanEmail());

			emailSenderService.sendEmail(mailMessage);
		}
		return "quen-mat-khau";
	}

	@RequestMapping(value = "/lay-lai-mat-khau", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView xacNhanLayLaiMatKhau(ModelAndView modelAndView, @RequestParam("token") String confirmationToken,
			Model model) {

		TaiKhoan token = taiKhoanRepository.findByXacNhanEmail(confirmationToken);

		if (token != null) {

			model.addAttribute("taiKhoan1", token);

			System.err.println(token);

			modelAndView.setViewName("/doi-mat-khau");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/doi-mat-khau", method = RequestMethod.POST)
	public String doiMatKhau(@ModelAttribute("taiKhoan1") TaiKhoan taiKhoan1, Model model) {

		TaiKhoan taiKhoan = taiKhoanRepository.findByTenTaiKhoanEmail(taiKhoan1.getTenTaiKhoanEmail());

		if (taiKhoan1.getMatKhau() != null) {
			taiKhoan.setMatKhau(bCryptPasswordEncoder.encode(taiKhoan1.getMatKhau()));
			taiKhoan.setXacNhanEmail(UUID.randomUUID().toString());

			taiKhoanRepository.save(taiKhoan);
			model.addAttribute("mess", "Bạn Đã đổi mật khẩu thành công");
		}
		return "/dang-nhap";

	}

	@RequestMapping(value = "/tai-khoan", method = RequestMethod.GET)
	public String HienThiTaiKhoan(Model model, Authentication authentication,
			@ModelAttribute("taiKhoan1") TaiKhoan taiKhoan1) {
		if (authentication != null) {
			TaiKhoan taiKhoan = taiKhoanRepository
					.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());

			NguoiDung khachHang = taiKhoan.getNguoiDung();

			taiKhoan.setNguoiDung(khachHang);
			khachHang.setTaiKhoan(taiKhoan);

			System.out.println(khachHang);

			model.addAttribute("khachHang1", khachHang);
			model.addAttribute("taiKhoan1", taiKhoan1);

			return "tai-khoan";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/sua-tai-khoan", method = RequestMethod.POST)
	public String suaNguoiDung(@ModelAttribute("khachHang1") NguoiDung khachHang1,
			@ModelAttribute("taiKhoan1") TaiKhoan taiKhoan1) {

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		NguoiDung khachHang = taiKhoan.getNguoiDung();

		khachHang.setTenShop(khachHang1.getTenShop());
		khachHang.setNgaySinh(khachHang1.getNgaySinh());
		khachHang.setHoTen(khachHang1.getHoTen());
		taiKhoan.setNguoiDung(khachHang1);

		if (taiKhoan1.getMatKhau() != null) {
			taiKhoan.setMatKhau(bCryptPasswordEncoder.encode(taiKhoan1.getMatKhau()));
			taiKhoan.setNgayDangKy(taiKhoan.getNgayDangKy());
			khachHang.setTaiKhoan(taiKhoan);

			if (taiKhoanRepository.save(taiKhoan) == true) {
				nguoiDungRepository.updateNguoiDung(khachHang);
			}
		}

		return "redirect:/tai-khoan";
	}

	@GetMapping("/tai-khoan/dia-chi")
	public String listLienHe(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Optional<Integer> page,
			@RequestParam(name = "size", required = true, defaultValue = "12") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@ModelAttribute("lienHeMoi") DiaChi lienHeMoi) {
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

		Page<DiaChi> pageSanPham = iLienHeService.findAllLienHesByNguoiDung(pageable, khachHang);
		int totalPage = pageSanPham.getTotalPages();
		if (totalPage > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("listSanPham", iLienHeService.findAllLienHesByNguoiDung(pageable, khachHang));// load len
		model.addAttribute("lienHeMoi", lienHeMoi);

		return "ds-lienhe";
	}

	@GetMapping("/tai-khoan/dia-chi/sua-dia-chi/{id}")
	public String suaLienHe(Model model, @PathVariable("id") String id) {

		DiaChi lienHe = iLienHeService.findById(id);

		model.addAttribute("lienHe", lienHe);

		return "sua-dia-chi";
	}

	@RequestMapping(value = "/tai-khoan/dia-chi/sua-dia-chi/{id}", method = RequestMethod.POST)
	public String suaDiaChi(Model model, @ModelAttribute("lienHe") DiaChi lienHe) {

		DiaChi lh = iLienHeService.findById(lienHe.getId());

		lh.setHoTen(lienHe.getHoTen());
		lh.setSoDienThoai(lienHe.getSoDienThoai());
		lh.setDiaChi(lienHe.getDiaChi());

		iLienHeService.saveLienHe(lh);

		return "redirect:/tai-khoan/dia-chi";

	}

	@GetMapping("/tai-khoan/dia-chi/xoa-dia-chi/{id}")
	public String deleteLienHe(Model model, @PathVariable("id") String id) {

		DiaChi lienHe = iLienHeService.findById(id);
		if (lienHe != null) {
			iLienHeService.delete(lienHe);
		}
		return "redirect:/tai-khoan/dia-chi";
	}

	@RequestMapping(value = "/tai-khoan/dia-chi/them-dia-chi-moi", method = RequestMethod.GET)
	public String themLienHeMoiTrongTaiKhoan(@ModelAttribute("lienHe") DiaChi lienHe) {
		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		lienHe.setNguoiDung(taiKhoan.getNguoiDung());

		lienHe.setTrangThai(false);
		iLienHeService.saveLienHe(lienHe);

		return "redirect:/tai-khoan/dia-chi";
	}

	@RequestMapping(value = "/tai-khoan/them-lien-he", method = RequestMethod.GET)
	public String themLienHeMoi(@ModelAttribute("lienHeNew") DiaChi lienHe) {
		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		lienHe.setNguoiDung(taiKhoan.getNguoiDung());
		if (lienHe.isTrangThai()) {
			DiaChi lh = iLienHeService.findByIdAndTrangThai(taiKhoan.getNguoiDung(), true);
			lh.setTrangThai(false);
			iLienHeService.saveLienHe(lh);
			iLienHeService.saveLienHe(lienHe);
		} else {
			iLienHeService.saveLienHe(lienHe);
		}
		return "redirect:/dat-hang";
	}
	 @RequestMapping("/taikhoan-mobi")
	  @ResponseBody
	  public List<TaiKhoan> list_taikhoan() {
	   
	    System.out.println(taiKhoanRepository.findAllTaiKhoan());
	    return taiKhoanRepository.findAllTaiKhoan();
	  }
}
