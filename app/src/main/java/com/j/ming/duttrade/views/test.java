package com.j.ming.duttrade.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class test {
    public static class ScoreItem {
        private int baseScore;
        private int plusScore;

        public int getBaseScore() {
            return baseScore;
        }

        public void setBaseScore(int baseScore) {
            this.baseScore = baseScore;
        }

        public int getPlusScore() {
            return plusScore;
        }

        public void setPlusScore(int plusScore) {
            this.plusScore = plusScore;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ruleCount = Integer.valueOf(scanner.nextLine().trim());
        Map<Integer, ScoreItem> scoreMap = new HashMap<>();
        int totalScore = 0;
        for (int i = 0; i < ruleCount; i++) {
            String[] input = scanner.nextLine().trim().split(" ");
            if (input.length != 3 && input.length != 4)
                continue;
            if (Integer.valueOf(input[0]) == 1) {
                int start = Integer.valueOf(input[1]);
                int end = Integer.valueOf(input[2]);
                int score = Integer.valueOf(input[3]);
                for (int j = start; j <= end; j++) {
                    ScoreItem item = scoreMap.get(j);
                    if (item == null) {
                        item = new ScoreItem();
                        totalScore = totalScore - item.getBaseScore() + score;
                        item.setBaseScore(score);
                        scoreMap.put(j, item);
                    } else if(item.getBaseScore() < score){
                        totalScore = totalScore - item.getBaseScore() + score;
                        item.setBaseScore(score);
                        scoreMap.put(j, item);
                    }
                }
            } else {        //任务加分
                int time = Integer.valueOf(input[1]);
                int score = Integer.valueOf(input[2]);
                ScoreItem item = scoreMap.get(time);
                if (item == null)
                    item = new ScoreItem();
                item.setPlusScore(item.getBaseScore() + score);
                totalScore += score;
            }
        }

        System.out.println(totalScore);
    }
}

/**
 *
 3
 1 1 5 10
 2 3 4
 1 4 6 -5
 */


