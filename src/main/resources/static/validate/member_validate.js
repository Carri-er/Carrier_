// member validate


var passwordValidate = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;

function reset_Pw(){
		/* 비번 유효성 검사 */
	if (passwordValidate.test(addMember.Member_Pw.value)) {
		if (addMember.Member_Pw.value.length === addMember.passwordCK.value.length) {
			alert("사용하실 수 있는 비밀번호 입니다.");

		} else {
			alert("비밀번호가 일치하지 않습니다");
			addMember.passwordCK.focus();
			return false;
		}
	} else {
		alert("비밀번호 유효성 검사 지켜라");
		addMember.Member_Pw.focus();
		return false;
	}
}

function signUp_check() {
	/*var passwordValidate = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;*/
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

	/* 아이디 중복 검사 */
	var idCk = $("#id").val();
	$.ajax({
		url: "/idCheck?id=" + idCk,
		success: function(data) {
			if (data == "fail") {
				/* $("#result").text("사용가능한 번호입니다."); */
				alert("중복된 아이디 입니다");
				$("#id").focus();
			} else if (data == "success") {
				/* $("#result").text("중복된 아이디입니다."); */
				document.getElementById('addMember').submit();
			}
		},
	});

	/* 비번 유효성 검사 */
	if (passwordValidate.test(addMember.Member_Pw.value)) {
		if (addMember.Member_Pw.value.length === addMember.passwordCK.value.length) {
			alert("사용하실 수 있는 비밀번호 입니다.");

		} else {
			alert("비밀번호가 일치하지 않습니다");
			addMember.passwordCK.focus();
			return false;
		}
	} else {
		alert("비밀번호 유효성 검사 지켜라");
		addMember.Member_Pw.focus();
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
}
