package com.gamerowo.beauty;
import java.util.*;

import com.badlogic.gdx.audio.Sound;
import com.gamerowo.beauty.Screens.Cutscene;
import com.gamerowo.beauty.Screens.PlayScreen;
import com.gamerowo.beauty.Sprites.Enemy;
import com.gamerowo.beauty.Sprites.Goomba;
import com.gamerowo.beauty.Sprites.Koopa;

public class Requirements {
    public static void main(String[] args){
        ArrayList<Integer> arrList = new ArrayList<Integer>();
        System.out.println(arrList.add(7));
        arrList.add(4);
        arrList.add(8);
        arrList.add(3);
        for(int i = 0; i < arrList.size(); i++){
            if(arrList.get(i) % 2 == 0){
                arrList.remove(i);
                i--;
            }
        }
        selectionSort(arrList);
        insertionSort(arrList);
        
        int[][] ints = {{2,1,4},{5,3,7},{6,2,9}};
        
        for(int[] s : ints){
            for(int ss : s){
                System.out.print(ss);
            }
            System.out.println();
        }

        for(int i = 0; i < ints[0].length; i++){
            for(int j = 0; j < ints.length; j++){
                System.out.print(ints[j][i]);
            }
            System.out.println();
        }
        
    }
    public static void selectionSort(ArrayList<Integer> arrList){
        int swaps = 0;
        for(int i = 0; i < arrList.size() - 1; i++){
            int minIndex = i;
            for(int j = i + 1; j < arrList.size(); j++){
                if(arrList.get(j) < arrList.get(minIndex)){
                minIndex = j;
                }
            }
            if(i != minIndex){
                int temp = arrList.get(minIndex);
                arrList.set(minIndex, arrList.get(i));
                arrList.set(i, temp);
                swaps++;
            }
        }
        System.out.println("Swaps: " + swaps);
    }
    public static void insertionSort(ArrayList<Integer> arrList){
    int swaps = 0;
    int insertions = 0;
    for(int i = 1; i < arrList.size(); i++){
      int temp = arrList.get(i);
      int insertionIndex = i;
      while(insertionIndex > 0 && temp < arrList.get(insertionIndex - 1)){
        arrList.set(insertionIndex, arrList.get(insertionIndex - 1));
        insertionIndex--;
        swaps++;
      }
      //and insert the value at index i in its proper position
      arrList.set(insertionIndex, temp);
      //also count the number of insertions
      insertions++;
    }
    System.out.println("Swaps: " + swaps);
    System.out.println("Insertions: " + insertions);
  }

}
