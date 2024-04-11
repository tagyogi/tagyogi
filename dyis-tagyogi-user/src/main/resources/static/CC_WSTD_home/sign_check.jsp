<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page language="java" import="java.io.*,java.util.*,crosscert.*" %>
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

    String idn = request.getParameter("idn");                   // 주민번호 DB에 저장될 값
    String signed_data = request.getParameter("signed_data");     // 서명된 값
    String sRandom = request.getParameter("userR"); 
    
    System.out.println("sign_check idn : " + idn);
    System.out.println("sign_check signed_data : " + signed_data);
    System.out.println("sign_check sRandom : " + sRandom);
    
    int nRet;
    boolean boolCertChk = true;
    String ErrMsg = "";
    String ErrCode = "";
    
    Base64 CBase64 = new Base64();  
    nRet = CBase64.Decode(signed_data.getBytes(), signed_data.getBytes().length);
    
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
                //out.println("인증서 정보 추출 결과 : " + Integer.toHexString(nRet));
                System.out.println("subject : " + CCertificate.subject +"<br>");
                
                
                /* System.out.println("from : " + CCertificate.from +"<br>");
                System.out.println("to : " + CCertificate.to +"<br>");
                System.out.println("signatureAlgId : " + CCertificate.signatureAlgId +"<br>");
                System.out.println("pubkey : " + CCertificate.pubkey +"<br>");
                System.out.println("signature : " + CCertificate.signature +"<br>");
                System.out.println("issuerAltName : " + CCertificate.issuerAltName +"<br>");
                System.out.println("subjectAltName : " + CCertificate.subjectAltName +"<br>");
                System.out.println("keyusage : " + CCertificate.keyusage +"<br>");
                System.out.println("policy : " + CCertificate.policy +"<br>");
                System.out.println("basicConstraint : " + CCertificate.basicConstraint +"<br>");
                System.out.println("policyConstraint : " + CCertificate.policyConstraint +"<br>");
                System.out.println("distributionPoint : " + CCertificate.distributionPoint +"<br>");
                System.out.println("authorityKeyId : " + CCertificate.authorityKeyId +"<br>");
                System.out.println("subjectKeyId : " + CCertificate.subjectKeyId +"<br>");
                System.out.println("serial : " + CCertificate.serial +"<br>"); */
                                
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

    if (boolCertChk == false)
    {
%>
        <SCRIPT LANGUAGE="JavaScript">
        <!--
            alert("<%=ErrMsg%>\n\n<%=ErrCode%>");
        	console.log("sign_check fail!!");
            parent.fnSignCertCallback("fail");
        //-->
        </SCRIPT>
<%
    }
    else
    {
        //out.print("성공<br>");
        //out.print("DN값과 주민/사업자번호를 DB에 저장함.<br>");
        //out.print("DN : " + userDN + "<br>");
        //out.print("아이디 : " + userid + "<br>");
        //out.print("주민번호 : " + idn + "<br>");
        
        String suretyYn = request.getParameter("suretyYn");                   // 연대보증입보동의 화면
        if(suretyYn == null || "".equals(suretyYn)){
        	// 기존페이지는 기존 방식대로 처리
%>		
            <SCRIPT LANGUAGE="JavaScript">
            <!--
            	console.log("sign_check succ!!");
            	parent.fnSignCertCallback("succ");
            //-->
            </SCRIPT>
<%      	
        }else{
        	// 연대보증동의 일 경우 인증한 사용자가 연대보증인인지 확인하여 처리
        	String contr_no		= request.getParameter("contr_no");
        	String p_sure_cd	= request.getParameter("p_sure_cd");
        	String p_sch_word	= request.getParameter("p_sch_word");
        	String sure_seq	= request.getParameter("sure_seq");
        	String p_name		= request.getParameter("p_name");
        	String memb_div		= request.getParameter("memb_div");
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("contr_no"	, contr_no);
        	param.put("p_sure_cd"	, p_sure_cd);
        	param.put("p_sch_word", p_sch_word);
        	param.put("sure_seq", sure_seq);
        	param.put("p_name"	, p_name);
        	
        	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext()); 
            SqlMapClient sqlMap = (SqlMapClient)ctx.getBean("sqlMapClient"); 

            // 연대보증인 정보조회
            HashMap suretyData = (HashMap)sqlMap.queryForObject("Contract.getSuretyDetail", param);
            Boolean chkFail = false;
        	if(suretyData == null ){
        		chkFail = true;
%>		
                <SCRIPT LANGUAGE="JavaScript">
                <!--
               		alert("보증인정보가 조회되지 않습니다.");
                	console.log("sign_check fail!!");
                	parent.location.href = "../surety/suretyList.do";
                //-->
                </SCRIPT>
<%      	
        	}
        	
        	String chkData = "";
        	if("10".equals(p_sure_cd) || "20".equals(p_sure_cd) || ("40".equals(p_sure_cd) && "P".equals(memb_div)) ){  
				// 개인
				chkData	= (String) suretyData.get("JUMIN_NO");
        	}else{
        		// 기업
        		chkData = (String) suretyData.get("BIZ_NO");
        	}
        	if(!idn.equals(chkData) ){
        		chkFail = true;
        	}
        	
        	
        	if(chkFail){
%>		
                <SCRIPT LANGUAGE="JavaScript">
                <!--
                	alert("보증인 정보와 전자서명의 정보가 일치하지 않습니다.");
                	console.log("sign_check fail!!");
                	parent.location.href = "../surety/suretyList.do";
                //-->
                </SCRIPT>
<%              
        	}else{
%>		
                <SCRIPT LANGUAGE="JavaScript">
                <!--
                	console.log("sign_check succ!!");
                	parent.fnSignCertCallback("succ");
                //-->
                </SCRIPT>
<%   	
        	}
        	
        }

    }
%>
