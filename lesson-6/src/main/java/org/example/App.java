package org.example;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App<V> {
    public static void main(String[] args) {
        GreetingService helloService = createMethodLoggingProxy(GreetingService.class);
        helloService.hello(); // logs nothing to the console
        helloService.gloryToUkraine(); // logs method invocation to the console


        System.out.println();
        System.out.println("===============================================================");
        System.out.println();

        List<Integer> list = new ArrayList<>();

        list.add(9);
        list.add(999);
        list.add(91);
        list.add(99);
        list.add(-9);
        list.add(-999);
        list.add(9);


        sort(list);

        System.out.println(list);
    }

    /**
     * Creates a proxy of the provided class that logs its method invocations. If a method that
     * is marked with {@link LogInvocation} annotation is invoked, it prints to the console the following statement:
     * "[PROXY: Calling method '%s' of the class '%s']%n", where the params are method and class names accordingly.
     *
     * @param targetClass a class that is extended with proxy
     * @param <T>         target class type parameter
     * @return an instance of a proxy class
     */
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GreetingService.class);

        enhancer.setCallback((MethodInterceptor) (obj, method, arguments, proxy) -> {
            if (method.getDeclaredAnnotations().length > 0
                    && method.getDeclaredAnnotations()[0].annotationType().equals(LogInvocation.class)) {
                System.out.printf("[PROXY: Calling method '%s' of the class '%s']%n", method.getName(), targetClass.getSimpleName());
                return proxy.invokeSuper(obj, arguments);
            }
            return obj;
        });
        return (T) enhancer.create();
    }



    public static<V extends Comparable<V>> void sort(List<V> list){
        sortAndMerge(list, 0, list.size()-1);
    }

    public static<V extends Comparable<V>> void sortAndMerge(List<V> list, int start, int end) {
        if ((end - start) >= 2) {
            int mid = (end - start) / 2;

            sortAndMerge(list, start, start + mid);
            sortAndMerge(list, start + mid + 1, end);

            int i = start;
            int j = start + mid + 1;

            while (i < j && j <= end) {
                if (list.get(i).compareTo(list.get(j)) > 0) {
                    list.add(i, list.remove(j));
                    i++;
                    j++;
                } else if (list.get(i) == list.get(j)) {
                    list.add(i + 1, list.remove(j));
                    i++;
                    j++;
                } else {
                    i++;
                }
            }

        } else {
            if (end > start) {
                if (list.get(start).compareTo(list.get(end)) > 0) {
                    V endValue = list.remove(end);
                    list.add(start, endValue);
                }
            }
        }
    }
}
