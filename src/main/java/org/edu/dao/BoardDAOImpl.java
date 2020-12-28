package org.edu.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.edu.vo.BoardVO;
import org.edu.vo.PageVO;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements IF_BoardDAO {

	@Inject //sql세션템플릿을 주입 인젝션 받아서 변수로 지정
	private SqlSession sqlSession;
	
	@Override
	public List<BoardVO> selectBoard(PageVO pageVO) throws Exception {
		// sql세션템플릿(select,update,delete같은 매서드가포함)매퍼쿼리 지정(아래)
		// 매퍼쿼리(위쿼리+insert등)를 실행할때, 개발자가 DB커넥션, 디스커넥션을 생각할 필요없이
		// 사용 가능한 매서드 집합을 구성해 놓은 것이 sql세션템플릿 입니다.
		return sqlSession.selectList("boardMapper.selectBoard", pageVO);
	}

}
