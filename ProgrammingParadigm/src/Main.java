import java.util.function.Consumer;
import java.util.function.Function;
@FunctionalInterface
interface MyFunction{
    int operation(int a, int b);
}

public class Main {
    static Function<Integer, Integer> multiplyBy(int x) {
        return y -> x * y;
    }
    public static void register (String name , Consumer<String> callback) {
        String main = name + "@domaincom";
        callback.accept(main);
    }
    static Consumer <String>sendWelcomeMail = mail -> System.out.println("Mailing " + mail);

    public static void main(String[] args) {

        // assigning function to variable
        MyFunction add = (a,b) -> a+b;

        //Function as Argument
        System.out.println(add.operation(2,3));
        register("Ziad" ,sendWelcomeMail);


        Function<Integer, Integer> doubleNumber = multiplyBy(2);
        Function<Integer, Integer> tripleNumber = multiplyBy(3);
        System.out.println((doubleNumber.apply(4)));
        System.out.println((tripleNumber.apply(4)));
        System.out.println((multiplyBy(5).apply(6)));
    }
}
