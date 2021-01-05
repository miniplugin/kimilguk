package org.edu.dao;

import java.util.List;

import org.edu.vo.ReplyVO;
import org.springframework.stereotype.Repository;

@Repository //MVC관련 애노테이션중 모델애노테이션이 @레포지토리 입니다.
public class ReplyDAOImpl implements IF_ReplyDAO {

	@Override
	public List<ReplyVO> selectReply(Integer bno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countReply(Integer bno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertReply(ReplyVO replyVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReply(ReplyVO replyVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReply(Integer rno) {
		// TODO Auto-generated method stub
		
	}

}
