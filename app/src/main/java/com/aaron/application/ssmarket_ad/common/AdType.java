package com.aaron.application.ssmarket_ad.common;

/**
 * Created by AaronMac on 2017. 7. 24..
 */

public enum AdType {
    T(4), A(3), B(2), C(1);

    private int typeNum;

    AdType(int typeNum) {
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public static AdType getType(int typeNum) {
        if(typeNum == T.getTypeNum()) {
            return T;
        } else if(typeNum == A.getTypeNum()) {
            return A;
        } else if(typeNum == B.getTypeNum()) {
            return B;
        } else {
            return C;
        }
    }
}
