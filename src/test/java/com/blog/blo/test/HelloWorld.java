package com.blog.blo.test;

/**
 * @Author: ALiang
 * @Date: 2021/3/30 20:31
 * @Description:
 */
public class HelloWorld {
    //1. 定义一个整型变量并赋任意五位正整数作为初始值，判断它是不是五位回文数
    //（五位回文数：个位与万位相同，十位与千位相同，例如：12321）：
    public void checkNumber(int number){
        int tempNumber = number; //存放number的值，用意在于在循环中，numbere本身的值不会发生改变
        int reversalNumber=0; //用于存放反转后的number
        while(tempNumber > 0){
            //每次循环都取tempNumber的最后一个位置的数字，且随着循环次数的增加，reversalNumber的值也会一位一位的增加，最后和tempNumber最开始的的位数一致
            reversalNumber = reversalNumber * 10 + tempNumber % 10;
            //每次循环 ， tempNumber的值都除以10 ， 用意在于随着循环的增加，reversalNumber慢慢就获取了tempNumber的个位，十位，百位，千位..上的数字
            tempNumber = tempNumber / 10;
        }
        //如果反转后的reversalNumber和number相等 ，说明这是一个回文数
        if (reversalNumber == number){
            System.out.println(number+"是一个回文数！");
        }else{
            //如果不相等，则不是回文数
            System.out.println(number+"不是一个回文数！");
        }
    }

//2. 定义一个整型变量并赋任意五位正整数作为初始值，输出各位数字之和
//（例如：12345 各位之和是：1+2+3+4+5 。也就是 15）
    public void numberSum(int number){
        int sum = 0;
        while(number > 0){
            sum += number % 10;
            number /= 10;
        }
        System.out.println(number+"的各位数字之和是:"+sum);
    }

    /**
     * 3. 定义整型变量 a、b，写出将 a、b 两个变量值进行互换的程序
     *（要求不能使用第三个变量）
     */
    public void exchangeTwoNumber(int a,int b){
        System.out.println("两个数交换前：a="+a+",b="+b);
        //第一步：将两个值相加起来，存放在a中
        a = a + b;
        //第二步，将相加后的值减去b 类似于 a + b - b = a,此时，把值赋给b,完成第一轮值交换
        b = a - b;
        //第三步，将相加后的值减去已经交换过值得b，类似于a + b - a = b ,此时，把值赋给a,完成第二次值交换
        a = a - b;
        System.out.println("两个数交换后：a="+a+",b="+b);
        System.out.println("####################");
    }

//4. 请写出一段遵守编码规范的 Hello World 代码
    public void printHelloWorld(){
        System.out.println("Hello World!");
    }


    public static void main(String[] args) {

        HelloWorld h = new HelloWorld();
       h.printHelloWorld();
    }

}
