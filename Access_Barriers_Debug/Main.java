import sun.misc.Signal;

public class Main {
    static void submethodMainClass(MainClass ins)
    {
        System.out.println("submethodMainClass " + ins.toString());
    }
    static void methodMainClass(MainClass ins)
    {
        System.out.println("methodMainClass " + ins.toString());
        submethodMainClass(ins);
    }

    static void methodInnerClass(InnerClass ins)
    {
        System.out.println("methodInnerClass " + ins.toString());
    }

    // static void extraMain()
    // {
    //     MainClass mainClass = new MainClass();

    //     System.out.println("Accessing InnerClass through MainClass");
    //     // Signal.raise(new Signal("INT"));
    //     for (int i = 0; i < 5; i++) {
    //         InnerClass ins = mainClass.innerClass;
    //         System.out.println("My log:" + ins.toString());
    //         methodInnerClass(ins);
    //     }
    //     System.out.println();

    //     System.out.println("Calling methods in inner class first reading inner through reference");
    //     // Signal.raise(new Signal("INT"));
    //     for (int i = 0; i < 5; i++) {
    //         InnerClass ins = mainClass.innerClass;
    //         ins.printMessage();
    //     }
    //     System.out.println();

    //     System.out.println("Accessing InnerClass through MainClass");
    //     for (int i = 0; i < 5; i++) {
    //         InnerClass ins = mainClass.innerClass;
    //         System.out.println("My log:" + ins.toString());
    //         // methodInnerClass(ins);
    //     }
    //     System.out.println();

    //     System.out.println("Accessing MainClass primitive");
    //     for (int i = 0; i < 5; i++) {
    //         mainClass.primitiveVar += 1;
    //     }
    //     System.out.println();

    //     System.out.println("Accessing MainClass Integer");
    //     for (int i = 0; i < 5; i++) {
    //         mainClass.objectVar += 1;
    //     }
    //     System.out.println();

    //     System.out.println("Calling methods in inner class through method in main class");
    //     for (int i = 0; i < 5; i++) {
    //         mainClass.callMethodInInnerClass();
    //     }
    //     System.out.println();

    //     System.out.println("Calling methods in inner class through reference access");
    //     for (int i = 0; i < 5; i++) {
    //         mainClass.innerClass.printMessage();
    //     }
    //     System.out.println();

    //     System.out.println("Calling methods in inner class first reading inner through reference");
    //     for (int i = 0; i < 5; i++) {
    //         InnerClass ins = mainClass.innerClass;
    //         ins.printMessage();
    //     }
    //     System.out.println();

    //     System.out.println("Passing MainClass to external methods");
    //     for (int i = 0; i < 5; i++) {
    //         methodMainClass(mainClass);
    //     }
    //     System.out.println();

    //     System.out.println("Passing InnerClass to external methods");
    //     for (int i = 0; i < 5; i++) {
    //         methodInnerClass(mainClass.innerClass);
    //     }
    //     System.out.println();
    // }

    // static MainClass staticMainClass = new MainClass();
    // static void extraStaticMainClass()
    // {
    //     System.out.println("Calling methods in inner class first reading inner through reference");
    //     // Signal.raise(new Signal("INT"));
    //     for (int i = 0; i < 5; i++) {
    //         InnerClass ins = staticMainClass.innerClass;
    //         System.out.println("whatever");
    //         ins.printMessage();
    //     }
    //     System.out.println();

    //     System.out.println("Calling methods in inner class first reading inner through reference");
    //     // Signal.raise(new Signal("INT"));
    //     for (int i = 0; i < 5; i++) {
    //         staticMainClass.innerClass.printMessage();
    //     }
    //     System.out.println();
    // }

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();
        System.out.println("Accessing MainClass primitive");
        for (int i = 0; i < 5; i++) {
            mainClass.primitiveVar += 1;
        }
        System.out.println();

        MainClass array[] = new MainClass[3];
        for (int i = 0; i < 3; ++i)
            array[i] = new MainClass();

        System.gc();
        System.out.println(mainClass);
        System.out.println(array);

        // int[] arr = new int[100000];
        // System.out.println(arr.length);
        // arr[0] = 1;
        // System.out.println(arr);
        // System.out.println("Program started");

        // MainClass[] mainClassArray = new MainClass[2];
        // for (int i = 0; i < 2; ++i)
        //     mainClassArray[i] = new MainClass();

        // InnerClass obj = new InnerClass();
        
        // Thread.sleep(6000);
        // System.out.println("Before GC");
        // System.gc();

        // System.out.println("First access");
        // // Signal.raise(new Signal("INT"));
        // MainClass ptr = mainClassArray[0];
        // System.out.println(mainClassArray[0]);
        // System.out.println(obj);
        // mainClassArray[1].primitiveVar += 1;

        // System.out.println("Before GC");
        // Thread.sleep(6000);
        // System.gc();

        // System.out.println("Second access");
        // mainClassArray[1].objectVar += 1;
        // mainClassArray[0].innerClass.var = 3;

        // System.out.println("Third access");
        // ptr.innerClass.printMessage();
        // ptr.innerClass.printMessage();
        // ptr.innerClass.printMessage();

        // extraMain();
        // extraStaticMainClass();

        // // Signal.raise(new Signal("INT"));
        // Thread.sleep(6000);
        // System.gc();
        // Thread.sleep(6000);
        // System.gc();
        // Thread.sleep(6000);
        // System.gc();
    }
}

class MainClass {
    public int primitiveVar = 3;
    private double doubleField = -4.2345;
    public Integer objectVar = 3;
    // public InnerClass innerClass;
    // public InnerClass[] innerClassArray;
    // public static InnerClass staticInnerClass = new InnerClass();

    public MainClass() {
        // this.innerClass = new InnerClass();
        // this.innerClassArray = new InnerClass[2];
        // this.innerClassArray[0] = new InnerClass();
        // this.innerClassArray[1] = new InnerClass();
    }

    // public void callMethodInInnerClass() {
    //     innerClass.printMessage();
    // }

    // public InnerClass getInnerClass()
    // {
    //     return innerClass;
    // }
}

class InnerClass {
    public int var = 3;
    public void printMessage() {
        System.out.println("Hello from InnerClass!");
    }
}
