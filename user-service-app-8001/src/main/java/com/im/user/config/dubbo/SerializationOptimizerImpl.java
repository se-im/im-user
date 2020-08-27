package com.im.user.config.dubbo;

import com.im.user.entity.vo.UserRegisterVo;
import com.im.user.entity.vo.UserVo;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
public class SerializationOptimizerImpl implements SerializationOptimizer
{

    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(UserVo.class);
        classes.add(UserRegisterVo.class);
        //TODO
        return classes;
    }
}