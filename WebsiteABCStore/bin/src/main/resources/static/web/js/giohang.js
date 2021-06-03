$(document).ready(
				function () {
				loadGH();
			});

			function themSP() {
				var sl = document.getElementById("sl").value;
				var id = document.getElementById("idsp").value;
				if(sl<=0){
					$("#sl").notify("Lỗi: Số lượng sản phẩm phải lớn hơn 0 !!!", "error");
					return;
				}
				$.ajax({
					type: "GET",
					url: "/gio-hang/them-san-pham/" + id + "/" + sl,
				}).done(function(e) {
					if (e == 'errorTK') {
						window.location.replace("/dang-nhap");
					} if (e == 'errorSL') {
						$("#sl").notify("Lỗi: Số lượng sản phẩm không còn đủ !!!", "error");
					}
					else {
						$("#sl").notify("Thêm thành công vào giỏ hàng!!", "success");
						$('#dssp').html('');
						$("#sl").val(1);
						loadGH();
					}
				}).fail(function(e) {
					$("#sl").notify("Lỗi: Thêm sản phẩm không thành công !!!", "error");
					console.log("ERROR: ", e);
				});
			}
			
			
			
			
			function loadGH(){
				$.ajax({
					type: "GET",
					url: "/gio-hang-header/",
				}).done(function(e) {
					if (e == 'TKRong') {
						imgGHRong();
						return;
					}else if (e == 'GHRong') {
						imgGHRong();
						return;
					}else {
						if(e.dssp.length==0) {
							$('#tongspgh').text(e.dssp.length);
							imgGHRong();
						}else {
							$('#liTT').css('visibility', 'visible');
							$('#btnGioHang').css('visibility', 'visible');
							$('#tongspgh').text(e.dssp.length);
							var sp = e.dssp;
							for(i=0;i<sp.length;i++){
								$('#dssp').append("<div class='single-cart-box'>"+
													"<div class='cart-img'>"+
														"<a hef="+"/san-pham/"+sp[i].sanPham.id+"> <img src="+sp[i].sanPham.hinhAnh+" alt='cart-image'></a>"+
														 "<span class='pro-quantity'>"+sp[i].soLuong+"X</span>"+
													"</div>"+
															"<div class='cart-content'>"+
																		"<h6><a href="+"/san-pham/"+sp[i].sanPham.id+">"+sp[i].sanPham.tenSanPham+"</a></h6>"+
															"<span class='cart-price'>"+sp[i].sanPham.donGia.toLocaleString('it-IT', {style : 'currency', currency : 'VND'})+"</span>"+
															"</div>"+
														"<a onclick='xoaSPGioHang("+i+")' class='del-icone'><i class='ion-close'></i></a>"+
												"</div>")
							
							}
							$('#tongTienGH').text(e.tongTien.toLocaleString('it-IT', {style : 'currency', currency : 'VND'}));
						}
						}
				}).fail(function(e) {
					console.log("ERROR: ", e);
				});
			}
			
			
			function xoaSPGioHang() {
				if(id==null){
				var id = document.getElementById("rowXoa").value;
				}
				$.ajax({
					type: "GET",
					url: "/gio-hang/xoa-san-pham/"+id,
				}).done(function(e) {	
					$('#dssp').html('');
					$('#tongTienGH').html('');
					$('#gioHangChiTiet').html('');
					loadGH();
					loadGHChiTiet();
				}).fail(function(e) {
					console.log("ERROR: ", e);
				});
			}
			
			
			function xoaSPGioHang2(idsp) {
				$.ajax({
					type: "GET",
					url: "/gio-hang/delete-san-pham/"+idsp,
				}).done(function(e) {	
					$('#dssp').html('');
					$('#tongTienGH').html('');
					$('#gioHangChiTiet').html('');
					loadGH();
					loadGHChiTiet();
				}).fail(function(e) {
					console.log("ERROR: ", e);
				});
			}
			
			
			function imgGHRong() {
				$('#dssp').html("<img style='margin-left: 90px;' src='/web/img/icon/shopping-cart.png'></br><p style='margin-left: 70px;'>Chưa có sản phẩm</p>");
				$('#liTT').css('visibility', 'hidden');
				$('#btnGioHang').css('visibility', 'hidden');
				$('#tongspgh').text(0)
			}

			function loadGHChiTiet() {
				$.ajax({
					type : "GET",
					url : "/gio-hang-header/",
				}).done(function(e) {
					if (e == 'TKRong') {
						window.location.replace("/dang-nhap");
						return;
					} else if (e.dssp.length==0) {
						gioHangRong()
						return;
					} else {
						var sp = e.dssp;
						for (i = 0; i < sp.length; i++) {
							var max = sp[i].sanPham.soLuongKho - sp[i].sanPham.soLuongBan;
							$('#gioHangChiTiet').append("<tr><td class='product-thumbnail'><a href='/san-pham/'"+sp[i].sanPham.id+"'>"+
							"<img src='"+sp[i].sanPham.hinhAnh+"' alt='cart-image' /></a></td>"+
							"<td class='product-name'><a href='/san-pham/"+sp[i].sanPham.id+"'>"+sp[i].sanPham.tenSanPham+"</a></br>"+ 
							"<i>Shop bán<a style='color:orange;' href='/shop/"+sp[i].sanPham.nguoiDung.id+"'>"+sp[i].sanPham.nguoiDung.tenShop+"</a></i></td>"+
							"<td class='product-price'><span class='amount' >"+sp[i].sanPham.donGia.toLocaleString('it-IT', {style : 'currency', currency : 'VND'})+"</span></td>"+
							"<td class='product-quantity'>"+  
							"<input type='hidden' value='"+sp[i].sanPham.id+"'>"+
							"<input onchange="+""+"updateGioHang(this,'"+sp[i].sanPham.id+"')"+""+"   type='number'  value='"+sp[i].soLuong+"'  min='0' max='"+max+"'/>"+
							"</td>"+
							"<td class='product-subtotal'>"+sp[i].tongTien.toLocaleString('it-IT', {style : 'currency', currency : 'VND'})+"</td>"+
							"<td class='product-remove'><a onclick='chuyenXoa("+i+")' href='#xoaSPGH' data-toggle='modal'><i class='fa fa-times' aria-hidden='true'></i></a></tr>");
						}
						$('#tongtienHD').text(e.tongTien.toLocaleString('it-IT', {style : 'currency', currency : 'VND'}))
					}
			
				}).fail(function(e) {
					console.log("ERROR: ", e);
				});
			}
			function gioHangRong(){
				$('#bodyGH').html("<div class='error404-area ptb-100 ptb-sm-60'>"+
							          "<div class='container'>"+
									       "<div class='row'>"+
									            "<div class='col-md-12'>"+
									                "<div class='error-wrapper text-center'>"+
									                 	"<div class='error-text'>"+
									                        "<h2>Giỏ Hàng Không Có Sản Phẩm</h2>"+
									                       "<p>Không có sản phẩm nào trong giỏ hàng của bạn.</p>"+
									                    "</div>"+
									                    "<div class='search-error'>"+
									                        "<form id='search-form' action='/tim-kiem' method='get'>"+
									                            "<input name='keyWord' type='text' placeholder='Tìm Kiếm'>"+
									                            "<button><i class='fa fa-search'></i></button>"+
									                        "</form>"+
									                    "</div>"+
									                    "<div class='error-button'>"+
									                       "<a href='/'>Tiếp tục mua sắm.</a>"+
									                    "</div>"+
									                "</div>"+
									            "</div>"+
									        "</div>"+
									    "</div>"+
									"</div>")
			}

			function loadThanhToan() {
				$.ajax({
					type : "GET",
					url : "/gio-hang-header/",
				}).done(function(e) {
					if (e == 'TKRong') {
						window.location.replace("/dang-nhap");
						return;
					} else if (e.dssp.length==0) {
						window.location.replace("/");
						return;
					} else {
						var sp = e.dssp;
						for (i = 0; i < sp.length; i++) {
							$('#thanhToanChiTiet').append("<tr class='cart_item'>"+
											"<td class='product-name'><span class='product-quantity'>"+sp[i].sanPham.tenSanPham+" X"+sp[i].soLuong+"</span>"
											+"</br><i style='font-size: 12px;'>"+
											"Shop bán: <a style='color: orange;' href='/'>"+sp[i].sanPham.nguoiDung.tenShop+"</a></i></td>"+
											"<td class='product-total'><span class='amount'>"+sp[i].tongTien+"</span>"
											+"</td>"+
										"</tr>");
						}
						$('#tongtienChiTiet').text(e.tongTien.toLocaleString('it-IT', {style : 'currency', currency : 'VND'}))
					}
				}).fail(function(e) {
					console.log("ERROR: ", e);
				});
			}
			
			function updateGioHang(slInput,id) {
				  var productRow = $(slInput).parent().parent();
				  var sl = $(slInput).val();
				  		if(isEmptyOrSpaces(sl)){
				  			$.notify("Lỗi: Số lượng không được để trống !!!", "error");
				  			$('#gioHangChiTiet').html('');
							loadGHChiTiet();
							loadGH();
				  		}else if(sl==0){
				  			if(confirm("Bạn thật sự muốn xóa sản phẩm ra giỏ hàng!")){
				  				xoaSPGioHang2(id);
				  			}else{
				  				$('#gioHangChiTiet').html('');
								loadGHChiTiet();
								loadGH();
				  			}
				  		}
							 else{
								  $.ajax({
										type: "GET",
										url: "/gio-hang/sua-san-pham/"+id+"/"+sl,
									}).done(function(e) {	
										if(e=='errorSLB'){
											$.notify("Lỗi: Số lượng sản phẩm không còn đủ !!!", "error");
										}else if(e=='errorSL'){
											$.notify("Lỗi: Số lượng sản phẩm không được âm !!!", "error");
										}else if(e=='errorTK'){
											window.location.replace("/dang-nhap");
										}
										$('#gioHangChiTiet').html('');
										$('#dssp').html('');
										$('#tongTienGH').html('');
										loadGHChiTiet();
										loadGH();
									}).fail(function(e) {
										console.log(e);
									});		
							  } 
				} 
function chuyenXoa(x){
	 $('#xoaSPGH #rowXoa').val(x);
}

function isEmptyOrSpaces(str){
    return str === null || str.match(/^ *$/) !== null;
}
