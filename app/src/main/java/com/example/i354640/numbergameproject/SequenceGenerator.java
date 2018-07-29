package com.example.i354640.numbergameproject;

public class SequenceGenerator {




    public Sequence generateSequence(int i){

        int[] nums = new int[]{3 + i,4 + i,5 + i,8 + i};
        char[] operators = new char[] {'x', '-', 'x'};
        return new Sequence(nums,operators);
    }

}
