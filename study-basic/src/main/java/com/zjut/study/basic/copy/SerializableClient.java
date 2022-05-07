package com.zjut.study.basic.copy;

import com.zjut.study.basic.copy.entity.Body;
import com.zjut.study.basic.copy.entity.Hand;
import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

/**
 * 序列化深度拷贝，内部自定义类也必须实现序列化接口Serializable，否则会抛出异常NotSerializableException
 */
public class SerializableClient extends CommonJunitFilter {

    @Test
    public void deepCopy() {
        Hand hand = new Hand("shoubu", 10);

        Body body = new Body("body", 56, hand);
        Body bodyClone = body.deepClone();
        // false 两个对象了
        System.out.println(body == bodyClone);
        // false 深度拷贝，是两个对象了
        System.out.println(body.getHand() == bodyClone.getHand());
    }
}
