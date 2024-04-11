<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page language="java" import="java.io.*,java.util.*,crosscert.*" %>
<%@ page import="com.kocfc.common.util.ParseUtil" %>
<%@ page import="com.kocfc.common.util.SessionUtil" %>
<%@ page import="org.springframework.transaction.PlatformTransactionManager" %>
<%@ page import="org.springframework.transaction.TransactionStatus" %>
<%@ page import="org.springframework.transaction.support.DefaultTransactionDefinition" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.ibatis.sqlmap.client.SqlMapClient" %>
<%
	/*-------------------------시작----------------------------*/
	response.setDateHeader("Expires",0);
	response.setHeader("Prama","no-cache");

	if(request.getProtocol().equals("HTTP/1.1"))
	{
		response.setHeader("Cache-Control","no-cache");
	}
	/*------------------------- 끝----------------------------*/

	String signeddata = request.getParameter("signedText");		// 서명된 값
	String sRandom = request.getParameter("userR");		// 사용자 인증서 R값
	
	//로그인용
	String idn = "";		            // 주민번호 ==> 위 DN에 해당하는 주민번호 DB에서 검색하여 가져와야 함.
	String userDN = "";
	String membNo = "";

	int nRet;
	boolean boolCertChk = true;
	String ErrMsg = "";
	String ErrCode = "";

	Base64 CBase64 = new Base64();
	nRet = CBase64.Decode(signeddata.getBytes(), signeddata.getBytes().length);

	if(nRet==0)
	{

		//out.println("서명값 Base64 Decode 결과 : 성공<br>") ;

		Verifier CVerifier = new Verifier();
		nRet=CVerifier.VerSignedData(CBase64.contentbuf, CBase64.contentlen);
		if(nRet==0)
		{
			//String sOrgData = new String(CVerifier.contentbuf);
			//out.println("전자서명 검증 결과 : 성공<br>") ;
			//out.println("원문 : " + sOrgData + "<br>");

			//인증서 정보 추출 결과
			Certificate CCertificate = new Certificate();
			nRet=CCertificate.ExtractCertInfo(CVerifier.certbuf, CVerifier.certlen);
			if(nRet==0)
			{
				out.println("인증서 정보 추출 결과 : " + Integer.toHexString(nRet));
				/*
				out.println("subject : " + CCertificate.subject +"<br>");

				out.println("from : " + CCertificate.from +"<br>");
				out.println("to : " + CCertificate.to +"<br>");
				out.println("signatureAlgId : " + CCertificate.signatureAlgId +"<br>");
				out.println("pubkey : " + CCertificate.pubkey +"<br>");
				out.println("signature : " + CCertificate.signature +"<br>");
				out.println("issuerAltName : " + CCertificate.issuerAltName +"<br>");
				out.println("subjectAltName : " + CCertificate.subjectAltName +"<br>");
				out.println("keyusage : " + CCertificate.keyusage +"<br>");
				out.println("policy : " + CCertificate.policy +"<br>");
				out.println("basicConstraint : " + CCertificate.basicConstraint +"<br>");
				out.println("policyConstraint : " + CCertificate.policyConstraint +"<br>");
				out.println("distributionPoint : " + CCertificate.distributionPoint +"<br>");
				out.println("authorityKeyId : " + CCertificate.authorityKeyId +"<br>");
				out.println("subjectKeyId : " + CCertificate.subjectKeyId +"<br>");
				out.println("serial : " + CCertificate.serial +"<br>");
				*/

				userDN = CCertificate.subject;

				/*
					userDN 으로 DB 검색하여 주민번호와 ID를 가져와야 함. DN 값은 기등록되어 있는 상태
					ID는 성공후 세션값으로 설정하기 위함

					idn = "주민번호";
				*/
				try {
		            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		            SqlMapClient sqlMap = (SqlMapClient)ctx.getBean("sqlMapClient");

		            HashMap param = new HashMap();
		            param.put("user_dn", userDN);
		            param.put("cert_sn", CCertificate.serial);

		            HashMap certData = (HashMap)sqlMap.queryForObject("Main.getCertInfo", param);

		            if(certData == null || certData.isEmpty()) {
	                    %>
	                    <SCRIPT LANGUAGE="JavaScript">
	                    <!--
	                        alert("등록되지 않은 공인인증서입니다.\n인증서 등록후에 이용해 주십시오.");
	                        parent.location.href = "/main/loginPage.do";
	                    //-->
	                    </SCRIPT>
	                    <%
	                    return;
	                }

		            idn = ParseUtil.toString(certData.get("USER_NUM"));
		            membNo = ParseUtil.toString(certData.get("MEMB_NO"));
				}
				catch(Exception e) {
					idn = "";
				}

				nRet=CCertificate.VerifyVID(CVerifier.certbuf, CVerifier.certlen, sRandom.getBytes(), sRandom.length(), idn);
				//nRet = 0;
				if(nRet==0)
				{

					//out.println("식별번호 검증 결과 : 성공<br>") ;

					String policies = "";

					/* 개인상호연동용(범용) */
					policies +="1.2.410.200004.5.2.1.1"    + "|";          // 한국정보인증               법인
					policies +="1.2.410.200004.5.1.1.7"    + "|";          // 한국증권전산               법인, 단체, 개인사업자
					policies +="1.2.410.200005.1.1.5"      + "|";          // 금융결제원                 법인, 임의단체, 개인사업자
					policies +="1.2.410.200004.5.3.1.1"    + "|";          // 한국전산원                 기관(국가기관 및 비영리기관)
					policies +="1.2.410.200004.5.3.1.2"    + "|";          // 한국전산원                 법인(국가기관 및 비영리기관을  제외한 공공기관, 법인)
					policies +="1.2.410.200004.5.4.1.2"    + "|";          // 한국전자인증               법인, 단체, 개인사업자
					policies +="1.2.410.200012.1.1.3"      + "|";          // 한국무역정보통신           법인
					
					/*2014.07.11 아이빛연구소(범용)*/
					policies +="1.2.410.200005.1.1.2"      + "|";        // 한국전자인증                 법인
					
					//2014.05.15 수정
					policies +="1.2.410.200005.1.1.6.8"      + "|";          // 한국무역정보통신           개인
                    policies +="1.2.410.200005.1.1.4"      + "|";
                	policies +="1.2.410.200005.1.1.4"      + "|";          // 금융결제원                 인터넷뱅킹용
                	policies +="1.2.410.200004.5.2.1.2"    + "|";          // 한국정보인증               개인
                	policies +="1.2.410.200004.5.1.1.5"    + "|";          // 한국증권전산               개인
                	policies +="1.2.410.200005.1.1.1"      + "|";          // 금융결제원                 개인
                	policies +="1.2.410.200004.5.3.1.4"    + "|";          // 한국전산원           개인(국가기관, 공공기관 및 법인의 소속직원 등)
                	policies +="1.2.410.200004.5.4.1.1"    + "|";          // 한국전자인증               개인
                	policies +="1.2.410.200012.1.1.1"      + "|";          // 한국무역정보통신           개인
                	
                	policies +="1.2.410.200004.5.1.1.5"      + "|";          // (주)이노지움              개인
                	policies +="1.2.410.200004.5.1.1.13"     + "|";  //코스콤
                	policies +="1.2.410.200004.10.1.1"     + "|";
                	
                	//2014.06.17 추가
                	policies +="1.2.410.200004.5.2.1.6.115"  + "|";          // 한국전자인증
					/* 전자인증 철도시설공단 전용 인증서 */
					/*
					policies += "1.2.410.200004.5.4.1.30"      + "|";
					*/
					 /* 개인상호연동용(범용) */                            //
					/*
					policies +="1.2.410.200004.5.2.1.2"    + "|";          // 한국정보인증               개인
					policies +="1.2.410.200004.5.1.1.5"    + "|";          // 한국증권전산               개인
					policies +="1.2.410.200005.1.1.1"      + "|";          // 금융결제원                 개인
					policies +="1.2.410.200004.5.3.1.4"    + "|";          // 한국전산원           개인(국가기관, 공공기관 및 법인의 소속직원 등)
					policies +="1.2.410.200004.5.4.1.1"    + "|";          // 한국전자인증               개인
					policies +="1.2.410.200012.1.1.1"      + "|";          // 한국무역정보통신           개인
					policies +="1.2.410.200005.1.1.4"      + "|";          // 금융결제원                 인터넷뱅킹용

					// 개인 용도제한용 인증서정책(OID)                 용도                    공인인증기관
					policies += "1.2.410.200004.5.4.1.101"    + "|";        // 은행거래용/보험용       한국전자인증
					//policies += "1.2.410.200004.5.4.1.102"    + "|";        // 증권거래용              한국전자인증
					//policies += "1.2.410.200004.5.4.1.103"    + "|";        // 신용카드용              한국전자인증
					//policies += "1.2.410.200004.5.4.1.104"    + "|";        // 전자민원용              한국전자인증
					policies += "1.2.410.200004.5.2.1.7.1"    + "|";        // 은행거래용/보험용       한국정보인증
					//policies += "1.2.410.200004.5.2.1.7.2"    + "|";        // 증권거래용/보험용       한국정보인증
					//policies += "1.2.410.200004.5.2.1.7.3"    + "|";        // 신용카드용              한국정보인증
					//policies += "1.2.410.200004.5.1.1.9"      + "|";          // 증권거래용/보험용       한국증전산
					//policies += "1.2.410.200004.5.1.1.9.2"    + "|";        // 신용카드용              한국증전산
					//policies += "1.2.410.200005.1.1.4"        + "|";            // 은행거래용/보험용       금융결제원
					//policies += "1.2.410.200005.1.1.6.2"      + "|";          // 신용카드용              금융결제원
					policies += "1.2.410.200012.1.1.101"      + "|";          // 은행거래용/보험용       한국무역정보통신
					//policies += "1.2.410.200012.1.1.103"      + "|";          // 증권거래용/보험용       한국무역정보통신
					//policies += "1.2.410.200012.1.1.105"      + "|";           // 신용카드용              한국무역정보통신

					//policies += "1.2.410.200004.5.2.1.5001"      + "|";           // 국세청              정보인증
					*/


					// 인증서 검증
					nRet=CCertificate.ValidateCert(CVerifier.certbuf, CVerifier.certlen, policies, 1);

					if(nRet==0)
					{
						//out.println("인증서 검증 결과 : 성공<br>") ;
					}
					else
					{
						boolCertChk = false;
						ErrMsg = "인증서 검증 실패 [ 에러내용 : " + CCertificate.errmessage + " ]";
						ErrCode = "에러코드 [ " + CCertificate.errcode + " ]";
						//out.println("인증서 검증 결과 : 실패<br>") ;
						//out.println("에러내용 : " + CCertificate.errmessage + "<br>");
						//out.println("에러코드 : " + CCertificate.errcode + "<br>");
					}
				}
				else
				{
					boolCertChk = false;
					ErrMsg = "식별번호 검증 실패 [ 에러내용 : 인증서의 주민번호를 확인바랍니다. ]";
					ErrCode = "에러코드 [ " + CCertificate.errcode + " ]";
					//out.println("식별번호 검증 결과 : 실패<br>") ;
					//out.println("에러내용 : " + CCertificate.errmessage + "<br>");
					//out.println("에러코드 : " + CCertificate.errcode + "<br>");
				}
			}
			else
			{

				boolCertChk = false;
				ErrMsg = "인증서 추출 실패 [ 에러내용 : " + CCertificate.errmessage + " ]";
				ErrCode = "에러코드 [ " + CCertificate.errcode + " ]";
				//out.println("인증서 추출 결과 : 실패<br>") ;
				//out.println("에러내용 : " + CCertificate.errmessage + "<br>");
				//out.println("에러코드 : " + CCertificate.errcode + "<br>");
			}
		}//
		else
		{

			boolCertChk = false;
			ErrMsg = "인증서 추출 실패 [ 에러내용 : " + CVerifier.errmessage + " ]";
			ErrCode = "에러코드 [ " + CVerifier.errcode + " ]";
			//out.println("전자서명 검증 결과 : 실패<br>") ;
			//out.println("에러내용 : " + CVerifier.errmessage + "<br>");
			//out.println("에러코드 : " + CVerifier.errcode + "<br>");
			//return;
		}
	}//
	else
	{

		boolCertChk = false;
		ErrMsg = "인증서 추출 실패 [ 에러내용 : " + CBase64.errmessage + " ]";
		ErrCode = "에러코드 [ " + CBase64.errcode + " ]";
		//out.println("서명값 Base64 Decode 결과 : 실패<br>") ;
		//out.println("에러내용 : " + CBase64.errmessage + "<br>");
		//out.println("에러코드 : " + CBase64.errcode + "<br>");
	}

	boolCertChk = true;
	if (boolCertChk == false)
	{
%>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
			alert("<%=ErrMsg%>\n\n<%=ErrCode%>");
			///history.back();
		//-->
		</SCRIPT>
<%
	}
	else
	{
		out.print("성공");
		// UserDN으로 부터 가져온 ID로 세션값을 설정함 로그인 OK

		try {
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
	        SqlMapClient sqlMap = (SqlMapClient)ctx.getBean("sqlMapClient");

	        //조합원정보조회
	        HashMap param = new HashMap();
	        param.put("memb_no", membNo);
	        HashMap membData = (HashMap)sqlMap.queryForObject("Main.getMemberData", param);

	        SessionUtil.setSessionValue("s_memb_no", membData.get("MEMB_NO"), request);
	        SessionUtil.setSessionValue("s_memb_nm", membData.get("MEMB_NM"), request);
	        SessionUtil.setSessionValue("s_bizno", membData.get("BIZ_NO"), request);

	        //세션시간설정
	        SessionUtil.setInterval(request, 120);

	        %>
            <SCRIPT LANGUAGE="JavaScript">
            <!--
                parent.location.href = "/memb/memberView.do";
            //-->
            </SCRIPT>
            <%
		}
		catch(Exception e) {
			%>
            <SCRIPT LANGUAGE="JavaScript">
            <!--
                alert("인증서 로그인도중 오류가 발생하였습니다.");
                parent.location.href = "/main/loginPage.do";
            //-->
            </SCRIPT>
            <%
		}
	}


%>
