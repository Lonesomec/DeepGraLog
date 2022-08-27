package pub.developers.forum.facade.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pub.developers.forum.common.enums.ErrorCodeEn;
import pub.developers.forum.common.exception.BizException;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.common.support.RequestContext;
import pub.developers.forum.common.support.StringUtil;
import pub.developers.forum.facade.support.ResultModelUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/28
 * @desc
 **/
@Slf4j
@Component
@Aspect
public class ExceptionHandlerAndLogAspect {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Around("execution(* pub.developers.forum..*.*(..))")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();
        Object result;
        Throwable throwable = null;
        Boolean invokeSuccess = true;

        try {
            result = pjp.proceed();
        } catch (BizException bizException) {
            result = ResultModelUtil.fail(bizException.getCode(), bizException.getMessage());
            invokeSuccess = false;
            throwable = bizException;
        } catch (Exception e) {
            result = ResultModelUtil.fail(ErrorCodeEn.SYSTEM_ERROR);
            invokeSuccess = false;
            throwable = e;
        }

//        logRequestInfo(invokeSuccess, pjp, start, result, throwable);

        String TraceID = RequestContext.getTraceId();
        if(!TraceID.equals(""))
            logRequestInfo(invokeSuccess,pjp, start,throwable);

        return result;
    }

    private void logRequestInfo(Boolean invokeSuccess,ProceedingJoinPoint pjp, Long start, Throwable e) {
        Long cost = System.currentTimeMillis() - start;
        String signature = pjp.getSignature().toString();
        String methodPath = signature.split(" ")[1];
        int lastCommIndex = methodPath.lastIndexOf(".");
        String methodClass = methodPath.substring(0,lastCommIndex);
        String method = methodPath.substring(lastCommIndex+1);
//        String parent = "";
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
//        System.out.println("stack size : "+ste.length);
//        List<String> stackTraceList = new ArrayList<String>();//find parent class method call using stack trace
//        int count = -1;
        String stackTraceInfo = "";//find parent class method call using stack trace
        for(StackTraceElement ele : ste){
            if(ele.getClassName().contains("pub.developers.forum")) {
                if(stackTraceInfo.equals(""))
                    stackTraceInfo += ele.getClassName().split("\\$")[0]+" "+ele.getMethodName();
                else
                    stackTraceInfo += "<-"+ele.getClassName().split("\\$")[0]+" "+ele.getMethodName();
//                stackTraceList.add(ele.getClassName().split("\\$")[0]+" "+ele.getMethodName());
//                System.out.print(++count + " : " + ele.getClassName().split("\\$")[0] + " " + ele.getMethodName() + " # ");
            }
        }
//        System.out.println();
//        for(StackTraceElement ele : ste){//find parent class method call using stack trace
//            if(ele.getClassName().contains("pub.developers.forum")){
//                String method1 = method.split("\\(")[0];
//                if(!ele.getClassName().contains("pub.developers.forum.facade.aspect.ExceptionHandlerAndLogAspect")
//                        && !(ele.getClassName().split("\\$")[0]+" "+ele.getMethodName()).equals(methodClass+" "+method1)){
//                    parent = ele.getClassName().split("\\$")[0]+" "+ele.getMethodName();
//                    break;
//                }
//
//            }
//        }
        if(null == e){
            LogUtil.info(log, "invokeInfo={} , class={} , method={} , stackTraceInfo={} , cost={}ms , event={} , exception=null"
                    , invokeSuccess ? "success" : "fail"
                    , methodClass
                    , method
                    , stackTraceInfo
                    , cost
                    , methodClass+" is executing the method : "+method
            );
            return;
        }
        if(e instanceof BizException){
            LogUtil.error(log, "invokeInfo={} , class={} , method={} , stackTraceInfo={} , cost={}ms , event={} , exception={}"
                    , invokeSuccess ? "success" : "fail"
                    , methodClass
                    , method
                    , stackTraceInfo
                    , cost
                    , methodClass+" is executing the method : "+method
                    , ((BizException) e).getCode() + " : " + e.getClass().getName() + " : " + e.getMessage()
            );
            return;
        }
        if(e instanceof Exception){
            LogUtil.error(log, "invokeInfo={} , class={} , method={} , stackTraceInfo={} , cost={}ms , event={} , exception={}"
                    , invokeSuccess ? "success" : "fail"
                    , methodClass
                    , method
                    , stackTraceInfo
                    , cost
                    , methodClass+" is executing the method : "+method
                    , ErrorCodeEn.SYSTEM_ERROR.getCode()+" : "+ e.getClass().getName() + " : " +e.getMessage()
            );
            return;
        }




    }

//    private void logRequestInfo(Boolean invokeSuccess, ProceedingJoinPoint pjp, Long start, Object result, Throwable e) {
//        Long cost = System.currentTimeMillis() - start;
//        if (null == e) {
//            LogUtil.info(log, "{}, ip={}. api={}#{}. cost={}ms. params={}. result={}"
//                    , invokeSuccess ? "success" : "fail"
//                    , httpServletRequest.getRemoteAddr()
//                    , pjp.getSourceLocation().getWithinType().getName()
//                    , pjp.getSignature().getName()
//                    , cost
//                    , StringUtil.toJSONString(pjp.getArgs())
//                    , (null == result ? "" : StringUtil.toJSONString(result)));
//            return;
//        }
//
//        LogUtil.info(log, e, "fail, ip={}. api={}#{}. cost={}ms. params={}. result={}. exception={}"
//                , httpServletRequest.getRemoteAddr()
//                , pjp.getSourceLocation().getWithinType().getName()
//                , pjp.getSignature().getName()
//                , cost
//                , StringUtil.toJSONString(pjp.getArgs())
//                , (null == result ? "" : StringUtil.toJSONString(result))
//                , e.getClass().getName() + ": " + e.getMessage());
//    }


}
