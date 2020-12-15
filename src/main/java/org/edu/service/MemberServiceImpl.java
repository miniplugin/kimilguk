package org.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.edu.dao.IF_MemberDAO;
import org.edu.vo.MemberVO;
import org.springframework.stereotype.Service;

/**
 * MemberServiceImpl는 DAO메서드 호출을 구현
 * @author 김일국
 *
 */
@Service
public class MemberServiceImpl implements IF_MemberService {

	@Inject
	IF_MemberDAO memberDAO;//IF_MemberDAO 주입받아서 memberDAO오브젝트생성.
	
	@Override
	public List<MemberVO> selectMember() throws Exception {
		// 회원정보 조회 DAO클래스의 메서드호출(아래)
		return memberDAO.selectMember();
	}

}
