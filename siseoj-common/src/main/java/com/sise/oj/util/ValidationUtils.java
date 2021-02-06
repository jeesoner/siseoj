package com.sise.oj.util;

import cn.hutool.core.util.ObjectUtil;
import com.sise.oj.exception.BadRequestException;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

/**
 * 验证工具
 *
 * @author Cijee
 * @version 1.0
 */
public class ValidationUtils {

    /**
     * 验证空
     */
    public static void isNull(Object obj, String entity, String parameter , Object value){
        if(ObjectUtil.isNull(obj)){
            String msg = entity + " 不存在: "+ parameter +" is "+ value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }
}
