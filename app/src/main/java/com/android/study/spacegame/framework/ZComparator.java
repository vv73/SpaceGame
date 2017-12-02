package com.android.study.spacegame.framework;

import java.util.Comparator;

//Cравниватель ZOrder-а. Если оба объекта имеют z-order, то сравниваем значения,
// иначе приоритетным считается объект с имеющимся zorder-ом.
class ZComparator implements Comparator<Object>{
    @Override
    public int compare(Object lhs, Object rhs) {
        if (lhs instanceof ZOrdered && rhs instanceof ZOrdered)
            return ((ZOrdered) lhs).zOrder() - ((ZOrdered) rhs).zOrder() > 0 ? 1 : -1;
        if (lhs instanceof ZOrdered)
            return 1;
        if (rhs instanceof ZOrdered)
            return -1;
        return 0;
    }
};