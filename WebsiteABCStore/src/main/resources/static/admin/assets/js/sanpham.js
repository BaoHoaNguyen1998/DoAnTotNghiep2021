	function xemChiTietSP(id) {
		console.log("oh ");
        $.ajax({
			url: "/chi-tiet-san-pham-ban-hang/" + id,
        }).done(function(data) {
			
        	$('#xemChiTietSP #id').val(data.id);
        	
		}
        ).fail(function(e){
        	console.log(e)});
   		 }





function khoaTaiKhoan() {
	var id = document.getElementById("idxoa").value;
	$.ajax({
		type: "GET",
		url: "/quan-ly/tai-khoan/khoa-tai-khoan/" + id,
	}).done(function() {
		$('#xoaTaiKhoan').modal('hide');
		alert("Thông báo: khóa tài khoản thành công !!!");
		location.reload();
	}).fail(function(e) {
		alert("Lỗi: khóa tài khoản không thành công !!!");
		console.log("ERROR: ", e);
	});
}

function xoaTaiKhoan() {
	if (confirm('Bạn thực sự muốn xóa tài khoản!!')) {
		var id = document.getElementById("idxoa").value;
		$.ajax({
			type: "GET",
			url: "/quan-ly/tai-khoan/xoa-tai-khoan/" + id,
		}).done(function(resultMsgDiv) {
			alert("Xác nhận: Xóa thành công tài khoản!!!");
			$('#xoaTaiKhoan').modal('hide');
			location.reload();
		}).fail(function(e) {
			alert("Lỗi: xóa tài khoản không thành công !!!");
			console.log("ERROR: ", e);
		});
	} else {
		return false;
	}
}
			function xoaSanPham() {
				if (confirm('Bạn thực sự muốn xóa sản phẩm!!')) {
					var id = document.getElementById("idxoa").value;
					$.ajax({
						type: "GET",
						url: "/quan-ly/san-pham/xoa-san-pham/" + id,
						
					}).done(function(resultMsgDiv) {
						alert("Xác nhận: Xóa thành công sản phẩm!!!");
						$('#xoaSanPham').modal('hide');
						location.reload();
					}).fail(function(e) {
			
			location.reload();
		});
				} else {
					return false;
				}
			}

function chuyenModalXoa(id) {
	$('#xoaSanPham #idxoa').val(id);
	console.log(id);
}
function chuyenModalBH(id) {
	$('#xacNhanTaiKhoan #idtkxn').val(id);
}

function xacNhanTaiKhoanEmail() {
	var id = document.getElementById("idtkxn").value;
	$.ajax({
		type: "GET",
		url: "/quan-ly/tai-khoan/chap-nhan-mail/" + id,
	}).done(function(e) {
		alert("Xác nhận: thành công tài khoản và đợi người dùng xác nhận tài khoản qua email!!!");
		$('#xacNhanTaiKhoan').modal('hide');
	}).fail(function(e) {
		alert("Lỗi:  xác nhận tài khoản thất bại !!!");
		console.log("ERROR: ", e);
	});

}


function xacNhanTaiKhoanBanHang() {
	var id = document.getElementById("idtkxn").value;
	$.ajax({
		type: "GET",
		url: "/quan-ly/tai-khoan/chap-nhan-ban-hang/" + id,
	}).done(function(e) {
		alert("Xác nhận: thành công tài khoản tài khoản !!!");
		$('#xacNhanTaiKhoan').modal('hide');
		location.reload();
	}).fail(function(e) {
		alert("Lỗi:  xác nhận tài khoản thất bại !!!");
		console.log("ERROR: ", e);
	});

}


function xemChiTietTK(id) {
	$.ajax({
		url: "/quan-ly/tai-khoan/" + id,
	}).done(function(data) {
		console.log(data)
		$('#xemChiTietTK #id').val(data.id);
		$('#xemChiTietTK #ngayDangKy').val(data.ngayDangKy);
		$('#xemChiTietTK #tenDangNhap').val(data.tenTaiKhoanEmail);
		$('#xemChiTietTK #hoTen').val(data.nguoiDung.hoTen);
		$('#xemChiTietTK #trangThai').val(data.trangThai);
		$('#xemChiTietTK #ngaySinh').val(data.nguoiDung.ngaySinh);

		var loaitk = data.loaiTaiKhoan;
		var loai = '';
		for (var i = 0; i < loaitk.length; i++) {
			loai = loai + ' ' + loaitk[i].tenLoaiTaiKhoan;
		}
		$('#xemChiTietTK #loaiTaiKhoan').val(loai);
	}
	).fail(function(e) {
		console.log(e)
	});
}

function xacNhanSuaTK() {
	if (confirm('Bạn thực sự muốn thay đổi tài khoản!!')) {
		var loaitkmoi = document.getElementById("loaiTaiKhoan").value;

		if (loaitkmoi != 'admin' && loaitkmoi != 'seller' && loaitkmoi != 'user') {
			alert('Lỗi khi thay đổi loại tài khoản không đúng dữ liệu!!')
			return false;
		}
		var id = document.getElementById("id").value;
		$.ajax({
			type: "GET",
			url: "/quan-ly/tai-khoan/sua-tai-khoan/" + id + "/" + loaitkmoi,
		}).done(function(e) {
			alert("Xác nhận:  Tài khoản sửa thành thành công !!!");
			location.reload();
		}).fail(function(e) {
			alert("Lỗi:  Tài khoản sửa không thành công !!!");
			console.log("ERROR: ", e);
		});
	} else {
		return false;
	}
}