package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.springboot.dto.BandDTO;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IBandDAO {
	public List<BandDTO> list();
	
	public int bandCreateDao();
}
