package com.example.finalwork.Helper;


import com.example.finalwork.model.Matter;

import java.util.Comparator;

/**
 * 两个倒数日根据离当前日期的日数进行比较
 */

public class MatterComparator implements Comparator {

    @Override
    public int compare(Object first, Object second) {
        int flag = 0;
        Matter a = (Matter) first;
        Matter b = (Matter) second;
        long duration1 = Utility.getDateInterval(a.getTargetDate());
        long duration2 = Utility.getDateInterval(b.getTargetDate());
        //比较a和b中的某个属性，根据大于小于等于 flag分别为1 -1 0
        if (duration1>duration2)
            flag = 1;
        else if (duration1==duration2){
            flag = 0;
        }else if (duration1<duration2){
            flag = -1;
        }

        return flag;
    }
}
