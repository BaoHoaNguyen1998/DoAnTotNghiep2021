package com.iuh.ABCStore.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime; 

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.ChiTietHoaDon;
import com.iuh.ABCStore.model.HoaDon;
import com.iuh.ABCStore.model.MyItem;
import com.iuh.ABCStore.model.MyItemsStartEnd;
import com.iuh.ABCStore.model.TongTienStartEnd;
import com.iuh.ABCStore.repository.ChiTietHoaDonRepository;
import com.iuh.ABCStore.repository.HoaDonRepository;
import com.iuh.ABCStore.repository.TaiKhoanRepository;

@Service
@Transactional
public class ThongKeUtil {
	@Autowired
	private TaiKhoanRepository taiKoan;
	@Autowired
	private HoaDonRepository hoaDon;
	@Autowired
	private ChiTietHoaDonRepository chiTiet;

	public List<MyItem> reportReceipt(Date date, int limit) {
//		 LocalDateTime lc = LocalDateTime.now();
//		 date = Date.from(lc.atZone(ZoneId.systemDefault()).toInstant());
		List<MyItem> list = new ArrayList<>();
		for (int i = limit - 1; i >= 0; i--) {
			Date d = subDays(date, i);

			MyItem myItem = new MyItem();
			myItem.setTime(covertD2S(d));
			myItem.setValue(taiKoan.countByNgayDangKy(d));

			System.out.println(d);

			list.add(myItem);
		}
		return list;
	}

	public List<MyItem> reportReceipt1(Date date, int limit) {

		List<MyItem> list = new ArrayList<>();
		for (int i = limit - 1; i >= 0; i--) {
			Date d = subDays(date, i);
			LocalDateTime ldt = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
			MyItem myItem = new MyItem();
			myItem.setTime(covertD2S(d));
			myItem.setValue(hoaDon.countByNgayLap(ldt));

			list.add(myItem);
		}
		return list;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<MyItemsStartEnd> soLuongHoaDonHomNay(Date start, Date end) throws ParseException {

		List<MyItemsStartEnd> list = new ArrayList<>();

		LocalDateTime ldt1 = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
		LocalDateTime ldt = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

		MyItemsStartEnd myItem = new MyItemsStartEnd();
		myItem.setStart(covertD2S(start));
		myItem.setEnd(covertD2S(end));
		myItem.setValue(hoaDon.countAllHoaDonsByNgayLapBetween(ldt1, ldt));

		list.add(myItem);

		return list;
	}

	public List<MyItemsStartEnd> soLuongHoaDonTungNgayTruocHomNay(Date start, int days) throws ParseException {

		List<MyItemsStartEnd> list = new ArrayList<>();

		LocalDateTime ldt1 = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
		for (int i = days - 1; i >= 0; i--) {
			Date dat = subDays(start, i);

			LocalDateTime date = LocalDateTime.ofInstant(dat.toInstant(), ZoneId.systemDefault());

			MyItemsStartEnd myItem = new MyItemsStartEnd();
			System.err.println(date + " - " + ldt1);

			myItem.setStart(covertD2S(dat));
			myItem.setEnd(covertD2S(start));

			myItem.setValue(hoaDon.countAllHoaDonsByNgayLapBetween(date, ldt1));

			list.add(myItem);
		}

		return list;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TongTienStartEnd> tongTienHoaDonHomNay(Date start, Date end) throws ParseException {

		List<TongTienStartEnd> list = new ArrayList<>();
		LocalDateTime ldt = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
		LocalDateTime ldt1 = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

//		MyItemsStartEnd myItem = new MyItemsStartEnd();

		TongTienStartEnd myItem = new TongTienStartEnd();
		myItem.setStart(covertD2S(start));
		myItem.setEnd(covertD2S(end));

		List<HoaDon> listHd = hoaDon.findAllHoaDonsByNgayLapBetween(ldt, ldt1);
		long tong = 0;
		for (int i = 0; i < listHd.size(); i++) {
			tong = tong + listHd.get(i).getTongTien().longValue();
		}

		myItem.setValue(BigDecimal.valueOf(tong));

		list.add(myItem);

		return list;
	}

	public List<MyItemsStartEnd> tongTienHoaDonTungNgayTruocHomNay(Date start, int days) throws ParseException {

		List<MyItemsStartEnd> list = new ArrayList<>();
		LocalDateTime ldt1 = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());

		for (int i = days - 1; i >= 0; i--) {

			Date dat = subDays(start, i);
			LocalDateTime date = LocalDateTime.ofInstant(dat.toInstant(), ZoneId.systemDefault());

			List<HoaDon> listHd = hoaDon.findAllHoaDonsByNgayLapBetween(date, ldt1);// truyen vao thoi diem bat dau -
																					// hien tai

			MyItemsStartEnd myItem = new MyItemsStartEnd();
			myItem.setStart(covertD2S(dat));
			myItem.setEnd(covertD2S(start));

			double tong = 0.0;
			for (int j = 0; j < listHd.size(); j++) {
				// tong = tong + listHd.get(j).getTongTien()

				tong += (listHd.get(j).getTongTien().doubleValue()) * 0.95;
				System.err.println(tong);
			}

			myItem.setValue(BigDecimal.valueOf(tong));

			list.add(myItem);
		}

		return list;
	}
	//////////////////////////////////////////////// /////////////////////////////////////////////////////////

	public List<MyItemsStartEnd> tongTienHoaDonTungNgayTruocHomNayTheoHinhThucThanhToan(String hinhThucThanhToan,
			Date start, int days, double phanTram) throws ParseException {

		List<MyItemsStartEnd> list = new ArrayList<>();
		LocalDateTime ldt1 = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());

		for (int i = days - 1; i >= 0; i--) {

			Date dat = subDays(start, i);
			LocalDateTime date = LocalDateTime.ofInstant(dat.toInstant(), ZoneId.systemDefault());

			List<HoaDon> listHd = hoaDon.findAllHoaDonsByHinhThucThanhToanAndTrangThaiAndNgayLapBetween(
					hinhThucThanhToan, "Đã Thanh Toán", date, ldt1);// truyen vao thoi diem bat dau -


			MyItemsStartEnd myItem = new MyItemsStartEnd();
			myItem.setStart(covertD2S(dat));
			myItem.setEnd(covertD2S(start));

			double tong = 0.0;
			for (int j = 0; j < listHd.size(); j++) {

				tong += (listHd.get(j).getTongTien().doubleValue()) * phanTram;
			}

			myItem.setValue(BigDecimal.valueOf(tong));

			list.add(myItem);
		}

		return list;
	}

