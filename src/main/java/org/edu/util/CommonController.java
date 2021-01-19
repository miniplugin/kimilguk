package org.edu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.edu.dao.IF_BoardDAO;
import org.edu.service.IF_MemberService;
import org.edu.vo.BoardVO;
import org.edu.vo.MemberVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * CommonController 공통사용(Admin,Home) 컨트롤러
 * @author 김일국
 *
 */
@Controller
public class CommonController {
	
	@Inject
	IF_MemberService memberService;
	
	@Inject
	IF_BoardDAO boardDAO;//첨부파일을 개별 삭제하기 위해서 인젝트 합니다.
	
	/**
	 * 첨부파일의 확장자를 비교해서 이미지인지, 엑셀,한글과같은 일반파일인지 확인하는 List객체변수
	 * 사용용도1: 게시물상세보기 첨부파일이 이미지면 미리보기이미지가 보이도록, 이미지가 아니면, 다운로드링크만 보이도록
	 * 사용용도2: 메인페이지 최근 겔러리 게시물에서 첨부파일이 있으면, 미리보기 이미지가 나오게 되는데, 
	 * 만약 첨부파일이 이미지가 아니면, 대체 이미지를 보이기 위해서 확장자 체크가 필요합니다.
	 * 변수생성 후 바로 리스트3개 입력처리.
	 */
	@SuppressWarnings("serial")
	private ArrayList<String> checkImgArray = new ArrayList<String>() {
		{
			add("gif");
			add("jpg");
			add("jpeg");
			add("png");
			add("bmp");
		}
	};
	//첨부파일 업로드할 경로를 변수값으로 가져옴 servlet-context.xml에 있는 내용
	@Resource(name="uploadPath")
	private String uploadPath;//위 uploadPaht영역의 값을 uploadPath 멤버(전역)변수에 저장.
	
