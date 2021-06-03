package com.iuh.ABCStore.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iuh.ABCStore.model.MyItem;
import com.iuh.ABCStore.model.MyItemsStartEnd;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.model.TongTienStartEnd;
import com.iuh.ABCStore.repository.ChiTietHoaDonRepository;
import com.iuh.ABCStore.services.ITaiKhoanService;
import com.iuh.ABCStore.utils.ThongKeUtil;


@Controller
public class ThongKeBanHangController {

	@Autowired
	private ThongKeUtil cc;

	@Autowired
	private ITaiKhoanService taiKhoanRepository;

	@Autowired
	ChiTietHoaDonRepository ct;

	private DecimalFormat format = new DecimalFormat("0.00");

	@RequestMapping(value = "/repo", method = RequestMethod.GET)
	public String thongKeSoTaiKhoanTheoTuan(Model model, HttpSession session) {
		Date date = new Date();
		List<MyItem> listItem = cc.reportReceipt(date, 7);

		Map<String, Long> surveyMap = new LinkedHashMap<>();
		for (int i = 0; i < listItem.size(); i++) {

			System.err.println(listItem.get(i));
			String time = listItem.get(i).getTime();
			long value = (long) listItem.get(i).getValue();

			surveyMap.put(time, value);

		}
		model.addAttribute("surveyMap", surveyMap);
		return "admin/barGraph";
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String thongKeTongSoHoaDonTheoTuan(Model model, HttpSession session) throws ParseException {

		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);
		System.err.println("start Hien Tai : " + start);

		Map<String, Long> surveyMap = new LinkedHashMap<>();

		int x = 0;
		while (x < 8) {
			Date start1 = cc.subDays(start, x);

			List<MyItemsStartEnd> my = cc.soLuongHoaDonTungNgayTruocHomNay(start1, 2);// truoc 1 ngay
			System.err.println(" Cac ngay Sau " + my.get(0));
			String time1 = my.get(0).getStart();
			long value1 = (long) my.get(0).getValue();
			System.err.println(my);
			surveyMap.put(time1, value1);

			x++;

		}

		Date end = new Date();
		List<MyItemsStartEnd> listItem = cc.soLuongHoaDonHomNay(start, end);
		System.err.println("Hien Tai : " + listItem.get(0));
		String time = listItem.get(0).getStart();
		long value = (long) listItem.get(0).getValue();
		surveyMap.put(time, value);

		model.addAttribute("surveyMap", surveyMap);

		return "admin/barGraph";
	}

	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public String thongKeTongTienTatCaHoaDonHomNay(Model model, HttpSession session) throws ParseException {

		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		System.err.println("start Hien Tai : " + start);
		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();

		Date end = new Date();
		List<TongTienStartEnd> listItem = cc.tongTienHoaDonHomNay(start, end);

		String time = listItem.get(0).getStart();
		BigDecimal value = listItem.get(0).getValue();
		System.err.println("Start : " + time);
		System.err.println("End : " + end);
		System.err.println("Tong Tien : " + value);
		System.err.println(listItem.get(0));
		surveyMap.put(time, value);

		model.addAttribute("surveyMap", surveyMap);

		return "admin/barGraph";
	}

