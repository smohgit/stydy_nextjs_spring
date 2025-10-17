package com.rest.domain.article.controller;

import com.rest.domain.article.dto.ArticleDto;
import com.rest.domain.article.entity.Article;
import com.rest.domain.article.service.ArticleService;
import com.rest.domain.member.entity.Member;
import com.rest.global.reData.RsData;
import com.rest.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
	private final ArticleService articleService;
	private final Rq rq;

	@Getter
	@AllArgsConstructor
	public static class ArticlesResponse {
		private List<ArticleDto> articles;
	}

	@GetMapping
	public RsData<ArticlesResponse> getArticles() {
		List<ArticleDto> articleDtoList = articleService.getList().stream().map(ArticleDto::new).toList();
		return RsData.of("S-1", "성공", new ArticlesResponse(articleDtoList));
	}

	@Getter
	@AllArgsConstructor
	public static class ArticleResponse {
		private ArticleDto articles;
	}

	@GetMapping("/{id}")
	public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
		return articleService.getArticle(id).map(article -> RsData.of(
				"S-1",
				"성공",
				new ArticleResponse(new ArticleDto(article))
		)).orElseGet(() -> RsData.of(
				"F-1",
				"실패"
		));
	}

	@Data
	public static class WriteRequest {
		@NotBlank
		private String subject;

		@NotBlank
		private String content;

	}

	@Getter
	@AllArgsConstructor
	public static class WriteResponse {
		private ArticleDto articles;
	}

	@PostMapping
	public RsData<WriteResponse> write(@Valid @RequestBody WriteRequest writeRequest) {
		System.out.println("writeRequest.getContent = " + writeRequest.getContent());
		Member member = rq.getMember();
		RsData<Article> writeRs = articleService.create(member, writeRequest.getSubject(), writeRequest.getContent());
		if (writeRs.isFail()) return (RsData) writeRs;
		return RsData.of(writeRs.getResultCode(), writeRs.getMsg(), new WriteResponse(new ArticleDto(writeRs.getData())));
	}


	@Data
	public static class ModifyRequest {
		private Long id;

		@NotBlank
		private String subject;

		@NotBlank
		private String content;

	}

	@Getter
	@AllArgsConstructor
	public static class ModifyResponse {
		private Article articles;
	}

	@PatchMapping("/{id}")
	public RsData<ModifyResponse> modify(@Valid @RequestBody ModifyRequest modifyRequest, @PathVariable("id") Long id){
		modifyRequest.setId(id);

		Optional<Article> opArticle = articleService.findById(id);
		if(opArticle.isEmpty()) return RsData.of("F-1", "%d번 게시물은 존재하지 않습니다.".formatted(id));

		//TODO 회원 권한 체크 canModify();

		RsData<Article> modifyRs = articleService.modify(opArticle.get(), modifyRequest.getSubject(), modifyRequest.getContent());

		return RsData.of(
				modifyRs.getResultCode(),
				modifyRs.getMsg(),
				new ModifyResponse(modifyRs.getData())
		);
	}

	@Getter
	@AllArgsConstructor
	public static class DeleteResponse {
		private Article articles;
	}

	@DeleteMapping("/{id}")
	public RsData<DeleteResponse> delete(@PathVariable("id") Long id) {
		Optional<Article> opArticle = articleService.findById(id);
		if(opArticle.isEmpty()) return RsData.of("F-1", "%번 게시물은 존재하지 않습니다.".formatted(id));

		//TODO 회원 권한 체크 canDelete();

		RsData<Article> deleteRs = articleService.delete(id);

		return RsData.of(deleteRs.getResultCode(), deleteRs.getMsg(), new DeleteResponse(opArticle.get()));
	}
}