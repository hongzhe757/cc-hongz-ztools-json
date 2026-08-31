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

import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * https://github.com/chinabugotech/hutool/issues/3051
 */
@DisplayName("Issue3051 测试")
public class Issue3051Test {
	@Test
    @DisplayName("解析")
	public void parseTest() {
		final JSONObject jsonObject = JSONUtil.parseObj(new EmptyBean(),
			JSONConfig.create().setIgnoreError(true));

		assertEquals("{}", jsonObject.toString());
	}

	@Test
    @DisplayName("解析")
	public void parseTest2() {
		final JSONObject jsonObject = JSONUtil.parseObj(new EmptyBean());

		assertEquals("{}", jsonObject.toString());
	}

	@Data
	static class EmptyBean {

	}
}