	public List<TongTienStartEnd> tongTienHoaDonHomNayByHinhThucThanhToan(String hinhThucThanhToan, Date start,
			Date end) throws ParseException {

		List<TongTienStartEnd> list = new ArrayList<>();
		LocalDateTime ldt = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
		LocalDateTime ldt1 = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

		TongTienStartEnd myItem = new TongTienStartEnd();
		myItem.setStart(covertD2S(start));
		myItem.setEnd(covertD2S(end));

		List<HoaDon> listHd = hoaDon.findAllHoaDonsByHinhThucThanhToanAndTrangThaiAndNgayLapBetween("Đã Thanh Toán",
				hinhThucThanhToan, ldt, ldt1);
		long tong = 0;
		for (int i = 0; i < listHd.size(); i++) {
			tong = tong + listHd.get(i).getTongTien().longValue();
		}

		myItem.setValue(BigDecimal.valueOf(tong));

		list.add(myItem);

		return list;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// nguoi ban

	public List<MyItemsStartEnd> tongTienTruocHomNayNguoiBan(String nguoiDung, String hinhThucThanhToan, Date start,
			int days) throws ParseException {

		List<MyItemsStartEnd> list = new ArrayList<>();
		LocalDateTime ldt1 = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());

		double tong = 0.0;
		for (int i = days - 1; i >= 0; i--) {

			Date dat = subDays(start, i);
			LocalDateTime date = LocalDateTime.ofInstant(dat.toInstant(), ZoneId.systemDefault());
			List<ChiTietHoaDon> listCt = chiTiet.findChiTiet(date, ldt1);
//			List<ChiTietHoaDon> listCt = hoaDon.findAllChiTietHoaDonByHinhThucThanhToanAndTrangThaiAndNgayLapBetween(hinhThucThanhToan, "Đã Thanh Toán",date,ldt1);

			MyItemsStartEnd myItem = new MyItemsStartEnd();
			myItem.setStart(covertD2S(dat));
			myItem.setEnd(covertD2S(start));

			for (int j = 0; j < listCt.size(); j++) {
				if (listCt.get(j).getSanPham().getNguoiDung().getId().equals(nguoiDung)
						&& listCt.get(j).getHoaDon().getHinhThucThanhToan().equals(hinhThucThanhToan)
						&& listCt.get(j).getHoaDon().getTrangThai().equals("Đã Thanh Toán")) {
					System.err.println(listCt.get(j).getHoaDon().getNgayLap());
					tong = (tong + listCt.get(j).getTongTien().doubleValue()) * 0.95;
				}

			}
			myItem.setValue(BigDecimal.valueOf(tong));

			list.add(myItem);
		}

		return list;
	}

