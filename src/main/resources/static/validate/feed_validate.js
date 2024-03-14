// feed validate

/*

 필수항목 -
1. 이미지 :
	- 적어도 1개 이상의 이미지를 사용할 것
	- 사진 업로드 개수는 최대 3장
2. 제목 :
	- 65자 이내로 작성할 것 
3. 태그 :
	- 두가지 태그 모두 사용할 것
	
-----------------------------------------------------
 선택항목 -
4. 내용 :
	- content는 작성 안 해도 무방하지만 133자 이내로 작성할 것

*/

	
function feed_write_check(){
	/* 글자수 제한 */
	var max_title = 65;
	var max_content = 133;
	
	/*제목*/
	if (feedWriteForm.Feed_title.value.length == "") {
		alert("제목을 작성해주세요");
		feedWriteForm.Feed_title.focus();
		return false;
	}
	
	if (feedWriteForm.Feed_title.value.length > max_title) {
		alert("제목은 "+max_title+"자 이내로 작성해주세요");
		feedWriteForm.Feed_title.focus();
		return false;
	}
	
	if (feedWriteForm.Feed_content.value.length > 133) {
		alert("내용은 "+max_content+"자 이내로 작성해주세요");
		feedWriteForm.Feed_content.focus();
		return false;
	}
	
	/*태그*/
	if($("input[name='Feed_theme']:radio:checked").length < 1){
	    alert("테마 버튼을 선택해 주세요.");
	    return false;
	}

	if($("input[name='Feed_area']:radio:checked").length < 1){
	    alert("지역 버튼을 선택해 주세요.");
	    return false;
	}
	
	/*이미지*/
	var imgFile1 = $('#fileUpload1').val();
	var imgFile2 = $('#fileUpload2').val();
	var imgFile3 = $('#fileUpload3').val();
	var fileForm = /(.*?)\.(jpg|jpeg|png|gif|bmp|pdf)$/;
	
	//alert(imgFile1+imgFile2+imgFile3);
	
if (imgFile1.length === 0 && imgFile2.length === 0 && imgFile3.length === 0) {
    alert("이미지 파일을 업로드 해주세요!");
    return false;
}

	document.getElementById('feedWriteForm').submit();

}


function feed_update_check(){
	/* 글자수 제한 */
	var max_title = 65;
	var max_content = 133;


	/*제목*/
	if (feedUpdateForm.Feed_title.value.length == "") {
		alert("제목을 작성해주세요");
		feedWriteForm.Feed_title.focus();
		return false;
	}
	
	if (feedUpdateForm.Feed_title.value.length > max_title) {
		alert("제목은 "+max_title+"자 이내로 작성해주세요");
		feedWriteForm.Feed_title.focus();
		return false;
	}
	
	if (feedUpdateForm.Feed_content.value.length > 133) {
		alert("내용은 "+max_content+"자 이내로 작성해주세요");
		feedWriteForm.Feed_content.focus();
		return false;
	}
	
	/*태그*/
	if($("input[name='Feed_theme']:radio:checked").length < 1){
	    alert("테마 버튼을 선택해 주세요.");
	    return false;
	}

	if($("input[name='Feed_area']:radio:checked").length < 1){
	    alert("지역 버튼을 선택해 주세요.");
	    return false;
	}
	

	document.getElementById('feedUpdateForm').submit();
	
}
