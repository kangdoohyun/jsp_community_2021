package com.jhs.exam.exam2.service;

import java.util.List;

import com.jhs.exam.exam2.container.Container;
import com.jhs.exam.exam2.dto.Article;
import com.jhs.exam.exam2.dto.Member;
import com.jhs.exam.exam2.dto.ResultData;
import com.jhs.exam.exam2.repository.ArticleRepository;
import com.jhs.exam.exam2.util.Ut;

public class ArticleService {
	private ArticleRepository articleRepository = Container.articleRepository;

	public ResultData write(String title, String body, int memberId, int boardId) {
		int id = articleRepository.write(title, body, memberId, boardId);

		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
	}
	
	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticles(boardId, searchKeywordTypeCode, searchKeyword);
	}
	
	public List<Article> getForPrintArticles(Member actor, int boardId,int page, int itemsInAPage, int limitFrom, int limitTake, String searchKeywordTypeCode, String searchKeyword) {

		List<Article> articles = articleRepository.getForPrintArticles(boardId, limitFrom, limitTake, searchKeywordTypeCode, searchKeyword);
		
		for(Article article : articles) {
			updateForPrintData(actor, article);
		}
		
		return articles;
	}

	public Article getForPrintArticleById(Member actor, int id) {
		Article article = articleRepository.getForPrintArticleById(id);
		
		updateForPrintData(actor, article);
		
		return article;
	}

	private void updateForPrintData(Member actor, Article article) {
		if(actor == null) {
			return;
		}
		
		boolean actorCanModify = acterCanModify(actor, article).isSuccess();
		boolean actorCanDelete = acterCanDelete(actor, article).isSuccess();
		
		article.setExtra__actorCanModify(actorCanModify);
		article.setExtra__actorCanDelete(actorCanDelete);
	}

	public ResultData delete(int id) {
		articleRepository.delete(id);

		return ResultData.from("S-1", Ut.f("%d번 게시물이 삭제되었습니다.", id), "id", id);
	}

	public ResultData modify(int id, String title, String body) {
		articleRepository.modify(id, title, body);

		return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "id", id);
	}

	public ResultData acterCanDelete(Member loginedMember, Article article) {
		int memberId = loginedMember.getId();
		int writerMemberId = article.getMemberId();
		
		if(memberId != writerMemberId) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "삭제가 가능합니다.");
	}

	public ResultData acterCanModify(Member loginedMember, Article article) {
		int memberId = loginedMember.getId();
		int writerMemberId = article.getMemberId();
		
		if(memberId != writerMemberId) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "수정이 가능합니다.");
	}

}
