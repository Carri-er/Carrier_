// member validate

function reset_Pw(){
	var passwordValidate = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
		/* 비번 유효성 검사 */
	if (passwordValidate.test(passwordReset.password.value)) {
		if (passwordReset.password.value.length === passwordReset.passwordCK.value.length) {
			document.getElementById('passwordReset').submit();

		} else {
			alert("비밀번호가 일치하지 않습니다");
			passwordReset.passwordCK.focus();
			return false;
		}
	} else {
		alert("비밀번호는 영문, 숫자, 특수문자 ( ! @ # $ % ^ & * ? _ ) 를 포함하여 최소 8자~16자 이내로 설정 가능합니다");
		passwordReset.password.focus();
		return false;
	}
}

function signUp_check() {

	/* 
	1. 비밀번호 자릿수 (최소 8자에서 16자까지)
	2. 최소 하나 이상의 영문자 (소문자 or 대문자)
	3. 최소 하나 이상의 숫자
	4. 최소 하나 이상의 특수문자 (!@#$%^&*?_)
	*/


	/* 이름 유효성 검사 */
	if (addMember.Member_Name.value.length == 0) {
		alert("이름이 누락됐습니다.");
		addMember.Member_Name.focus(); // 포커스를 이동시켜 바로 아이디를 입력할 수 있게
		return false;
	}

	/* 아이디 유효성 검사 */
	if (addMember.Member_Id.value.length == 0) {
		alert("아이디가 누락됐습니다.");
		addMember.Member_Id.focus();
		return false;
	}


	/* 비번 유효성 검사 */
	var passwordValidate = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
	
	if (addMember.Member_Pw.value.length == 0 || addMember.Member_Pw.value.length == 0){
		alert("비밀번호를 확인해주세요.");
		return false;
	}
	
	if (addMember.Member_Pw.value === addMember.passwordCK.value) {
		if (!passwordValidate.test(addMember.Member_Pw.value)) {
			alert("비밀번호는 영문, 숫자, 특수문자 ( ! @ # $ % ^ & * ? _ ) 를 포함하여 최소 8자~16자 이내로 설정 가능합니다");
			addMember.Member_Pw.focus();
			return false;
		}
	} else {
		alert("비밀번호가 일치하지 않습니다");
		addMember.passwordCK.focus();
		return false;
	}
	

	/* 이메일 유효성 검사 */
	if (addMember.mail1.value.length == 0) {
		alert("이메일을 입력해주세요");
		addMember.mail1.focus();
		return false;
	}
	

	/* 전화번호 */
	if (addMember.phone1.value.length == 0
		|| addMember.phone2.value.length < 4
		|| addMember.phone3.value.length < 4) {
		alert("핸드폰 번호를 다시 확인해주세요");

		if (addMember.phone1.value.length == 0) {
			addMember.phone1.focus();
		} else if (addMember.phone2.value.length < 4) {
			addMember.phone2.focus();
		} else {
			addMember.phone3.focus();
		}
		return false;
	}

	/* 연령 유효성 검사 */
	if (addMember.Member_Age.value.length == 0) {
		alert("연령을 선택해주세요");
		addMember.Member_Age.focus();
		return false;
	}

	/* 테마 유효성 검사 */
	if (addMember.Member_Thema.value == ""
		|| addMember.Member_Thema.value == "null") {
		alert("선호하시는 테마를 선택해주세요");
		addMember.Member_Thema.focus();
		return false;
	}

	/* 지역 유효성 검사 */
	if (addMember.Member_Area.value == ""
		|| addMember.Member_Area.value == "null") {
		alert("선호하시는 지역을 선택해주세요");
		addMember.Member_Area.focus();
		return false;
	}
	
	/* 아이디 중복 검사 */
	var idCk = $("#id").val();
	$.ajax({
		url: "/idCheck?id=" + idCk,
		success: function(data) {
			if (data == "fail") {
				/* $("#result").text("중복된 아이디입니다."); */
				alert("중복된 아이디 입니다");
				$("#id").focus();
				return false;
			} else if (data == "success") {
				document.getElementById('addMember').submit();
			}
		},
	});

}

function edit_member_check() {

	/* 
	1. 비밀번호 자릿수 (최소 8자에서 16자까지)
	2. 최소 하나 이상의 영문자 (소문자 or 대문자)
	3. 최소 하나 이상의 숫자
	4. 최소 하나 이상의 특수문자 (!@#$%^&*?_)
	*/


	/* 이름 유효성 검사 */
	if (editMember.Member_Name.value.length == 0) {
		alert("이름이 누락됐습니다.");
		editMember.Member_Name.focus(); // 포커스를 이동시켜 바로 아이디를 입력할 수 있게
		return false;
	}

	/* 비번 유효성 검사 */
	var passwordValidate = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
	
	if (editMember.Member_Pw.value.length == 0 || editMember.Member_Pw.value.length == 0){
		alert("비밀번호를 확인해주세요.");
		return false;
	}
	
	if (editMember.Member_Pw.value === editMember.passwordCK.value) {
		if (!passwordValidate.test(editMember.Member_Pw.value)) {
			alert("비밀번호는 영문, 숫자, 특수문자 ( ! @ # $ % ^ & * ? _ ) 를 포함하여 최소 8자~16자 이내로 설정 가능합니다");
			editMember.Member_Pw.focus();
			return false;
		}
	} else {
		alert("비밀번호가 일치하지 않습니다");
		editMember.passwordCK.focus();
		return false;
	}
	

	/* 이메일 유효성 검사 */
	if (editMember.mail1.value.length == 0) {
		alert("이메일을 입력해주세요");
		editMember.mail1.focus();
		return false;
	}
	

	/* 전화번호 */
	if (editMember.phone1.value.length == 0
		|| editMember.phone2.value.length < 4
		|| editMember.phone3.value.length < 4) {
		alert("핸드폰 번호를 다시 확인해주세요");

		if (editMember.phone1.value.length == 0) {
			editMember.phone1.focus();
		} else if (editMember.phone2.value.length < 4) {
			editMember.phone2.focus();
		} else {
			editMember.phone3.focus();
		}
		return false;
	}
	
	document.getElementById('editMember').submit();
}
