package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("IssueI59LW4 测试")
public class IssueI59LW4Test {

	@Test
    @DisplayName("字节")
	public void bytesTest(){
		final JSONObject jsonObject = JSONUtil.createObj().set("bytes", new byte[]{1});
		assertEquals("{\"bytes\":[1]}", jsonObject.toString());

		final byte[] bytes = jsonObject.getBytes("bytes");
		assertArrayEquals(new byte[]{1}, bytes);
	}

	@Test
    @DisplayName("字节InJSON数组")
	public void bytesInJSONArrayTest(){
		final JSONArray jsonArray = JSONUtil.createArray().set(new byte[]{1});
		assertEquals("[[1]]", jsonArray.toString());

		final byte[] bytes = jsonArray.getBytes(0);
		assertArrayEquals(new byte[]{1}, bytes);
	}
}
