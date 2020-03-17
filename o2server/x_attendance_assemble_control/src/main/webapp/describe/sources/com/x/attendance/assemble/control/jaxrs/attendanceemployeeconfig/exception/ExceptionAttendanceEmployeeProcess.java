package com.x.attendance.assemble.control.jaxrs.attendanceemployeeconfig.exception;

import com.x.base.core.project.exception.PromptException;

public class ExceptionAttendanceEmployeeProcess extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	public ExceptionAttendanceEmployeeProcess( Throwable e, String message ) {
		super("用户在进行考勤人员配置信息处理时发生异常！message:" + message, e );
	}
}
