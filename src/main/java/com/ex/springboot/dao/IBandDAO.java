package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.springboot.dto.BandDTO;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IBandDAO {
	public List<BandDTO> bandList();
	
	public BandDTO myBand(String band_code);
	
	public int bandCreateDao(@Param("band_name") String band_name, 
			@Param("band_admin") String band_admin, 
			@Param("band_content") String band_content,
			@Param("band_thumnail") String band_thumnail);
}
