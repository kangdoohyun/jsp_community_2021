<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="아이디 찾기" />
<%@ include file="../part/head.jspf"%>

<section class="section section-member-find flex-grow flex items-center justify-center">
	<div class="w-full max-w-md card-wrap">

		<div class="card bordered shadow-lg">
			<div class="card-title">
				<span>
					<i class="fas fa-sign-in-alt"></i>
				</span>
				<span>아이디 찾기 완료</span>
			</div>

			<div class="px-4 py-4">
				<div>
					<div class="badge badge-primary">아이디 목록</div>
					<label class="label">
							<span class="label-text">${loginId}</span>
							<span class="label-text">${regDate}</span>
					</label>
				</div>
				<div class="btn-box">
					<a class="btn btn-link" href="./login">로그인하기</a>
					<a class="btn btn-link" href="#">비밀번호찾기</a>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../part/foot.jspf"%>