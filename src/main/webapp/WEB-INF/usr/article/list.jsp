
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<section class="section section-article-list">
	<div class="container mx-auto card-wrap px-4">

		<div class="card bordered shadow-lg">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer">
					<i class="fas fa-chevron-left"></i>
				</a>
				<span>게시물 리스트</span>
			</div>
			<div class="px-4">
				<a class="btn btn-link" href="./write?boardId=${boardId}">글 쓰기</a>
				<div class="py-4 flex w-full">
					<div>
						<c:if test="${param.searchKeyword != null && param.searchKeyword != ''}">
							<div class="badge badge-primary">검색어 타입</div>
							<span>${param.searchKeywordTypeCode}</span>

							<br />

							<div class="badge badge-primary">검색어</div>
							<span>${param.searchKeyword}</span>

							<br />
						</c:if>
						<div class="badge badge-primary">
							<span>전체 게시물 수</span>
						</div>
						<span>
							<fmt:formatNumber type="number" maxFractionDigits="3" value="${allArticles.size()}" />
						</span>
					</div>
					
				</div>
				<c:forEach items="${articles}" var="article">
					<c:set var="detailUri" value="../article/detail?id=${article.id}" />

					<div class="py-4">
						<div class="grid gap-3" style="grid-template-columns: 100px 1fr;">
							<a href="${detailUri}">
								<img class="rounded-full w-full" src="https://i.pravatar.cc/200?img=37" alt="">
							</a>
							<a href="${detailUri}" class="hover:underline cursor-pointer">
								<span class="badge badge-outline">제목</span>
								<div class="line-clamp-3">${article.titleForPrint}</div>
							</a>
						</div>

						<div class="mt-3 grid sm:grid-cols-2 lg:grid-cols-4 gap-3">
							<a href="${detailUri}" class="hover:underline">
								<span class="badge badge-primary">번호</span>
								<span>${article.id}</span>
							</a>

							<a href="${detailUri}" class="cursor-pointer hover:underline">
								<span class="badge badge-accent">작성자</span>
								<span>${article.extra__writerName}</span>
							</a>

							<a href="${detailUri}" class="hover:underline">
								<span class="badge">등록날짜</span>
								<span class="text-gray-600 text-light">${article.regDate}</span>
							</a>

							<a href="${detailUri}" class="hover:underline">
								<span class="badge">수정날짜</span>
								<span class="text-gray-600 text-light">${article.updateDate}</span>
							</a>
						</div>

						<a href="${detailUri}"
							class="block mt-3 hover:underline cursor-pointer col-span-1 sm:col-span-2 xl:col-span-3">
							<span class="badge badge-outline">본문</span>

							<div class="mt-2">
								<img class="rounded" src="https://picsum.photos/id/237/300/300" alt="" />
							</div>

							<div class="line-clamp-3">${article.bodySummaryForPrint}</div>
						</a>
						<div class="btns mt-3">
							<c:if test="${article.extra__actorCanModify}">
								<a href="../article/modify?id=${article.id}" class="btn btn-link">
									<span>
										<i class="fas fa-edit"></i>
									</span>
									<span>수정</span>
								</a>
							</c:if>
							<c:if test="${article.extra__actorCanDelete}">
								<a onclick="if ( !confirm('정말로 삭제하시겠습니까?') ) return false;"
									href="../article/doDelete?id=${article.id}" class="btn btn-link">
									<span>
										<i class="fas fa-trash-alt"></i>
									</span>
									<span>삭제</span>
								</a>
							</c:if>
						</div>
					</div>
					<hr />
				</c:forEach>
			</div>

			<c:set var="baseUri" value="?boardId=${boardId}" />
			<c:set var="baseUri" value="${baseUri}&searchKeywordTypeCode=${param.searchKeywordTypeCode}" />
			<c:set var="baseUri" value="${baseUri}&searchKeyword=${param.searchKeyword}" />

			<!-- 페이지네이션 버전 1 -->
			<div class="page-menu flex justify-center items-center px-8 hidden">

				<c:set var="pageMenuArmSize" value="2" />
				<c:set var="startPage" value="${page - pageMenuArmSize >= 1  ? page - pageMenuArmSize : 1}" />
				<c:set var="endPage"
					value="${page + pageMenuArmSize <= totalPage ? page + pageMenuArmSize : totalPage}" />

				<a class="first" href="${baseUri}&page=1">
					<i class="fas fa-angle-double-left"></i>
				</a>
				<c:if test="${page == 1}">
					<span class="prev text-gray-200">
						<i class="fas fa-chevron-left"></i>
					</span>
				</c:if>
				<c:if test="${page != 1}">
					<a class="prev" href="${baseUri}&page=${page - 1}">
						<i class="fas fa-chevron-left"></i>
					</a>
				</c:if>

				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<c:set var="aClassStr" value="${i == param.page ? 'text-red-500 font-bold' : ''}" />
					<a class="page-menu__list ${aClassStr}" href="${baseUri}&page=${i}">${ i }</a>
				</c:forEach>

				<c:if test="${page >= endPage}">
					<span class="next text-gray-200">
						<i class="fas fa-chevron-right"></i>
					</span>
				</c:if>
				<c:if test="${page < endPage}">
					<a class="next" href="${baseUri}&page=${page + 1}">
						<i class="fas fa-chevron-right"></i>
					</a>
				</c:if>
				<a class="end" href="${baseUri}&page=${totalPage}">
					<i class="fas fa-angle-double-right"></i>
				</a>
			</div>
			<!-- 페이지네이션 버전 2 -->
			<div class="page-menu hidden md:flex justify-center py-2">
				<div class="btn-group">
					<c:set var="pageArm" value="2" />
					<c:set var="startPage" value="${page - pageArm >= 1 ? page - pageArm : 1 }" />
					<c:set var="endPage" value="${page + pageArm <= totalPage ? page + pageArm : totalPage }" />
					<c:if test="${startPage > 1}">
						<a class="btn btn-sm" href="${baseUri}&page=1">1</a>
						<c:if test="${page - pageArm != 2}">
							<button class="btn btn-sm btn-disabled">...</button>
						</c:if>
					</c:if>
					<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
						<c:set var="activeBtn" value="${page == i ? 'btn-active' : ''}" />
						<a class="btn btn-sm ${activeBtn}" href="${baseUri}&page=${i}">${i}</a>
					</c:forEach>
					<c:if test="${totalPage > endPage}">
						<c:if test="${page + pageArm != totalPage - 1}">
							<button class="btn btn-sm btn-disabled">...</button>
						</c:if>
						<a class="btn btn-sm" href="${baseUri}&page=${totalPage}">${totalPage}</a>
					</c:if>
				</div>
			</div>
			<!-- 페이지네이션 버전 2 모바일 -->
			<div class="page-menu flex md:hidden justify-center py-2">
				<div class="btn-group">
					<c:set var="pageArm" value="2" />
					<c:set var="startPage" value="${page - pageArm >= 1 ? page - pageArm : 1 }" />
					<c:set var="endPage" value="${page + pageArm <= totalPage ? page + pageArm : totalPage }" />
					<c:if test="${startPage > 1}">
						<a class="btn btn-xs" href="${baseUri}&page=1">1</a>
						<c:if test="${page - pageArm != 2}">
							<button class="btn btn-xs btn-disabled">...</button>
						</c:if>
					</c:if>
					<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
						<c:set var="activeBtn" value="${page == i ? 'btn-active' : ''}" />
						<a class="btn btn-xs ${activeBtn}" href="${baseUri}&page=${i}">${i}</a>
					</c:forEach>
					<c:if test="${totalPage > endPage}">
						<c:if test="${page + pageArm != totalPage - 1}">
							<button class="btn btn-xs btn-disabled">...</button>
						</c:if>
						<a class="btn btn-xs" href="${baseUri}&page=${totalPage}">${totalPage}</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</section>

<%@ include file="../part/foot.jspf"%>