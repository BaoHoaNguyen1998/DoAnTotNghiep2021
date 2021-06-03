package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;


@Entity

public class DanhMuc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name ="id")
	private String id;
	
	@Column(name = "ten_danh_muc",columnDefinition = "NVARCHAR(255)")
	private String tenDanhMuc;
	
	
	@OneToMany(mappedBy = "danhMuc")
	private List<SanPham> sanPhams;
	

	public DanhMuc() {
		super();
	}

	@Override
	public String toString() {
		return "DanhMuc [id=" + id + ", tenDanhMuc=" + tenDanhMuc + "]";
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
		if (!(obj instanceof DanhMuc))
			return false;
		DanhMuc other = (DanhMuc) obj;
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

	public String getTenDanhMuc() {
		return tenDanhMuc;
	}

	public void setTenDanhMuc(String tenDanhMuc) {
		this.tenDanhMuc = tenDanhMuc;
	}

	public List<SanPham> getSanPhams() {
		return sanPhams;
	}

	public void setSanPhams(List<SanPham> sanPhams) {
		this.sanPhams = sanPhams;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	

	

	

}
