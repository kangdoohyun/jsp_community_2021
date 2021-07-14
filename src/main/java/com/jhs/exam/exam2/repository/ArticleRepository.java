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

	public List<Article> getForPrintArticles(int boardId,int limitFrom, int limitTake, String searchKeywordTypeCode, String searchKeyword) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*, IFNULL(M.nickname, \"탈퇴한 회원\") AS extra__writerName, B.name");
		sql.append("FROM article AS A");
		sql.append("LEFT JOIN `member` AS M");
		sql.append("ON M.id = A.memberId");
		sql.append("LEFT JOIN board AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("WHERE 1");
		if(boardId != 0) {
			sql.append("AND B.id = ?", boardId);
		}
		if(searchKeyword.length() != 0) {
			switch (searchKeywordTypeCode) {
			case "title": 
				sql.append("AND A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
				break;
			case "body":
				sql.append("AND A.body LIKE CONCAT('%', ?, '%')", searchKeyword);
				break;
			case "title,body":
				sql.append("AND A.title LIKE CONCAT('%', ?, '%') OR A.body LIKE CONCAT('%', ?, '%')", searchKeyword, searchKeyword);
			}
		}
		sql.append("ORDER BY A.id DESC");
		sql.append("LIMIT ?", limitTake);
		sql.append("OFFSET ?", limitFrom);
		
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

	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*, IFNULL(M.nickname, \"탈퇴한 회원\") AS extra__writerName");
		sql.append("FROM article AS A");
		sql.append("LEFT JOIN `member` AS M");
		sql.append("ON M.id = A.memberId");
		sql.append("LEFT JOIN board AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("WHERE 1");
		if(boardId != 0) {
			sql.append("AND B.id = ?", boardId);
		}
		if(searchKeyword.length() != 0) {
			switch (searchKeywordTypeCode) {
			case "title": 
				sql.append("AND A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
				break;
			case "body":
				sql.append("AND A.body LIKE CONCAT('%', ?, '%')", searchKeyword);
				break;
			case "title,body":
				sql.append("AND A.title LIKE CONCAT('%', ?, '%') OR A.body LIKE CONCAT('%', ?, '%')", searchKeyword, searchKeyword);
			}
		}
		sql.append("ORDER BY A.id DESC");
		
		return MysqlUtil.selectRows(sql, Article.class);
	}
}
