package cc.hongz.ztools.json.xml;

import cc.hongz.ztools.core.util.StrUtil;
import cc.hongz.ztools.json.JSONException;
import cc.hongz.ztools.json.XML;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Issue2748Test {

	@Test
	public void toJSONObjectTest() {
		final String s = StrUtil.repeat("<a>", 600);

		assertThrows(JSONException.class, () -> {
			XML.toJSONObject(s, ParseConfig.of().setMaxNestingDepth(512));
		});
	}
}
