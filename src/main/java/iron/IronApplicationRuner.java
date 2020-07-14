package iron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import iron.util.Qiniu;

@Component
public class IronApplicationRuner implements CommandLineRunner {
	@Autowired
	ConfigBean bean;

	public void run(String... args) throws Exception {
		Qiniu.init(bean);
	}
}