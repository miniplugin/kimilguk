package org.edu.controller;

import java.util.List;

import javax.inject.Inject;

import org.edu.dao.IF_MemberDAO;
import org.edu.vo.MemberVO;
import org.edu.vo.PageVO;
import org.hsqldb.lib.SimpleLog;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonDataController {
	@Inject
	private IF_MemberDAO memberDAO;
	private Logger logger = Logger.getLogger(SimpleLog.class);
	
	//RestAPI서버 : 안드로이드앱에서 회원목록중 선택한 id 삭제.
	@RequestMapping(value="/android/delete/{user_id}", method=RequestMethod.POST)
	public ResponseEntity<String> androidDelete(@PathVariable("user_id") String user_id) {
		ResponseEntity<String> entity = null;
		try {
			memberDAO.deleteMember(user_id);
			entity = new ResponseEntity<>("success", HttpStatus.OK);//200값을 success텍스트와 함께 앱으로 전송
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400을 앱으로 전송
		}
		return entity;//텍스트 데이터를 리턴
	}
	
	//RestAPI인증서버 : 안드로이드앱에서 로그인에 사용됨(아래)
	@RequestMapping(value="/android/login", method=RequestMethod.POST)
	public ResponseEntity<MemberVO> androidLogin(@RequestParam("txtUsername") String user_id, @RequestParam("txtPassword") String user_pw) {
		ResponseEntity<MemberVO> entity = null;
		try {
			MemberVO memberVO = memberDAO.readMember(user_id);
			String bcryptPassword = memberVO.getUser_pw();
			//스프링 시큐리티 4.x BCryptPasswordEncoder 로 암호화된 값을 비교
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);//매개변수 10 = 암호화 강약정도.
			if(passwordEncoder.matches(user_pw, bcryptPassword)) {
				logger.debug("계정정보 일치");
				entity = new ResponseEntity<>(memberVO, HttpStatus.OK);//200
			} else {
				logger.debug("계정정보 불일치");
				entity = new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
			}
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
		}
		return entity;//json 데이터(Key:Value,)로 반환값 리턴
	}
	
	//RestAPI서버 회원목록을 출력 기능(아래)
	@RequestMapping(value="/android/list", method=RequestMethod.POST)
	public ResponseEntity<List<MemberVO>> androidMember() {
		ResponseEntity<List<MemberVO>> entity = null;
		PageVO pageVO = new PageVO();
		pageVO.setPage(1);
		pageVO.setPerPageNum(10);
		pageVO.setQueryPerPageNum(1000);//1회 쿼리에서 1000명 허용
		try {
			entity = new ResponseEntity<>(memberDAO.selectMember(pageVO),HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;		
	}
}
