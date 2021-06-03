package com.iuh.ABCStore.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iuh.ABCStore.model.DanhGia;


@Service
@Transactional
public interface IDanhGiaService {

	void save (DanhGia danhgia);
}
