package com.example.algorithms;

import java.util.Arrays;
import java.util.List;
// if input [-1,-2, ,-3, -4] return 1 since its the smallest positive integer
//if input [1,2,3] return 4 i.e n+1
// input [3,4,1, -1] return 2 as its the smallest positive integer missing
public class FindSmallestPositiveinteger {

    static void main() {
        List<Integer> list = Arrays.asList(3, 4, 1, -1);
        System.out.println(findSmallest(list));
    }

    private static int findSmallest(List<Integer> list) {

        int n = list.size();
        for (int i = 0; i < n; i++) {
            while(list.get(i) >=1 && list.get(i) <= n && list.get(i) != i+1) {
                int j = list.get(i) -1;
                int  tmp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, tmp);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) != i+1) {
                return i+1;
            }
        }
        return n+1;
    }
}
