package com.iuh.ABCStore.services.Imls;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.DiaChi;
import com.iuh.ABCStore.model.DiaChiGiaoHangHoaDon;
import com.iuh.ABCStore.model.GioHang;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.repository.HoaDonRepository;
import com.iuh.ABCStore.repository.SanPhamRepository;
import com.iuh.ABCStore.services.IDiaChiService;
import com.iuh.ABCStore.services.IGioHangService;
import com.iuh.ABCStore.services.IHoaDonService;
import com.iuh.ABCStore.utils.TinhTien;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
@Transactional
public class GioHangServiceImpl implements IGioHangService {
	@Autowired
	private SanPhamRepository sanPhamRepository;

	@Autowired
	private IHoaDonService iHoaDonService;

	@Autowired
	private TinhTien tinhtien;
	
	@Autowired
	private IDiaChiService iLienHeService;

	private HoaDon hd;
	private List<ChiTietHoaDon> dscthd;
	private List<SanPham> dssp;
	private SanPham sanpham;
	private ChiTietHoaDon cthd;
	private DiaChiGiaoHangHoaDon dcgh;
	
	@Override
	public boolean thanhToanPayPal(HashMap<String, GioHang> giohang, NguoiDung nguoiDung,String idLienHe) {
		dcgh = new DiaChiGiaoHangHoaDon();
		hd = new HoaDon();
		dscthd = new ArrayList<>();
		dssp = new ArrayList<SanPham>();
		hd.setNgayLap(LocalDateTime.now());

		for (Map.Entry<String, GioHang> entry : giohang.entrySet()) {
			Optional<SanPham> temp = sanPhamRepository.findById(entry.getValue().getSanPham().getId());
			sanpham = temp.get();// set san pham
			sanpham.setSoLuongBan(sanpham.getSoLuongBan() + entry.getValue().getSoLuong());// so luong ban
//			cthd = new ChiTietHoaDon(BigDecimal.valueOf(sanpham.getDonGia().doubleValue()* entry.getValue().getSoLuong()),
//					entry.getValue().getSoLuong(), sanpham);
			cthd.setHoaDon(hd);
			dscthd.add(cthd);
			dssp.add(sanpham);	
		}
		DiaChi lh = iLienHeService.findLienHeById(idLienHe).get();
		dcgh.setDiaChi(lh.getDiaChi());
		dcgh.setHoTen(lh.getHoTen());
		dcgh.setSoDienThoai(lh.getSoDienThoai());
		dcgh.setHoaDon(hd);
		hd.setDssp(dscthd);
		hd.setNguoiDung(nguoiDung);
		hd.setTrangThai("Đã Thanh Toán");
		hd.setTongTien(tinhtien.tongTien(giohang));
		hd.setTienShip(0);
		hd.setDiaChiGiaoHang(dcgh);
		hd.setHinhThucThanhToan("Thanh toán Pay-Pal");
		if (iHoaDonService.saveHoaDon(hd) != null) {
			sanPhamRepository.saveAll(dssp);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean thanhToanGiaohang(HashMap<String, GioHang> giohang, NguoiDung nguoiDung,String idLienHe) {
		dcgh = new DiaChiGiaoHangHoaDon();
		hd = new HoaDon();
		dscthd = new ArrayList<ChiTietHoaDon>();
		dscthd = new ArrayList<>();
		dssp = new ArrayList<SanPham>();
		hd.setNgayLap(LocalDateTime.now());
		for (Map.Entry<String, GioHang> entry : giohang.entrySet()) {
			
			Optional<SanPham> temp = sanPhamRepository.findById(entry.getValue().getSanPham().getId());
			sanpham = temp.get();
//			cthd = new ChiTietHoaDon(BigDecimal.valueOf(sanpham.getDonGia().doubleValue()* entry.getValue().getSoLuong()),
//					entry.getValue().getSoLuong(), sanpham);
			
			sanpham.setSoLuongBan(sanpham.getSoLuongBan() + entry.getValue().getSoLuong());// so luong ban
			cthd.setHoaDon(hd);
			dscthd.add(cthd);
			dssp.add(sanpham);
		}
		DiaChi lh = iLienHeService.findLienHeById(idLienHe).get();
		dcgh.setDiaChi(lh.getDiaChi());
		dcgh.setHoTen(lh.getHoTen());
		dcgh.setSoDienThoai(lh.getSoDienThoai());
		dcgh.setHoaDon(hd);
		hd.setDssp(dscthd);
		hd.setNguoiDung(nguoiDung);
		hd.setTrangThai("Đang Giao Hàng");
		hd.setTongTien(tinhtien.tongTien(giohang));
		hd.setTienShip(0);
		hd.setHinhThucThanhToan("Thanh toán khi nhận hàng");
		
		hd.setDiaChiGiaoHang(dcgh);
		if (iHoaDonService.saveHoaDon(hd) != null) {
			sanPhamRepository.saveAll(dssp);
			return true;
		} else {
			return false;
		}

	}

}
