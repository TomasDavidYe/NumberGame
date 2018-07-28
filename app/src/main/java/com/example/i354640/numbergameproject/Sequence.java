package com.example.i354640.numbergameproject;

import java.util.HashMap;
import java.util.Map;

public class Sequence {

    private int result;
    private int[] numbers;
    private char[] operators;
    public static final Map<Character, Operator> operatorInterpretation = new HashMap<Character, Operator>(){
        {
            put('+', (a,b) -> a + b);
            put('-', ((a, b) -> a - b));
            put('x', ((a, b) -> a*b));
        }
    };

    public Sequence(int[] numbers, char[] operators){
        this.numbers = numbers;
        this.operators = operators;
        this.result = evaluate();

    }

    public int getResult() {
        return result;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void submitNewOperators(char[] newOperatos){
        this.operators = newOperatos;
    }

    public int evaluate(){
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
        public abstract int Operation(int a, int b);
    }


}
