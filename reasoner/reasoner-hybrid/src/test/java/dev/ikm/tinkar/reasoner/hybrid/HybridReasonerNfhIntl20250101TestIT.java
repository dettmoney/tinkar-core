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
package dev.ikm.tinkar.reasoner.hybrid;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.ikm.tinkar.common.service.PrimitiveData;

public class HybridReasonerNfhIntl20250101TestIT extends HybridReasonerNfhTestBase
		implements SnomedVersionInternational {

	private static final Logger LOG = LoggerFactory.getLogger(HybridReasonerNfhIntl20250101TestIT.class);

	private static String write_db = "" + UUID.randomUUID();

	static {
		test_case = "snomed-intl-20250101";
	}

	{
		expected_child_miss = 4;
	}

	@Override
	public String getVersion() {
		return "20250101";
	}

	@BeforeAll
	public static void startPrimitiveData() throws IOException {
		LOG.info("Write: " + write_db);
		PrimitiveDataTestUtil.copyDb(test_case + "-sa", write_db);
		PrimitiveDataTestUtil.setupPrimitiveData(write_db);
		PrimitiveData.start();
	}

	@AfterAll
	public static void stopPrimitiveData() throws IOException {
		PrimitiveDataTestUtil.stopPrimitiveData();
//		PrimitiveDataTestUtil.deleteDb(write_db);
	}

}
