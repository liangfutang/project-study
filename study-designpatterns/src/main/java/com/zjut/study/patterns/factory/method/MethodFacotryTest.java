package com.zjut.study.patterns.factory.method;

public class MethodFacotryTest {

    public static void main(String[] args) {
        Phone phoneFactory = new MIPhoneFactory().createPhone();
        phoneFactory.call();
    }
}

//=====================================================

interface Phone {

    void call();
}

class MiPhone implements Phone {

    @Override
    public void call() {
        System.out.println("小米手机打电话");
    }
}

class HuaWeiPhone implements Phone {

    @Override
    public void call() {
        System.out.println("华为手机打电话");
    }
}

//=====================================================

interface PhoneFactory {
    /**
     * 生产手机
     * @return
     */
    Phone createPhone();
}

class MIPhoneFactory implements PhoneFactory {

    @Override
    public Phone createPhone() {
        return new MiPhone();
    }
}

class HuaweiPhoneFactory implements PhoneFactory {

    @Override
    public Phone createPhone() {
        return new HuaWeiPhone();
    }
}