package pw.cdmi.msm.praise.model;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class PraiseTarget {
	private String id;
	private String ownerId;
	private String type;
}
