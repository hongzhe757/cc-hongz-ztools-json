package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.collection.ListUtil;
import cc.hongz.ztools.core.io.resource.ResourceUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Predicate多层过滤
 */
@DisplayName("IssueI5OMSC 测试")
public class IssueI5OMSCTest {

	@Test
    @DisplayName("过滤")
	public void filterTest(){
		final JSONObject json = JSONUtil.parseObj(ResourceUtil.readUtf8Str("issueI5OMSC.json"));

		final String s = json.toJSONString(0, (entry) -> {
			final Object key = entry.getKey();
			if(key instanceof String){
				return ListUtil.of("store", "bicycle", "color", "book", "author").contains(key);
			}
			return true;
		});
		assertEquals("{\"store\":{\"bicycle\":{\"color\":\"red\"},\"book\":[{\"author\":\"Evelyn Waugh\"},{\"author\":\"Evelyn Waugh02\"}]}}", s);
	}
}
