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
package dev.ikm.tinkar.events;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void testSimple() {
        String hello = "Hello World!";
        Subscriber<Evt> subscriber = evt -> {
            System.out.println("Event received " + evt.getEventType());
        };
        EvtBusFactory.getDefaultEvtBus().subscribe(hello, Evt.class, subscriber);

        EvtBusFactory.getDefaultEvtBus().publish(hello, new MyEvent(this, Evt.ANY));


    }

    class MyEvent extends Evt {

        /**
         * Constructs a prototypical Event.
         *
         * @param source    the object on which the Event initially occurred
         * @param eventType
         * @throws IllegalArgumentException if source is null
         */
        public MyEvent(Object source, EvtType eventType) {
            super(source, eventType);
        }

    }
}
