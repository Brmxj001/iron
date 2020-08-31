package iron.util;

import org.springframework.stereotype.Component;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import iron.ConfigBean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Slf4j
public class Qiniu {
	
	private static  Qiniu QINIU;
//	private Configuration cfg = new Configuration(Region.region2()); // 华南
	private Configuration cfg = new Configuration(Region.regionNa0()); // 北美
	private UploadManager uploadManager = new UploadManager(cfg);
	private Auth auth;
	private BucketManager bucketManager;
	private String bucket;
	private String ak;
	private String sk;
	public static Qiniu getQiniu() {
		return QINIU;
	}
	public String getUpToken() {
		return auth.uploadToken(bucket);
	}
	private Qiniu(ConfigBean bean) {
		ak = bean.getAccesskey();
		sk = bean.getSecretkey();
		bucket  = bean.getBucket();
		auth = Auth.create(ak,sk);
		bucketManager = new BucketManager(auth, cfg);
		
	}
	public static void init(ConfigBean bean) {
		log.info("七牛初始化");
		QINIU = new Qiniu(bean);
	}
}
