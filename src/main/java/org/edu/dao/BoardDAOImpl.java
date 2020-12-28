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

	@Override
	public int countBoard(PageVO pageVO) throws Exception {
		// sql세션템플릿 사용해서 게시물개수 구하기 매퍼쿼리 연결(아래)
		return sqlSession.selectOne("boardMapper.countBoard", pageVO);
	}

	@Override
	public BoardVO readBoard(Integer bno) throws Exception {
		// 게시물 상세보기 매퍼쿼리 연결(아래)
		return sqlSession.selectOne("boardMapper.readBoard", bno);
	}

	@Override
	public List<String> readAttach(Integer bno) throws Exception {
		// 게시물에 딸린 첨부파일 보기 매퍼쿼리 연결(아래)
		return sqlSession.selectList("boardMapper.readAttach", bno);
	}

	@Override
	public void updateViewCount(Integer bno) throws Exception {
		// 게시물 상세보기시 조회수 +1 업데이트 처리 매퍼쿼리 연결(아래)
		sqlSession.update("boardMapper.updateViewCount", bno);
	}

}
