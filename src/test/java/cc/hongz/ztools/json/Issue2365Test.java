package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@DisplayName("Issue2365 测试")
public class Issue2365Test {

	@Test
    @DisplayName("转Bean")
	public void toBeanTest(){
		String jsonStr = "{\"fileName\":\"aaa\",\"fileBytes\":\"AQ==\"}";
		final FileInfo fileInfo = JSONUtil.toBean(jsonStr, FileInfo.class);
		assertEquals("aaa", fileInfo.getFileName());
		assertArrayEquals(new byte[]{1}, fileInfo.getFileBytes());
	}

	@Data
	public static class FileInfo {
		private String fileName;
		private byte[] fileBytes;
	}
}
