package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.lang.Console;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * JSON字符串格式化单元测试
 * @author looly
 *
 */
@DisplayName("JSONStrFormatter 测试")
public class JSONStrFormatterTest {

	@Test
    @DisplayName("格式化")
	public void formatTest() {
		String json = "{'age':23,'aihao':['pashan','movies'],'name':{'firstName':'zhang','lastName':'san','aihao':['pashan','movies','name':{'firstName':'zhang','lastName':'san','aihao':['pashan','movies']}]}}";
		String result = JSONStrFormatter.format(json);
		assertNotNull(result);
	}

	@Test
    @DisplayName("格式化")
	public void formatTest2() {
		String json = "{\"abc\":{\"def\":\"\\\"[ghi]\"}}";
		String result = JSONStrFormatter.format(json);
		assertNotNull(result);
	}

	@Test
    @DisplayName("格式化")
	public void formatTest3() {
		String json = "{\"id\":13,\"title\":\"《标题》\",\"subtitle\":\"副标题z'c'z'xv'c'xv\",\"user_id\":6,\"type\":0}";
		String result = JSONStrFormatter.format(json);
		assertNotNull(result);
	}

	@Test
	@Disabled
    @DisplayName("格式化")
	public void formatTest4(){
		String jsonStr = "{\"employees\":[{\"firstName\":\"Bill\",\"lastName\":\"Gates\"},{\"firstName\":\"George\",\"lastName\":\"Bush\"},{\"firstName\":\"Thomas\",\"lastName\":\"Carter\"}]}";
		Console.log(JSONUtil.formatJsonStr(jsonStr));
	}
}
