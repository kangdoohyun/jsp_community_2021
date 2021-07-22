package com.jhs.exam.exam2.http.controller;

import com.jhs.exam.exam2.container.Container;
import com.jhs.exam.exam2.dto.Member;
import com.jhs.exam.exam2.dto.ResultData;
import com.jhs.exam.exam2.http.Rq;
import com.jhs.exam.exam2.service.MemberService;
import com.jhs.exam.exam2.util.Ut;

public class UsrMemberController extends Controller {
	private MemberService memberService = Container.memberService;

	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "login":
			actionShowLogin(rq);
			break;
		case "doLogin":
			actionDoLogin(rq);
			break;
		case "doLogout":
			actionDoLogout(rq);
			break;
		case "join":
			actionShowJoin(rq);
			break;
		case "doJoin":
			actionDoJoin(rq);
			break;
		case "loginIdCheck":
			actionDoLoginIdCheck(rq);
			break;
		case "nicknameCheck":
			actionDoNicknameCheck(rq);
			break;
		case "emailCheck":
			actionDoEmailCheck(rq);
			break;
		case "myPage":
			actionShowMyPage(rq);
			break;
		case "modify":
			actionShowModify(rq);
			break;
		case "doModify":
			actionDoModify(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}

	private void actionShowModify(Rq rq) {
		rq.jsp("usr/member/modify");
	}

	private void actionShowMyPage(Rq rq) {
		rq.jsp("usr/member/myPage");
	}

	private void actionDoModify(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		String loginPw = rq.getParam("loginPw", "");
		String name = rq.getParam("name", "");
		String nickname = rq.getParam("nickname", "");
		String email = rq.getParam("email", "");
		String cellphoneNo = rq.getParam("cellphoneNo", "");
		
		if (loginId.length() == 0) {
			rq.historyBack("loginId를 입력해주세요.");
			return;
		}
		
		if (loginPw.length() == 0) {
			rq.historyBack("loginPw를 입력해주세요.");
			return;
		}
		
		if (name.length() == 0) {
			rq.historyBack("name을 입력해주세요.");
			return;
		}
		
		if (nickname.length() == 0) {
			rq.historyBack("nickname을 입력해주세요.");
			return;
		}
		
		if (email.length() == 0) {
			rq.historyBack("email을 입력해주세요.");
			return;
		}
		
		if (cellphoneNo.length() == 0) {
			rq.historyBack("cellphoneNo를 입력해주세요.");
			return;
		}
		
		ResultData modifyRd = memberService.modify(loginId, loginPw, name, nickname, email, cellphoneNo);
		
		if(modifyRd.isFail()) {
			rq.historyBack(modifyRd.getMsg());
			return;
		}
		
		String redirectUri = "../home/main";
		
		rq.replace(modifyRd.getMsg(), redirectUri);
	}

	private void actionDoEmailCheck(Rq rq) {
		String email = rq.getParam("email", "");
		if(!Ut.isValidEmail(email)) {
			rq.print("false");
			return;
		}
		Member member = memberService.getMemberByEmail(email);
		if(member != null) {
			rq.print(member.getEmail());
		}
		rq.print("");
	}

	private void actionDoLoginIdCheck(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		Member member = memberService.getMemberByLoginId(loginId);
		if(member != null) {
			rq.print(member.getLoginId());
		}
		rq.print("");
	}
	
	private void actionDoNicknameCheck(Rq rq) {
		String ncikname = rq.getParam("nickname", "");
		Member member = memberService.getMemberByNickname(ncikname);
		if(member != null) {
			rq.print(member.getNickname());
			return;
		}
		rq.print("");
	}

	private void actionDoJoin(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		String loginPw = rq.getParam("loginPw", "");
		String loginPwCheck = rq.getParam("loginPwCheck", "");
		String name = rq.getParam("name", "");
		String nickname = rq.getParam("nickname", "");
		String email = rq.getParam("email", "");
		String cellphoneNo = rq.getParam("cellphoneNo", "");
		
		if (loginId.length() == 0) {
			rq.historyBack("loginId를 입력해주세요.");
			return;
		}

		if (loginPw.length() == 0) {
			rq.historyBack("loginPw를 입력해주세요.");
			return;
		}
		
		if (loginPwCheck.length() == 0) {
			rq.historyBack("loginPwCheck를 입력해주세요.");
			return;
		}
		
		if (!loginPw.trim().equals(loginPwCheck.trim())) {
			rq.historyBack("비밀번호가 서로 일치하지 않습니다." + loginPw + " " + loginPwCheck);
			return;
		}
		
		if (name.length() == 0) {
			rq.historyBack("name을 입력해주세요.");
			return;
		}
		
		if (nickname.length() == 0) {
			rq.historyBack("nickname을 입력해주세요.");
			return;
		}
		
		if (email.length() == 0) {
			rq.historyBack("email을 입력해주세요.");
			return;
		}
		
		if (cellphoneNo.length() == 0) {
			rq.historyBack("cellphoneNo를 입력해주세요.");
			return;
		}
		
		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, email, cellphoneNo);
		
		if(joinRd.isFail()) {
			rq.historyBack(joinRd.getMsg());
			return;
		}
		
		String redirectUri = "../member/login";
		
		rq.replace(joinRd.getMsg(), redirectUri);
	}

	private void actionShowJoin(Rq rq) {
		rq.jsp("usr/member/join");
	}

	private void actionDoLogout(Rq rq) {
		rq.removeSessionAttr("loginedMemberJson");
		rq.replace(null, "../article/list");
	}

	private void actionDoLogin(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		String loginPw = rq.getParam("loginPw", "");

		if (loginId.length() == 0) {
			rq.historyBack("loginId를 입력해주세요.");
			return;
		}

		if (loginPw.length() == 0) {
			rq.historyBack("loginPw를 입력해주세요.");
			return;
		}

		ResultData loginRd = memberService.login(loginId, loginPw);

		if (loginRd.isFail()) {
			rq.historyBack(loginRd.getMsg());
			return;
		}

		Member member = (Member) loginRd.getBody().get("member");

		rq.setSessionAttr("loginedMemberJson", Ut.toJson(member, ""));

		String redirectUri = rq.getParam("redirectUri", "../article/list");

		rq.replace(loginRd.getMsg(), redirectUri);

	}

	private void actionShowLogin(Rq rq) {
		rq.jsp("usr/member/login");
	}
}
