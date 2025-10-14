package com.rest.global.rq;

import com.rest.domain.member.entity.Member;
import com.rest.global.security.SecurityUser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final EntityManager entityManager;
	private Member member;


	public void addHeaderCookie(String tokenName, String token) {
		ResponseCookie cookie = ResponseCookie.from(tokenName, token)
				.path("/")
				.sameSite("None")
				.secure(true)
				.httpOnly(true)
				.build();

		response.addHeader("Set-Cookie", cookie.toString());
	}

	public void removeCrossDomainCookie(String tokenName) {
		ResponseCookie cookie = ResponseCookie.from(tokenName, null)
				.path("/")
				.maxAge(0)
				.sameSite("None")
				.secure(true)
				.httpOnly(true)
				.build();

		response.addHeader("Set-Cookie", cookie.toString());
	}


	public String getCookie(String name) {
		Cookie[] cookies = request.getCookies();

		return Arrays.stream(cookies)
				.filter(cookie -> cookie.getName().equals(name))
				.findFirst()
				.map(Cookie::getValue)
				.orElse("");
	}
	
	public Member getMember () {
		if (isLogout()) return null;
		if (member == null) {
			member = entityManager.getReference(Member.class, getUser().getId());
		}
		return member;
	}


	public void setLogin(SecurityUser securityUser) {
		// 인가 처리
		SecurityContextHolder.getContext().setAuthentication(securityUser.getAuthentication());
	}

	private SecurityUser getUser() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
				.map(context -> context.getAuthentication())
				.filter(authentication -> authentication.getPrincipal() instanceof SecurityUser)
				.map(authentication -> (SecurityUser) authentication.getPrincipal())
				.orElse(null);
	}

	private boolean isLogin() {
		return getUser() != null;
	}

	private boolean isLogout() {
		return !isLogin();
	}
}
