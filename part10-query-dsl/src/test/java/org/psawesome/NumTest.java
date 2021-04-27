package org.psawesome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pilseong.ko
 */
public class NumTest {

    @Test
    void testInit() {
        assertEquals(1, 자리수_구하기(7));
        assertEquals(2, 자리수_구하기(77));
        assertEquals(3, 자리수_구하기(777));
        assertEquals(4, 자리수_구하기(7777));
        assertEquals(5, 자리수_구하기(77773));
    }

    @Test
    void 십의_승수_구하기() {
        assertEquals(10, 십의_승수(1));
        assertEquals(100, 십의_승수(2));
        assertEquals(1000, 십의_승수(3));
    }

    @Test
    void 세번째() {
        assertEquals(10, 십의_승수(자리수_구하기(7)));
        assertEquals(100, 십의_승수(자리수_구하기(77)));
        assertEquals(1000, 십의_승수(자리수_구하기(777)));
        assertEquals(10000, 십의_승수(자리수_구하기(7777)));
    }

    @Test
    void testInitial() {
        assertEquals(31, getCount(77770, 77780, 77));
        assertEquals(2, getCount(7770, 7771, 77));
        int count2 = getCount(77770, 77771, 777);
        System.out.println("count2 = " + count2);
    }

    @Test
    void testGetCount2() {
        int count2 = getCount2(77770, 77, 100);
        assertEquals(3, count2);
    }

    @Test
    void testCount() {
        assertEquals(31, getCount(77770, 77780, 77));
        assertEquals(2, getCount(77770, 77771, 777));
    }

    private int getCount(int start, int end, int target) {
        int totalCount = 0;
        int targetSize = 자리수_구하기(target);
        int 십의_승수_값 = 십의_승수(targetSize);
        for (int i = start; i < end; i++) {
            totalCount += getCount2(i, target, 십의_승수_값);
        }
        return totalCount;
    }

    private int getCount2(int field, int target, int 십의_승수_값) {
        int fieldSize = 자리수_구하기(field);
        int temp = field;
        int count = 0;
        for (int j = 0; j < fieldSize; j++) {
            //            System.out.println("temp =  : " + temp);
            int mod = temp % 십의_승수_값;
            if (target == mod) {
                count++;
            }
            //            System.out.println("mod = " + mod);
            temp = temp / 10;
        }
        return count;
    }

    /**
     * 십의 승수를 구한다.
     * ex) 1 -> 10
     *
     * @param 자리수
     * @return
     */
    private int 십의_승수(int 자리수) {
        int result = 10;
        for (int i = 1; i < 자리수; i++) {
            result *= 10;
        }
        return result;
    }

    /**
     * 자리수 구하기
     *
     * @param targetNum
     * @return
     */
    private int 자리수_구하기(int targetNum) {
        int maxSize = 1;
        if (targetNum / 10 == 0) {
            return maxSize;
        }
        for (int i = 10; ; i *= 10) {
            int temp = targetNum / i;
            if (temp == 0) {
                return maxSize;
            }
            maxSize++;
        }
    }
}
