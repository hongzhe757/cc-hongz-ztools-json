package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import cc.hongz.ztools.core.io.resource.ResourceUtil;
import cc.hongz.ztools.core.lang.TypeReference;
import lombok.Data;

@DisplayName("Issue488 测试")
public class Issue488Test {

	@Test
    @DisplayName("转Bean")
	public void toBeanTest() {
		String jsonStr = ResourceUtil.readUtf8Str("issue488.json");

		ResultSuccess<List<EmailAddress>> result = JSONUtil.toBean(jsonStr,
				new TypeReference<ResultSuccess<List<EmailAddress>>>() {}, false);

		assertEquals("https://graph.microsoft.com/beta/$metadata#Collection(microsoft.graph.emailAddress)", result.getContext());

		List<EmailAddress> adds = result.getValue();
		assertEquals("会议室101", adds.get(0).getName());
		assertEquals("MeetingRoom101@abc.com", adds.get(0).getAddress());
		assertEquals("会议室102", adds.get(1).getName());
		assertEquals("MeetingRoom102@abc.com", adds.get(1).getAddress());
		assertEquals("会议室103", adds.get(2).getName());
		assertEquals("MeetingRoom103@abc.com", adds.get(2).getAddress());
		assertEquals("会议室219", adds.get(3).getName());
		assertEquals("MeetingRoom219@abc.com", adds.get(3).getAddress());
	}

	@Test
    @DisplayName("转集合Bean")
	public void toCollctionBeanTest() {
		String jsonStr = ResourceUtil.readUtf8Str("issue488Array.json");

		List<ResultSuccess<List<EmailAddress>>> resultList = JSONUtil.toBean(jsonStr,
				new TypeReference<List<ResultSuccess<List<EmailAddress>>>>() {}, false);

		ResultSuccess<List<EmailAddress>> result = resultList.get(0);

		assertEquals("https://graph.microsoft.com/beta/$metadata#Collection(microsoft.graph.emailAddress)", result.getContext());

		List<EmailAddress> adds = result.getValue();
		assertEquals("会议室101", adds.get(0).getName());
		assertEquals("MeetingRoom101@abc.com", adds.get(0).getAddress());
		assertEquals("会议室102", adds.get(1).getName());
		assertEquals("MeetingRoom102@abc.com", adds.get(1).getAddress());
		assertEquals("会议室103", adds.get(2).getName());
		assertEquals("MeetingRoom103@abc.com", adds.get(2).getAddress());
		assertEquals("会议室219", adds.get(3).getName());
		assertEquals("MeetingRoom219@abc.com", adds.get(3).getAddress());
	}

	@Data
	public static class ResultSuccess<T> {
		private String context;
		private T value;
	}

	@Data
	public static class EmailAddress {
		private String name;
		private String address;
	}
}
