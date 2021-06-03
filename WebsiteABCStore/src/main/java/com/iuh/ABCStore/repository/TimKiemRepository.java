package com.iuh.ABCStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iuh.ABCStore.model.NguoiDung;
import com.iuh.ABCStore.model.TimKiem;

public interface TimKiemRepository extends JpaRepository<TimKiem,String>{
	
	TimKiem findTimKiemByKeyWord(String keyword);
	
	
	
	
	
	

}
