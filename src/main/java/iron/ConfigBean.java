package iron;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "iron")
public class ConfigBean {

	private String address;

	private String accesskey;

	private String secretkey;
	
	private String bucket;

}
