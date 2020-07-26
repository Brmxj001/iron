$.fn.genTimer_2 = function(options) {
    var defaults = {
        beginTime: (new Date()),
        unitWord: {
            dates: '<sup>d</sup>',
            hours: ":",
            minutes: ":",
            seconds: ""
        },
        callbackOnlyDatas: false
    };
    var DAY_MILLISECOND = 1000 * 60 * 60 * 24;
    var HOUER_MILLISECOND = 1000 * 60 * 60;
    var opts = $.extend({},
    defaults, options);
    var _this = this;
    var callback = function() {
        if (duration < 0) {
            opts.callback.call(_this, opts.callbackOnlyDatas ? {
                hours: '00',
                minutes: '00',
                seconds: '00'
            }: "00" + opts.unitWord.hours + "00" + opts.unitWord.minutes + "00");
            clearInterval(_this.interval)
        } else {
            var viewDuration = calcView(duration);
            if (opts.callbackOnlyDatas) {
                opts.callback.call(_this, viewDuration)
            } else {
                var DayHtml = '';
                if (!(typeof show_countdown_day == "undefined") && viewDuration.dates > 0) {
                    var DayHtml = '<span class="day">' + viewDuration.dates + opts.unitWord.dates + '</span><span class="time_label time_label_two"></span>';
                }
				opts.callback.call(_this,DayHtml + '<span class="hours">' + viewDuration.hours + '</span><span class="time_label">' + opts.unitWord.hours + '</span><span class="minutes">' + viewDuration.minutes + '</span><span class="time_label">' + opts.unitWord.minutes + '</span><span class="seconds">'+viewDuration.seconds + '</span>');
            }
        }
        duration -= 1000
    };
    _this.interval = setInterval(callback, 1000);
    opts.targetTime = opts.targetTime.replace(/\-/g, "/");
    var duration = new Date(opts.targetTime) - new Date(opts.beginTime);
    callback();
    function calcView(duration) {
        /*var hours = Math.floor(duration / HOUER_MILLISECOND);
        var minutes = Math.floor((duration - hours * HOUER_MILLISECOND) / (1000 * 60));
        var seconds = Math.floor((duration - hours * HOUER_MILLISECOND - minutes * 1000 * 60) / 1000);*/
        var dates = Math.floor(duration / DAY_MILLISECOND);
        var hours = Math.floor((duration - dates * DAY_MILLISECOND) / (1000 * 60 * 60));
        var minutes = Math.floor((duration - dates * DAY_MILLISECOND - hours * 1000 * 60 * 60) / (1000 * 60));
        var seconds = Math.floor((duration - dates * DAY_MILLISECOND - hours * 1000 * 60 * 60 - minutes * 1000 * 60) / 1000);
        return {
            dates: dates,
            hours: (hours>9? hours:('0' + hours).slice( - 2)),
            minutes: ('0' + minutes).slice( - 2),
            seconds: ('0' + seconds).slice( - 2)
        }
    }
};