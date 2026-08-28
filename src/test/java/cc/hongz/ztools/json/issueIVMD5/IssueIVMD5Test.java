package cc.hongz.ztools.json.issueIVMD5;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import cc.hongz.ztools.core.io.resource.ResourceUtil;
import cc.hongz.ztools.core.lang.TypeReference;
import cc.hongz.ztools.json.JSONUtil;

public class IssueIVMD5Test {

	/**
	 * 测试泛型对象中有泛型字段的转换成功与否
	 */
	@Test
	public void toBeanTest() {
		String jsonStr = ResourceUtil.readUtf8Str("issueIVMD5.json");

		TypeReference<BaseResult<StudentInfo>> typeReference = new TypeReference<BaseResult<StudentInfo>>() {};
		BaseResult<StudentInfo> bean = JSONUtil.toBean(jsonStr, typeReference.getType(), false);

		StudentInfo data2 = bean.getData2();
		assertEquals("B4DDF491FDF34074AE7A819E1341CB6C", data2.getAccountId());
	}

	/**
	 * 测试泛型对象中有包含泛型字段的类型的转换成功与否，比如List&lt;T&gt; list
	 */
	@Test
	public void toBeanTest2() {
		String jsonStr = ResourceUtil.readUtf8Str("issueIVMD5.json");

		TypeReference<BaseResult<StudentInfo>> typeReference = new TypeReference<BaseResult<StudentInfo>>() {};
		BaseResult<StudentInfo> bean = JSONUtil.toBean(jsonStr, typeReference.getType(), false);

		List<StudentInfo> data = bean.getData();
		StudentInfo studentInfo = data.get(0);
		assertEquals("B4DDF491FDF34074AE7A819E1341CB6C", studentInfo.getAccountId());
	}
}
