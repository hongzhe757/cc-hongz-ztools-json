package cc.hongz.ztools.json;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.core.io.resource.ResourceUtil;
import cc.hongz.ztools.core.lang.TypeReference;
import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@DisplayName("IssueI82AM8 测试")
public class IssueI82AM8Test {

	@Test
    @DisplayName("转Bean")
	public void toBeanTest() {
		final String json = ResourceUtil.readUtf8Str("issueI82AM8.json");

		Map<String, MedicalCenter.MedicalCenterLocalized> bean1 =
			JSONUtil.toBean(json, new TypeReference<Map<String, MedicalCenter.MedicalCenterLocalized>>() {
			}, false);

		bean1.forEach((k, v) -> assertNotNull(v.getTestimonials()));
	}

	// 对象
	@Data
	public static class MedicalCenter {

		private Map<String, MedicalCenterLocalized> medicalCenterLocalized;

		@Data
		public static class MedicalCenterLocalized {

			private List<Testimonial> testimonials;

			@Data
			public static class Testimonial {
				private LocalDateTime createTime;
			}
		}
	}
}
