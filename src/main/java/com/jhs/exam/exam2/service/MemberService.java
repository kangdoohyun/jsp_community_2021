package com.jhs.exam.exam2.service;

import com.jhs.exam.exam2.container.Container;
import com.jhs.exam.exam2.dto.Member;
import com.jhs.exam.exam2.dto.ResultData;
import com.jhs.exam.exam2.repository.MemberRepository;

public class MemberService {
	private MemberRepository memberRepository = Container.memberRepository;

	public ResultData login(String loginId, String loginPw) {
		Member member = getMemberByLoginId(loginId);

		if (member == null) {
			return ResultData.from("F-1", "존재하지 않는 회원의 로그인아이디 입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-2", "비밀번호가 일치하지 않습니다.");
		}

		return ResultData.from("S-1", "환영합니다.", "member", member);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	
	public Member getMemberByNickname(String nickname) {
		return memberRepository.getMemberByNickname(nickname);
	}
	
	public Member getMemberByEmail(String email) {
		return memberRepository.getMemberByEmail(email);
	}

	public ResultData join(String loginId, String loginPw, String name, String nickname, String email,
			String cellphoneNo) {
		Member memberByLoginId = getMemberByLoginId(loginId);
		Member memberByNickname = getMemberByNickname(nickname);
		Member memberByEmail = getMemberByEmail(email);
		if (memberByLoginId != null) {
			return ResultData.from("F-1", "중복된 아이디 입니다.");
		}
		if (memberByNickname != null) {
			return ResultData.from("F-1", "중복된 닉네임 입니다.");
		}
		if (memberByEmail != null) {
			return ResultData.from("F-1", "중복된 이메일 입니다.");
		}

		memberRepository.join(loginId, loginPw, name, nickname, email, cellphoneNo);
		return ResultData.from("S-1", "환영합니다.");
	}

	public ResultData modify(String loginId, String loginPw, String name, String nickname, String email,
			String cellphoneNo) {
		Member memberByLoginId = getMemberByLoginId(loginId);
		Member memberByNickname = getMemberByNickname(nickname);
		if (memberByLoginId != null) {
			if(!memberByLoginId.getLoginId().trim().equals(loginId.trim())) {
				return ResultData.from("F-1", "중복된 아이디 입니다.");
			}
		}
		if (memberByNickname != null) {
			if(!memberByLoginId.getNickname().trim().equals(nickname.trim())) {
				return ResultData.from("F-1", "중복된 닉네임 입니다.");
			}
		}
		

		memberRepository.modify(loginId, loginPw, name, nickname, email, cellphoneNo);
		return ResultData.from("S-1", "회원정보 수정이 완료되었습니다..");
	}

}
