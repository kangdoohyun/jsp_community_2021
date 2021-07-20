<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="메인 페이지" />
<%@ include file="../part/head.jspf"%>

<section class="section section-home-main px-4">
	<div class="container mx-auto">

		<div class="card bordered shadow-lg">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer">
					<i class="fas fa-chevron-left"></i>
				</a>
				<span>메인 페이지</span>
			</div>

			<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Explicabo voluptatibus
				facilis maxime illum iste ducimus officiis asperiores debitis fuga sed consequatur praesentium
				ab quia nulla sequi tempore soluta architecto ratione.</div>
		</div>
	</div>

</section>
<%@ include file="../part/foot.jspf"%>