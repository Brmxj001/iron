function share(sharetype, url, title, pic)
{
    var left=($(window).width()-600)/2;
    switch(sharetype)
    {
        case "facebook":
            window.open("https://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent(url)+"&t="+(title)+"&display=popup",
                '', 'top=100,left='+left+',menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');
            break;
        case "google":
            window.open("https://plus.google.com/share?url="+encodeURIComponent(url),
                '', 'top=100,left='+left+'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');
            break;
        case "twitter":
            window.open("https://www.addthis.com/bookmark.php?v=300&winname=addthis&pub=at_com&source=tbx32-300&lng=en&s=twitter&url="+encodeURIComponent(url)+"&title="+(title)+"&ate=AT-at_com/-/-/518caae63e6472f7/12/4df16999c52e1d97&frommenu=1&ips=1&uid=4df16999c52e1d97&ufbl=1&ct=1&pre=http%3A%2F%2Fwww.addthis.com%2F&tt=0&captcha_provider=nucaptcha=",
                '', 'top=100,left='+left+'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');
            break;
        case "email":
            window.open("https://www.addthis.com/bookmark.php?v=300&winname=addthis&pub=at_com&source=tbx32-300&lng=en&s=gmail&url="+encodeURIComponent(url)+"&title="+(title)+"&ate=AT-at_com/-/-/518caae63e6472f7/14/4df16999c52e1d97&frommenu=1&ips=1&uid=4df16999c52e1d97&ufbl=1&ct=1&pre=http%3A%2F%2Fwww.addthis.com%2F&tt=0&captcha_provider=nucaptcha",
                '', 'top=100,left='+left+'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');
            break;
    }
}