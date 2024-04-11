(function() {
    return {
    	IDS_CERT_LOADING_MSG:            ["인증서 목록을 불러오는 중입니다.", "전자서명 중입니다.", "인증서 폐지를 진행중입니다.", "인증서를 삭제하는 중입니다.", "인증서를 갱신하는 중입니다.", "인증서를 복사하는 중입니다.", 
    	                                  "인증서를 가져오는 중입니다.", "인증서를 내보내는 중입니다.", "비밀번호를 변경하는 중입니다.", "비밀번호를 확인하는 중입니다.", "전자봉투 생성 중입니다.", 
    	                                  "전자봉투 검증 중입니다.", "인증서를 검증하는 중입니다.", "라이센스를 검증하는 중입니다.", "저장매체 정보를 가져오는 중입니다.", "인증서를 발급하는 중입니다.", 
    	                                  "전자서명 데이터 검증 중입니다.", "인증서 백업 중입니다.", "백업된 인증서 복원 중입니다.", "해쉬 데이터 생성중입니다.", "대칭키 암호화 중입니다."],
        IDS_ERROR_USER_CANCEL:            "사용자가 실행을 취소했습니다.",
        IDS_ERROR_NOT_SUPPORT_ALGORITHM:  "지원하지 않는 알고리즘입니다.",
        IDS_ERROR_FILE_NOT_FOUND:		  "모듈 로드에 실패하였습니다.",
        IDS_ERROR_DO_NOT_AVAILABLE:       "웹 실행파일의 검증정보 획득에 실패하였습니다.\n인증서 발급/재발급/갱신이 안되실 경우\n한국전자인증 고객센터 1566-0566으로 문의하여 주십시오.",
        IDS_ERROR_NIM_NOT_INSTALL:		  "인증서 관리 프로그램이 설치되지 않아 서비스 이용이 불가합니다.\n프로그램 설치 페이지로 이동합니다.",
        IDS_ERROR_NIM_NOT_INSTALL_CFM:		  "인증서 관리 프로그램이 설치되지 않아 서비스 이용이 불가합니다.\n프로그램 설치 페이지로 이동 하시겠습니까?", 
        IDS_ERROR_NIM_NOT_LASTEST_VERSION:		  "인증서 관리 프로그램이 최신버전이 아닙니다.\n프로그램 설치 페이지로 이동합니다.",
        IDS_ERROR_BROWSER_NOT_SUPPORT:    "이 브라우져에서는 지원하지 않는 기능입니다.",
        IDS_MSGBOX_NIM_ERROR_UNLOAD:     "UniCRSLocalserver가  언로드 되었습니다.\n페이지 새로 고침을 하시거나 첫 페이지로 이동하셔서 재시도 해주십시오.",
		IDS_ERROR_REQUEST_RESPONSE:		  "UniCRSLocalserver와의 통신에 실패했습니다.\n 다시 시도하시기 바랍니다.",         
        
        IDS_VERIFYKEY_SIGN:				  "[서 명 용  인증서] ",
        IDS_VERIFYKEY_KM:				  "[암호화용 인증서] ",
        
        IDS_VERIFYKEY_OK:				  "성공",
        IDS_VERIFYKEY_FAIL:				  "실패",
        IDS_VERIFYKEY_NONE:				  "없음",
        
        IDS_VERIFY_SIGN:				  "전자서명 검증에 실패했습니다.",
        IDS_VID_VERIFY_MESSAGE:			  "본인확인을 수행하는 중입니다. 잠시만 기다려주세요.",
        // yjyoon 2016.06.01, 키보드 보안 모듈 설치를 확인하고 있습니다
        IDS_KS_INSTALL_CHECK:			  "모듈 설치 체크 중입니다.", 
        
        IDS_NOALLOW_UPDATECERT: 		  "인증서 갱신을 진행할 수 없습니다. 해당 발급기관에서 갱신을 시도하여 주시기 바랍니다.",
        IDS_NOALLOW_SOE: 		 		  "인증서 효력정지를 진행할 수 없습니다. 해당 발급기관에서 효력정지를 시도하여 주시기 바랍니다.",
        IDS_NOALLOW_REVOCATION: 		  "인증서 폐지를 진행할 수 없습니다. 해당 발급기관에서 폐지를 시도하여 주시기 바랍니다.",
                
        IDS_MSGBOX_VID_ERROR_VERIFICATION: 	"인증서의 신원확인 검증에 실패하였습니다. \n 인증서의 주민등록번호 또는 사업자번호와 일치하지 않습니다.",
        
        IDS_MSGBOX_VERIFY_LICENSE: 		  	"License 검증에 실패했습니다. License를 확인하시기 바랍니다.",
        
        IDS_MSGBOX_LOCALSERVER_UNLOAD: 		"UniCRSLocalServer가 unload 되어 있습니다. \n현재 진행중인 작업은 종료됩니다. \n페이지 새로 고침을 하시거나 첫 페이지로 이동하셔서 재시도 해주십시오.",
        IDS_ERROR_NOT_SUPPORT_DEVICE:  		"지원하지 않는 매체입니다.",
        
        IDS_ERROR_NOT_MATTCHED_PWD:  		"비밀번호가 올바르지 않습니다.",
        IDS_NO_KMCERT:						"암호화용 인증서가 없습니다.",
        
        IDS_ERROR_FUC_NOT_SUPPORT:  		"현재모드에서는 지원하지 않는 기능입니다.",  
        
        IDS_CANT_ISSUECERT_BIOTOKEN:		"발급 및 갱신, 재발급시 지문보안토큰을 이용할 수 없습니다.",
        IDS_ERROR_VERIFY_ID:				"신원확인에 실패했습니다.",
		IDS_ERROR_EMPTY_PARAMS:				"인자값이 올바르지 않습니다",
		IDS_VERIFY_CERT_ERROR_REVOKED:						"인증서가 폐지되었습니다.",
   		IDS_VERIFY_CERT_ERROR_EXPIRED:						"인증서의 유효 기간이 만료되었거나 아직 유효하지 않습니다.",
		IDS_VERIFY_CERT_ERROR_CHECKING_ISSUER_FAIL: 		"인증서의 발급자 정보 확인에 실패하였습니다.",
		IDS_VERIFY_CERT_OK: 								"유효한 인증서 입니다.",
		IDS_ERROR_RENEW_WHALE:			"인증서 갱신이 허가 되어 있지 않습니다. 해당 등록기관에 확인하신 후 갱신을 시도하여 주시기 바랍니다.",
		IDS_MSGBOX_COMMON_ERROR_GET_CERT:					"인증서 획득에 실패하였습니다.",
		IDS_CANCEL_BACKUP:				"인증서 백업이 취소 되었습니다.\n 선택하신 저장매체에 인증서 발급은 완료 되었습니다."
    }
})();