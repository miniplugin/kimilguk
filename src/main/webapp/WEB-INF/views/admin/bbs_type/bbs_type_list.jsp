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
            <h1 class="m-0">게시판타입리스트</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">게시판타입리스트</li>
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
            <div class="card"><!-- 부트스트랩의 카드 클래스:네모난 디자인 -->
              
              <div class="card-body table-responsive p-0">
                <table class="table table-hover text-nowrap">
                  <thead>
                    <tr>
                      <th>번호</th>
                      <th>게시판타입</th><!-- 테이블 헤드 타이틀태그th -->
                      <th>게시판이름</th>
                      <th>출력순서</th>
                    </tr>
                  </thead>
                  <tbody>
                  <!-- jstl core를 갖다쓰는 이유는 향상된 for반복문을 사용하기 위해서 지정(아래) -->
                  <c:forEach items="${board_type_list}" var="boardTypeVO" varStatus="status">
                  	<tr>
                      <td>
                      ${status.count}
                      </td>
                      <td>
                      <a href="/admin/bbs_type/bbs_type_update?board_type=${boardTypeVO.board_type}">
                      ${boardTypeVO.board_type}
                      </a>
                      </td>
                      <td>
                      ${boardTypeVO.board_name}
                      </td>
                      <td>
                      ${boardTypeVO.board_sun}
                      </td>
                    </tr>
                  </c:forEach>
                    
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
            
            </div>
            <!-- /.card -->
            
            <!-- 버튼영역 시작 -->
              <div class="card-body">
              	<a href="/admin/bbs_type/bbs_type_write" class="btn btn-primary float-right">게시판생성</a>
              </div>
            <!-- 버튼영역 끝 -->
            
          </div>
        </div>
        
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<%@ include file="../include/footer.jsp" %>