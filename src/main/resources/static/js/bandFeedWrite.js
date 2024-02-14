$(document).ready(function() {
	$('#summernote').summernote({
		placeholder : '​​​​​​​새로운 소식을 남겨보세요.',
		// 썸머노트 옵션 설정
		tabsize: 4,
		height: 500,
		codeviewFilter: false, // 코드 보기 필터 비활성화
		codeviewIframeFilter: false, // 코드 보기 iframe 필터 비활성화
		disableXSSProtection: true,
		minHeight: 500,
		maxHeight: 800,
		focus: true, // 에디터 로딩 후 포커스 설정
		lang: 'ko-KR', // 언어 설정 (한국어)
		toolbar: [
			// 스타일 관련 기능
			['style', ['style']],
			// 글자 크기 설정
			['fontsize', ['fontsize']],
			// 글꼴 스타일
			['font', ['bold', 'underline', 'clear']],
			// 글자 색상
			['color', ['color']],
			// 테이블 삽입
			['table', ['table']],
			// 문단 스타일
			['para', ['paragraph']],
			// 에디터 높이 설정
			['height', ['height']],
			// 이미지, 링크, 동영상 삽입
			['insert', ['picture', 'link', 'video']],
			// 코드 보기, 전체화면, 도움말
			['view', ['codeview', 'fullscreen', 'help']],
		],
		fontSizes: [
			// 글자 크기 선택 옵션
			'8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'
		],
		styleTags: [
			// 스타일 태그 옵션
			'p',
			{ title: 'Blockquote', tag: 'blockquote', className: 'blockquote', value: 'blockquote' },
			'pre',
			{ title: 'code_light', tag: 'pre', className: 'code_light', value: 'pre' },
			{ title: 'code_dark', tag: 'pre', className: 'code_dark', value: 'pre' },
			'h1', 'h2', 'h3', 'h4', 'h5', 'h6'
		],

		callbacks: {
			onImageUpload : function(files) {
                    uploadSummernoteImageFile(files[0],this);
                }
		},
	})
})

// 이미지 업로드 ajax
function uploadSummernoteImageFile(file, editor) {
            data = new FormData();
            data.append("file", file);
            $.ajax({
                data : data,
                type : "POST",
                url : "/uploadSummernoteImageFile",
                contentType : false,
                processData : false,
                success : function(data) {
                    //항상 업로드된 파일의 url이 있어야 한다.
                    $(editor).summernote('insertImage', data.url);
                }
            });
        }
    
