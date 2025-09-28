package com.rest.domain.member.controller;

import com.rest.domain.member.dto.MemberDto;
import com.rest.domain.member.entity.Member;
import com.rest.domain.member.service.MemberService;
import com.rest.global.reData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
	private final MemberService memberService;

	@Getter
	public static class LoginRequestBody{
		@NotBlank
		public String username;
		@NotBlank
		public String password;
	}
	
	@Getter
	@AllArgsConstructor
	public static class LoginResponseBody{
		private MemberDto memberDto;
	}

	@PostMapping("/login")
	public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody requestBody) {
		//username, password => accessToken
		RsData<MemberService.AuthAndMakeTokenResponseBody> authAndMakeTokenRs = memberService.authAndMakeTokens(requestBody.getUsername(), requestBody.getPassword());

		return RsData.of(
				authAndMakeTokenRs.getResultCode(),
				authAndMakeTokenRs.getMsg(),
				new LoginResponseBody(new MemberDto(authAndMakeTokenRs.getData().getMember()))
		);
	}
}
