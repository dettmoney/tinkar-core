/*
 * Copyright © 2015 Integrated Knowledge Management (support@ikm.dev)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.ikm.tinkar.common.id;

import dev.ikm.tinkar.common.id.impl.IntId0List;
import dev.ikm.tinkar.common.id.impl.IntId0Set;
import dev.ikm.tinkar.common.id.impl.IntIdListArray;
import dev.ikm.tinkar.common.id.impl.IntIdSetArray;
import dev.ikm.tinkar.common.util.time.Stopwatch;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IntIdsTest {

    @Test
    public void performanceTest10_1() {
        Random random = new Random();
        int number1 = random.nextInt(-2000000, 0);
        int number2 = random.nextInt(-2000000, 0);
        int number3 = random.nextInt(-2000000, 0);
        int number4 = random.nextInt(-2000000, 0);
        int number5 = random.nextInt(-2000000, 0);
        int number6 = random.nextInt(-2000000, 0);
        int number7 = random.nextInt(-2000000, 0);
        int number8 = random.nextInt(-2000000, 0);
        int number9 = random.nextInt(-2000000, 0);
        int number10 = random.nextInt(-2000000, 0);
        Stopwatch stopwatch = new Stopwatch();
        for (int i=0; i<1000000;i++) {
            IntIdSet set1 = IntIdSetFactoryEnum.INSTANCE.of(
                    number1,
                    number2,
                    number3,
                    number4,
                    number5,
                    number6,
                    number7,
                    number8,
                    number9,
                    number10
            );
            IntIdSet set2 = IntIdSetFactoryEnum.INSTANCE.of(
                    number1,
                    number2,
                    number3,
                    number4,
                    number5,
                    number6,
                    number7,
                    number8,
                    number9,
                    number10
            );
            boolean eq = set1.equals(set2);
        }
        System.out.println("***JTD: performance10_1 duration " + stopwatch.durationString());
    }

    @Test
    public void performanceTest10_2() {
        Random random = new Random();
        int number1 = random.nextInt(-2000000, 0);
        int number2 = random.nextInt(-2000000, 0);
        int number3 = random.nextInt(-2000000, 0);
        int number4 = random.nextInt(-2000000, 0);
        int number5 = random.nextInt(-2000000, 0);
        int number6 = random.nextInt(-2000000, 0);
        int number7 = random.nextInt(-2000000, 0);
        int number8 = random.nextInt(-2000000, 0);
        int number9 = random.nextInt(-2000000, 0);
        int number10 = random.nextInt(-2000000, 0);
        Stopwatch stopwatch = new Stopwatch();
        for (int i=0; i<1000000;i++) {
            IntIdSet set1 = IntIdSetFactoryEnum.INSTANCE.of(
                    number1,
                    number2,
                    number3,
                    number4,
                    number5,
                    number6,
                    number7,
                    number7,
                    number7,
                    number7
            );
            IntIdSet set2 = IntIdSetFactoryEnum.INSTANCE.of(
                    number1,
                    number2,
                    number3,
                    number4,
                    number5,
                    number6,
                    number7,
                    number7,
                    number7,
                    number7
            );
            boolean eq = set1.equals(set2);
        }
        System.out.println("***JTD: performance10_2 duration " + stopwatch.durationString());
    }

    @Test
    public void intIdOrderedSetTests() {
        // Size 0
        IntIdSet set1 = IntId0Set.INSTANCE;
        IntIdSet set2 = IntId0Set.INSTANCE;
        assertEquals(set1, set2);

        // Size 1
        set1 = IntIdSetFactoryEnum.INSTANCE.of(0);
        set2 = IntIdSetFactoryEnum.INSTANCE.of(1);
        assertNotEquals(set1, set2);
        assertNotEquals(set1.toArray()[0], set2.toArray()[0]);

        set1 = IntIdSetFactoryEnum.INSTANCE.of(0);
        set2 = IntIdSetFactoryEnum.INSTANCE.of(0);
        assertEquals(set1, set2);
        assertEquals(set1.toArray()[0], set2.toArray()[0]);

        set2 = IntIdSetArray.newIntIdSet(0);
        assertEquals(set1, set2);
        assertEquals(set1.toArray()[0], set2.toArray()[0]);

        // Size 2
        set1 = IntIdSetFactoryEnum.INSTANCE.of(1, 0);
        set2 = IntIdSetFactoryEnum.INSTANCE.of(0, 1);
        assertEquals(set1, set2);
        assertNotEquals(set1.toArray()[0], set2.toArray()[0]);

        // Size > 2
        set1 = IntIdSetFactoryEnum.INSTANCE.of(1, 0, 2);
        set2 = IntIdSetFactoryEnum.INSTANCE.of(0, 3);
        assertNotEquals(set1, set2);
        assertNotEquals(set1.toArray()[0], set2.toArray()[0]);

        set2 = IntIdSetFactoryEnum.INSTANCE.of(1, 1, 1);
        assertNotEquals(set1, set2);
        IntIdSet set3 = IntIdSetFactoryEnum.INSTANCE.of(1, 2, 0);
        assertEquals(set1, set3);
        System.out.println("***JTD: hashcodes for set1 " + set1.hashCode() + ", set2 " + set2.hashCode() + ", set3 " + set3.hashCode());

        // Ensure order is preserved
        set1 = IntIdSetFactoryEnum.INSTANCE.of(1, 0, 2, 1);
        assertEquals(3, set1.size());
        List<Integer> tempList = new ArrayList<>();
        set1.forEach(tempList::add);
        assertEquals(3, tempList.size());
        assertEquals(1, tempList.get(0));
        assertEquals(0, tempList.get(1));
        assertEquals(2, tempList.get(2));

        set1 = set1.with(8, 7, 6, 6).with(6).with(1);
        assertEquals(6, set1.size());
        tempList.clear();
        set1.forEach(tempList::add);
        assertEquals(6, tempList.size());
        assertEquals(1, tempList.get(0));
        assertEquals(0, tempList.get(1));
        assertEquals(2, tempList.get(2));
        assertEquals(8, tempList.get(3));
        assertEquals(7, tempList.get(4));
        assertEquals(6, tempList.get(5));
    }

    @Test
    public void intIdListTests() {
        // Size 0
        IntIdList list1 = IntId0List.INSTANCE;
        IntIdList list2 = IntId0List.INSTANCE;
        assertEquals(list1, list2);

        // Size 1
        list1 = IntIdListFactoryEnum.INSTANCE.of(0);
        list2 = IntIdListFactoryEnum.INSTANCE.of(1);
        assertNotEquals(list1, list2);
        assertNotEquals(list1.toArray()[0], list2.toArray()[0]);

        list1 = IntIdListFactoryEnum.INSTANCE.of(0);
        list2 = IntIdListFactoryEnum.INSTANCE.of(0);
        assertEquals(list1, list2);
        assertEquals(list1.toArray()[0], list2.toArray()[0]);

        list2 = new IntIdListArray(0);
        assertEquals(list1, list2);
        assertEquals(list1.toArray()[0], list2.toArray()[0]);

        // Size 2
        list1 = IntIdListFactoryEnum.INSTANCE.of(1, 0);
        list2 = IntIdListFactoryEnum.INSTANCE.of(0, 1);
        assertNotEquals(list1, list2);
        assertNotEquals(list1.toArray()[0], list2.toArray()[0]);

        // Size > 2
        list1 = IntIdListFactoryEnum.INSTANCE.of(1, 0, 2);
        list2 = IntIdListFactoryEnum.INSTANCE.of(0, 3);
        assertNotEquals(list1, list2);
        assertNotEquals(list1.toArray()[0], list2.toArray()[0]);

        // Ensure order is preserved
        list1 = IntIdListFactoryEnum.INSTANCE.of(1, 0, 2, 1);
        assertEquals(4, list1.size());
        List<Integer> tempList = new ArrayList<>();
        list1.forEach(tempList::add);
        assertEquals(4, tempList.size());
        assertEquals(1, tempList.get(0));
        assertEquals(0, tempList.get(1));
        assertEquals(2, tempList.get(2));
        assertEquals(1, tempList.get(3));

        list1 = list1.with(8, 7, 6, 6).with(6).with(1);
        assertEquals(10, list1.size());
        tempList.clear();
        list1.forEach(tempList::add);
        assertEquals(10, tempList.size());
        assertEquals(1, tempList.get(0));
        assertEquals(0, tempList.get(1));
        assertEquals(2, tempList.get(2));
        assertEquals(1, tempList.get(3));
        assertEquals(8, tempList.get(4));
        assertEquals(7, tempList.get(5));
        assertEquals(6, tempList.get(6));
        assertEquals(6, tempList.get(7));
        assertEquals(6, tempList.get(8));
        assertEquals(1, tempList.get(9));
    }
}