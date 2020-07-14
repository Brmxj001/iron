jQuery(function(){
	var host=window.location.host;
	var referer=document.referrer;
	if(/^[^m].(.*)\.ly200\.net(.*)$/g.test(host) || /^[^m].(.*)\.ueeshop\.com(.*)$/g.test(host)){// && setStr.IsMobile!='Y'
		var toper='<link type="text/css" rel="stylesheet" href="https://www.ueeshop.com/css/open.css?v=1" /><div id="open_title"><div id="open_inner"><img src="https://www.ueeshop.com/images/ul.png" class="fl" /><div class="fr open_fr"><div class="opfl"></div><div class="fr fr_inner"><div class="fwrx">服务热线：</div><div class="item">400-9620-018</div><img src="https://www.ueeshop.com/images/l3.png" class="fr close" /></div></div><div class="clear">,/div></div></div>';
		
		jQuery('body').prepend(toper);
		if ($(window).width() < 1000) {
			$('#open_title').css({'top':'auto', 'bottom':0});
		} else {
			jQuery('body').css('margin-top', '44px');
		}
		jQuery('body').delegate('#open_title .close', 'click', function(){jQuery('#open_title').fadeOut();jQuery('html,body').animate({marginTop:'0px'}, 500);});

		referer.indexOf(host)==-1 && jQuery('body').append('<iframe src="/source.php?referer='+referer+'" style="display:none;"></iframe>');

		jQuery('body').delegate('#open_title .opfl', 'click', function(){
			jQuery.get('/source.php', '', function(data){
				window.location.href="https://"+(data.ret==1?data.msg:'www.ueeseop.com')+"/reg.php";//console.log(data.msg);
			}, 'json');
		});
		
		/*var copyright='<div class="crt">Copyright © 2005-2015 UEESHOP Ltd. All Rights Reserved.&nbsp;&nbsp;&nbsp;&nbsp; <a href="https://www.ueeshop.com" target="_blank">POWERED BY UEESHOP</a></div>';
		jQuery('#footer').append(copyright);
		*/
	}else if(/^(.*)\.vgcart\.com(.*)$/g.test(host) || /^(.*)\.chuangdianx\.com(.*)$/g.test(host) || /^(.*)\.shopcto\.com(.*)$/g.test(host) || /^(.*)\.shopcto\.net(.*)$/g.test(host) || /^(.*)\.shopcto\.club(.*)$/g.test(host) || /^(.*)\.shoptago\.com(.*)$/g.test(host)){
		//创店删除技术支持
		jQuery('a[href="https://www.ueeshop.com"]').each(function(){
			if(jQuery(this).html()=='POWERED BY UEESHOP') jQuery(this).parent().remove();
		});
		jQuery('a[href="https://www.ueeshop.com"]').each(function(){
			if(jQuery(this).html()=='POWERED BY UEESHOP') jQuery(this).parent().remove();
		});
		//jQuery('.copyright a').remove();
		//jQuery('a[href="https://shopsbring.com"]').html('POWERED BY SHOPSBRING');//.remove();
	}
});
