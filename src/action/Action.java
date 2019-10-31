package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface Action {
	// XXXAction 클래스에서 수행할 작업을 공통 메서드인 execute() 메서드로 정의(추상메서드)
	// => 파라미터로 HttpServletRequest 객체와 HttpServletResponse 객체 전달
	// => 리턴타입으로 ActionForward 타입 지정
	// => 모든 예외를 호출한 곳으로 위임(throws)
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
