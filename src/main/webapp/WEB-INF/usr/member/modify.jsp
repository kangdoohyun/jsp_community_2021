<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원정보 수정" />
<%@ include file="../part/head.jspf"%>

<section class="section section-member-modify">
	<div class="container mx-auto card-wrap px-4">

		<div class="card bordered shadow-lg">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer">
					<i class="fas fa-chevron-left"></i>
				</a>
				<span>회원정보 수정</span>
			</div>

			<div class="px-4 py-4">
				<script>
					let MemberModify__submitDone = false;
					function MemberModify__submit(form) {
						if (MemberModify__submitDone) {
							return;
						}

						form.loginPw.value = form.loginPw.value.trim();
						if (form.loginPw.value.length == 0) {
							alert('비밀번호를 입력해주세요.');
							form.loginPw.focus();

							return;
						}
						
						form.name.value = form.name.value.trim();
						if (form.name.value.length == 0) {
							alert('이름을 입력해주세요.');
							form.name.focus();

							return;
						}

						form.nickname.value = form.nickname.value.trim();
						if (form.nickname.value.length == 0) {
							alert('닉네임을 입력해주세요.');
							form.nickname.focus();

							return;
						}

						form.email.value = form.email.value.tim();
						if (form.email.value.length == 0) {
							alert('이메일을 입력해주세요.');
							form.email.focus();

							return;
						}

						form.cellphoneNo.value = form.cellphoneNo.value.trim();
						if (form.cellphoneNo.value.length == 0) {
							alert('전화번호를 입력해주세요.');
							form.cellphoneNo.focus();

							return;
						}

						form.submit();
						MemberModify__submitDone = true;
					}
				</script>
				<script>
					$(document).ready(function() {
						$("#join-submit").click(function(){
							var checked = $("#join-submit").val();	
							if(!checked.contains('loginId')){
								$("#loginIdCheckMsg").html('아이디 중복을 확인해주세요.');
								
								return false;
							}
						});
						$("#join-submit").click(function(){
							var checked = $("#join-submit").val();	
							if(!checked.contains('nickname')){
								$("#nicknameCheckMsg").html('닉네임 중복을 확인해주세요.');
								
								return false;
							}
						});
						$("#join-submit").click(function(){
							var checked = $("#join-submit").val();
							if(!checked.contains('email')){
								$("#emailCheckMsg").html('이메일 중복을 확인해주세요.');
								
								return false;
							}
						});
						$('#nickname').blur(function(){
							var nickname = $('#nickname').val();
							$("#join-submit").val() + 'nickname';
							$.ajax({
								type: 'POST',
								url: './nicknameCheck',
								data: {nickname:nickname},
								success: function(result){
									if(result == ''){
										if(nickname.length == 0){
											$("#nicknameCheckMsg").html('닉네임을 입력해주세요.');
											$("#nicknameCheckMsg").css("color", "red");
											$("#join-submit").attr("disabled", true);
											return;
										}
										else{
											$("#nicknameCheckMsg").html('사용할 수 있는 닉네임입니다.');
											$("#nicknameCheckMsg").css("color", "green");
											$("#join-submit").attr("disabled", false);
											return;
										}
									}
									else{
										if(result.trim() === "${rq.loginedMember.nickname}"){
											$("#join-submit").attr("disabled", false);	
											return;
										}
										else{
											$("#nicknameCheckMsg").html('사용할 수 없는 닉네임입니다.');
											$("#nicknameCheckMsg").css("color", "red");
											$("#join-submit").attr("disabled", true);	
											return;	
										}
									}
								}
							});
						});
						$('#email').blur(function(){
							var email = $('#email').val();
							$("#join-submit").val() + 'email';
							$.ajax({
								type: 'POST',
								url: './emailCheck',
								data: {email:email},
								success: function(result){
									if(email.length == 0){
										$("#emailCheckMsg").html('이메일을 입력해주세요.');
										$("#emailCheckMsg").css("color", "red");
										$("#join-submit").attr("disabled", true);
										return;
									}
									if(result.startsWith('false')){
										$("#emailCheckMsg").html('이메일 형식에 맞게 입력해주세요.');
										$("#emailCheckMsg").css("color", "red");
										$("#join-submit").attr("disabled", true);
										return;
									}
									else if(result == ''){
										$("#emailCheckMsg").html('사용할 수 있는 이메일입니다.');
										$("#emailCheckMsg").css("color", "green");
										$("#join-submit").attr("disabled", false);
										return;
									}
									else{
										if(result.trim() === "${rq.loginedMember.email}"){
											$("#join-submit").attr("disabled", false);	
											return;
										}
										else{
											$("#emailCheckMsg").html('사용할 수 없는 이메일입니다.');
											$("#emailCheckMsg").css("color", "red");
											$("#join-submit").attr("disabled", true);
											return;	
										}
									}
								}
							});
						});
					});
				</script>
				<form action="../member/doModify" method="GET" onsubmit="MemberModify__submit(this); return false;">
					<div class="form-control">
						<input class="input input-bordered w-full" maxlength="100" id="loginId" name="loginId" type="hidden" value="${rq.loginedMember.loginId}"/>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">비밀번호</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" id="loginPw" name="loginPw" type="password"
								placeholder="비밀번호를 입력해주세요." value="${rq.loginedMember.loginPw}"/>
						</div>
					</div>
					
					<div class="form-control">
						<label class="label">
							<span class="label-text">이름</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" name="name" type="text"
								placeholder="이름을 입력해주세요." value="${rq.loginedMember.name}"  />
						</div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">닉네임</span>
						</label>
						<div class="flex">
							<input class="input input-bordered w-full" maxlength="100" id="nickname" name="nickname" type="text"
								placeholder="닉네임을 입력해주세요." value="${rq.loginedMember.nickname}"  />
						</div>
						<div class="pl-1 pt-2 text-sm" id="nicknameCheckMsg" style="color : red;"></div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">이메일</span>
						</label>
						<div class="flex">
							<input class="input input-bordered w-full" maxlength="100" id="email" name="email" type="email"
							placeholder="이메일을 입력해주세요." value="${rq.loginedMember.email}" />
						</div>
						<div class="pl-1 pt-2 text-sm" id="emailCheckMsg" style="color : red;"></div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">전화번호</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" name="cellphoneNo" type="tel"
								placeholder="전화번호를 입력해주세요." value="${rq.loginedMember.cellphoneNo}" />
						</div>
					</div>

					<div class="btns">
						<button id="join-submit" type="submit" class="btn btn-link" value="0">수정완료</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<%@ include file="../part/foot.jspf"%>