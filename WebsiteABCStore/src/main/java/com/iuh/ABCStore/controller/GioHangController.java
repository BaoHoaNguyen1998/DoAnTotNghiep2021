package com.iuh.ABCStore.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.DiaChi;
import com.iuh.ABCStore.model.DiaChiGiaoHangHoaDon;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.services.IChiTietHoaDonService;
import com.iuh.ABCStore.services.IDiaChiService;
import com.iuh.ABCStore.services.IHoaDonService;
import com.iuh.ABCStore.services.ISanPhamService;
import com.iuh.ABCStore.services.ITaiKhoanService;

@Controller
public class GioHangController {

	@Autowired
	private IChiTietHoaDonService chitiethoaDon;

	@Autowired
	private ITaiKhoanService taiKhoanService;

	@Autowired
	private IDiaChiService lienHeService;

	@Autowired
	private IHoaDonService hoaDonService;

	@Autowired
	private ISanPhamService sanPhamService;

	@RequestMapping("/hoan-tat")
	public String hoanTatDatHang() {
		return "hoan-tat";
	}

	@RequestMapping("/gio-hang")
	public String gioHang() {
		return "gio-hang";
	}

	@RequestMapping("/gio-hang-header")
	public ResponseEntity<Object> giohangHeader(Model model) {
		TaiKhoan taiKhoan = taiKhoanService
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		if (taiKhoan == null) {
			return new ResponseEntity<Object>("TKRong", HttpStatus.OK);
		}
		HoaDon hd = hoaDonService.findHoaDonsByNguoiDungAndTrangThai(taiKhoan.getNguoiDung(), "Giỏ Hàng");
		if (hd == null) {
			return new ResponseEntity<Object>("GHRong", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(hd, HttpStatus.OK);
		}
	}

	@GetMapping("/gio-hang/them-san-pham/{id}/{sl}")
	public ResponseEntity<Object> themSanPhamNhieu(ModelMap mm, @PathVariable("id") String id,
			@PathVariable("sl") int sl) {
		TaiKhoan taiKhoan = taiKhoanService
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (taiKhoan == null) {
			return new ResponseEntity<Object>("errorTK", HttpStatus.OK);
		}
		HoaDon hd = hoaDonService.findHoaDonsByNguoiDungAndTrangThai(taiKhoan.getNguoiDung(), "Giỏ Hàng");
		SanPham sp = sanPhamService.findbyId(id);
		long slTrong = sp.getSoLuongKho() - sp.getSoLuongBan();
		if (hd != null) {
			for (int i = 0; i < hd.getDssp().size(); i++) {
				if (hd.getDssp().get(i).getSanPham().getId().equalsIgnoreCase(id)) {
					int s = hd.getDssp().get(i).getSoLuong();
					int tong = s + sl;
					if (tong < slTrong) {
						int a = s + sl;
						hd.getDssp().get(i).setSoLuong(a);
						hd.getDssp().get(i).setTongTien(
								hd.getDssp().get(i).getSanPham().getDonGia().multiply(BigDecimal.valueOf(a)));
						hd.setTongTien(hd.tinhTongTien());
						hoaDonService.saveHoaDon(hd);
						return new ResponseEntity<Object>("Ok", HttpStatus.OK);
					} else {
						return new ResponseEntity<Object>("errorSL", HttpStatus.BAD_REQUEST);
					}
				}
			}
			ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sl, sp);
			hd.getDssp().add(cthd);
			hd.setTongTien(hd.tinhTongTien());
			try {
				hoaDonService.saveHoaDon(hd);
				return new ResponseEntity<Object>("OK", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Object>("Thất Bại", HttpStatus.BAD_REQUEST);
			}
		} else {
			HoaDon hdnew = new HoaDon();
			hd = hoaDonService.saveHoaDon(hdnew);
			hd.setNguoiDung(taiKhoan.getNguoiDung());
			hd.setTrangThai("Giỏ Hàng");

			List<ChiTietHoaDon> dssp = new ArrayList<ChiTietHoaDon>();
			ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sl, sp);
			dssp.add(cthd);
			hd.setDssp(dssp);
			hd.setTongTien(hd.tinhTongTien());

			try {
				hoaDonService.saveHoaDon(hd);
				return new ResponseEntity<Object>("OK", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				return new ResponseEntity<Object>("Thất Bại", HttpStatus.BAD_REQUEST);

			}

		}

	}

	@GetMapping("/gio-hang/xoa-san-pham/{id}")
	public ResponseEntity<Object> xoaSanPhamGH(@PathVariable("id") int id) {
		TaiKhoan taiKhoan = taiKhoanService
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (taiKhoan == null) {
			return new ResponseEntity<Object>("errorTK", HttpStatus.OK);
		}
		HoaDon hd = hoaDonService.findHoaDonsByNguoiDungAndTrangThai(taiKhoan.getNguoiDung(), "Giỏ Hàng");
		ChiTietHoaDon cthd = hd.getDssp().get(id);
		List<ChiTietHoaDon> dsct = hd.getDssp();
		dsct.remove(id);
		hd.setDssp(dsct);
		hd.setTongTien(hd.tinhTongTien());
		hoaDonService.saveHoaDon(hd);
		chitiethoaDon.delete(cthd);
		try {
			return new ResponseEntity<Object>("Ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Lỗi!!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/gio-hang/delete-san-pham/{id}")
	public ResponseEntity<Object> xoaSanPhamGH2(@PathVariable("id") String id) {
		TaiKhoan taiKhoan = taiKhoanService
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (taiKhoan == null) {
			return new ResponseEntity<Object>("errorTK", HttpStatus.OK);
		}
		HoaDon hd = hoaDonService.findHoaDonsByNguoiDungAndTrangThai(taiKhoan.getNguoiDung(), "Giỏ Hàng");
		ChiTietHoaDon cthd = new ChiTietHoaDon();
		for(int i =0;i<hd.getDssp().size();i++){
			if(hd.getDssp().get(i).getSanPham().getId().equalsIgnoreCase(id)) {
				cthd = hd.getDssp().get(i);
				hd.getDssp().remove(i);
				break;
			}
		}
		hd.setTongTien(hd.tinhTongTien());
		hoaDonService.saveHoaDon(hd);
		chitiethoaDon.delete(cthd);
		try {
			return new ResponseEntity<Object>("Ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Lỗi!!", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/gio-hang/sua-san-pham/{id}/{sl}")
	public ResponseEntity<Object> suaSanPhamGH(@PathVariable("id") String id, @PathVariable("sl") int sl) {
		TaiKhoan taiKhoan = taiKhoanService
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (taiKhoan == null) {
			return new ResponseEntity<Object>("errorTK", HttpStatus.OK);
		}
		if (sl <0 ) {
			return new ResponseEntity<Object>("errorSL", HttpStatus.OK);
		}
		
		SanPham sp = sanPhamService.findbyId(id);
		long slsp =sp.getSoLuongKho() -sp.getSoLuongBan();
		if(slsp<sl) {
			return new ResponseEntity<Object>("errorSLB", HttpStatus.OK);
		}
		HoaDon hd = hoaDonService.findHoaDonsByNguoiDungAndTrangThai(taiKhoan.getNguoiDung(), "Giỏ Hàng");
		ChiTietHoaDon cthd = new ChiTietHoaDon();
		try {
			for (int i = 0; i < hd.getDssp().size();i++) {
				 if(hd.getDssp().get(i).getSanPham().getId().equalsIgnoreCase(id)) {
					cthd = hd.getDssp().get(i);
					hd.getDssp().remove(i);
					cthd.setSoLuong(sl);
					cthd.setTongTien(cthd.getSanPham().getDonGia().multiply(BigDecimal.valueOf(cthd.getSoLuong())));
					hd.getDssp().add(cthd);
					hd.setTongTien(hd.tinhTongTien());
					hoaDonService.saveHoaDon(hd);
				}
			}
			return new ResponseEntity<Object>("Ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Lỗi!!", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping("/dat-hang")
	public String datHang(Model model) {
		TaiKhoan taiKhoan = taiKhoanService
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (taiKhoan.getNguoiDung().getDanhSachLienHe().isEmpty()
				|| taiKhoan.getNguoiDung().getDanhSachLienHe() == null) {
			return "redirect:/tai-khoan/dia-chi";
		} else {
			DiaChi lienHeNew = new DiaChi();
			model.addAttribute("dslienhe", lienHeService.findByNguoiDung(taiKhoan.getNguoiDung()));
			model.addAttribute("lienHeNew", lienHeNew);
			return "thanh-toan-dia-chi";
		}

	}

	@GetMapping("/dat-hang/chi-tiet")
	public String datHangChiTiet(HttpSession session,Model model, @RequestParam(value = "idLienhe", required = false) String idLienhe) {
		model.addAttribute("lienhe", lienHeService.findById(idLienhe));
		session.setAttribute("idLH", idLienhe);
		return "thanh-toan-chi-tiet";
	}

	@RequestMapping("/dat-hang/thanh-toan-giao-hang/{id}")
	public String thanhToanGiaoHang(Model model, @PathVariable(value = "id") String idlh) {
		try {
			TaiKhoan taiKhoan = taiKhoanService
					.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			
			DiaChi dc = lienHeService.findById(idlh);
			HoaDon hd = hoaDonService.findHoaDonsByNguoiDungAndTrangThai(taiKhoan.getNguoiDung(), "Giỏ Hàng");
			DiaChiGiaoHangHoaDon chiGiaoHangHoaDon = new DiaChiGiaoHangHoaDon();
			chiGiaoHangHoaDon.setDiaChi(dc.getDiaChi());
			chiGiaoHangHoaDon.setHoTen(dc.getHoTen());
			chiGiaoHangHoaDon.setSoDienThoai(dc.getSoDienThoai());
			chiGiaoHangHoaDon.setHoaDon(hd);
			hd.setDiaChiGiaoHang(chiGiaoHangHoaDon);
			hd.setNgayLap(LocalDateTime.now().withNano(0));
			hd.setTienShip(0.0);
			hd.setTrangThai("Đang Giao Hàng");
			hd.setHinhThucThanhToan("Thanh toán khi nhận hàng");
			List<ChiTietHoaDon> dscthd = hd.getDssp();
			List<SanPham> dssp = new ArrayList<SanPham>();
			for(ChiTietHoaDon cthd :dscthd) {
				SanPham sp = cthd.getSanPham();
				sp.setSoLuongBan(sp.getSoLuongBan()+cthd.getSoLuong());
				dssp.add(sp);
			}
			sanPhamService.saveALLSanPham(dssp);
			hoaDonService.saveHoaDon(hd);
			return "hoan-tat";
		} catch (Exception e) {
			return "giao-dich-that-bai";
		}
	}

}
