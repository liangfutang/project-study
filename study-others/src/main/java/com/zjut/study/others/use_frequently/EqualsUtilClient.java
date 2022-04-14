package com.zjut.study.others.use_frequently;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.others.use_frequently.entity.Personal;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Equals比较工具的使用案例
 * @author jack
 */
public class EqualsUtilClient extends CommonJunitFilter {

    @Before
    public void init() {
        jack = new Personal()
                .setAge(12)
                .setName("jack");

        rose = new Personal()
                .setAge(12)
                .setName("rose");

    }
    private Personal jack;
    private Personal rose;


    /**
     * 判断两个对象中的属性是不是都相等
     */
    @Test
    public void equalsBuilder() {
        boolean equals = new EqualsBuilder()
                .append(jack.getAge(), rose.getAge())
                .append(jack.getName(), rose.getName())
                .isEquals();
        System.out.println("这两个对象的相等测试对比结果:" + equals);

        // 属性为null的测试
        jack.setAge(null);
        boolean equals1 = new EqualsBuilder()
                .append(jack.getAge(), rose.getAge())
                .isEquals();
        System.out.println("属性存在null时的测试结果:" + equals1);
    }
}
