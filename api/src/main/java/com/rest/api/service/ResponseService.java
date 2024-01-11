package com.rest.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rest.api.model.CommonResult;
import com.rest.api.model.ListResult;
import com.rest.api.model.SingleResult;

@Service
public class ResponseService {
	
	// enum으로 api 요청 결과에 대한 code, message를 정의
	public enum CommonResponse {
		
		SUCCESS(0, "성공"),
		FAIL(-1, "실패");
		
		int code;
		String resMsg;
		
		CommonResponse(int code, String resMsg) {
			this.code = code;
			this.resMsg = resMsg;
		}
		
		public int getCode() {
			return code;
		}
		
		public String getResMsg() {
			return resMsg;
		}
		
	}
	
	// 단일 건 결과를 처리하는 메서드
	public <T> SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}
	
	// 다중 건 결과를 처리하는 메서드
	public <T> ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		setSuccessResult(result);
		return result;
	}
	
	// 성공 결과만 처리하는 메서드
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}
	
	// 실패 결과만 처리하는 메서드
	public CommonResult getFailResult() {
		CommonResult result = new CommonResult();
		result.setSuccess(false);
		result.setCode(CommonResponse.FAIL.getCode());
		result.setResMsg(CommonResponse.FAIL.getResMsg());
		return result;
	}
	
	// 결과 모델에 api 요청 성공 데이터를 세팅해주는 메서드
	private void setSuccessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setResMsg(CommonResponse.SUCCESS.getResMsg());
	}

}