	@RequestMapping(value = "/reports1", method = RequestMethod.GET)
	public String thongKeTongTien7NgayHoaDonTruocHomNay(Model model) throws ParseException {

		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		System.err.println("start Hien Tai : " + start);
		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();

		int x = 0;
		BigDecimal value1 = null;
		while (x < 7) {
			Date start1 = cc.subDays(start, x);

			List<MyItemsStartEnd> my = cc.tongTienHoaDonTungNgayTruocHomNay(start1, 2);
			System.err.println(" Cac ngay Sau " + my.get(0));
			String time1 = my.get(0).getStart();
			value1 = (BigDecimal) my.get(0).getValue();

			surveyMap.put(time1, value1);

			x++;

		}

		// Date end = new Date();
		// List<TongTienStartEnd> listItem = cc.tongTienHoaDonHomNay(start, end);
		//
		// String time = listItem.get(0).getStart();
		// long value = (long) listItem.get(0).getValue();
		// System.err.println("Start : " + time);
		// System.err.println("End : " + end);
		// System.err.println("Tong Tien : " + value);
		// System.err.println(listItem.get(0));
		// surveyMap.put(time, value);

		model.addAttribute("surveyMap", surveyMap);

		return "admin/barGraph";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/reports2", method = RequestMethod.GET)
	public String thongKeTongTien2hinhThucThanhToan(Model model) throws ParseException {

		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		System.err.println("start Hien Tai : " + start);
		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
		Map<String, BigDecimal> surveyMap1 = new LinkedHashMap<>();

		int x = 0;
		@SuppressWarnings("deprecation")
		int x1 = start.getDate() - 1;
		BigDecimal value = null;
		BigDecimal value1 = null;

		List<BigDecimal> listtimsolonnhat = new ArrayList<BigDecimal>();
		List<BigDecimal> listNhanHang = new ArrayList<BigDecimal>();
		List<BigDecimal> listPayPal = new ArrayList<BigDecimal>();
		while (x < x1) {
			Date start1 = cc.subDays(start, x);

			List<MyItemsStartEnd> my = cc.tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan(
					"Thanh toán khi nhận hàng", start1, 2, 0.95);
			List<MyItemsStartEnd> my1 = cc.tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan("Thanh toán Pay-Pal",
					start1, 2, 0.95);

			String time = my.get(0).getStart();
			value = (BigDecimal) my.get(0).getValue();
			surveyMap.put(time, value);

			String time1 = my1.get(0).getStart();
			value1 = (BigDecimal) my1.get(0).getValue();
			surveyMap1.put(time1, value1);

			x++;

			listtimsolonnhat.add(value);
			listtimsolonnhat.add(value1);

			listNhanHang.add(value);
			listPayPal.add(value1);

		}

		BigDecimal t1 = findMax(listtimsolonnhat);

		model.addAttribute("tong", t1);

		double tongthuong = 0.0;
		for (int j = 0; j < listNhanHang.size(); j++) {
			tongthuong += listNhanHang.get(j).doubleValue();
		}
		double tongpaypal = 0.0;
		for (int j = 0; j < listPayPal.size(); j++) {
			tongpaypal += listPayPal.get(j).doubleValue();
		}
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("surveyMap1", surveyMap1);

		double tongTatCa = tongthuong + tongpaypal;
		double pass = tongthuong * 100 / tongTatCa;
		double fail = tongpaypal * 100 / tongTatCa;

		System.err.println(t1);

		model.addAttribute("pass", pass);
		model.addAttribute("fail", fail);

		return "admin/thong-ke-2-hinh-thuc-thanh-toan";
	}

// thong ke dau thang den nay admin 
	@RequestMapping(value = "/quan-ly/thong-ke/Bay-ngay-lien-tuc-quan-ly", method = RequestMethod.GET)
	public String thongKeBayNgayLienTucQuanly(Model model) throws ParseException {

		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
		Map<String, BigDecimal> surveyMap1 = new LinkedHashMap<>();
		
		
		Date end = new Date();
		List<TongTienStartEnd> homNay = cc.tongTienHoaDonHomNayByHinhThucThanhToan("Thanh toán khi nhận hàng", start, end);
		List<TongTienStartEnd> homNay1 = cc.tongTienHoaDonHomNayByHinhThucThanhToan( "Thanh toán Pay-Pal", start, end);
		
		BigDecimal value2 = null;
		String time2 = homNay.get(0).getStart();
		value2 = (BigDecimal) homNay.get(0).getValue();
		surveyMap.put(time2, value2);

		BigDecimal value3 = null;
		String time3 = homNay1.get(0).getStart();
		value3 = (BigDecimal) homNay1.get(0).getValue();
		surveyMap1.put(time3, value3);

		int x = 0;
		int x1 = 7;
		BigDecimal value = null;
		BigDecimal value1 = null;

		List<BigDecimal> listtimsolonnhat = new ArrayList<BigDecimal>();
		List<BigDecimal> listNhanHang = new ArrayList<BigDecimal>();
		List<BigDecimal> listPayPal = new ArrayList<BigDecimal>();
		while (x < x1) {
			Date start1 = cc.subDays(start, x);

			List<MyItemsStartEnd> my = cc.tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan(
					"Thanh toán khi nhận hàng", start1, 2, 0.05);
			List<MyItemsStartEnd> my1 = cc.tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan("Thanh toán Pay-Pal",
					start1, 2, 0.05);

			String time = my.get(0).getStart();
			value = (BigDecimal) my.get(0).getValue();
			surveyMap.put(time, value);

			String time1 = my1.get(0).getStart();
			value1 = (BigDecimal) my1.get(0).getValue();
			surveyMap1.put(time1, value1);

			x++;

			listtimsolonnhat.add(value);
			listtimsolonnhat.add(value1);

			listNhanHang.add(value);
			listPayPal.add(value1);

		}
		
		listtimsolonnhat.add(value2);
		listtimsolonnhat.add(value3);

		listNhanHang.add(value2);
		listPayPal.add(value3);
		
		BigDecimal t1 = findMax(listtimsolonnhat);
		model.addAttribute("tong", t1);

		double tongthuong = 0.0;
		for (int j = 0; j < listNhanHang.size(); j++) {
			tongthuong += listNhanHang.get(j).doubleValue();
		}
		double tongpaypal = 0.0;
		for (int j = 0; j < listPayPal.size(); j++) {
			tongpaypal += listPayPal.get(j).doubleValue();
		}
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("surveyMap1", surveyMap1);

		double tongTatCa = tongthuong + tongpaypal;
		double pass = tongthuong * 100 / tongTatCa;
		double fail = tongpaypal * 100 / tongTatCa;

		model.addAttribute("pass", pass);
		model.addAttribute("fail", fail);

		String formatted = format.format(tongthuong);
		model.addAttribute("tongthuong", formatted);

		String formatted1 = format.format(tongpaypal);
		model.addAttribute("tongpaypal", formatted1);

		String formatted2 = format.format(tongTatCa);
		model.addAttribute("tongtatca", formatted2);

		return "admin/thong-ke-hai-hinh-thuc-thanh-toan";
	}
	
	@RequestMapping(value = "/quan-ly/thong-ke/tu-dau-thang-den-nay-quan-ly", method = RequestMethod.GET)
	public String thongKeTongTienTheoNgayQuanLy(Model model) throws ParseException {

		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
		Map<String, BigDecimal> surveyMap1 = new LinkedHashMap<>();

		int x = 0;
		@SuppressWarnings("deprecation")
		int x1 = start.getDate() - 1;
		BigDecimal value = null;
		BigDecimal value1 = null;

		List<BigDecimal> listtimsolonnhat = new ArrayList<BigDecimal>();
		List<BigDecimal> listNhanHang = new ArrayList<BigDecimal>();
		List<BigDecimal> listPayPal = new ArrayList<BigDecimal>();
		while (x < x1) {
			Date start1 = cc.subDays(start, x);

			List<MyItemsStartEnd> my = cc.tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan(
					"Thanh toán khi nhận hàng", start1, 2, 0.05);
			List<MyItemsStartEnd> my1 = cc.tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan("Thanh toán Pay-Pal",
					start1, 2, 0.05);

			String time = my.get(0).getStart();
			value = (BigDecimal) my.get(0).getValue();
			surveyMap.put(time, value);

			String time1 = my1.get(0).getStart();
			value1 = (BigDecimal) my1.get(0).getValue();
			surveyMap1.put(time1, value1);

			x++;

			listtimsolonnhat.add(value);
			listtimsolonnhat.add(value1);

			listNhanHang.add(value);
			listPayPal.add(value1);

		}
		BigDecimal t1 = findMax(listtimsolonnhat);
		model.addAttribute("tong", t1);

		double tongthuong = 0.0;
		for (int j = 0; j < listNhanHang.size(); j++) {
			tongthuong += listNhanHang.get(j).doubleValue();
		}
		double tongpaypal = 0.0;
		for (int j = 0; j < listPayPal.size(); j++) {
			tongpaypal += listPayPal.get(j).doubleValue();
		}
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("surveyMap1", surveyMap1);

		double tongTatCa = tongthuong + tongpaypal;
		double pass = tongthuong * 100 / tongTatCa;
		double fail = tongpaypal * 100 / tongTatCa;

		model.addAttribute("pass", pass);
		model.addAttribute("fail", fail);

		String formatted = format.format(tongthuong);
		model.addAttribute("tongthuong", formatted);

		String formatted1 = format.format(tongpaypal);
		model.addAttribute("tongpaypal", formatted1);

		String formatted2 = format.format(tongTatCa);
		model.addAttribute("tongtatca", formatted2);

		return "admin/thong-ke-hai-hinh-thuc-thanh-toan";
	}

	// hoan tat dau thang den nay nguoi ban
	@GetMapping("/ban-hang/thong-ke/bay-ngay-lien-tuc-nguoi-ban")
	public String thongKeTuanNguoiBan(Model model) throws ParseException {
		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		String nguoiDung = taiKhoan.getNguoiDung().getId();

		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
		Map<String, BigDecimal> surveyMap1 = new LinkedHashMap<>();

		Date end = new Date();
		List<TongTienStartEnd> homNay = cc.tongTienHomNayNguoiBan(nguoiDung, "Thanh toán khi nhận hàng", start, end);
		List<TongTienStartEnd> homNay1 = cc.tongTienHomNayNguoiBan(nguoiDung, "Thanh toán Pay-Pal", start, end);

		BigDecimal value2 = null;
		String time2 = homNay.get(0).getStart();
		value2 = (BigDecimal) homNay.get(0).getValue();
		surveyMap.put(time2, value2);

		BigDecimal value3 = null;
		String time3 = homNay1.get(0).getStart();
		value3 = (BigDecimal) homNay1.get(0).getValue();
		surveyMap1.put(time3, value3);

		int x = 0;
		int x1 = 7;
		BigDecimal value = null;
		BigDecimal value1 = null;

		List<BigDecimal> listtimsolonnhat = new ArrayList<BigDecimal>();
		List<BigDecimal> listNhanHang = new ArrayList<BigDecimal>();
		List<BigDecimal> listPayPal = new ArrayList<BigDecimal>();

		while (x < x1) {
			Date start1 = cc.subDays(start, x);
			List<MyItemsStartEnd> my = cc.tongTienTruocHomNayNguoiBan(nguoiDung, "Thanh toán khi nhận hàng", start1, 2);
			List<MyItemsStartEnd> my1 = cc.tongTienTruocHomNayNguoiBan(nguoiDung, "Thanh toán Pay-Pal", start1, 2);

			String time = my.get(0).getStart();
			value = (BigDecimal) my.get(0).getValue();
			surveyMap.put(time, value);

			String time1 = my1.get(0).getStart();
			value1 = (BigDecimal) my1.get(0).getValue();
			surveyMap1.put(time1, value1);

			x++;

			listtimsolonnhat.add(value);
			listtimsolonnhat.add(value1);

			listNhanHang.add(value);
			listPayPal.add(value1);

		}
		listtimsolonnhat.add(value2);
		listtimsolonnhat.add(value3);

		listNhanHang.add(value2);
		listPayPal.add(value3);

		BigDecimal t1 = findMax(listtimsolonnhat);
		model.addAttribute("tong", t1);

		double tongthuong = 0.0;
		for (int j = 0; j < listNhanHang.size(); j++) {
			tongthuong += listNhanHang.get(j).doubleValue();
		}
		double tongpaypal = 0.0;
		for (int j = 0; j < listPayPal.size(); j++) {
			tongpaypal += listPayPal.get(j).doubleValue();
		}

		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("surveyMap1", surveyMap1);

		double tongTatCa = tongthuong + tongpaypal;
		double pass = tongthuong * 100 / tongTatCa;
		double fail = tongpaypal * 100 / tongTatCa;

		model.addAttribute("pass", pass);
		model.addAttribute("fail", fail);

		String formatted = format.format(tongthuong);
		model.addAttribute("tongthuong", formatted);

		String formatted1 = format.format(tongpaypal);
		model.addAttribute("tongpaypal", formatted1);

		String formatted2 = format.format(tongTatCa);
		model.addAttribute("tongtatca", formatted2);

		return "nguoiban/thong-ke-hai-hinh-thuc-thanh-toan";
	}
	
	@GetMapping("/ban-hang/thong-ke/tu-dau-thang-den-nay-nguoi-ban")
	public String thongKeDauThangDennayNguoiBan(Model model) throws ParseException {
		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		String nguoiDung = taiKhoan.getNguoiDung().getId();

		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
		Map<String, BigDecimal> surveyMap1 = new LinkedHashMap<>();

		Date end = new Date();
		List<TongTienStartEnd> homNay = cc.tongTienHomNayNguoiBan(nguoiDung, "Thanh toán khi nhận hàng", start, end);
		List<TongTienStartEnd> homNay1 = cc.tongTienHomNayNguoiBan(nguoiDung, "Thanh toán Pay-Pal", start, end);

		BigDecimal value2 = null;
		String time2 = homNay.get(0).getStart();
		value2 = (BigDecimal) homNay.get(0).getValue();
		surveyMap.put(time2, value2);

		BigDecimal value3 = null;
		String time3 = homNay1.get(0).getStart();
		value3 = (BigDecimal) homNay1.get(0).getValue();
		surveyMap1.put(time3, value3);

		int x = 0;
		@SuppressWarnings("deprecation")
		int x1 = start.getDate() - 1;
		BigDecimal value = null;
		BigDecimal value1 = null;

		List<BigDecimal> listtimsolonnhat = new ArrayList<BigDecimal>();
		List<BigDecimal> listNhanHang = new ArrayList<BigDecimal>();
		List<BigDecimal> listPayPal = new ArrayList<BigDecimal>();

		while (x < x1) {
			Date start1 = cc.subDays(start, x);
			List<MyItemsStartEnd> my = cc.tongTienTruocHomNayNguoiBan(nguoiDung, "Thanh toán khi nhận hàng", start1, 2);
			List<MyItemsStartEnd> my1 = cc.tongTienTruocHomNayNguoiBan(nguoiDung, "Thanh toán Pay-Pal", start1, 2);

			String time = my.get(0).getStart();
			value = (BigDecimal) my.get(0).getValue();
			surveyMap.put(time, value);

			String time1 = my1.get(0).getStart();
			value1 = (BigDecimal) my1.get(0).getValue();
			surveyMap1.put(time1, value1);

			x++;

			listtimsolonnhat.add(value);
			listtimsolonnhat.add(value1);

			listNhanHang.add(value);
			listPayPal.add(value1);

		}
		listtimsolonnhat.add(value2);
		listtimsolonnhat.add(value3);

		listNhanHang.add(value2);
		listPayPal.add(value3);

		BigDecimal t1 = findMax(listtimsolonnhat);
		model.addAttribute("tong", t1);

		double tongthuong = 0.0;
		for (int j = 0; j < listNhanHang.size(); j++) {
			tongthuong += listNhanHang.get(j).doubleValue();
		}
		double tongpaypal = 0.0;
		for (int j = 0; j < listPayPal.size(); j++) {
			tongpaypal += listPayPal.get(j).doubleValue();
		}

		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("surveyMap1", surveyMap1);

		double tongTatCa = tongthuong + tongpaypal;
		double pass = tongthuong * 100 / tongTatCa;
		double fail = tongpaypal * 100 / tongTatCa;

		model.addAttribute("pass", pass);
		model.addAttribute("fail", fail);

		String formatted = format.format(tongthuong);
		model.addAttribute("tongthuong", formatted);

		String formatted1 = format.format(tongpaypal);
		model.addAttribute("tongpaypal", formatted1);

		String formatted2 = format.format(tongTatCa);
		model.addAttribute("tongtatca", formatted2);

		return "nguoiban/thong-ke-hai-hinh-thuc-thanh-toan";
	}

	@GetMapping("/ban-hang/thong-ke/tu-dau-nam-den-nay-nguoi-ban")
	public String thongKeTheoThangNguoiBan(Model model) throws ParseException {
		Date start = new Date();
		SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		LocalDateTime today = LocalDateTime.now();
		String dateToString = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00"));
		start = stringToDate.parse(dateToString);

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		String nguoiDung = taiKhoan.getNguoiDung().getId();

		Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
		Map<String, BigDecimal> surveyMap1 = new LinkedHashMap<>();

		int x = 0;
		@SuppressWarnings("deprecation")
		int xngay = start.getDate();
		@SuppressWarnings("deprecation")
		int x1 = start.getMonth() - 1;
		System.err.println(x1);
		BigDecimal value = null;
		BigDecimal value1 = null;

		List<BigDecimal> listtimsolonnhat = new ArrayList<BigDecimal>();
		List<BigDecimal> listNhanHang = new ArrayList<BigDecimal>();
		List<BigDecimal> listPayPal = new ArrayList<BigDecimal>();

		Date start1 = cc.subDays(start, xngay);
		System.err.println(start1);

		Date end = new Date();
		List<TongTienStartEnd> homNay = cc.tongTienHomNayNguoiBan(nguoiDung, "Thanh toán khi nhận hàng", start1, end);
		List<TongTienStartEnd> homNay1 = cc.tongTienHomNayNguoiBan(nguoiDung, "Thanh toán Pay-Pal", start1, end);

		BigDecimal value2 = null;
		String time2 = homNay.get(0).getStart();
		value2 = (BigDecimal) homNay.get(0).getValue();
		surveyMap.put(time2, value2);

		BigDecimal value3 = null;
		String time3 = homNay1.get(0).getStart();
		value3 = (BigDecimal) homNay1.get(0).getValue();
		surveyMap1.put(time3, value3);

		while (x < x1) {
			Date start2 = cc.subMonths(start1, x);
			List<MyItemsStartEnd> my = cc.tongTienTruocThangNayNguoiBan(nguoiDung, "Thanh toán khi nhận hàng", start2,
					2);
			System.err.println("Thuong" + my);
			List<MyItemsStartEnd> my1 = cc.tongTienTruocThangNayNguoiBan(nguoiDung, "Thanh toán Pay-Pal", start2, 2);
			System.err.println("pa" + my1);

			String time = my.get(0).getStart();
			value = (BigDecimal) my.get(0).getValue();
			surveyMap.put(time, value);

			String time1 = my1.get(0).getStart();
			value1 = (BigDecimal) my1.get(0).getValue();
			surveyMap1.put(time1, value1);

			x++;

			listtimsolonnhat.add(value);
			listtimsolonnhat.add(value1);

			listNhanHang.add(value);
			listPayPal.add(value1);

		}
		listtimsolonnhat.add(value2);
		listtimsolonnhat.add(value3);

		listNhanHang.add(value2);
		listPayPal.add(value3);

		BigDecimal t1 = findMax(listtimsolonnhat);
		model.addAttribute("tong", t1);

		double tongthuong = 0.0;
		for (int j = 0; j < listNhanHang.size(); j++) {
			tongthuong += listNhanHang.get(j).doubleValue();
		}
		double tongpaypal = 0.0;
		for (int j = 0; j < listPayPal.size(); j++) {
			tongpaypal += listPayPal.get(j).doubleValue();
		}

		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("surveyMap1", surveyMap1);

		double tongTatCa = tongthuong + tongpaypal;
		double pass = tongthuong * 100 / tongTatCa;
		double fail = tongpaypal * 100 / tongTatCa;

		model.addAttribute("pass", pass);
		model.addAttribute("fail", fail);

		String formatted = format.format(tongthuong);
		model.addAttribute("tongthuong", formatted);

		String formatted1 = format.format(tongpaypal);
		model.addAttribute("tongpaypal", formatted1);

		String formatted2 = format.format(tongTatCa);
		model.addAttribute("tongtatca", formatted2);

		return "nguoiban/thong-ke-hai-hinh-thuc-thanh-toan";
	}

	private static BigDecimal findMax(List<BigDecimal> list) {
		List<BigDecimal> sortedlist = new ArrayList<>(list);
		Collections.sort(sortedlist);
		return sortedlist.get(sortedlist.size() - 1);
	}

}
