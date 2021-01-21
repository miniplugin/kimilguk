package org.edu.util;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverLoginApi extends DefaultApi20 {

	@Override
	public String getAccessTokenEndpoint() {
		//OAuth2.0 인증체크 네이버 Rest-API주소(아래) : 네이버에서 만들어 주는 것을 개발자가 갖다가 사용.
		//로그인해도 되는 사람인지만 체크(관리자,일반사용자인지는 권한 체크 안함<-우리개발자가 만들어야 함.)
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		// 네이버 인증체크 하는 URL주소(아래)
		return "https://nid.naver.com/oauth2.0/authorize";
	}
	
	//싱클톤 인스턴스 객체를 생성하기 위해서 인스턴스 홀더 그릇을 만듭니다.
	private static class InstanceHolder {
		private static final NaverLoginApi INSTANCE = new NaverLoginApi();
	}
	//싱글톤으로 인스턴스 객체 생성하는 방식:클래스실행을 1번만 하기 위해서 
	public static NaverLoginApi instance() {
		return InstanceHolder.INSTANCE;
	}

}
