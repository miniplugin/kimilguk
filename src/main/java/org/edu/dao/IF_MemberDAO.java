package org.edu.dao;

import java.util.List;

import org.edu.vo.MemberVO;

/**
 * 회원관리 DAO 메서드 명세(목차) 인터페이스.
 * @author 김일국
 *
 */
public interface IF_MemberDAO {
	public List<MemberVO> selectMember() throws Exception; 
}
