package com.iuh.ABCStore.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.GioHang;
@Service
@Transactional
public class TinhTien {

	
//	public BigDecimal tongTien(HashMap<Long, GioHang> gioHang) {
//		BigDecimal count = null;
//		for (Map.Entry<Long, GioHang> list : gioHang.entrySet()) {
//			count =  count.add(list.getValue().getSanPham().getDonGia().multiply(BigDecimal.valueOf(list.getValue().getSoLuong())))    ;
//		}
//		return count;
//	}Big
	
	
	public BigDecimal tongTien(HashMap<String, GioHang> gioHang) {
		double count = 0;
		for (Map.Entry<String, GioHang> list : gioHang.entrySet()) {
			count += list.getValue().getSanPham().getDonGia().doubleValue() * list.getValue().getSoLuong();
		}
		return BigDecimal.valueOf(count);
	}
	

}
