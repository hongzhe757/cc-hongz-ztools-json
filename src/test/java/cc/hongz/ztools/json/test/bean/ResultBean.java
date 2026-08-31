package cc.hongz.ztools.json.test.bean;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;

import java.util.List;

@Data
public class ResultBean {
	public List<List<List<ItemsBean>>> items;

	@Data
	public static class ItemsBean {
		public DetailBean detail;

		@Data
		public static class DetailBean {
			public String visitorStatus;
		}
	}
}
