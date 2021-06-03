package com.iuh.ABCStore.services;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.GioHang;
import com.iuh.ABCStore.model.NguoiDung;


@Service
@Transactional
public interface IGioHangService {
	
    boolean thanhToanPayPal(HashMap<String, GioHang> gioHang,NguoiDung nguoiDung,String idLienHe);

    boolean thanhToanGiaohang(HashMap<String, GioHang> gioHang,NguoiDung nguoiDung,String idLienHe);
}
