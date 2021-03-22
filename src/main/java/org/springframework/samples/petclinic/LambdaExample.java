package org.springframework.samples.petclinic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class LambdaExample {

    public void hi() {
        //if add this for next build it will get modified method 'methodWithLambda'
//        Function<String, String> newLambda = testItemResource -> testItemResource + "1";
        List<String> linkedList = new LinkedList<>(Arrays.asList("sad", "lol"));
        methodWithLambda(linkedList);
    }

    private void methodWithLambda(List<String> stringList) {
        stringList.stream()
                .map(it -> it + "suffix")
                .collect(toList());
    }
}
