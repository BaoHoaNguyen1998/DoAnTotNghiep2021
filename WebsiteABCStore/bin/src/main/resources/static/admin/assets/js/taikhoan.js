
	function khoaTaiKhoan() {
		var id = document.getElementById("idxoa").value;
    		$.ajax({
    			type : "GET",
    			url : "/quan-ly/tai-khoan/khoa-tai-khoan/" + id,
    		}).done(function (){
				$('#xoaTaiKhoan').modal('hide');
				alert("Thông báo: khóa tài khoản thành công !!!");
				var row = document.getElementById("rowXoa").value;
				var table =  document.getElementById("tableTK");
				table.rows[row].cells[3].innerHTML = 'false';
    		}).fail(function(e){
    			alert("Lỗi: khóa tài khoản không thành công !!!");
    			console.log("ERROR: ", e);
    		});
    }

	function xoaTaiKhoan() {
		if (confirm('Bạn thực sự muốn xóa tài khoản!!')) {
		var id = document.getElementById("idxoa").value;  
		var row = document.getElementById("rowXoa").value;  	
    			$.ajax({
    			type : "GET",
    			url : "/quan-ly/tai-khoan/xoa-tai-khoan/" + id,
    		}).done(function (resultMsgDiv){
				alert("Xác nhận: Xóa thành công tài khoản!!!");
				$('#xoaTaiKhoan').modal('hide');
				$("#resultMsgDiv").html("<p style='background-color:#67597E; color:white; padding:20px 20px 20px 20px'>" +
    					"Tài khoản với Id=" + id + " được xóa!"  +
    				"</p>");
			var table =  document.getElementById("tableTK").deleteRow(row);
    		}).fail(function(e){
    			alert("Lỗi: xóa tài khoản không thành công !!!");
    			console.log("ERROR: ", e);
    		});
			}else{
				return false;
			}
    }
	
	function chuyenModalXoa(id) {
		$('#xoaTaiKhoan #idxoa').val(id);
    }
	function chuyenModalBH(id) {
        $('#xacNhanTaiKhoan #idtkxn').val(id);
    }
	
	function xacNhanTaiKhoanEmail() {
		var id = document.getElementById("idtkxn").value;
    			$.ajax({
    			type : "GET",
    			url : "/quan-ly/tai-khoan/chap-nhan-mail/" + id,
    		}).done(function (e){
    			alert("Xác nhận: thành công tài khoản và đợi người dùng xác nhận tài khoản qua email!!!");
				$('#xacNhanTaiKhoan').modal('hide');
    		}).fail(function(e){
    			alert("Lỗi:  xác nhận tài khoản thất bại !!!");
    			console.log("ERROR: ", e);
    		});
        
    }
	
	
	function xacNhanTaiKhoanBanHang() {
        	var id = document.getElementById("idtkxn").value;
    			$.ajax({
    			type : "GET",
    			url : "/quan-ly/tai-khoan/chap-nhan-ban-hang/" + id,
    		}).done(function (e){
    			alert("Xác nhận: thành công tài khoản tài khoản !!!");
				$('#xacNhanTaiKhoan').modal('hide');
				var row = document.getElementById("rowXoa").value;
				var table =  document.getElementById("tableTK");
				table.rows[row].cells[3].innerHTML = 'true';
    		}).fail(function(e){
    			alert("Lỗi:  xác nhận tài khoản thất bại !!!");
    			console.log("ERROR: ", e);
    		});
        
    }
	
	
	function xemChiTietTK(id) {
        $.ajax({
			url: "/quan-ly/tai-khoan/" + id,
        }).done(function(data) {
			
        	$('#xemChiTietTK #id').val(data.id);
        	$('#xemChiTietTK #ngayDangKy').val(data.ngayDangKy);
        	$('#xemChiTietTK #tenDangNhap').val(data.tenTaiKhoanEmail);
        	$('#xemChiTietTK #hoTen').val(data.nguoiDung.hoTen);
        	$('#xemChiTietTK #trangThai').val(data.trangThai);
			$('#xemChiTietTK #ngaySinh').val(data.nguoiDung.ngaySinh);
			
			var loaitk = data.loaiTaiKhoan;
			var loai = '';
			for(var i=0 ; i<loaitk.length;i++){
				 loai= loai+' '+loaitk[i].tenLoaiTaiKhoan;			
			}
			$('#xemChiTietTK #loaiTaiKhoan').val(loai);
		}
        ).fail(function(e){
        	console.log(e)});
   		 }

		
		$('#tableTK td').click(function(){  
			var row = this.parentNode.rowIndex;
			$('#xoaTaiKhoan #rowXoa').val(row);
			$('#xemChiTietTK #rowSua').val(row);
		})
			
			
		function xacNhanSuaTK() {
	        if (confirm('Bạn thực sự muốn thay đổi tài khoản!!')) {
				var loaitkmoi = document.getElementById("loaiTaiKhoan").value;
				if(loaitkmoi !='admin'&&loaitkmoi!='seller'&&loaitkmoi!='user'){
					alert('Lỗi khi thay đổi loại tài khoản không đúng dữ liệu!!')
					return false;
				}
	        	var id = document.getElementById("id").value;
	    			$.ajax({
	    			type : "GET",
	    			url : "/quan-ly/tai-khoan/sua-tai-khoan/"+id+"/"+loaitkmoi,
	    		}).done(function (e){
	    			alert("Xác nhận:  Tài khoản sửa thành thành công !!!");
					$('#xemChiTietTK').modal('hide');
	    			var row = document.getElementById("rowSua").value;
					var table =  document.getElementById("tableTK");
					table.rows[row].cells[4].innerHTML = loaitkmoi;
	    		}).fail(function(e){
	    			alert("Lỗi:  Tài khoản sửa không thành công !!!");
	    			console.log("ERROR: ", e);
	    		});
	        } else {
	            return false;
	        }
	    }