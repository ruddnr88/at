package com.sbs.rko.at.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.rko.at.dto.Member;

@Mapper
public interface MemberDao {

	Member getMemberById(@Param("id") int id);

}