package com.iuh.ABCStore.services.Imls;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.iuh.ABCStore.model.LoaiTaiKhoan;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.repository.TaiKhoanRepository;



public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TaiKhoan taiKhoan = taiKhoanRepository.findByTenTaiKhoanEmail(username);

		if (taiKhoan != null && taiKhoan.isTrangThai() == true) {
			return toUserDetails(taiKhoan);

		} else {
			throw new UsernameNotFoundException("Tai Khoan Khong Ton Tai");
		}

	}

	private UserDetails toUserDetails(TaiKhoan taiKhoan) {
		Set<LoaiTaiKhoan> rolelist = taiKhoan.getLoaiTaiKhoan();
		ArrayList<String> strs = new ArrayList<String>();
		for (LoaiTaiKhoan loaiTaiKhoan : rolelist) {
			strs.add(loaiTaiKhoan.getTenLoaiTaiKhoan());
		}
		String[] roles = strs.toArray(new String[0]);
		return User.withUsername(taiKhoan.getTenTaiKhoanEmail()).password(taiKhoan.getMatKhau()).roles(roles).build();

	}

}
