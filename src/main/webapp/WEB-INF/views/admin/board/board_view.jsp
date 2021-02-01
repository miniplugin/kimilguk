<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../include/header.jsp" %>

  <!-- 대시보드 본문 Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- 본문헤더 Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">글상세보기</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">글상세보기</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- 본문내용 Main content -->
    <section class="content">
      <div class="container-fluid">
        
        <div class="row"><!-- 부트스트랩의 디자인 클래스 row -->
          <div class="col-12"><!-- 그리드시스템중 12가로칼럼 width:100% -->
          <div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">READ Board</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <strong><i class="fas fa-book mr-1"></i> title</strong>
                <p class="text-muted">
                <!-- 아래와 같은 출력형태는 EL(Express Language)표시라고 합니다 -->
                <%-- ${boardVO.title} --%>
                <!-- 위 자바의 EL출력은 보안에 취약하기 때문에 아래처럼 처리함. -->
                <c:out value="${boardVO.title}"></c:out>
                </p>

                <hr><!-- horizontal 수평선 태그 -->
                <strong><i class="fas fa-map-marker-alt mr-1"></i> content</strong>
                <p class="text-muted">
                	${boardVO.content}
                </p>
				<!-- 부트스트랩 오른쪽여백주기클래스명mr-1:(margin-right: .25rem!important;) -->
                <hr>
                <strong><i class="fas fa-pencil-alt mr-1"></i> 작성자</strong>
                <p class="text-muted">
                <c:out value="${boardVO.writer}"></c:out>
                </p>
                <c:forEach var="index" begin="0" end="1">
                	<c:if test="${boardVO.save_file_names[index] != null}">
	                	<hr>
		                <strong><i class="far fa-save mr-1"></i> 첨부파일${index}</strong>
		                <p class="text-muted">
		                <a href="/download?save_file_name=${boardVO.save_file_names[index]}&real_file_name=${boardVO.real_file_names[index]}">
		                ${boardVO.real_file_names[index]}-파일다운로드${index}
		                </a>
		                <c:set var="fileNameArray" value="${fn:split(boardVO.save_file_names[index],'.')}" />
		                <c:set var="extName" value="${fileNameArray[fn:length(fileNameArray)-1]}" />
		                <!-- length결과는 2 - 1 = 배열의 인덱스1 -->
		                <!-- 첨부파일이 이미지 인지 아닌지 비교해서 img태그를 사용할 지 결정(아래) -->
		                <!-- fn:contains함수({'jpg','gif','png'...}비교배열내용,JPG,jpg첨부파일확장자) -->
		                <c:choose>
		                	<c:when test="${fn:containsIgnoreCase(checkImgArray,extName)}">
		                		<img style="width:100%;" src="/image_preview?save_file_name=${boardVO.save_file_names[index]}&real_file_name=${boardVO.real_file_names[index]}">
		                	</c:when>
		                	<c:otherwise>
		                		<c:out value="${checkImgArray}" />
		                		<!-- 사용자홈페이지 메인 최근게시물 미리보기 이미지가 없을때 사용예정. -->
		                	</c:otherwise>
		                </c:choose>
		                <!-- true이면 이미지파일 이란 의미 -->
		                </p>
	                </c:if>
                </c:forEach>
                
              </div>
              <!-- /.card-body -->
            </div>
          
          <!-- 버튼영역 시작 -->
          <div class="card-body">
            	<a href="/admin/board/board_list?page=${pageVO.page}" class="btn btn-primary float-right mr-1">LIST ALL</a>
              	<button class="btn btn-danger float-right mr-1" id="btn_board_delete">DELETE</button>
				<a href="/admin/board/board_update?page=${pageVO.page}&bno=${boardVO.bno}" class="btn btn-warning float-right mr-1 text-white">UPDATE</a>              	
              	<!-- 부트스트랩 디자인 버튼클래스를 이용해서 a태그를 버튼모양 만들기(위) -->
              	<!-- btn클래스명이 버튼모양으로 변경, btn-primary클래스명은 버튼색상을 변경하는역할 -->
              	<!-- 
              	어떻게 스타일이 아닌 클래스에다가 넣어줌으로서 모양과 위치과 바뀌는건가요?
              	... 생각하는 부트스트랩은 html과 css를 모아놓은 집합체라고 생각하는데 
              	거기에 float-right클라스로 정해놓은곳에 오른쪽으로 가게하는 스타일이 지정되어있어서 = 부트스트랩
              	클래스 이름만 지정해줘도 그 클래스로 지정된 스타일이 적용되어서 
              	클래스 이름 지정만으로도 스타일이 적용된다고 보면...          	
              	 -->
              </div>
          <!-- 버튼영역 끝 -->
          <!-- 댓글영역 시작 -->
          <div class="card card-primary card-outline col-12">
	          <div class="card-header">
	            <h5 class="card-title">Add New Reply</h5>
	          </div>
	          <form action="board_view.html" name="reply_form" method="post">
	          <div class="card-body">
	          	<div class="form-group">
                   <label for="replyer">Replyer</label>
                   <input value="${session_username}" type="text" class="form-control" name="replyer" id="replyer" placeholder="작성자를 입력해 주세요." required>
                   <!-- 폼에서 input같은 입력태그에는 name속성이 반드시 필요, 이유는 DB에 입력할때,
                   	 값을 전송하게 되는데, 전송값을 담아두는 이름이 name가 되고, 위에서는 writer 입니다. -->
                </div>
                <div class="form-group">
                   <label for="reply_text">Reply Text</label>
                   <input type="text" class="form-control" name="reply_text" id="reply_text" placeholder="내용을 입력해 주세요." required>
                   <!-- 아래 게시판에서는 폼을 전송할때 submit 타입을 사용 하지만, 댓글은 Ajax로 전송하기 때문에, button타입으로 지정함. -->
                </div>
                <button type="button" class="btn btn-warning float-left mr-1 text-white" id="insertReplyBtn">댓글등록</button>
	          </div>
	          </form>
	          <div class="timeline">
	          	  <!-- .time-label의 before 위치 -->
		          <div class="time-label">
	                <span data-toggle="collapse" data-target="#div_reply" class="bg-red" id="btn_reply_list" style="cursor:pointer;">Reply List[<span id="reply_count">${boardVO.reply_count}</span>]&nbsp;&nbsp;</span>
	              </div>
	              <div id="div_reply" class="collapse timeline">
	              	<!-- append 토글영역 -->
	              	<!-- 페이징처리 시작 -->
			          <div class="pagination justify-content-center">
			            <ul class="pagination pageVO">
			            	<!-- 
			            	 <li class="paginate_button page-item previous disabled" id="example2_previous">
			            	 <a href="#" aria-controls="example2" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
			            	 </li>
			            	 위 이전게시물링크
			            	 <li class="paginate_button page-item active"><a href="#" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">1</a></li>
			            	 <li class="paginate_button page-item "><a href="#" aria-controls="example2" data-dt-idx="2" tabindex="0" class="page-link">2</a></li>
			            	 <li class="paginate_button page-item "><a href="#" aria-controls="example2" data-dt-idx="3" tabindex="0" class="page-link">3</a></li>
			            	 아래 다음게시물링크
			            	 <li class="paginate_button page-item next" id="example2_next">
			            	 <a href="#" aria-controls="example2" data-dt-idx="7" tabindex="0" class="page-link">Next</a>
			            	 </li>
			            	 -->
			            </ul>
			          </div>
				  	<!-- 페이징처리 끝 -->  
	              </div>
	              <!-- .time-label의 after 위치 -->
		          <!-- <div>
	                <i class="fas fa-envelope bg-blue"></i>
	                <div class="timeline-item">
	                  <h3 class="timeline-header">작성자</h3>
	                  <div class="timeline-body">
	                    	댓글 입력 테스트
	                  </div>
	                  <div class="timeline-footer">
	                    Button trigger modal
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#replyModal">
						  수정
						</button>
	                  </div>
	                </div>
	              </div> -->
	              
	          </div><!-- //.timeline -->
	             
	      </div>
          <!-- 댓글영역 끝 -->
          </div><!-- //col-12 -->
        </div><!-- //row -->
        
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<%@ include file="../include/footer.jsp" %>
<input type="hidden" id="reply_page" value="1"><!-- #btn_reply_list클릭할때 가져올 페이지값 -->
<%-- 자바스트립트용 #template 엘리먼트 제작(아래) jstl 향상된 for문과 같은 역할 
jstl을 사용하려면, jsp에서 <%@ taglib uri=... 처럼 외부 core를 가져와서 사용한 것처럼
자바스크립트에서도 외부 core를 가져와야 합니다.(아래)
--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<!-- 댓글리스트 템플릿(빵틀) 만들기(아래) -->
<%-- jsp <c:forEach items="${members}" var="member"> 같은 역할 {{#each .}} --%>
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<div class="template-div" data-rno="{{rno}}">
 <i class="fas fa-envelope bg-blue"></i>
 <div class="timeline-item">
   <h3 class="timeline-header">{{replyer}}</h3>
   <div class="timeline-body">{{reply_text}}</div>
   <div class="timeline-footer">
	 <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#replyModal">
  		수정
	 </button>
   </div>
 </div>
</div>
{{/each}}
</script>
<!-- pageVO를 디자인에 프로그램 입히는 작업=파싱하는 함수(아래) -->
<script>
var printPageVO = function(pageVO, target) {
	var paging = "";//출력변수(이전링크+페이지번호+다음링크에 대한 디자인이 저장되는 누적변수)
	//이전 댓글 링크-pageVO.prev(아래)
	if(pageVO.prev) {
		paging = paging + 
	'<li class="paginate_button page-item previous" id="example2_previous"><a href="'+(pageVO.startPage-1)+'" aria-controls="example2" data-dt-idx="0" tabindex="0" class="page-link">Previous</a></li>';
	}  	
	//pageVO를 target영역에 페이징 번호파싱-반복문사용(아래)
	for(var cnt=pageVO.startPage;cnt<=pageVO.endPage;cnt++){
		//for(cnt초기값;cnt종료값;cnt증가값){반복내용} cnt=cnt+1;누적변수
		var active = (cnt==pageVO.page)?"active":"";
		paging = paging +
		'<li class="paginate_button page-item '+active+'"><a href="'+cnt+'" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">'+cnt+'</a></li>';
		//자바스크립트에서 +'변수'+문자의 결합, "클래스영역"
	}
	if(pageVO.next) {
	//이후 댓글 링크-pageVO.next(아래)
		paging = paging + 
	'<li class="paginate_button page-item next" id="example2_next"><a href="'+(pageVO.endPage+1)+'" aria-controls="example2" data-dt-idx="7" tabindex="0" class="page-link">Next</a></li>';
	}
	target.html(paging);
}
</script>
<!-- 화면을 재구현Representation하는 함수(아래) -->
<script>
var printReplyList = function(data, target, templateObject) {
	var template = Handlebars.compile(templateObject.html());//html태그로 변환
	var html = template(data);//빅데이터를 리스트템플릿에 바인딩 결합시켜주는 역할. 변수html에 저장되었음.
	$(".template-div").remove();//화면에 보이는 댓글리스트만 지우기.
	//target.after(html);//target은 .time-label 클래스영역을 가리킵니다. after는 외부내용추가
	//target.append(html);//target은 #div_reply 아이디영역을 가리킵니다. append 내부내용추가 기존내용에 뒤붙이기
	target.prepend(html);//prepend 내부내용추가시 기존내용의 앞에 추가합니다.
};
</script>
<!-- btn_reply_list버튼에 적용한 ajax로 댓글 리스트를 구하는 함수를 외부로 뺍니다. -->
<!-- 외부로 함수를 빼는 이유는 btn_reply_list버튼에 토글기능을 적용되서, 토글기능과 Ajax기능을 분리하는 목적  -->
<script>
var replyList = function() {
	var page = $("#reply_page").val();
	//alert('선택한 페이지 값은 ' + page);//디버그
	$.ajax({ //$.getJSON 으로 대체 해도 됩니다.
		type:"post",
		url:"/reply/reply_list/${boardVO.bno}/"+page,//116게시물번호에 대한 댓글목록을 가져오는 URL
		dataType:"json",//받을때 json데이터를 받는다.
		success:function(result) {//result에는 댓글 목록을 json데이터로 받음.
			//alert("디버그" + result);
			if(typeof result=="undefined" || result=="" || result==null) {
				$("#div_reply").empty();//조회된 값이 없을때, 화면내용클리어.
				$("#div_reply").html('<div class="pagination justify-content-center"><ul class="pagination pageVO"></ul></div>');
				alert('조회된 값이 없습니다.');
			}else{
				//빵틀에 result데이터를 바인딩해서 출력합니다.
				//console.log(result);
				//var result = JSON.parse(result);//dataTayp:'text' 일때 실행 텍스트자료를 제이슨 자료로 변환.
				//console.log("여기까지" + result.replyList);//디버그용 
				printReplyList(result.replyList, $("#div_reply"), $("#template"));//화면에 출력하는 구현함수를 호출하면 실행.
				printPageVO(result.pageVO, $(".pageVO"));//result.pageVO데이터를 .pageVO클래스영역에 파싱합니다.
			}
		},
		error:function(result) {
			alert("RestApi서버에 문제가 발생했습니다. 다음에 이용해 주세요!");
		}
	});
}
</script>
<script>
/* 위 댓글 페이징에서 링크 태그의 페이지 이동을 방지하고, btn_reply_list버튼을 클릭해서   
   /reply/reply_list/${boardVO.bno}/{1}-> 링크한 페이지값이로 대체해서 실행하는 역할하는 코드(아래)
*/
$(document).ready(function(){
	$(".pageVO").on("click", "li a", function(event){
		event.preventDefault();//a태그의 기본기능인 이동기능을 막겠다는 명령.
		var page = $(this).attr("href");//현재 클릭한 페이지 값을 저장.
		//alert(page);//디버그
		$("#reply_page").val(page);
		//$("#btn_reply_list").click();//페이징번호에서 해당되는 번호를 클릭했을때, btn_reply_list버튼을클릭
		//위 버튼을 클릭하면 토글기능이 작동되기 때문에 댓글 목록만 가져오는 replyList함수를 실행(아래)
		replyList();
	});
});
</script>

<!-- 댓글 리스트 버튼 클릭시 Ajax RestApi컨트롤러 호출(아래)해서 댓글목록 Json데이터로  -->
<script>
$(document).ready(function(){
	$("#btn_reply_list").on("click", function(){//부트스트랩의 토글기능이 자동적용
		replyList();//댓글리스트를 Ajax로 호출하는 함수 실행.
	});
});
</script>
<!-- 댓글 삭제 버튼 액션 처리(아래) -->
<script>
$(document).ready(function(){
	$("#deleteReplyBtn").on("click", function(){
		var rno = $("#rno").val();//삭제할 댓글 번호값 변수
		//alert("선택한 댓글 번호는 : " + rno);//디버그용
		$.ajax({
			type:"delete",
			url:"/reply/reply_delete/${boardVO.bno}/"+rno,
			dataType:"text",//반환값 문자열
			success:function(result){
				if(result=="success") {
					alert("삭제가 성공 되었습니다.");
					var reply_count = $("#reply_count").text();//$("영역").val(input데이터),
					$("#reply_count").text(parseInt(reply_count)-1);//$("영역").text(영역안쪽의문자열)
					replyList();//댓글리스트 메서드호출 댓글영역 html재생성
					$("#replyModal").modal("hide");//모달창(팝업창)숨기기
				}
			},
			error:function(result){
				alert("RestAPI서버오류로 삭제에 실패했습니다.");
			}
		});
	});
});
</script>
<!-- 댓글 수정 버튼 액션 처리(아래) -->
<script>
$(document).ready(function() {
	$("#updateReplyBtn").on("click", function(){
		var rno = $("#rno").val();//모달창의 input태그값 변수지정
		var reply_text_modal = $("#reply_text_modal").val();//모달창의 input태그값 변수지정
		$.ajax({
			type:"patch",
			url:"/reply/reply_update",
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"PATCH"
			},
			data:JSON.stringify({//json데이터로 변환해서 RestAPI서버로 전송
				reply_text:reply_text_modal,
				rno:rno
			}),
			dataType:"text",//RestAPI에서 반환되는 값
			success:function(result){
				if(result=="success") {//=대입, ==비교, 1==='1'(비교+타입포함),
					alert("수정에 성공하였습니다.");
					$("#replyModal").modal("hide");
					replyList();
				}
			},
			error:function(result){
				alert("RestAPI서버에 문제가 있습니다.");
			}
		});
	});
});
</script>
<!-- 댓글 등록 버튼 액션 처리(아래) -->
<script>
$(document).ready(function() {
	$("#insertReplyBtn").on("click", function() {//댓글등록버튼을 클릭했을 때 구현내용(아래)
		//alert("디버그");
		//Ajax를 이용해서, 화면을 Representation (REST-API방식) 부분 화면을 재구현(아래)
		//RestAPI서버단에 보낼 변수값 정의
		var bno = "${boardVO.bno}";//자바 EL 변수값
		var reply_text = $("#reply_text").val();//댓글폼 input 태그값
		var replyer = $("#replyer").val();//댓글폼 input 태그값
		//alert(bno + ":" + reply_text + ":" + replyer);//디버그
		//return false;//디버그용 강제 중지
		if(reply_text == "" || replyer == ""){
			//불린boolean타입 1x0=0,1x1=1,0x1=0 (&& = and 두개다 true이어야 지만 모두 true)
			//불린boolean타입 1+0=1,1+1=1,0+1=1 (|| = or 둘중에 하나라도 true이면 모두true)  
			alert("댓글내용과 댓글작성자 입력은 필수 입니다.");
			return false;
		}
		$.ajax({//통신프로그램
			//여기서부터는 프론트 엔드 개발자 영역
			type:'post',//지금은 html이라서 get방식이지만, jsp로가면, post방식으로 바꿔야 합니다.
			url:'/reply/reply_write',//jsp로 가면, ReplyController 에서 지정한 url로 바꿔야 합니다.
			dataType:'text',//ReplyController에서 받은 데이터의 형식은 text형식으로 받겠다고 명시.
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"POST"
			},
			data:JSON.stringify({
				bno:bno,
				reply_text:reply_text,
				replyer:replyer
			}),
			success:function(result) {//응답이 성공하면(상태값200)위경로에서 반환받은 result(json데이터)를 이용해서 화면을 재구현
				//alert(result);//디버그용
				//지금은 html이라서 result값을 이용할 수가 없어서 댓글 더미데이터를 만듭니다.(아래)
				/*
				result = [
					//{rno:댓글번호,bno:게시물번호,replytext:"첫번째 댓글",replyer:"admin",regdate:타임스탬프}
					{rno:1,bno:15,reply_text:"첫번째 댓글",replyer:"admin",reg_date:1601234512345},//첫번째 댓글 데이터
					{rno:2,bno:15,reply_text:"두번째 댓글",replyer:"admin",reg_date:1601234512345}//두번째 댓글 데이터
				];
				*/
				//위 URL이 공공데이터생각하면,위 데이터를 화면에 구현하면, 빅데이터의 시각화로 불리게 됩니다.
				//printReplyList(빅데이터, 출력할 타켓위치, 빅데이터를 가지고 바인딩된-묶인 템플릿화면);
				//printReplyList(result, $(".time-label"), $("#template"));//화면에 출력하는 구현함수를 호출하면 실행.
				//입력이 success된 후에 페이지는 댓글갯수1증가+1페이지로 가고+replyList()댓글 목록 호출
				var reply_count = $("#reply_count").text();//$("영역").val(input데이터),
				//alert(reply_count);//디버그
				if(reply_count == ""){reply_count=0;}//DB 초기값이 null일때 체크
				$("#reply_count").text(parseInt(reply_count)+1);//$("영역").text(영역안쪽의문자열)
				$("#reply_page").val("1");
				replyList();
				$("#replyer").val("");
				$("#reply_text").val("");
			},
			error:function(result) {
				alert("RestAPI서버가 작동하지 않습니다.");
			}
		});
	} );
});
</script>
<!-- 댓글리스트에서 수정 버튼을 클릭했을때, 팝업창이 뜨는데, 그 팝업창에 내용을 동적으로 변경시켜주는 구현(아래)  -->
<script>
$(document).ready(function() {
	$(".timeline").on("click", ".template-div", function() {//.template-div 댓글 리스트영역
		//$(this);클릭한 댓글에 따라서 this는 첫번째 댓글일 수 도 있고, 두번째 댓글일 수도 있습니다.
		$("#rno").val($(this).attr("data-rno"));
		$(".modal-title").html($(this).find(".timeline-header").text());
		$("#reply_text_modal").val($(this).find(".timeline-body").text());
		//alert("디버그" + $(this).find(".timeline-body").text());
		//마우스클릭으로 선택한 댓글의 .timeline-body영역의 text문자를 
		//모달창의 #replaytext영역에 값으로 입력하겠다.
	});
});
</script>
<!-- Modal -->
<div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">작성자</h5><!-- 작성자는 동적으로 값이 바뀝니다. -->
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<input type="hidden" name="rno" id="rno" value=""><!-- 상단 $(".timeline").on("click" 액션으로 value값이 채워집니다. -->
        <input type="text" class="form-control" name="reply_text_modal" id="reply_text_modal" placeholder="내용을 입력해 주세요.">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary" id="updateReplyBtn">수정</button>
        <button type="button" class="btn btn-danger" id="deleteReplyBtn">삭제</button>
      </div>
    </div>
  </div>
</div>
<!-- 게시물 삭제 버튼 클릭시 액션(아래) -->
<form name="action_form">
	<input type="hidden" name="bno" value="${boardVO.bno}">
	<input type="hidden" name="page" value="${pageVO.page}">
</form>
<script>
$(document).ready(function(){
	$("#btn_board_delete").on("click",function(){
		//alert("디버그");
		if(confirm("정말로 삭제 하시겠습니까?")) {
			$('form[name="action_form"]').attr("method","post");
			$('form[name="action_form"]').attr("action","/admin/board/board_delete");
			$('form[name="action_form"]').submit();
		}
	});
});
</script>