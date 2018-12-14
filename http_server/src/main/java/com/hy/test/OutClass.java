package com.hy.test;

/**
 * @ClassName OutClass
 * @Description Todo 内部类和外部类测试
 * @Author holy
 * @Date 2018/12/13 13:58
 **/
public class OutClass {
    private int a = 0;
    public int b = 0;
    private static int c = 0;

    public void OutPrint() {
        InnerClass innerClass = new InnerClass();
        System.out.println("OutClass方法:" + innerClass.aint);
    }

    public InnerClass getInner() {
        return new InnerClass();
    }

    public static void outPrintStatic() {
        System.out.println("OutClass静态方法");
    }

    public class InnerClass {
        private int aint = 1;
        public int bint = 1;

        public void print() {
            //输出外部类的私有成员变量
            System.out.println("OutClass私有成员变量:" + a);
            System.out.println("OutClass私有静态成员变量:" + c);
            OutPrint();
            outPrintStatic();
        }
    }

    public static class InnerStaticClass {
        private int asint = 2;
        public int bsint = 2;
        public static int csint = 2;

        public static void print() {
            System.out.println("OutClass私有静态成员变量:" + c);
            outPrintStatic();
        }

    }


}
