package org.edu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.edu.vo.AttachVO;
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
	public List<AttachVO> readAttach(Integer bno) throws Exception {
		// 게시물에 딸린 첨부파일 보기 매퍼쿼리 연결(아래) 해시 #
		return sqlSession.selectList("boardMapper.readAttach", bno);
	}
	
	@Override
	public List<HashMap<String,Object>> readAttach_noUse(Integer bno) throws Exception {
		// 게시물에 딸린 첨부파일 보기 매퍼쿼리 연결(아래) 해시 #
		return sqlSession.selectList("boardMapper.readAttach", bno);
	}

	@Override
	public void updateViewCount(Integer bno) throws Exception {
		// 게시물 상세보기시 조회수 +1 업데이트 처리 매퍼쿼리 연결(아래)
		sqlSession.update("boardMapper.updateViewCount", bno);
	}

	@Override
	public void insertBoard(BoardVO boardVO) throws Exception {
		// 게시물 등록 매퍼쿼리 연결(아래)
		sqlSession.insert("boardMapper.insertBoard", boardVO);
	}

	@Override
	public void deleteBoard(Integer bno) throws Exception {
		// 게시물 삭제 매퍼쿼리 연결(아래)
		sqlSession.delete("boardMapper.deleteBoard", bno);
	}

	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		// 게시물 수정 매퍼궈리 연결(아래)
		// 위쪽의 메서드인 updateBoard매서드의 매개변수 해석(아래) BoardVO클래스는 개발자생성한 참조형데이터타입
		//jsp에서 update_form태그에서 전송된 값 boardVO클래스에 담겨서 데이터를 받습니다.
		//함수는 오브젝트 생성해야지만, 사용이 가능합니다.자바에선 new, 스프링에선 Inject
		//BoardVO 개발자 선언한 클래스, 데이터클래스, 오브젝트클래스 - C언어 구조체
		//String 자바가 선언한 클래스
		sqlSession.update("boardMapper.updateBoard", boardVO);
	}

	@Override
	public void insertAttach(String save_file_name, String real_file_name) throws Exception {
		// 첨부파일 입력 매퍼쿼리 연결(아래)
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("save_file_name", save_file_name);
		paramMap.put("real_file_name", real_file_name);
		sqlSession.insert("boardMapper.insertAttach", paramMap);
	}

	@Override
	public void deleteAttach(String save_file_name) throws Exception {
		// 첨부파일 1개 삭제 매퍼쿼리 연결(아래)
		sqlSession.delete("boardMapper.deleteAttach", save_file_name);
	}

	@Override
	public void deleteAttachAll(Integer bno) throws Exception {
		// 해당 게시물의 첨부파일 모두 삭제 매퍼쿼리 연결(아래)
		sqlSession.delete("boardMapper.deleteAttachAll", bno);
	}

	@Override
	public void updateAttach(String save_file_name, String real_file_name, Integer bno) throws Exception {
		// 해당 게시물의 첨부파일 업데이트 매퍼쿼리 연결(아래)
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("save_file_name", save_file_name);
		paramMap.put("real_file_name", real_file_name);
		paramMap.put("bno", bno);
		sqlSession.insert("boardMapper.updateAttach", paramMap);
	}

}
