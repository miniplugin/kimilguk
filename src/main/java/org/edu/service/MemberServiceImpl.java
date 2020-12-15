package org.edu.service;

import java.util.List;

import org.edu.vo.MemberVO;
import org.springframework.stereotype.Service;

/**
 * MemberServiceImpl는 DAO메서드 호출을 구현
 * @author 김일국
 *
 */
@Service
public class MemberServiceImpl implements IF_MemberService {

	@Override
	public List<MemberVO> selectMember() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
