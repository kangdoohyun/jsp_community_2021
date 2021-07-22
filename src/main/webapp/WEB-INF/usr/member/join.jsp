<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="로그인" />
<%@ include file="../part/head.jspf"%>

<section class="section section-member-join">
	<div class="container mx-auto card-wrap px-4">

		<div class="card bordered shadow-lg">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer">
					<i class="fas fa-chevron-left"></i>
				</a>
				<span>회원 가입</span>
			</div>

			<div class="px-4 py-4">
				<script>
					let MemberJoin__submitDone = false;
					function MemberJoin__submit(form) {
						if (MemberJoin__submitDone) {
							return;
						}
						
						form.loginId.value = form.loginId.value.trim();
						if (form.loginId.value.length == 0) {
							alert('아이디를 입력해주세요.');
							form.loginId.focus();

							return;
						}

						form.loginPw.value = form.loginPw.value.trim();
						if (form.loginPw.value.length == 0) {
							alert('비밀번호를 입력해주세요.');
							form.loginPw.focus();

							return;
						}
						
						form.loginPwCheck.value = form.loginPwCheck.value.trim();
						if (form.loginPwCheck.value.length == 0) {
							alert('비밀번호를 확인해 주세요.');
							form.loginPwCheck.focus();

							return;
						}
						
						if (form.loginPw.value != form.loginPwCheck.value) {
							alert('비밀번호가 서로 일치하지 않습니다.');
							form.loginPwCheck.focus();

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
						MemberJoin__submitDone = true;
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
						$('#loginId').blur(function(){
							var loginId = $('#loginId').val();
							$("#join-submit").val() + 'loginId';
							$.ajax({
								type: 'POST',
								url: './loginIdCheck',
								data: {loginId:loginId},
								success: function(result){
									if(result == ''){
										if(loginId.length == 0){
											$("#loginIdCheckMsg").html('아이디를 입력해주세요.');
											$("#loginIdCheckMsg").css("color", "red");
											$("#join-submit").attr("disabled", true);
											return;
										}
										else{
											$("#loginIdCheckMsg").html('사용할 수 있는 아이디입니다.');
											$("#loginIdCheckMsg").css("color", "green");
											$("#join-submit").attr("disabled", false);
											return;
										}
									}
									else{
										$("#loginIdCheckMsg").html('사용할 수 없는 아이디입니다.');
										$("#loginIdCheckMsg").css("color", "red");
										$("#join-submit").attr("disabled", true);
										return;
									}
								}
							});
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
										$("#nicknameCheckMsg").html('사용할 수 없는 닉네임입니다.');
										$("#nicknameCheckMsg").css("color", "red");
										$("#join-submit").attr("disabled", true);	
										return;
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
										$("#emailCheckMsg").html('사용할 수 없는 이메일입니다.');
										$("#emailCheckMsg").css("color", "red");
										$("#join-submit").attr("disabled", true);
										return;
									}
								}
							});
						});
					});
					
					function passwordCheckFunction(){
						var loginPw = $("#loginPw").val();
						var loginPwCheck = $("#loginPwCheck").val();
						if(loginPw != loginPwCheck){
							$("#loginPwCheckMsg").html("비밀번호가 서로 일치하지 않습니다");
							$("#loginPwCheckMsg").css("color", "red");
						}
						else{
							$("#loginPwCheckMsg").html("");
						}
					}
				</script>
				<form action="../member/doJoin" method="POST" onsubmit="MemberJoin__submit(this); return false;">
					<div class="form-control">
						<label class="flex text-sm py-2">
							<input class="checkbox checkbox-sm" type="checkbox" />
							<span class="pl-1">이용 약관 동의</span>
						</label>
					</div>
					<div class="form-control">
						<label class="flex text-sm py-2">
							<input class="checkbox checkbox-sm" type="checkbox" />
							<span class="pl-1">개인정보 수집 약관 동의</span>
						</label>
					</div>
					<div class="form-control">
						<label class="label">
							<span class="label-text">아이디</span>
						</label>
						<div class="flex">
							<input class="input input-bordered w-full" maxlength="100" id="loginId" name="loginId" type="text"
								placeholder="아이디를 입력해주세요." />
						</div>
						<div class="pl-1 pt-2 text-sm" id="loginIdCheckMsg" style="color : red;"></div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">비밀번호</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" id="loginPw" name="loginPw" type="password"
								placeholder="비밀번호를 입력해주세요." onkeyup="passwordCheckFunction();"/>
						</div>
					</div>
					
					<div class="form-control">
						<label class="label">
							<span class="label-text">비밀번호 확인</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" id="loginPwCheck" name="loginPwCheck" type="password"
								placeholder="비밀번호를 한번 더 입력해주세요." onkeyup="passwordCheckFunction();"/>
						</div>
						<div class="pl-1 pt-2 text-sm" id="loginPwCheckMsg"></div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">이름</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" name="name" type="text"
								placeholder="이름을 입력해주세요." />
						</div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">닉네임</span>
						</label>
						<div class="flex">
							<input class="input input-bordered w-full" maxlength="100" id="nickname" name="nickname" type="text"
								placeholder="닉네임을 입력해주세요." />
						</div>
						<div class="pl-1 pt-2 text-sm" id="nicknameCheckMsg" style="color : red;"></div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">이메일</span>
						</label>
						<div class="flex">
							<input class="input input-bordered w-full" maxlength="100" id="email" name="email" type="email"
							placeholder="이메일을 입력해주세요." />
						</div>
						<div class="pl-1 pt-2 text-sm" id="emailCheckMsg" style="color : red;"></div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">전화번호</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100" name="cellphoneNo" type="tel"
								placeholder="전화번호를 입력해주세요." />
						</div>
					</div>

					<div class="btns">
						<button id="join-submit" type="submit" class="btn btn-link" value="0">회원가입</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<%@ include file="../part/foot.jspf"%>