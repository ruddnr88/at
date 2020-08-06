package com.sbs.rko.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("BeforeActionInterceptor")
	HandlerInterceptor BeforeActionInterceptor;

	// needToLoginInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("NeedToLoginInterceptor")
	HandlerInterceptor NeedToLoginInterceptor;

	// needToLogoutInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("NeedToLogoutInterceptor")
	HandlerInterceptor NeedToLogoutInterceptor;

	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// beforeActionInterceptor 를 모든 액션(/**)에 연결합니다. 단 /resource 로 시작하는 액션은 제외
		registry.addInterceptor(BeforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**");

//		// 메인, 로그인, 로그인 처리, 가입, 가입 처리, 게시물 리스트, 게시물 상세 빼고는 모두 로그인 상태여야 접근이 가능하다.
//		registry.addInterceptor(NeedToLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
//				.excludePathPatterns("/").excludePathPatterns("/member/login").excludePathPatterns("/member/doLogin")
//				.excludePathPatterns("/member/join").excludePathPatterns("/member/doJoin")
//				.excludePathPatterns("/article/list").excludePathPatterns("/article/detail")
//				.excludePathPatterns("/article/getForPrintArticleRepliesRs").excludePathPatterns("/home/**");
//
//		// 로그인, 로그인처리, 가입, 가입 처리는 로그인 상태일 때 접근할 수 없다.
//		registry.addInterceptor(NeedToLogoutInterceptor).addPathPatterns("/member/login")
//				.addPathPatterns("/member/doLogin").addPathPatterns("/member/join").addPathPatterns("/member/doJoin");

	}
}