	public String getUploadPath() {//컨트롤러에서 파일 삭제시 호출로 사용 
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	/**
	 * 게시물 첨부파일 이미지보기 메서드 구현(크롬에서는 문제없고, IE에서 스프링시큐리티적용 후 IE에서 지원가능)
	 * 에러메시지 처리: getOutputStream() has already been called for this response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/image_preview", method = RequestMethod.GET)//, produces = MediaType.IMAGE_JPEG_VALUE
	@ResponseBody
	public ResponseEntity<byte[]> getImageAsByteArray(@RequestParam("save_file_name") String save_file_name, HttpServletResponse response) throws IOException {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		fis = new FileInputStream(uploadPath + "/" + save_file_name);//업로드된 이미지를 fis변수 저장
		int readCount = 0;
		byte[] buffer = new byte[1024];//1k바이트 단위로 읽어 들이기 위해서
		byte[] fileArray = null;
	while((readCount = fis.read(buffer)) != -1){
		baos.write(buffer,0,readCount);
	}
	fileArray = baos.toByteArray();//바이트 단위로 되있는 변수에 아웃풋스트림내용을 저장해서 return 으로 반환
	fis.close();//고전 자바프로그램에서는 메모리 관리를 위해서 fis파일인풋스트림변수 생성후 소멸시키는 작업이 필수
	baos.close();//스프링프레임워크 기반의 프로그램구조에서는 close와 같은 메모리관리가 할 필요없습니다. = 스프링에는 가비지컬렉트기능이 내장.
	
	final HttpHeaders headers = new HttpHeaders();//크롬 개발자도구>네트워크>image_preview클릭>헤더탭확인
	String ext = FilenameUtils.getExtension(save_file_name);//파일 확장자 구하기
	switch(ext.toLowerCase()) {//확장자 소문자로 변경후 스위치 ~ 케이스문으로 분리
	case "png":
		headers.setContentType(MediaType.IMAGE_PNG);break;
	case "jpg":
		headers.setContentType(MediaType.IMAGE_JPEG);break;
	case "gif":
		headers.setContentType(MediaType.IMAGE_GIF);break;
	case "jpeg":
		headers.setContentType(MediaType.IMAGE_JPEG);break;
	case "bmp":
		headers.setContentType(MediaType.parseMediaType("image/bmp"));
		break;
	default:break;
	}

	return new ResponseEntity<byte[]>(fileArray, headers, HttpStatus.CREATED);
	}
	
	//파일 다운로드 구현한 메서드(아래)
	@RequestMapping(value="/download", method=RequestMethod.GET)
	@ResponseBody //이 애노테이션으로 지정된 메서드는 페이지 이동처리아니고, RestAPI처럼 현재페이지에 구현결과를 전송받습니다.
	public FileSystemResource download(
			@RequestParam("save_file_name") String save_file_name,
			@RequestParam("real_file_name") String real_file_name,
			HttpServletResponse response
			) throws Exception {//파일시스템리소스로 현재페이지에서 반환받음.
		File file = new File(uploadPath + "/" + save_file_name);//다운받을 경로 지정
		response.setContentType("application/download; utf-8");//파일내용중 한글이 깨지는 것 방지
		real_file_name = URLEncoder.encode(real_file_name, "UTF-8").replaceAll("\\+", "%20");
		//위에 URL엔코더는 파일명이 한글(일본어,베트남어)일때 깨지는 것을 방지
		response.setHeader("Content-Disposition", "attachment; filename=" + real_file_name);
		return new FileSystemResource(file);//실제 다운로드 시작
	}
	
	//파일 업로드= xml에서 지정한 폴더에 실제파일 저장을 구현한 메서드(아래)
	public String fileUpload(MultipartFile file) throws IOException {
		String realFileName = file.getOriginalFilename();//jsp에서 전송한 파일명->확장자를 구하려고 사용
		//폴더에 저장할 PK용 파일명 만들기(아래)
		UUID uid = UUID.randomUUID();//유니크 아이디 생성 Unique ID: 폴더에 저장할 파일명으로 사용
		//String saveFileName = uid.toString() + "." + realFileName.split("\\.")[1];//문제발생 아래코드 대체
		String saveFileName = uid.toString() + "." + StringUtils.getFilenameExtension(realFileName);
		//값.split("정규표현식");(Regular Expression):realFileName을 . 으로 분할해서 배열변수로 만드는 메서드
		//예를 들면, abc.jpg -> realFileName[0] = abc, realFileName[1] = jpg 으로 결과가 나옵니다.
		//String[] files = new String[] {saveFileName};//saveFileName 스트링형을 배열변수 files로 형변환 
		byte[] fileData = file.getBytes();//jsp폼에서 전송된 파일이 fileData변수(메모리)에 저장됩니다.
		File target = new File(uploadPath, saveFileName);//파일저장 하기 바로전 설정저장.
		FileCopyUtils.copy(fileData, target);//실제로 target폴더에 파일로 저장되는 메서드=업로드 종료
		return saveFileName;//copy로 업로드 이후에 저장된 real_file_name 스트링문자열값 1개를 반환합니다.
	}

	//REST-API서비스로 사용할때 @ResponseBody애노테이션으로 json|텍스트데이터를 반환함(아래)
	//아래는 Rest-API백엔드단, Ajax(jsp)부분은 Rest-API의 프론트엔드단.
	@RequestMapping(value="/id_check",method=RequestMethod.GET)
	@ResponseBody
	public String id_check(@RequestParam("user_id") String user_id) {
		String result = "0";//아이디 중복값을 체크하는 변수 초기값은 중복값 없음.
		//Rest-API서비스에서는 스프링을 통해서 Ajax로 에러메세지를 받을수 없기 때문에 여기서 에러를 처리해야 합니다. 
		try {
			MemberVO memberVO = memberService.readMember(user_id);
			if(memberVO != null) {//아이디 중복값이 있을 경우
				result = "1";
			}
		} catch (Exception e) {
			// 위 readMember메서드가 에러발생시
			result = e.toString();
		}
		return result;//결과값 0, 1, 에러메세지
	}
	
	@Transactional
	@RequestMapping(value="/file_delete",method=RequestMethod.POST)
	@ResponseBody //메서드 응답을 내용만 반환 받겠다고 명시 RestAPI 
	public String file_delete(@RequestParam("save_file_name") String save_file_name) {
		String result = "";
		try {
			boardDAO.deleteAttach(save_file_name);//DB에서만 지워짐.
			//실제 폴더에서 파일도 지우기(아래)
			File target = new File(uploadPath, save_file_name);
			if(target.exists()) {
				target.delete();//폴더에서 기존첨부파일 지우기
			}
			result = "success";
		} catch (Exception e) {
			result = "fail : " + e.toString();
		}
		return result;
	}

	public ArrayList<String> getCheckImgArray() {
		return checkImgArray;
	}

	public void setCheckImgArray(ArrayList<String> checkImgArray) {
		this.checkImgArray = checkImgArray;
	}

}
