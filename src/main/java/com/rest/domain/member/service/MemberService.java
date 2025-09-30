package com.rest.domain.member.service;

import com.rest.domain.member.entity.Member;
import com.rest.domain.member.repository.MemberRepository;
import com.rest.global.jwt.JwtProvider;
import com.rest.global.reData.RsData;
import com.rest.global.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;


	public Member join(String username, String password, String email) {

		Member member = Member.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();

		memberRepository.save(member);
		return member;
	}

	public SecurityUser getUserFromAccessToken(String accessToken) {
		Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);
		long id = (long) payloadBody.get("id");
		String username = (String) payloadBody.get("username");
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new SecurityUser(id, username, "", authorities);
	}
	
	@Getter
	@AllArgsConstructor
	public static class AuthAndMakeTokenResponseBody{
		private Member member;
		private String accessToken;
	}

	public RsData<AuthAndMakeTokenResponseBody> authAndMakeTokens(String username, String password) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
		
		// 시간 설정 및 토큰 생성
		String accessToken = jwtProvider.genToken(member, 60 * 60 * 5);

		return RsData.of("200-1", "로그인 성공", new AuthAndMakeTokenResponseBody(member, accessToken));
	}
}
