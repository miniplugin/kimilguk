package org.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.edu.dao.IF_ReplyDAO;
import org.edu.vo.PageVO;
import org.edu.vo.ReplyVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReplyController.java 클래스
 * 댓글 구현 Rest-API전용 컨트롤러=RestAPI서버
 * @author 김일국
 *
 */
@RestController
public class ReplyController {
	
	@Inject
	private IF_ReplyDAO replyDAO;
	
	//댓글 리스트 메서드(아래)
	@RequestMapping(value="/reply/reply_list/{bno}/{page}", method=RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> reply_list(@PathVariable("bno") Integer bno,@PathVariable("page") Integer page) throws Exception {
		//페이징 계산식 처리 시작
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);//조건은 Ajax로 호출시 page변수는 반드시 보내야 합니다.
		pageVO.setPerPageNum(3);//페이지 하단에 보이는 페이징 번호의 개수
		pageVO.setQueryPerPageNum(5);//댓글 1페이지당 보여줄 댓글 개수
		int replyCount = replyDAO.selectReplyCount(bno);
		pageVO.setTotalCount(replyCount);//전체 댓글 개수 구해서 set하는 순간이 필수 prev,next구할때필요.
		//페이지 계산식 처리 끝
		//현재 게시물에 달린 댓글 전체개수 구하기: 게시물 관리 테이블에 있는 reply_count를 가져다가 사용
		
		System.out.println("디버그 : 패스베리어블 변수 는 " + bno);
		ResponseEntity<Map<String,Object>> result = null;
		
		Map<String,Object> resultMap = new HashMap<String,Object>();//해시맵타입으로 Json저장소생성
		//Map변수=데이터형 [{'key':'List<>'},{'key':'ClassVO'},{'':''},...]
		//{"replyList":[{"reply_text":"1댓글 입력 테스트 입니다.","replyer":"관리자"},
		//             {"reply_text":"2댓글 입력 테스트 입니다.","replyer":"관리자"}]
		//}
		Map<String,Object> dummyMap1 = new HashMap<String,Object>();
		dummyMap1.put("rno", "1");
		dummyMap1.put("replyer", "관리자");
		dummyMap1.put("reply_text", "컨트롤1 댓글 입력 테스트 입니다.");
		Map<String,Object> dummyMap2 = new HashMap<String,Object>();
		dummyMap2.put("rno", "2");
		dummyMap2.put("replyer", "관리자2");
		dummyMap2.put("reply_text", "컨트롤2 댓글 입력 테스트 입니다.");
		List<Object> dummyMapList = new ArrayList<Object>();
		dummyMapList.add(0, dummyMap1);
		dummyMapList.add(1, dummyMap2);
		//resultMap.put("replyList", dummyMapList);//put메서드로 Key:Value 제이슨데이터 생성
		//-----------------------------------------------
		//dummyMapList대신 DB tbl_reply 테이블에서 조회된 결과값으로 대체.
		try {
			List<ReplyVO> replyList = replyDAO.selectReply(bno,pageVO);
			if(replyList.isEmpty()) {
				//result = null;//jsp에서 받는 값이 text 일때 적용
				result = new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NO_CONTENT);//코드204
			}else{
				resultMap.put("replyList", replyList);
				resultMap.put("pageVO", pageVO);//페이징처리때문에 추가
				//resultMap를 Json데이터로 반환하려면, jackson-databind 모듈이 필수(pom.xml)
				result = new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
			}
		} catch (Exception e) {
			result = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);//코드500
		}
		
		return result;
	}
	
	//댓글 삭제 메서드(아래) 전송방식 POST(전통방식)가 아니고, DELETE(최근방식)를 사용
	@RequestMapping(value="/reply/reply_delete/{bno}/{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> reply_delete(@PathVariable("bno") Integer bno,@PathVariable("rno") Integer rno) {
		ResponseEntity<String> result = null;
		try {
			replyDAO.deleteReply(rno);
			result = new ResponseEntity<String>("success",HttpStatus.OK);
			replyDAO.updateCountReply(bno);//서브쿼리를 사용해서 tbl_board테이블 reply_count필드를 업데이트 합니다.
		} catch (Exception e) {
			result = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}
	//댓글 수정 메서드(아래) 전송방식 POST(전통방식)가 아니고, PATCH(최근방식RestAPI)를 사용
	@RequestMapping(value="/reply/reply_update",method=RequestMethod.PATCH)
	public ResponseEntity<String> reply_update(@RequestBody ReplyVO replyVO) {
		ResponseEntity<String> result = null;
		try {
			replyDAO.updateReply(replyVO);
			result = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			result = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}
	
	//댓글 입력 매서드(아래)
	@RequestMapping(value="/reply/reply_write", method=RequestMethod.POST)
	public ResponseEntity<String> reply_write(@RequestBody ReplyVO replyVO) {
		//@RequestBody클래스는 ajax로 보내온 폼데이터를 ReplyVO 클래스에 바인딩시켜주는 애노테이션클래스
		//ResponseEntity<String> result = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		//ResponseEntity는 json텍스트를 반환하는데, 전송내용:"SUCCESS", 전송상태-HttpStatus.OK(200)
		//전송내용:e.getMessage()실패메세지값, 전송상태-HttpStatus.BAD_REQUEST(400)
		ResponseEntity<String> result = null;
		try {//예외처리를 상위 메서드로 보내지않는 이유는 RestAPI에서 예외 메세지를 개발자가 제공하기 위해서
			replyDAO.insertReply(replyVO);
			result = new ResponseEntity<String>("success", HttpStatus.OK);
			replyDAO.updateCountReply(replyVO.getBno());
		} catch (Exception e) {
			result = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return result;//ResponseEntity클래스형 String값을 ajax로 호출한 페이지로 반환. 
	}
	//기존 @Controller의 메서드 반환값은 파일위치, 대신에 @RestController의
	//위 메서드의 반환값 ResponseEntity는 json텍스트(전송내용, 전송상태값)로서 Ajax로 호출한 jsp로 리턴값을 보내게 됩니다.
}
