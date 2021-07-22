<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="마이페이지" />
<%@ include file="../part/head.jspf"%>

<section class="section section-member-myPage">
	<div class="container mx-auto card-wrap px-4">

		<div class="card bordered shadow-lg">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer">
					<i class="fas fa-chevron-left"></i>
				</a>
				<span>마이 페이지</span>
			</div>
			
			<div class="px-4 py-4">
				<a class="btn btn-link" href="./modify">회원정보 수정</a>
			</div>
		</div>
	</div>
</section>
<%@ include file="../part/foot.jspf"%>