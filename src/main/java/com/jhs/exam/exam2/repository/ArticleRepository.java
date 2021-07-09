package com.jhs.exam.exam2.repository;

import java.util.List;

import com.jhs.exam.exam2.dto.Article;
import com.jhs.mysqliutil.MysqlUtil;
import com.jhs.mysqliutil.SecSql;

public class ArticleRepository {
	public int write(String title, String body, int memberId, int boardId) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", memberId = ?", memberId);
		sql.append(", boardId = ?", boardId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		int id = MysqlUtil.insert(sql);
		
		return id;
	}

	public List<Article> getForPrintArticles(int page, int itemsInAPage, String searchKeywordTypeCode, String searchKeyword) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*, IFNULL(M.nickname, \"탈퇴한 회원\") AS extra__writerName");
		sql.append("FROM article AS A");
		sql.append("LEFT JOIN `member` AS M");
		sql.append("ON M.id = A.memberId");
		if(searchKeywordTypeCode.equals("title") && searchKeyword.length() != 0) {
			sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		else if(searchKeywordTypeCode.equals("body") && searchKeyword.length() != 0) {
			sql.append("WHERE A.body LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		sql.append("ORDER BY A.id DESC");
		sql.append("LIMIT ?", itemsInAPage);
		sql.append("OFFSET ?", (page - 1) * itemsInAPage);
		
		return MysqlUtil.selectRows(sql, Article.class);
	}

	public Article getForPrintArticleById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*, IFNULL(M.nickname, \"탈퇴한 회원\") AS extra__writerName");
		sql.append("FROM article AS A");
		sql.append("LEFT JOIN `member` AS M");
		sql.append("ON M.id = A.memberId");
		sql.append("WHERE A.id = ?", id);
		
		return MysqlUtil.selectRow(sql, Article.class);
	}

	public int delete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		
		return MysqlUtil.delete(sql);
	}

	public int modify(int id, String title, String body) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		
		if ( title != null ) {
			sql.append(", title = ?", title);
		}
		
		if ( body != null ) {
			sql.append(", body = ?", body);
		}
		
		sql.append("WHERE id = ?", id);
		
		return MysqlUtil.update(sql);
	}

	public List<Article> getArticles(String searchKeywordTypeCode, String searchKeyword) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*, IFNULL(M.nickname, \"탈퇴한 회원\") AS extra__writerName");
		sql.append("FROM article AS A");
		sql.append("LEFT JOIN `member` AS M");
		sql.append("ON M.id = A.memberId");
		if(searchKeywordTypeCode.equals("title") && searchKeyword.length() != 0) {
			sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		else if(searchKeywordTypeCode.equals("body") && searchKeyword.length() != 0) {
			sql.append("WHERE A.body LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		sql.append("ORDER BY A.id DESC");
		
		return MysqlUtil.selectRows(sql, Article.class);
	}
}
