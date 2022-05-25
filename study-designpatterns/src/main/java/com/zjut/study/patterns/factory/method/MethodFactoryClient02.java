package com.zjut.study.patterns.factory.method;

/**
 * @author jack
 * @see ./pic/工厂方法UML图.png
 * @see ./pic/简单工厂模式类图-简化使用.png
 */
public class MethodFactoryClient02 {
    public static void main(String[] args) {
        // 创建一个工厂
        AbstractHumanFactory factory = new HumanFactory();
        // 用工厂创建对象
        Human yellowHuman = factory.createHuman(YellowHuman.class);
        yellowHuman.getColor();
        yellowHuman.talk();

        Human blackHuman = factory.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();
    }
}

//===============================================================
abstract class AbstractHumanFactory {
    public abstract <T extends Human> T createHuman(Class<T> t);
}

/**
 * 其中一种工厂
 * 可以简化使用，去除抽象层，将方法静态化使用
 */
class HumanFactory extends AbstractHumanFactory {
    @Override
    public <T extends Human> T createHuman(Class<T> t) {
        T human = null;
        try {
            human = (T) Class.forName(t.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return human;
    }
}


//===============================================================
interface Human {
    void getColor();
    void talk();
}

class BlackHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("小黑");
    }

    @Override
    public void talk() {
        System.out.println("黑人说黑人的话");
    }
}

class YellowHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("黄种人");
    }

    @Override
    public void talk() {
        System.out.println("说中国话");
    }
}

class WhiteHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("小白");
    }

    @Override
    public void talk() {
        System.out.println("英语");
    }
}
