<%@ page language="java" import="java.io.*,java.util.*,crosscert.*" %>
<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import="org.springframework.transaction.PlatformTransactionManager" %>
<%@ page import="org.springframework.transaction.TransactionStatus" %>
<%@ page import="org.springframework.transaction.support.DefaultTransactionDefinition" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.ibatis.sqlmap.client.SqlMapClient" %>

<%
    /*-------------------------시작----------------------------*/
    request.setCharacterEncoding("euc-kr");
	response.setDateHeader("Expires",0); 
	response.setHeader("Prama","no-cache"); 

	if(request.getProtocol().equals("HTTP/1.1")) 
	{ 
		response.setHeader("Cache-Control","no-cache"); 
	} 
    /*------------------------- 끝----------------------------*/
	
    String signeddata = request.getParameter("signedText");		// 서명된 값
	String getR = request.getParameter("userR");		// 사용자 인증서 R값
	
	String idn = request.getParameter("idn");                   // 주민번호 DB에 저장될 값
    String certType = request.getParameter("cert_type");         //인증서구분 B : 사업자번호 , P : 주민등록번호
    String userDN = "";   // DB에 저장될 값 인증서에서 추출함.
  	//등록용
    String startDate = "";
    String endDate = "";
    String certSerial = ""; 
    
    //out.println("certType >> "+certType);
    int  nRet;
	boolean boolCertChk = true;
	String ErrMsg = "";
	String ErrCode = "";
	
	
	crosscert.Base64 CBase64 = new crosscert.Base64();  
	nRet = CBase64.Decode(signeddata.getBytes(), signeddata.getBytes().length);
	
	if(nRet==0) 
	{
		
		Verifier CVerifier = new Verifier();
		nRet=CVerifier.VerSignedData(CBase64.contentbuf, CBase64.contentlen); 
		if(nRet==0) 
		{
			String sOrgData = new String(CVerifier.contentbuf);
			//System.out.println("전자서명 검증 결과 : 성공");
			//System.out.println("원문 : " + sOrgData );

			//인증서 정보 추출 결과	
			Certificate CCertificate = new Certificate();
			nRet=CCertificate.ExtractCertInfo(CVerifier.certbuf, CVerifier.certlen);
			if(nRet==0) 
			{			
				System.out.println("인증서 정보 추출 결과: 성공 ");
				System.out.println("인증서 DN  : " + CCertificate.subject);
				System.out.println("==============================================");
				System.out.println("subject  : " + CCertificate.subject);
				System.out.println("from : " + CCertificate.from);
				System.out.println("to : " + CCertificate.to);
				System.out.println("signatureAlgId : " + CCertificate.signatureAlgId);
				System.out.println("pubkey : " + CCertificate.pubkey);
				System.out.println("signature : " + CCertificate.signature);
				System.out.println("issuerAltName : " + CCertificate.issuerAltName);
				System.out.println("subjectAltName : " + CCertificate.subjectAltName);
				System.out.println("keyusage : " + CCertificate.keyusage);
				System.out.println("policy : " + CCertificate.policy);
				System.out.println("basicConstraint : " + CCertificate.basicConstraint);
				System.out.println("policyConstraint : " + CCertificate.policyConstraint);
				System.out.println("distributionPoint : " + CCertificate.distributionPoint);
				System.out.println("authorityKeyId : " + CCertificate.authorityKeyId);
				System.out.println("subjectKeyId : " + CCertificate.subjectKeyId);
				System.out.println("==============================================");
				
	            //등록 정보 세팅 
				userDN = CCertificate.subject;
	            startDate = CCertificate.from;
	            endDate = CCertificate.to;
	            certSerial = CCertificate.serial;
            
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
                
                //2014.05.15 조현설대리 요청
                policies +="1.2.410.200005.1.1.4"      + "|";
            	policies +="1.2.410.200005.1.1.4"      + "|";          // 금융결제원                 인터넷뱅킹용
            	policies +="1.2.410.200004.5.2.1.2"    + "|";          // 한국정보인증               개인
            	policies +="1.2.410.200004.5.1.1.5"    + "|";          // 한국증권전산               개인
            	policies +="1.2.410.200005.1.1.1"      + "|";          // 금융결제원                 개인
            	policies +="1.2.410.200004.5.3.1.4"    + "|";          // 한국전산원           개인(국가기관, 공공기관 및 법인의 소속직원 등)
            	policies +="1.2.410.200004.5.4.1.1"    + "|";          // 한국전자인증               개인
            	policies +="1.2.410.200012.1.1.1"      + "|";          // 한국무역정보통신           개인
            	policies +="1.2.410.200005.1.1.6.8"      + "|";          // 한국무역정보통신           개인
            	policies +="1.2.410.200004.5.1.1.5"      + "|";          // (주)이노지움              개인
            	policies +="1.2.410.200004.5.1.1.13"     + "|";  //코스콤


                //2014.05.15 조현설대리 요청
                
                //2014.06.17 추가
            	policies +="1.2.410.200004.5.2.1.6.115"  + "|";          // 한국전자인증
            	policies +="1.2.410.200004.10.1.1"     + "|";

            	/* 전자인증 철도시설공단 전용 인증서 */
                /*
                policies += "1.2.410.200004.5.4.1.30"      + "|";
                */
                 /* 개인상호연동용(범용)                             //

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

                System.out.println("인증서 검증 결과 : 성공") ;

				System.out.println("인증서 검증 결과 : 성공  CVerifier : " + CVerifier) ;
				System.out.println("인증서 검증 결과 : 성공  getR : " + getR) ;
				System.out.println("인증서 검증 결과 : 성공  idn : " + idn) ;
				
				if(nRet==0) 
				{
					// 식별번호 검증	 DB에 저장된 주민/사업자번호와 getR 값을 통한 신원확인
					nRet=CCertificate.VerifyVID(CVerifier.certbuf, CVerifier.certlen, getR.getBytes(), getR.length(), idn);
					if(nRet==0) 
					{
						//System.out.println("식별번호 검증 결과 : 성공") ;
						/* 
							
							인증서 로직 종료 
							사용자 DN  DB에 저장 : CCertificate.subject 
							사용자 인증서 주민/사업자번호 : idn 
						*/
					}
					else
					{
						boolCertChk = false;
						System.out.println("식별번호 검증 결과 : 실패") ;

						ErrMsg = "식별번호 검증 실패 [ 에러내용 : " + CCertificate.errmessage + " ]";
						ErrCode = "에러코드 [ " + CCertificate.errcode + " ]";  
						//System.out.println("인증서 식별번호 검증 실패");
					}
				}
				else
				{
					boolCertChk = false;
					ErrMsg = "인증서 검증 실패 [ 에러내용 : " + CCertificate.errmessage + " ]";
					ErrCode = "에러코드 [ " + CCertificate.errcode + " ]";  
				}// 인증서만 검증if문 끝
			
			}
			else
			{
				boolCertChk = false;
				ErrMsg = "인증서 추출 실패 [ 에러내용 : " + CCertificate.errmessage + " ]";
				ErrCode = "에러코드 [ " + CCertificate.errcode + " ]";  
			}
		}//
		else
		{			
			boolCertChk = false;
			ErrMsg = "전자서명 검증 결과 실패 [ 에러내용 : " + CVerifier.errmessage + " ]";
			ErrCode = "에러코드 [ " + CVerifier.errcode + " ]";  
		}
	}//
	else
	{
		boolCertChk = false;
		ErrMsg = "서명값 Base64 Decode 결과 실패 [ 에러내용 : " + CBase64.errmessage + " ]";
		ErrCode = "에러코드 [ " + CBase64.errcode + " ]";  
	} //서명값 Base64 Decode If문 끝...
	if (boolCertChk == false)
	{
%>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
			alert("<%=ErrMsg%>\n\n<%=ErrCode%>");
			//alert("전자서명을 다시 하여 주십시오");
			parent.location.href = "/main/loginPage.do";
		//-->
		</SCRIPT>
<%
	}
	else
	{
		//System.out.println("성공");
		//System.out.println("DN값과 주민/사업자번호를 DB에 저장함.");
		//System.out.println("DN : " + userDN );
		//System.out.println("주민번호 : " + idn );

        try {
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
            SqlMapClient sqlMap = (SqlMapClient)ctx.getBean("sqlMapClient");

            try {
            	sqlMap.startTransaction();
            	
                HashMap param = new HashMap();

                HashMap membData = new HashMap();

                //out.println("idn >> "+idn);

                if(certType.equals("B")) {
                	param.put("biz_no", idn);

	                //조합원정보조회
	                membData = (HashMap) sqlMap.queryForObject("Main.getMemberDataByBizNo", param);
                } else {
                	param.put("pers_no",idn);

                	//조합원정보조회
	                membData = (HashMap) sqlMap.queryForObject("Main.getMemberDataByPersNo", param);
                }

                if(membData == null || membData.isEmpty()) {
                    %>
                    <SCRIPT LANGUAGE="JavaScript">
                    <!--
                        alert("조합원 정보를 찾을 수 없습니다.");
                        parent.location.href = "/main/loginPage.do";
                    //-->
                    </SCRIPT>
                    <%
                    return;
                }

                //등록여부확인
                param.put("user_dn", userDN);
                param.put("cert_sn", certSerial);

                HashMap certData = (HashMap)sqlMap.queryForObject("Main.getCertInfo", param);

                if(certData != null && !certData.isEmpty() && !"".equals(certData.get("MEMB_NO"))) {
                	%>
                    <SCRIPT LANGUAGE="JavaScript">
                    <!--
                        alert("이미 등록되어있는 인증서입니다.");
                        parent.location.href = "/main/loginPage.do";
                    //-->
                    </SCRIPT>
                    <%
                    return;
                }

                //인증서등록
                param.put("memb_no", membData.get("MEMB_NO"));
                param.put("start_date", startDate);
                param.put("end_date", endDate);
                param.put("user_num", idn);

                sqlMap.insert("Main.insertMembCert", param);
                sqlMap.commitTransaction();
            }
            catch(Exception e) {
                throw e;
            }
            finally {
            	sqlMap.endTransaction();
            }
            
            %>
            <SCRIPT LANGUAGE="JavaScript">
            <!--
                alert("인증서가 등록되었습니다.");
                parent.location.href = "/main/loginPage.do";
            //-->
            </SCRIPT>
            <%
        }
        catch(Exception e) {
            %>
            <SCRIPT LANGUAGE="JavaScript">
            <!--
                alert("인증서등록도중 오류가 발생하였습니다.\n<%=e%>");
                parent.location.href = "/main/loginPage.do";
            //-->
            </SCRIPT>
            <%
        }
	}
%>
