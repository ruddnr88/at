package com.sbs.rko.at.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.rko.at.dao.MemberDao;
import com.sbs.rko.at.dto.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

}
