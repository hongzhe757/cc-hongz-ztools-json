package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * https://gitee.com/chinabugotech/hutool/issues/I4RBZ4
 */
@DisplayName("IssueI4RBZ4 测试")
public class IssueI4RBZ4Test {

	@Test
    @DisplayName("排序")
	public void sortTest(){
		String jsonStr = "{\"id\":\"123\",\"array\":[1,2,3],\"outNum\":356,\"body\":{\"ava1\":\"20220108\",\"use\":1,\"ava2\":\"20230108\"},\"name\":\"John\"}";

		final JSONObject jsonObject = JSONUtil.parseObj(jsonStr, JSONConfig.create().setNatureKeyComparator());
		assertEquals("{\"array\":[1,2,3],\"body\":{\"ava1\":\"20220108\",\"ava2\":\"20230108\",\"use\":1},\"id\":\"123\",\"name\":\"John\",\"outNum\":356}", jsonObject.toString());
	}
}
