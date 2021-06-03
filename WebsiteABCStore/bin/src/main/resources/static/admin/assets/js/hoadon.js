
	function ktraXoaHD(idHoaDon) {
        if (confirm('Bạn thực sự muốn xóa hóa đơn này!!!')) {
    		var workingObject = $(document.getElementById("xoaHD"));
    			$.ajax({
    			type : "GET",
    			url : "/quan-ly/hoa-don/xoa-hoa-don/"+ idHoaDon,
    		}).done(function (resultMsgDiv){
    			$("#resultMsgDiv").html("<p style='background-color:#67597E; color:white; padding:20px 20px 20px 20px'>" +
    					"Hóa Đơn với Id=" + idHoaDon + " được xóa!"  +
    				"</p>");

    			workingObject.closest("tr").remove();
    		
    		}).fail(function(e){
    			alert("Lỗi: Xóa hóa đơn không thành công !!!");
    			console.log("ERROR: ", e);
    		});
        } else {
            return false;
        }
    }
	
	
	function xacNhanHoanThanh(id) {
        if (confirm('Hóa đơn này đã thực sự hoàn thành')) {
        	var workingObject = $(document.getElementById("xacNhan"));
        	alert(id)
    			$.ajax({
    			type : "GET",
    			url : "/quan-ly/hoa-don/hoan-thanh/" + id,
    		}).done(function (e){
    			alert("Xác nhận:  Hóa đơn giao hàng thành công !!!");
    			workingObject.closest("tr").remove();
    		}).fail(function(e){
    			alert("Lỗi:  Hóa đơn xác nhận không thành công !!!");
    			console.log("ERROR: ", e);
    		});
        } else {
            return false;
        }
    }
	
	
	function xemChiTietHD(id) {
        $.ajax({
            url: "/quan-ly/hoa-don/" + id,
        }).done(function(hd) {
        	$('#xemChiTietHD #id').val(hd.id);
        	$('#xemChiTietHD #ngayLap').val(hd.ngayLap);
        	$('#xemChiTietHD #hinhThucThanhToan').val(hd.hinhThucThanhToan);
        	$('#xemChiTietHD #tongTien').val(hd.tongTien);
        	$('#xemChiTietHD #trangThai').val(hd.trangThai);
        	$('#xemChiTietHD #tienShip').val(hd.tienShip);
        	$('#xemChiTietHD #hoTenNguoiNhan').val(hd.diaChiGiaoHang.hoTen);
        	$('#xemChiTietHD #diaChi').val(hd.diaChiGiaoHang.diaChi);
        	$('#xemChiTietHD #sdt').val(hd.diaChiGiaoHang.soDienThoai);
			
        	var sp = hd.dssp;
      		 for (var i = 0; i < sp.length; i++){
      			$("#dssp").append("<div class='col-md-3 col-sm-6'>"+
                        "<label>Tên Sản Phẩm</label>"+
                       " <input type='text' class='form-control' value="+sp[i].sanPham.tenSanPham+" readonly  id='hoTenNguoiNhan' name='hoTenNguoiNhan' >"+
                       "</div>"+
                       "<div class='col-md-3 col-sm-6'>"+
                       " <label>Số Lượng</label>"+
                       " <input type='text' class='form-control' value="+sp[i].soLuong+" readonly id='diaChi' name='diaChi' >"+
                      " </div>"+
                      "<div class='col-md-3 col-sm-6'>"+
                      " <label>Đơn Giá</label>"+
                      " <input type='text' class='form-control' value="+sp[i].sanPham.donGia+" readonly id='diaChi' name='diaChi' >"+
                     " </div>"+
                       "<div class='col-md-3 col-sm-6'>"+
                       "  <label>Tổng Tiền</label>"+
                       " <input type='text' class='form-control' value="+sp[i].tongTien+" readonly id='sdt' name='sdt' >"+
                     "</div> ")}
             }
        ).fail(function(e){
        	console.log(e)});
    }
		$(document).on('hide.bs.modal','#xemChiTietHD', function () {
			$("#dssp").html("");
		});
		function xacNhanSuaHD() {
	        if (confirm('Bạn thực sự muốn thay đổi hóa đơn!!')) {
				var tt = document.getElementById("trangThai").value;
				if(tt !='Đang Giao Hàng'&&tt!='Chờ Xử Lý'&&tt!='Đã Thanh Toán'){
					alert('Lỗi khi thay đổi trạng thái không đúng dữ liệu!!')
					return false;
				}
	        	var id = document.getElementById("id").value;
	    			$.ajax({
	    			type : "GET",
	    			contentType: "application/json; charset=utf-8",
	    			url : "/quan-ly/hoa-don/sua-hoa-don/"+id+"/"+tt,
	    		}).done(function (e){
	    			alert("Xác nhận:  Hóa đơn sửa thành thành công !!!");
	    			location.reload();
	    		}).fail(function(e){
	    			alert("Lỗi:  Hóa đơn sửa không thành công không thành công !!!");
	    			console.log("ERROR: ", e);
	    		});
	        } else {
	            return false;
	        }
	    }