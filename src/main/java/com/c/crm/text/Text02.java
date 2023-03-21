package com.c.crm.text;

public class Text02 {



    public static void main(String[] args) {
        String a="aa";
        String b= new String("aa");
        String c=b;
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(b==c);
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
    }
}
