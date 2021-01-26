<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

  <!-- 대시보드 본문 Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- 본문헤더 Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">게시판타입생성</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">게시판타입생성</li>
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
          
          <!-- form start -->
          <form name="write_form" action="/admin/bbs_type/bbs_type_write" method="post">
          
          <div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">게시판 타입 등록</h3>
              </div>
              <!-- /.card-header -->
              
                <div class="card-body">
                  <div class="form-group">
                    <label for="board_type">게시판타입</label><!-- 조건:게시판타입은 ID이기 때문에 중복값 등록불가 -->
                    <input type="text" value="" class="form-control" name="board_type" id="board_type" placeholder="게시판타입을 영문으로 입력해 주세요." required>
                  </div>
                  <div class="form-group">
                    <label for="board_name">게시판이름</label>
                    <input type="text" value="" class="form-control" name="board_name" id="board_name" placeholder="게시판명을 입력해 주세요." required>
                  </div>
                  <div class="form-group">
                  	<label for="board_sun">게시판출력순서</label>
                  	<input type="text" value="" class="form-control" name="board_sun" id="board_sun" placeholder="게시판출력 순서를 입력해 주세요" required>
                  </div>
                  
                </div>
                <!-- /.card-body -->
              
           </div>
          
          <!-- 버튼영역 시작 -->
            <div class="card-body">
            	<a href="/admin/bbs_type/bbs_type_list" class="btn btn-primary float-right mr-1">목록</a>
              	<button disabled id="btn_insert" type="submit" class="btn btn-danger float-right mr-1">등록</button>              	
              	<!-- a태그는 링크이동은 되지만, post값을 전송하지는 못합니다. 그래서, button태그를 사용. -->
            </div>
          <!-- 버튼영역 끝 -->
          </form>
          <!-- 폼내부에 버튼이 있어야지만, 전송버튼이 작동 됩니다. -->
          
          </div>
        </div>
        
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<%@ include file="../include/footer.jsp" %>
<!-- 현재 J쿼리코어파일이 footer.jsp에 있기 때문에, 아래jQuery실행은 코어 아래쪽에 있어야 함 -->
<!-- 이렇게 상단부터 프로그램 내용을 해석-컴파일하는 과정의 언어를 인터프리터형 프로그램 언어입니다.  -->
<script>
jQuery(document).ready(function() {
	jQuery("#board_type").bind("blur",function(){
		if(jQuery(this).val() != "") {
			//자바오브젝트변수의 board_type 값과 jQuery(this).val()
			var board_type_list = "${board_type_list}";//자바스크립트변수에 자바변수값을 넣기
			//정규식(정규표현식)을 이용해서 문자제거하기(아래)
			board_type_list = board_type_list.replace(/\[/g,"");//특수문자 \[제거
			board_type_list = board_type_list.replace(/\]/g,"");//특수문자 \]제거
			board_type_list = board_type_list.replace(/\s/g,"");//특수문자 \s공백제거
			var board_type_array = board_type_list.split(",");
			//alert(board_type_array[0]);//디버그배열값 확인
			//alert(jQuery(this).val());//디버그 input 게시판타입 확인
			if(jQuery.inArray(jQuery(this).val(),board_type_array) >= 0) {//자바변수값과 input 게시판타입값 비교해서 있으면
				//inArray함수는 반환값이 인덱스값을 반환합니다. 0보다크면 인덱스가 존재 
				alert("기존 게시판이 존재 합니다. 다시 입력해 주세요");
				jQuery("#btn_insert").attr("disabled",true);
			}else{
				alert("중복된 게시판이 존재하지 않습니다. 사용가능합니다.");
				jQuery("#btn_insert").attr("disabled",false);
			}
		}
	});
});
</script>