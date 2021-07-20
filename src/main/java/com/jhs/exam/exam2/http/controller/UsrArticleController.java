package com.jhs.exam.exam2.http.controller;

import java.util.List;

import com.jhs.exam.exam2.container.Container;
import com.jhs.exam.exam2.dto.Article;
import com.jhs.exam.exam2.dto.Board;
import com.jhs.exam.exam2.dto.Member;
import com.jhs.exam.exam2.dto.ResultData;
import com.jhs.exam.exam2.http.Rq;
import com.jhs.exam.exam2.service.ArticleService;
import com.jhs.exam.exam2.service.BoardService;
import com.jhs.exam.exam2.service.MemberService;
import com.jhs.exam.exam2.util.Ut;

public class UsrArticleController extends Controller {
	private BoardService boardService = Container.boardService;
	private ArticleService articleService = Container.articleService;
	private MemberService memberService = Container.memberService;

	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "list":
			actionShowList(rq);
			break;
		case "detail":
			actionShowDetail(rq);
			break;
		case "write":
			actionShowWrite(rq);
			break;
		case "doWrite":
			actionDoWrite(rq);
			break;
		case "modify":
			actionShowModify(rq);
			break;
		case "doModify":
			actionDoModify(rq);
			break;
		case "doDelete":
			actionDoDelete(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}

	private void actionDoDelete(Rq rq) {
		int id = rq.getIntParam("id", 0);
		String redirectUri = rq.getParam("redirectUri", "../article/list");

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}
		ResultData acterCanDeleteRd = articleService.acterCanDelete(rq.getLoginedMember(), article);
		
		if(acterCanDeleteRd.isFail()) {
			rq.historyBack(acterCanDeleteRd.getMsg());
			return;
		}
		
		articleService.delete(id);

		rq.replace(Ut.f("%d번 게시물을 삭제하였습니다.", id), redirectUri);
	}

	private void actionShowDetail(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);
		

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}
		
		Member member = memberService.getMemberById(article.getMemberId());
		
		rq.setAttr("article", article);
		rq.jsp("usr/article/detail");
	}

	private void actionShowList(Rq rq) {
		int page = rq.getIntParam("page", 1);
		int boardId = rq.getIntParam("boardId", 0);
		String searchKeywordTypeCode = rq.getParam("searchKeywordTypeCode", "title");
		String searchKeyword = rq.getParam("searchKeyword", "");
		int itemsInAPage = 10;
		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMember(), boardId, page, itemsInAPage, limitFrom, limitTake, searchKeywordTypeCode, searchKeyword);
		List<Article> allArticles = articleService.getArticles(boardId, searchKeywordTypeCode, searchKeyword);
				
		int blockCount = 5; // 페이지 블럭당 페이지 갯수.
		int blockNum = (int)Math.floor(((page-1)/ blockCount) + 1); // 현제 페이지블럭 번호 # floor 내림 # (현제 페이지 - 1) / 블럭당 페이지갯수 # (6 - 1) / 5 + 1 = 2; 
		int blockStartNum = (blockCount * (blockNum - 1))  + 1; // 현제 페이지블럭이 몇번 페이지부터 시작하는지 # (블럭당 페이지갯수 * 현제 페이지블럭 번호) + 1 # (5 * (2 - 1)) + 1 = 6;
		int blockLastNum = blockStartNum + (blockCount - 1); // 현제 페이지블럭이 몇번 페이지까지 인지 # 페이지블럭 시작 페이지 + (블럭당 페이지갯수 - 1) # 6 + (5 - 1) = 10;
		// double 을 걸어주는 이유는 실수 끼리 나누면 자동으로 자바가 나머지를 버림해서 실수로 넣어주기 때문이다.
		int totalPage = (int)Math.ceil((double)allArticles.size() / itemsInAPage); // 총 페이지의 수 # ceil 올림 # 전체 게시물 수 / 페이지당 개시물 수 # 1024 / 10 = 102.4 (103); 
		int endBlock = (int)Math.ceil((double)totalPage / blockCount); // 마지막 페이지 블럭 인덱스 # 총 페이지 수 / 페이지 블럭당 페이지 갯수 # 103 / 5 = 20.6 (21);	
		
		rq.setAttr("page", page);
		rq.setAttr("articles", articles);
		rq.setAttr("allArticles", allArticles);
		rq.setAttr("blockStartNum", blockStartNum);
		rq.setAttr("blockLastNum", blockLastNum);
		rq.setAttr("endBlock", endBlock);
		rq.setAttr("blockNum", blockNum);
		rq.setAttr("totalPage", totalPage);
		rq.setAttr("boardId", boardId);
		rq.setAttr("searchKeyword", searchKeyword);
		rq.setAttr("searchKeywordTypeCode", searchKeywordTypeCode);
		
		rq.jsp("usr/article/list");
	}

	private void actionDoWrite(Rq rq) {
		String title = rq.getParam("title", "");
		String body = rq.getParam("body", "");
		String redirectUri = rq.getParam("redirectUri", "../article/list");
		int memberId = rq.getLoginedMemberId();
		int boardId = rq.getIntParam("boardId", 0);

		if (title.length() == 0) {
			rq.historyBack("title을 입력해주세요.");
			return;
		}

		if (body.length() == 0) {
			rq.historyBack("body를 입력해주세요.");
			return;
		}
		
		if (boardId == 0) {
			rq.historyBack("boardId를 입력해주세요.");
			return;
		}

		ResultData writeRd = articleService.write(title, body, memberId, boardId);
		int id = (int) writeRd.getBody().get("id");

		redirectUri = redirectUri.replace("[NEW_ID]", id + "");

		rq.replace(writeRd.getMsg(), redirectUri);
	}

	private void actionShowWrite(Rq rq) {
		List<Board> boards = boardService.getBoards();
		
		rq.setAttr("boards", boards);
		rq.jsp("usr/article/write");
	}

	private void actionDoModify(Rq rq) {
		int id = rq.getIntParam("id", 0);
		String title = rq.getParam("title", "");
		String body = rq.getParam("body", "");
		String redirectUri = rq.getParam("redirectUri", Ut.f("../article/detail?id=%d", id));

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		if (title.length() == 0) {
			rq.historyBack("title을 입력해주세요.");
			return;
		}

		if (body.length() == 0) {
			rq.historyBack("body를 입력해주세요.");
			return;
		}
		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);
		
		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}
		
		ResultData acterCanModifyRd = articleService.acterCanModify(rq.getLoginedMember(), article);
		
		if(acterCanModifyRd.isFail()) {
			rq.historyBack(acterCanModifyRd.getMsg());
			return;
		}

		ResultData modifyRd = articleService.modify(id, title, body);

		rq.replace(modifyRd.getMsg(), redirectUri);
	}

	private void actionShowModify(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}
		ResultData acterCanModifyRd = articleService.acterCanModify(rq.getLoginedMember(), article);
		
		if(acterCanModifyRd.isFail()) {
			rq.historyBack(acterCanModifyRd.getMsg());
			return;
		}

		rq.setAttr("article", article);
				
		rq.jsp("usr/article/modify");
	}
}
