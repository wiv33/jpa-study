package org.psawesome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pilseong.ko
 */
class HelloControllerTest {


    @Test
    void testMaxSize() {
        int maxSize = 자리수_구하기(7);
        assertEquals(1, maxSize);
        int startSIze = 자리수_구하기(77770);
        assertEquals(10000, startSIze);
    }

    @Test
    void testInit() {
        assertEquals(31, getCount2(77770, 77780, 77));
        assertEquals(2, getCount2(7770, 7771, 77));
        int count2 = getCount2(77770, 77771, 777);
        System.out.println("count2 = " + count2);
        //        assertEquals(2, count2);
    }

    private int getCount2(int start, int end, int target) {
        int result = 0;
        int targetSize = 자리수_구하기(target);
        int startSize = 자리수_구하기(start);

        for (int i = start; i < end; i++) {
            int temp = i;
            for (int j = startSize; targetSize <= j; j /= targetSize) {
                int num = extracted(temp, j, targetSize);
                if (num == target) {
                    result++;
                }

                temp %= j;
            }
        }

        return result;
    }

    private int extracted(int target, int targetSize, int extractNum) {
        int maxSizeCnt = getMaxSizeCnt(extractNum);
        int result = 0;

        for (int i = 0; i < maxSizeCnt; i++) {
            int root = target / targetSize * extractNum;
            int next = target % targetSize;

            int maxSize = 자리수_구하기(next);
            if (maxSize == 0) {
                return result;
            }
            next = next / maxSize;
            System.out.printf("root=%d, next=%d, maxSize=%d, sum=%d\n", root, next, maxSize, root + next);
            result = root + next;
        }
        return result;
    }

    /**
     * 자리수
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
            maxSize = i;
        }
    }

    /**
     * 자리수
     *
     * @param targetNum
     * @return
     */
    private int getMaxSizeCnt(int targetNum) {
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
