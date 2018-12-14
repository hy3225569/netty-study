package com.hy.test;

/**
 * @ClassName TestClass
 * @Description Todo测试类
 * @Author holy
 * @Date 2018/12/13 14:20
 **/
public class TestClass {
    public static void main(String[] args) {
        System.out.println("***************外部类打印信息******************");
        OutClass outClass = new OutClass();
        outClass.OutPrint();

        System.out.println("***************内部类打印信息******************");
        //内部类实例可调用外部类的成员变量，静态成员变量，成员方法，静态成员方法
        OutClass.InnerClass innerClass = outClass.getInner();
        innerClass.print();

        //普通的内部类是不可以实例化的
        //InnerClass innerClass=new InnerClass();
        System.out.println("***************内部静态类打印信息******************");
        //静态内部类中只有外部类的静态成员变量和静态成员方法是可以访问的
        OutClass.InnerStaticClass.print();
    }
}
