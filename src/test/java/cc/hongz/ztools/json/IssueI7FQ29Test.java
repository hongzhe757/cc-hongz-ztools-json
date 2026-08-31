/*
 * Copyright (c) 2023 looly(loolly@aliyun.com)
 * Hutool is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.lang.TypeReference;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("IssueI7FQ29 测试")
public class IssueI7FQ29Test {
	@Test
    @DisplayName("转Map")
	public void toMapTest() {
		final String jsonStr = "{\"trans_no\": \"java.lang.String\"}";
		final Map<String, Class<?>> map = JSONUtil.toBean(jsonStr, new TypeReference<Map<String, Class<?>>>() {
		}, false);

		assertNotNull(map);
		assertEquals(String.class, map.get("trans_no"));
	}
}
