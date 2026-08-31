package cc.hongz.ztools.json.test.bean;

import org.junit.jupiter.api.DisplayName;

import cc.hongz.ztools.json.JSONObject;
import lombok.Data;

@Data
public class JSONBean {
	private int code;
	private JSONObject data;
}
