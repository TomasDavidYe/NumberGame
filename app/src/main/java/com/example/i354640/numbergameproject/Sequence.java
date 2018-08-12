package com.example.i354640.numbergameproject;

import java.util.HashMap;
import java.util.Map;

public class Sequence {

    private int[] numbers;
    public static final Map<Character, Operator> operatorInterpretation = new HashMap<Character, Operator>(){
        {
            put('+', (a,b) -> a + b);
            put('-', ((a, b) -> a - b));
            put('x', ((a, b) -> a*b));
        }
    };

    public Sequence(int[] numbers){
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public int evaluate(char[] operators){
        int temp = numbers[0];
        int length = operators.length;
        for(int i = 0; i < length; i++){
            char operatorSign = operators[i];
            Operator operator = operatorInterpretation.get(operatorSign);
            int nextNum = numbers[i + 1];
            temp = operator.Operation(temp, nextNum);
        }

        return temp;

    }

    interface Operator {
         int Operation(int a, int b);
    }


}
