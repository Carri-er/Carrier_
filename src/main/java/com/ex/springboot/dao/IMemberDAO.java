package com.ex.springboot.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ex.springboot.dto.MemberDTO;

@Mapper
public interface IMemberDAO {
	public List<MemberDTO> list();
}
