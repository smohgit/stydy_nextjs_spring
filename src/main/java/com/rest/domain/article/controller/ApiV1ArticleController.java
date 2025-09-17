package com.rest.domain.article.controller;

import com.rest.domain.article.entity.Article;
import com.rest.domain.article.service.ArticleService;
import com.rest.global.reData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {

	private final ArticleService articleService;

	@Getter
	@AllArgsConstructor
	public static class ArticlesResponse{
		private List<Article> articles;
	}

	@GetMapping
	public RsData<ArticlesResponse> getArticles(){
		List<Article> articles = articleService.getList();
		return RsData.of("S-1", "성공", new ArticlesResponse(articles));
	}

	@Getter
	@AllArgsConstructor
	public static class ArticleResponse{
		private Article articles;
	}

	@GetMapping("/{id}")
	public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id){
		return articleService.getArticle(id).map( article -> RsData.of(
				"S-1",
				"성공",
				new ArticleResponse(article)
		)).orElseGet( () -> RsData.of(
				"F-1",
				"실패",
				null
		));
	}
}
