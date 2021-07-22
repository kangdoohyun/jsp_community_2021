package com.jhs.exam.exam2.repository;

import com.jhs.exam.exam2.dto.Member;
import com.jhs.mysqliutil.MysqlUtil;
import com.jhs.mysqliutil.SecSql;

public class MemberRepository {

	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM member AS M");
		sql.append("WHERE M.loginId = ?", loginId);
		
		return MysqlUtil.selectRow(sql, Member.class);
	}

	public Member getMemberById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.* FROM `member` AS M");
		sql.append("WHERE M.id = ?", id);
		return MysqlUtil.selectRow(sql, Member.class);
	}

	public void join(String loginId, String loginPw, String name, String nickname, String email, String cellphoneNo) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		sql.append(", nickname = ?", nickname);
		sql.append(", email = ?", email);
		sql.append(", cellphoneNo = ?", cellphoneNo);
		MysqlUtil.insert(sql);
	}

	public Member getMemberByNickname(String nickname) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM member AS M");
		sql.append("WHERE M.nickname = ?", nickname);
		
		return MysqlUtil.selectRow(sql, Member.class);
	}

	public Member getMemberByEmail(String email) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM member AS M");
		sql.append("WHERE M.email= ?", email);
		
		return MysqlUtil.selectRow(sql, Member.class);
	}

	public int modify(String loginId, String loginPw, String name, String nickname, String email, String cellphoneNo) {
		SecSql sql = new SecSql();
		sql.append("UPDATE `member` SET");
		sql.append("loginPw = ?", loginPw);
		sql.append(", name = ?", name);
		sql.append(", nickname = ?", nickname);
		sql.append(", email = ?", email);
		sql.append(", cellphoneNo = ?", cellphoneNo);
		sql.append("WHERE loginid = ?", loginId);
		
		return MysqlUtil.update(sql);
	}
	
}
