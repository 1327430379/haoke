package cn.haoke.mgmt.controller.base;

import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.BindException;
import java.util.List;

@Controller
public class AbstractBaseController {

    private Logger logger = LoggerFactory.getLogger(AbstractBaseController.class);

    /***
     * 失败状态码
     */
    public static final int FAIL_CODE = -1;

    /***
     * 失败错误提示信息
     */
    public static final String MSG_SYSTEM_ERROR = "系统错误！";

    /***
     * 操作错误的通用处理方法
     * @param msg 要返回的错误提示信息
     * @return RestResponse （resultCode=-1)
     */
    protected RestResponse fail(String msg) {
        return new RestResponse(FAIL_CODE, msg, null);
    }

    /***
     * 全局异常控制
     */
    @ExceptionHandler(Exception.class)
    public RestResponse exceptionHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);

        if (ex instanceof UndeclaredThrowableException) {
            return fail("服务不可用！");
        }

        if (ex instanceof BindException) {
            return fail(ex.getMessage().replaceAll(".*?", ""));
        }
        if (ex instanceof BusinessException) {
            return fail(ex.getMessage().replaceAll(".*?]", ""));
        }
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            ObjectError objectError = allErrors.get(0);
            return fail(objectError.getDefaultMessage().replaceAll(".*?]",""));
        }

        return fail(MSG_SYSTEM_ERROR);

    }

    /***
     * 获取Springmvc参数校验错误信息
     */
    public String getBindErrorMsg(BindingResult bindingResult) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            //每次只返回一个提示信息，如需返回全部则遍历errorList
            ObjectError objectError = errorList.get(0);
            return objectError.getDefaultMessage();
        }
        return MSG_SYSTEM_ERROR;
    }

    /***
     * 返回前端错误
     * @param errorCode
     * @param errorMsg
     */
    public void error(int errorCode,String errorMsg)  {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        assert response != null;
        response.setStatus(errorCode);
        try {
            response.getWriter().println(errorMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
