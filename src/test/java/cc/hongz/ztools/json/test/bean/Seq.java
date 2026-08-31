package cc.hongz.ztools.json.test.bean;

import org.junit.jupiter.api.DisplayName;

import lombok.Data;

@Data
public class Seq {
	private String seq;

	public Seq() {
	}

	public Seq(String seq) {
		this.seq = seq;
	}
}
