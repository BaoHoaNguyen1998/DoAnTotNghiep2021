package com.iuh.ABCStore.controller;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.iuh.ABCStore.model.DanhGia;
import com.iuh.ABCStore.model.DanhMuc;
import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.SanPham;
import com.iuh.ABCStore.model.TaiKhoan;
import com.iuh.ABCStore.repository.TaiKhoanRepository;
import com.iuh.ABCStore.services.IDanhGiaService;
import com.iuh.ABCStore.services.IDanhMucService;
import com.iuh.ABCStore.services.ISanPhamService;
import com.iuh.ABCStore.utils.Utils;

@Controller
@ComponentScan("com.iuh.BanHangOnline.services")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SanPhamController {
	

	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	@Autowired
	private ISanPhamService sanPhamRepository;

	@Autowired
	private IDanhMucService danhMucRepository;


	@Autowired
	private IDanhGiaService danhGiaService;



	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@RequestMapping("/quan-ly/san-pham/them-san-pham-moi")
	public String saveSanPham(Model model, @ModelAttribute("danhMuc") DanhMuc danhMuc,
			@ModelAttribute("sanPham") SanPham sanPham) {

		List<DanhMuc> danhMucs = danhMucRepository.findAllLoaiSanPham();
		model.addAttribute("listDanhMuc", danhMucs);

		sanPham.setDanhMuc(danhMuc);
		return "them-san-pham-moi-2";
	}

	@PostMapping(value = "/quan-ly/san-pham/them-san-pham-moi")
	public String xuLyThemSanPham(Model model, @ModelAttribute("sanPham") SanPham sanPham, HttpServletRequest request,
			@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "file1") MultipartFile file1,
			@RequestPart(value = "file2") MultipartFile file2, @RequestPart(value = "file3") MultipartFile file3)
			throws Exception {

		TaiKhoan taiKhoan = taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		NguoiDung khachHang = taiKhoan.getNguoiDung();
		sanPham.setNguoiDung(khachHang);
		sanPham.setTrangThai("Đã Xác Nhận");
		sanPham.setNgayTao(LocalDate.now());
		
		Path staticPath=Paths.get("static");
		Path imagePath=Paths.get("images");
		if(!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
		 Path anhdaidien = CURRENT_FOLDER.resolve(staticPath)
	                .resolve(imagePath).resolve(file.getOriginalFilename());
	        try (OutputStream os = Files.newOutputStream(anhdaidien)) {
	            os.write(file.getBytes());
	        }
	        Path anhPhu1 = CURRENT_FOLDER.resolve(staticPath)
	                .resolve(imagePath).resolve(file1.getOriginalFilename());
	        try (OutputStream os = Files.newOutputStream(anhPhu1)) {
	            os.write(file.getBytes());
	        }
	        Path anhPhu2 = CURRENT_FOLDER.resolve(staticPath)
	                .resolve(imagePath).resolve(file2.getOriginalFilename());
	        try (OutputStream os = Files.newOutputStream(anhPhu2)) {
	            os.write(file.getBytes());
	        }
	        Path anhPhu3 = CURRENT_FOLDER.resolve(staticPath)
	                .resolve(imagePath).resolve(file3.getOriginalFilename());
	        try (OutputStream os = Files.newOutputStream(anhPhu3)) {
	            os.write(file.getBytes());
	        }
//		File filecv = this.amazonClient.convertMultiPartToFile(file);
//		String fileName = this.amazonClient.generateFileName(file);
//		String fileUrl = "http://" + bucketName + ".s3.us-east-2.amazonaws.com/" + fileName;
//		this.amazonClient.uploadFileTos3bucket(fileName, filecv);
//		filecv.delete();
//	     String url1="http://192.168.1.2:8090/";
	        String url1="http://"+Utils.urlID()+":8090/";
	        sanPham.setHinhAnh(url1+imagePath.resolve(( file).getOriginalFilename()).toString());
		sanPham.setHinhAnh1(url1+imagePath.resolve(( file1).getOriginalFilename()).toString());
		sanPham.setHinhAnh2(url1+imagePath.resolve(( file2).getOriginalFilename()).toString());
		sanPham.setHinhAnh3(url1+imagePath.resolve(( file3).getOriginalFilename()).toString());


		sanPhamRepository.saveSanPham(sanPham);
		model.addAttribute("tx", "Thêm sản phẩm mới thành công, " + "Tiếp tục thêm sản phẩm mới");

		return "redirect:/quan-ly/san-pham/them-san-pham-moi";

	}






	@RequestMapping(value = "/{id}")
	public String getChitietSanPham(Model model, @PathVariable(name = "id") String id) {
		Optional<SanPham> sp = sanPhamRepository.findSanPhamById(id);
		sp.isPresent();
		DanhGia danhgia = new DanhGia();

		if (sp.isPresent()) {
			model.addAttribute("sanpham", sp.get());
			model.addAttribute("danhgianew", danhgia);
			return "chi-tiet-san-pham";
		}
		return "trang-chu";
	}


	@RequestMapping(value = "/san-pham-danh-gia/{id}", method = RequestMethod.POST)
	public String danhGiaSP(@ModelAttribute("danhgianew") DanhGia danhGia) {
		Optional<TaiKhoan> tk = Optional.ofNullable(taiKhoanRepository
				.findByTenTaiKhoanEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
		if (tk.isPresent()) {
			DanhGia danhgia1 = new DanhGia();
			danhgia1.setNoiDung(danhGia.getNoiDung());
			danhgia1.setNguoiDung(tk.get().getNguoiDung());
			danhgia1.setNgayDanhGia(LocalDateTime.now());
			danhgia1.setSanPham(danhGia.getSanPham());
			danhGiaService.save(danhgia1);
		}
		return "redirect:/san-pham/{id}";
	}
	
	@GetMapping("/chi-tiet-san-pham-ban-hang/{id}")
	public ResponseEntity<Object> chiTietSanPhamModal(Model model,@PathVariable("id") String id){
		try {
			SanPham sanPham = sanPhamRepository.findbyId(id);
			return new ResponseEntity<Object>(sanPham,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Null",HttpStatus.BAD_REQUEST);
		}
	} 


}
