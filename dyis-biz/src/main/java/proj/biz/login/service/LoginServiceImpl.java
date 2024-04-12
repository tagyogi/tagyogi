package proj.biz.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import proj.biz.login.repository.LoginMapper;
import java.util.HashMap;

/**
 *<p>LoginServiceImpl </p>
 *
 * @author 	: 변성균 
 * date 	: 2022.06.24
 * modifier :
 * modify-date :
 * description : 메뉴관리 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;

 
	/**
	 * <p>로그인 정보 조회 </p>
	 *
	 * @see LoginServiceImpl#selectCmEmployee()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object>selectCmEmployee(HashMap<String, Object> paramMap) {
		return loginMapper.selectCmEmployee(paramMap);
	} 
	
	/**
	 * <p>로그인 로그 처리 </p>
	 *
	 * @see LoginServiceImpl#insertLoginLog()
	 * @param domain 도메인
	 * @return 현황
	 */
	public void insertLoginLog(HashMap<String, Object> paramMap) {
		loginMapper.insertLoginLog(paramMap);
	}

	
}