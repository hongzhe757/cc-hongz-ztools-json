package cc.hongz.ztools.json.xml;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.json.JSONObject;
import cc.hongz.ztools.json.XML;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("Issue3560 测试")
public class Issue3560Test {
	@Test
    @DisplayName("转JSONObject")
	public void toJSONObjectTest() {
		String inPara= "<ROOT><ID>002317479934367853</ID><CONTENT><![CDATA[asdfadf&amp;21sdgzxv&amp;aasfasf]]></CONTENT></ROOT>";
		JSONObject json = XML.toJSONObject(inPara, true);
		assertEquals("{\"ROOT\":{\"ID\":\"002317479934367853\",\"CONTENT\":\"asdfadf&amp;21sdgzxv&amp;aasfasf\"}}", json.toString());
	}
}
