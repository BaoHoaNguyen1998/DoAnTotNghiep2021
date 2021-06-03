package com.iuh.ABCStore.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;


@Entity


public class TimKiem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 276063947766646072L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String keyWord;
	private int countKeyWord;
	private String trangThai;
	
	
	@OneToMany(mappedBy = "timKiem",cascade = CascadeType.ALL)
	List<ChiTietTimKiem> dstk;
	


	
	@Override
	public String toString() {
		return  keyWord +" "+ countKeyWord;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimKiem other = (TimKiem) obj;
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

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public int getCountKeyWord() {
		return countKeyWord;
	}

	public void setCountKeyWord(int countKeyWord) {
		this.countKeyWord = countKeyWord;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public List<ChiTietTimKiem> getDstk() {
		return dstk;
	}

	public void setDstk(List<ChiTietTimKiem> dstk) {
		this.dstk = dstk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
