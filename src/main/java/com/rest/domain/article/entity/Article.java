package com.rest.domain.article.entity;

import com.rest.domain.member.entity.Member;
import com.rest.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
	private String subject;
	private String content;
	
	@ManyToOne
	private Member author;
}
