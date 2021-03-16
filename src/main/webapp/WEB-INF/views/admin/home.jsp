<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="./include/header.jsp" %>

  <!-- 대시보드 본문 Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- 본문헤더 Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Dashboard</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Dashboard v1</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- 본문내용 Main content -->
    <section class="content">
      <div class="container-fluid">
      
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">최근등록한 회원목록</h3>

          <div class="card-tools">
            <button type="button" class="btn btn-tool" data-card-widget="collapse">
              <i class="fas fa-minus"></i>
            </button>
            <button type="button" class="btn btn-tool" data-card-widget="remove">
              <i class="fas fa-times"></i>
            </button>
          </div>
        </div>
        <!-- /.card-header -->
        <div class="card-body p-0">
          <ul class="users-list clearfix">
            <c:forEach items="${latest_member}" var="memberVO">
            <li>
              <img src="/resources/dist/img/default-150x150.png" alt="User Image">
              <a class="users-list-name" href="/admin/member/member_view?page=1&user_id=${memberVO.user_id}">
              ${memberVO.user_name}
              </a>
              <span class="users-list-date">
              <c:out value="${(memberVO.enabled eq 'true')?'활성':'비활성'}" />
              </span>
            </li>
            </c:forEach>
          </ul>
          <!-- /.users-list -->
        </div>
        <!-- /.card-body -->
        <div class="card-footer text-center">
          <a href="/admin/member/member_list">View All Users</a>
        </div>
        <!-- /.card-footer -->
      </div>

      <c:forEach items="${board_type_list}" var="boardTypeVO">

      	<!-- 관리자에서 생성한 게시판 개수만큼 반복문실행 -->
        <div class="card">
              <div class="card-header border-transparent">
                <h3 class="card-title">최근 ${boardTypeVO.board_name}</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
              <!-- /.card-header -->
              <div class="card-body p-0">
                <div class="table-responsive">
                  <table class="table m-0">
                    <thead>
                    <tr>
                      <th>title</th>
                      <th>writer</th>
                      <th>reg_date</th>
                      <th>view_count</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 대시보드 만들기 2번째 방법(아래) -->
                    <!-- 파일 import시 매개변수 board_type=${baordTypeVO.board_type} -->
                    <c:import url="/admin/latest/latest_board?board_type=${boardTypeVO.board_type}" />
                    </tbody>
                  </table>
                </div>
                <!-- /.table-responsive -->
              </div>
              <!-- /.card-body -->
              <div class="card-footer clearfix">
                <a href="/admin/board/board_list?board_type=${boardTypeVO.board_type}" class="btn btn-sm btn-secondary float-right">${boardTypeVO.board_name} 목록</a>
              </div>
              <!-- /.card-footer -->
            </div>
            
      </c:forEach>
      
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<%@ include file="./include/footer.jsp" %>