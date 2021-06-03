
package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;



@Entity

public class DanhGia implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name ="id")
	private String id;

	@Column(name = "noi_dung", columnDefinition = "NVARCHAR(255)")
	private String noiDung;
	
	private LocalDateTime ngayDanhGia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nguoi_dung_id", referencedColumnName = "id")
	private NguoiDung nguoiDung;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "san_pham_id", referencedColumnName = "id")
	private SanPham sanPham;

	public DanhGia() {
		super();
	}

	@Override
	public String toString() {
		return "DanhGia [id=" + id + ", noiDung=" + noiDung + ", nguoiDung=" + nguoiDung.getHoTen() + ", sanPham=" + sanPham.getId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DanhGia))
			return false;
		DanhGia other = (DanhGia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public LocalDateTime getNgayDanhGia() {
		return ngayDanhGia;
	}

	public void setNgayDanhGia(LocalDateTime ngayDanhGia) {
		this.ngayDanhGia = ngayDanhGia;
	}

	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
	

	

}
