package com.zjut.study.patterns.factory.simple;

public class SimpleFactoryTest {

    public static void main(String[] args) {
        PhoneFactory phoneFactory = new PhoneFactory();
        HuaWeiPhone huaWeiPhone = phoneFactory.creatHuaWeiPhone();
        MiPhone miPhone = phoneFactory.creatMiPhone();
        Phone mi = phoneFactory.creatPhone("mi");

        huaWeiPhone.call();
        miPhone.call();
        mi.call();
    }
}

//======================================================================

interface Phone{
    /**
     *
     */
    void call();
}

class MiPhone implements Phone{

    @Override
    public void call() {
        System.out.println("这个是小米手机");
    }
}

class HuaWeiPhone implements Phone  {

    @Override
    public void call() {
        System.out.println("这个是华为手机");
    }
}

//======================================================================

class PhoneFactory {
    /**
     * 生产小米手机
     * @return
     */
    public MiPhone creatMiPhone() {
        return new MiPhone();
    }

    /**
     * 生产一个华为手机
     * @return
     */
    public HuaWeiPhone creatHuaWeiPhone() {
        return new HuaWeiPhone();
    }

    /**
     * 根据指定的名字生产一部指定的手机
     * @param phoneName
     * @return
     */
    public Phone creatPhone(String phoneName) {
        if ("mi".equals(phoneName)) {
            return new MiPhone();
        } else if ("huawei".equals(phoneName)) {
            return new HuaWeiPhone();
        }
        return null;
    }
}