	public List<TongTienStartEnd> tongTienHomNayNguoiBan(String nguoiBan, String hinhThucThanhToan, Date start,
			Date end) throws ParseException {

		List<TongTienStartEnd> list = new ArrayList<>();
		LocalDateTime ldt = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
		LocalDateTime ldt1 = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

		TongTienStartEnd myItem = new TongTienStartEnd();
		myItem.setStart(covertD2S(start));
		myItem.setEnd(covertD2S(end));

		List<ChiTietHoaDon> listCt = chiTiet.findChiTiet(ldt, ldt1);
		double tong = 0;
		for (int j = 0; j < listCt.size(); j++) {
			if (listCt.get(j).getSanPham().getNguoiDung().getId().equals(nguoiBan)
					&& listCt.get(j).getHoaDon().getHinhThucThanhToan().equals(hinhThucThanhToan)
					&& listCt.get(j).getHoaDon().getTrangThai().equals("Đã Thanh Toán")) {
				System.err.println(listCt.get(j).getHoaDon().getNgayLap());
				tong = (tong + listCt.get(j).getTongTien().doubleValue()) * 0.95;
			}

		}

		myItem.setValue(BigDecimal.valueOf(tong));

		list.add(myItem);

		return list;
	}

//////////////////////////////////////////////// Month ////////////////////////////////////////////////////////////
	
	public List<MyItemsStartEnd> tongTienTruocThangNayNguoiBan(String nguoiDung, String hinhThucThanhToan, Date start,
			int months) throws ParseException {

		List<MyItemsStartEnd> list = new ArrayList<>();
		LocalDateTime ldt1 = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());

		double tong = 0.0;
		for (int i = months - 1; i >= 0; i--) {

			Date dat = subMonths(start, i);
			LocalDateTime date = LocalDateTime.ofInstant(dat.toInstant(), ZoneId.systemDefault());
			List<ChiTietHoaDon> listCt = chiTiet.findChiTiet(date, ldt1);

			for (int j = 0; j < listCt.size(); j++) {
				if (listCt.get(j).getSanPham().getNguoiDung().getId().equals(nguoiDung)
						&& listCt.get(j).getHoaDon().getHinhThucThanhToan().equals(hinhThucThanhToan)
						&& listCt.get(j).getHoaDon().getTrangThai().equals("Đã Thanh Toán")) {
					System.err.println(listCt.get(j));
					tong = (tong + listCt.get(j).getTongTien().doubleValue()) * 0.95;
				}

			}
			MyItemsStartEnd myItem = new MyItemsStartEnd();
			myItem.setStart(covertD2M(dat));
			myItem.setEnd(covertD2M(start));
			myItem.setValue(BigDecimal.valueOf(tong));

			list.add(myItem);
		}

		return list;
	}
	

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public Date subDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}
	
	public Date subMonths(Date date, int months) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -months);
		return cal.getTime();
	}

	public String covertD2S(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyy");
		return df.format(date);
	}
	
	public String covertD2M(Date date) {
		DateFormat df = new SimpleDateFormat("MM/yyy");
		return df.format(date);
	}

}
