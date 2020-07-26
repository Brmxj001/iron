/*! jq.puzz.js */
(function ($) {
    var $tip;
    var html = '<div class="popup-tip-all"><div class="popup-tip-arrows"></div><div class="popup-tip-body"></div></div>';
    var upCss = "popup-tip-arrows-up";
    var timeId;

    function pos(el) {
        var R = el.getBoundingClientRect(), r = {};
        var d = document, dd = d.documentElement, db = d.body, X = Math.max;
        for (var k in R) {
            r[k] = R[k]
        }
        r.left += X(dd.scrollLeft, db.scrollLeft) - (dd.clientLeft || db.clientLeft || 0);
        r.top += X(dd.scrollTop, db.scrollTop) - (dd.clientTop || db.clientTop || 0);
        return r
    }

    function createTip() {
        if (!$tip) {
            $tip = $(html).appendTo(document.body)
        }
    }

    $.fn.tip = function (str, x, y, showTime) {
        var $self = this.eq(0);
        x = x || 0;
        y = y || 0;
        createTip();
        clearTimeout(timeId);
        var offset = pos($self.get(0));
        $tip.find("div.popup-tip-body").html(str);
        if ($self.closest(".qc_scroll").length > 0) {
            console.log("tip: " + offset.top);
            if ($("#qc_scroll").scrollTop() >= offset.top) {
                $("#qc_scroll").scrollTop(offset.top - 100)
            }
        } else {
            if ($(document).scrollTop() >= offset.top) {
                $(document).scrollTop(offset.top - 100)
            }
        }
        var offset = pos($self.get(0));
        $tip.show().stop().css({opacity: 1, left: offset.left + x, top: offset.top - $tip.height() - 2 + y});
        if (showTime) {
            timeId = setTimeout(function () {
                $tip.fadeOut()
            }, showTime)
        }
    };
    $.fn.tip.hide = function () {
        $tip.fadeOut();
        return this
    }
})(jQuery);
/*! jquery.lazyLoad.js */
(function ($, window, document, undefined) {
    var $window = $(window);
    $.fn.lazyload = function (options) {
        var elements = this;
        var $container;
        var settings = {
            threshold: 0,
            failure_limit: 0,
            event: "scroll",
            effect: "show",
            container: window,
            data_attribute: "original",
            skip_invisible: true,
            appear: null,
            load: null,
            placeholder: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC"
        };

        function update() {
            var counter = 0;
            elements.each(function () {
                var $this = $(this);
                if (settings.skip_invisible && !$this.is(":visible")) {
                    return;
                }
                if ($.abovethetop(this, settings) || $.leftofbegin(this, settings)) {
                } else if (!$.belowthefold(this, settings) && !$.rightoffold(this, settings)) {
                    $this.trigger("appear");
                    counter = 0;
                } else {
                    if (++counter > settings.failure_limit) {
                        return false;
                    }
                }
            });
        }

        if (options) {
            if (undefined !== options.failurelimit) {
                options.failure_limit = options.failurelimit;
                delete options.failurelimit;
            }
            if (undefined !== options.effectspeed) {
                options.effect_speed = options.effectspeed;
                delete options.effectspeed;
            }
            $.extend(settings, options);
        }
        $container = (settings.container === undefined || settings.container === window) ? $window : $(settings.container);
        if (0 === settings.event.indexOf("scroll")) {
            $container.bind(settings.event, function () {
                return update();
            });
        }
        this.each(function () {
            var self = this;
            var $self = $(self);
            self.loaded = false;
            if ($self.attr("src") === undefined || $self.attr("src") === false) {
                if ($self.is("img")) {
                    $self.attr("src", settings.placeholder);
                }
            }
            $self.one("appear", function () {
                if (!this.loaded) {
                    if (settings.appear) {
                        var elements_left = elements.length;
                        settings.appear.call(self, elements_left, settings);
                    }
                    $("<img />").bind("load", function () {
                        var original = $self.attr("data-" + settings.data_attribute);
                        $self.hide();
                        if ($self.is("img")) {
                            $self.attr("src", original);
                        } else {
                            $self.css("background-image", "url('" + original + "')");
                        }
                        $self[settings.effect](settings.effect_speed);
                        self.loaded = true;
                        var temp = $.grep(elements, function (element) {
                            return !element.loaded;
                        });
                        elements = $(temp);
                        if (settings.load) {
                            var elements_left = elements.length;
                            settings.load.call(self, elements_left, settings);
                        }
                    }).attr("src", $self.attr("data-" + settings.data_attribute));
                }
            });
            if (0 !== settings.event.indexOf("scroll")) {
                $self.bind(settings.event, function () {
                    if (!self.loaded) {
                        $self.trigger("appear");
                    }
                });
            }
        });
        $window.bind("resize", function () {
            update();
        });
        if ((/(?:iphone|ipod|ipad).*os 5/gi).test(navigator.appVersion)) {
            $window.bind("pageshow", function (event) {
                if (event.originalEvent && event.originalEvent.persisted) {
                    elements.each(function () {
                        $(this).trigger("appear");
                    });
                }
            });
        }
        $(document).ready(function () {
            update();
        });
        return this;
    };
    $.belowthefold = function (element, settings) {
        var fold;
        if (settings.container === undefined || settings.container === window) {
            fold = (window.innerHeight ? window.innerHeight : $window.height()) + $window.scrollTop();
        } else {
            fold = $(settings.container).offset().top + $(settings.container).height();
        }
        return fold <= $(element).offset().top - settings.threshold;
    };
    $.rightoffold = function (element, settings) {
        var fold;
        if (settings.container === undefined || settings.container === window) {
            fold = $window.width() + $window.scrollLeft();
        } else {
            fold = $(settings.container).offset().left + $(settings.container).width();
        }
        return fold <= $(element).offset().left - settings.threshold;
    };
    $.abovethetop = function (element, settings) {
        var fold;
        if (settings.container === undefined || settings.container === window) {
            fold = $window.scrollTop();
        } else {
            fold = $(settings.container).offset().top;
        }
        return fold >= $(element).offset().top + settings.threshold + $(element).height();
    };
    $.leftofbegin = function (element, settings) {
        var fold;
        if (settings.container === undefined || settings.container === window) {
            fold = $window.scrollLeft();
        } else {
            fold = $(settings.container).offset().left;
        }
        return fold >= $(element).offset().left + settings.threshold + $(element).width();
    };
    $.inviewport = function (element, settings) {
        return !$.rightoffold(element, settings) && !$.leftofbegin(element, settings) && !$.belowthefold(element, settings) && !$.abovethetop(element, settings);
    };
    $.extend($.expr[":"], {
        "below-the-fold": function (a) {
            return $.belowthefold(a, {threshold: 0});
        }, "above-the-top": function (a) {
            return !$.belowthefold(a, {threshold: 0});
        }, "right-of-screen": function (a) {
            return $.rightoffold(a, {threshold: 0});
        }, "left-of-screen": function (a) {
            return !$.rightoffold(a, {threshold: 0});
        }, "in-viewport": function (a) {
            return $.inviewport(a, {threshold: 0});
        }, "above-the-fold": function (a) {
            return !$.belowthefold(a, {threshold: 0});
        }, "right-of-fold": function (a) {
            return $.rightoffold(a, {threshold: 0});
        }, "left-of-fold": function (a) {
            return !$.rightoffold(a, {threshold: 0});
        }
    });
})(jQuery, window, document);
/*! gentimer.js */
$.fn.genTimer = function (options) {
    var defaults = {
        beginTime: (new Date()),
        unitWord: {hours: ":", minutes: ":", seconds: ""},
        callbackOnlyDatas: false
    };
    var DAY_MILLISECOND = 1000 * 60 * 60 * 24;
    var opts = $.extend({}, defaults, options);
    var _this = this;
    var day_label = "D";
    var days_label = "D";
    var callback = function () {
        if (duration < 0) {
            opts.callback.call(_this, opts.callbackOnlyDatas ? {
                hours: "00",
                minutes: "00",
                seconds: "00",
                dates: 0
            } : "00" + opts.unitWord.hours + "00" + opts.unitWord.minutes + "00");
            clearInterval(_this.interval)
        } else {
            var viewDuration = calcView(duration);
            if (opts.callbackOnlyDatas) {
                opts.callback.call(_this, viewDuration)
            } else {
                if (duration >= DAY_MILLISECOND * 2) {
                    var DayHtml = "";
                    if (!(typeof show_countdown_day == "undefined") && viewDuration.dates > 0) {
                        var DayHtml = '<span class="day_count">' + viewDuration.dates + '</span><span class="day">' + days_label + "</span>"
                    }
                    opts.callback.call(_this, DayHtml + '<span class="day_seconds">' + viewDuration.hours + opts.unitWord.hours + viewDuration.minutes + opts.unitWord.minutes + viewDuration.seconds + opts.unitWord.seconds + "</span>")
                } else {
                    if (duration >= DAY_MILLISECOND) {
                        var DayHtml = "";
                        if (!(typeof show_countdown_day == "undefined") && viewDuration.dates > 0) {
                            var DayHtml = '<span class="day_count">' + viewDuration.dates + '</span><span class="day">' + day_label + "</span>"
                        }
                        opts.callback.call(_this, DayHtml + '<span class="day_seconds">' + viewDuration.hours + opts.unitWord.hours + viewDuration.minutes + opts.unitWord.minutes + viewDuration.seconds + opts.unitWord.seconds + "</span>")
                    } else {
                        opts.callback.call(_this, '<span class="seconds">' + viewDuration.hours + opts.unitWord.hours + viewDuration.minutes + opts.unitWord.minutes + viewDuration.seconds + opts.unitWord.seconds + "</span>")
                    }
                }
            }
        }
        duration -= 1000
    };
    _this.interval = setInterval(callback, 1000);
    opts.targetTime = opts.targetTime.replace(/\-/g, "/");
    opts.beginTime = opts.beginTime.replace(/\-/g, "/");
    var duration = new Date(opts.targetTime) - new Date(opts.beginTime);
    callback();

    function calcView(duration) {
        var dates = Math.floor(duration / DAY_MILLISECOND);
        var hours = Math.floor((duration - dates * DAY_MILLISECOND) / (1000 * 60 * 60));
        var minutes = Math.floor((duration - dates * DAY_MILLISECOND - hours * 1000 * 60 * 60) / (1000 * 60));
        var seconds = Math.floor((duration - dates * DAY_MILLISECOND - hours * 1000 * 60 * 60 - minutes * 1000 * 60) / 1000);
        return {
            hours: ("0" + hours).slice(-2),
            minutes: ("0" + minutes).slice(-2),
            seconds: ("0" + seconds).slice(-2),
            dates: dates
        }
    }
};
/*! jquery.autocomplete.js */
(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory)
    } else {
        factory(jQuery)
    }
}(function ($) {
    'use strict';
    var utils = (function () {
        return {
            escapeRegExChars: function (value) {
                return value.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&")
            }, createNode: function (containerClass) {
                var div = document.createElement('div');
                div.className = containerClass;
                div.style.position = 'absolute';
                div.style.display = 'none';
                return div
            }
        }
    }()), keys = {ESC: 27, TAB: 9, RETURN: 13, LEFT: 37, UP: 38, RIGHT: 39, DOWN: 40};

    function Autocomplete(el, options) {
        var noop = function () {
        }, that = this, defaults = {
            autoSelectFirst: false,
            appendTo: 'body',
            serviceUrl: null,
            lookup: null,
            onSelect: null,
            width: 'auto',
            minChars: 1,
            maxHeight: 300,
            deferRequestBy: 0,
            params: {},
            formatResult: Autocomplete.formatResult,
            delimiter: null,
            zIndex: 9999,
            type: 'GET',
            noCache: false,
            onSearchStart: noop,
            onSearchComplete: noop,
            onSearchError: noop,
            containerClass: 'autocomplete-suggestions',
            tabDisabled: false,
            dataType: 'text',
            currentRequest: null,
            triggerSelectOnValidInput: false,
            preventBadQueries: true,
            lookupFilter: function (suggestion, originalQuery, queryLowerCase) {
                return suggestion.value.toLowerCase().indexOf(queryLowerCase) !== -1
            },
            paramName: 'query',
            transformResult: function (response) {
                return typeof response === 'string' ? $.parseJSON(response) : response
            }
        };
        that.element = el;
        that.el = $(el);
        that.suggestions = [];
        that.badQueries = [];
        that.selectedIndex = -1;
        that.currentValue = that.element.value;
        that.intervalId = 0;
        that.cachedResponse = {};
        that.onChangeInterval = null;
        that.onChange = null;
        that.isLocal = false;
        that.suggestionsContainer = null;
        that.options = $.extend({}, defaults, options);
        that.classes = {selected: 'autocomplete-selected', suggestion: 'autocomplete-suggestion'};
        that.hint = null;
        that.hintValue = '';
        that.selection = null;
        that.initialize();
        that.setOptions(options)
    }

    Autocomplete.utils = utils;
    $.Autocomplete = Autocomplete;
    Autocomplete.formatResult = function (suggestion, currentValue) {
        var pattern = '(' + utils.escapeRegExChars(currentValue) + ')';
        return suggestion.value.replace(new RegExp(pattern, 'gi'), '<strong>$1<\/strong>')
    };
    Autocomplete.prototype = {
        killerFn: null, initialize: function () {
            var that = this, suggestionSelector = '.' + that.classes.suggestion, selected = that.classes.selected,
                options = that.options, container;
            that.element.setAttribute('autocomplete', 'off');
            that.killerFn = function (e) {
                if ($(e.target).closest('.' + that.options.containerClass).length === 0) {
                    that.killSuggestions();
                    that.disableKillerFn()
                }
            };
            that.suggestionsContainer = Autocomplete.utils.createNode(options.containerClass);
            container = $(that.suggestionsContainer);
            container.appendTo(options.appendTo);
            if (options.width !== 'auto') {
                container.width(options.width)
            }
            container.on('mouseover.autocomplete', suggestionSelector, function () {
                that.activate($(this).data('index'))
            });
            container.on('mouseout.autocomplete', function () {
                that.selectedIndex = -1;
                container.children('.' + selected).removeClass(selected)
            });
            container.on('click.autocomplete', suggestionSelector, function () {
                that.select($(this).data('index'))
            });
            that.fixPosition();
            that.fixPositionCapture = function () {
                if (that.visible) {
                    that.fixPosition()
                }
            };
            $(window).on('resize.autocomplete', that.fixPositionCapture);
            that.el.on('keydown.autocomplete', function (e) {
                that.onKeyPress(e)
            });
            that.el.on('keyup.autocomplete', function (e) {
                that.onKeyUp(e)
            });
            that.el.on('blur.autocomplete', function () {
                that.onBlur()
            });
            that.el.on('focus.autocomplete', function () {
                that.onFocus()
            });
            that.el.on('change.autocomplete', function (e) {
                that.onKeyUp(e)
            })
        }, onFocus: function () {
            var that = this;
            that.fixPosition();
            if (that.options.minChars <= that.el.val().length) {
            }
        }, onBlur: function () {
            this.enableKillerFn()
        }, setOptions: function (suppliedOptions) {
            var that = this, options = that.options;
            $.extend(options, suppliedOptions);
            that.isLocal = $.isArray(options.lookup);
            if (that.isLocal) {
                options.lookup = that.verifySuggestionsFormat(options.lookup)
            }
            $(that.suggestionsContainer).css({
                'max-height': options.maxHeight + 'px',
                'width': options.width + 'px',
                'z-index': options.zIndex
            })
        }, clearCache: function () {
            this.cachedResponse = {};
            this.badQueries = []
        }, clear: function () {
            this.clearCache();
            this.currentValue = '';
            this.suggestions = []
        }, disable: function () {
            var that = this;
            that.disabled = true;
            if (that.currentRequest) {
                that.currentRequest.abort()
            }
        }, enable: function () {
            this.disabled = false
        }, fixPosition: function () {
            var that = this, offset, styles;
            if (that.options.appendTo !== 'body') {
                return
            }
            offset = that.el.offset();
            styles = {top: (offset.top + that.el.outerHeight() - 2) + 'px', left: offset.left + 'px'};
            if (that.options.width === 'auto') {
                styles.width = (that.el.outerWidth() - 2) + 'px'
            }
            $(that.suggestionsContainer).css(styles)
        }, enableKillerFn: function () {
            var that = this;
            $(document).on('click.autocomplete', that.killerFn)
        }, disableKillerFn: function () {
            var that = this;
            $(document).off('click.autocomplete', that.killerFn)
        }, killSuggestions: function () {
            var that = this;
            that.stopKillSuggestions();
            that.intervalId = window.setInterval(function () {
                that.hide();
                that.stopKillSuggestions()
            }, 50)
        }, stopKillSuggestions: function () {
            window.clearInterval(this.intervalId)
        }, isCursorAtEnd: function () {
            var that = this, valLength = that.el.val().length, selectionStart = that.element.selectionStart, range;
            if (typeof selectionStart === 'number') {
                return selectionStart === valLength
            }
            if (document.selection) {
                range = document.selection.createRange();
                range.moveStart('character', -valLength);
                return valLength === range.text.length
            }
            return true
        }, onKeyPress: function (e) {
            var that = this;
            if (!that.disabled && !that.visible && e.which === keys.DOWN && that.currentValue) {
                that.suggest();
                return
            }
            if (that.disabled || !that.visible) {
                return
            }
            switch (e.which) {
                case keys.ESC:
                    that.el.val(that.currentValue);
                    that.hide();
                    break;
                case keys.RIGHT:
                    if (that.hint && that.options.onHint && that.isCursorAtEnd()) {
                        that.selectHint();
                        break
                    }
                    return;
                case keys.TAB:
                    if (that.hint && that.options.onHint) {
                        that.selectHint();
                        return
                    }
                case keys.RETURN:
                    if (that.selectedIndex === -1) {
                        that.hide();
                        return
                    }
                    that.select(that.selectedIndex);
                    if (e.which === keys.TAB && that.options.tabDisabled === false) {
                        return
                    }
                    break;
                case keys.UP:
                    that.moveUp();
                    break;
                case keys.DOWN:
                    that.moveDown();
                    break;
                default:
                    return
            }
            e.stopImmediatePropagation();
            e.preventDefault()
        }, onKeyUp: function (e) {
            var that = this;
            if (that.disabled) {
                return
            }
            switch (e.which) {
                case keys.UP:
                case keys.DOWN:
                    return
            }
            clearInterval(that.onChangeInterval);
            if (that.currentValue !== that.el.val()) {
                that.findBestHint();
                if (that.options.deferRequestBy > 0) {
                    that.onChangeInterval = setInterval(function () {
                        that.onValueChange()
                    }, that.options.deferRequestBy)
                } else {
                    that.onValueChange()
                }
            }
        }, onValueChange: function () {
            var that = this, options = that.options, value = that.el.val(), query = that.getQuery(value), index;
            if (that.selection) {
                that.selection = null;
                (options.onInvalidateSelection || $.noop).call(that.element)
            }
            clearInterval(that.onChangeInterval);
            that.currentValue = value;
            that.selectedIndex = -1;
            if (options.triggerSelectOnValidInput) {
                index = that.findSuggestionIndex(query);
                if (index !== -1) {
                    return
                }
            }
            if (query.length < options.minChars) {
                that.hide()
            } else {
                that.getSuggestions(query)
            }
        }, findSuggestionIndex: function (query) {
            var that = this, index = -1, queryLowerCase = query.toLowerCase();
            $.each(that.suggestions, function (i, suggestion) {
                if (suggestion.value.toLowerCase() === queryLowerCase) {
                    index = i;
                    return false
                }
            });
            return index
        }, getQuery: function (value) {
            var delimiter = this.options.delimiter, parts;
            if (!delimiter) {
                return value
            }
            parts = value.split(delimiter);
            return $.trim(parts[parts.length - 1])
        }, getSuggestionsLocal: function (query) {
            var that = this, options = that.options, queryLowerCase = query.toLowerCase(),
                filter = options.lookupFilter, limit = parseInt(options.lookupLimit, 10), data;
            data = {
                suggestions: $.grep(options.lookup, function (suggestion) {
                    return filter(suggestion, query, queryLowerCase)
                })
            };
            if (limit && data.suggestions.length > limit) {
                data.suggestions = data.suggestions.slice(0, limit)
            }
            return data
        }, getSuggestions: function (q) {
            var response, that = this, options = that.options, serviceUrl = options.serviceUrl, params, cacheKey;
            options.params[options.paramName] = q;
            params = options.ignoreParams ? null : options.params;
            if (that.isLocal) {
                response = that.getSuggestionsLocal(q)
            } else {
                if ($.isFunction(serviceUrl)) {
                    serviceUrl = serviceUrl.call(that.element, q)
                }
                cacheKey = serviceUrl + '?' + $.param(params || {});
                response = that.cachedResponse[cacheKey]
            }
            if (response && $.isArray(response.suggestions)) {
                that.suggestions = response.suggestions;
                that.suggest()
            } else if (!that.isBadQuery(q)) {
                if (options.onSearchStart.call(that.element, options.params) === false) {
                    return
                }
                if (that.currentRequest) {
                    that.currentRequest.abort()
                }
                that.currentRequest = $.ajax({
                    url: serviceUrl,
                    data: params,
                    type: options.type,
                    dataType: options.dataType
                }).done(function (data) {
                    var result;
                    that.currentRequest = null;
                    result = options.transformResult(data);
                    that.processResponse(result, q, cacheKey);
                    options.onSearchComplete.call(that.element, q, result.suggestions)
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    options.onSearchError.call(that.element, q, jqXHR, textStatus, errorThrown)
                })
            }
        }, isBadQuery: function (q) {
            if (!this.options.preventBadQueries) {
                return false
            }
            var badQueries = this.badQueries, i = badQueries.length;
            while (i--) {
                if (q.indexOf(badQueries[i]) === 0) {
                    return true
                }
            }
            return false
        }, hide: function () {
            var that = this;
            that.visible = false;
            that.selectedIndex = -1;
            $(that.suggestionsContainer).hide();
            that.signalHint(null)
        }, suggest: function () {
            if (this.suggestions.length === 0) {
                this.hide();
                return
            }
            var that = this, options = that.options, formatResult = options.formatResult,
                value = that.getQuery(that.currentValue), className = that.classes.suggestion,
                classSelected = that.classes.selected, container = $(that.suggestionsContainer),
                beforeRender = options.beforeRender, html = '', index, width;
            if (options.triggerSelectOnValidInput) {
                index = that.findSuggestionIndex(value);
                if (index !== -1) {
                    that.select(index);
                    return
                }
            }
            $.each(that.suggestions, function (i, suggestion) {
                html += '<div class="' + className + '" data-index="' + i + '">' + formatResult(suggestion, value) + '</div>'
            });
            if (options.width === 'auto') {
                width = that.el.outerWidth() - 2;
                container.width(width > 0 ? width : 300)
            }
            container.html(html);
            if (options.autoSelectFirst) {
                that.selectedIndex = 0;
                container.children().first().addClass(classSelected)
            }
            if ($.isFunction(beforeRender)) {
                beforeRender.call(that.element, container)
            }
            container.show();
            that.visible = true;
            that.findBestHint()
        }, findBestHint: function () {
            var that = this, value = that.el.val().toLowerCase(), bestMatch = null;
            if (!value) {
                return
            }
            $.each(that.suggestions, function (i, suggestion) {
                var foundMatch = suggestion.value.toLowerCase().indexOf(value) === 0;
                if (foundMatch) {
                    bestMatch = suggestion
                }
                return !foundMatch
            });
            that.signalHint(bestMatch)
        }, signalHint: function (suggestion) {
            var hintValue = '', that = this;
            if (suggestion) {
                hintValue = that.currentValue + suggestion.value.substr(that.currentValue.length)
            }
            if (that.hintValue !== hintValue) {
                that.hintValue = hintValue;
                that.hint = suggestion;
                (this.options.onHint || $.noop)(hintValue)
            }
        }, verifySuggestionsFormat: function (suggestions) {
            if (suggestions.length && typeof suggestions[0] === 'string') {
                return $.map(suggestions, function (value) {
                    return {value: value, data: null}
                })
            }
            return suggestions
        }, processResponse: function (result, originalQuery, cacheKey) {
            var that = this, options = that.options;
            result.suggestions = that.verifySuggestionsFormat(result.suggestions);
            if (!options.noCache) {
                that.cachedResponse[cacheKey] = result;
                if (options.preventBadQueries && result.suggestions.length === 0) {
                    that.badQueries.push(originalQuery)
                }
            }
            if (originalQuery !== that.getQuery(that.currentValue)) {
                return
            }
            that.suggestions = result.suggestions;
            that.suggest()
        }, activate: function (index) {
            var that = this, activeItem, selected = that.classes.selected, container = $(that.suggestionsContainer),
                children = container.children();
            container.children('.' + selected).removeClass(selected);
            that.selectedIndex = index;
            if (that.selectedIndex !== -1 && children.length > that.selectedIndex) {
                activeItem = children.get(that.selectedIndex);
                $(activeItem).addClass(selected);
                return activeItem
            }
            return null
        }, selectHint: function () {
            var that = this, i = $.inArray(that.hint, that.suggestions);
            that.select(i)
        }, select: function (i) {
            var that = this;
            that.hide();
            that.onSelect(i)
        }, moveUp: function () {
            var that = this;
            if (that.selectedIndex === -1) {
                return
            }
            if (that.selectedIndex === 0) {
                $(that.suggestionsContainer).children().first().removeClass(that.classes.selected);
                that.selectedIndex = -1;
                that.el.val(that.currentValue);
                that.findBestHint();
                return
            }
            that.adjustScroll(that.selectedIndex - 1)
        }, moveDown: function () {
            var that = this;
            if (that.selectedIndex === (that.suggestions.length - 1)) {
                return
            }
            that.adjustScroll(that.selectedIndex + 1)
        }, adjustScroll: function (index) {
            var that = this, activeItem = that.activate(index), offsetTop, upperBound, lowerBound, heightDelta = 25;
            if (!activeItem) {
                return
            }
            offsetTop = activeItem.offsetTop;
            upperBound = $(that.suggestionsContainer).scrollTop();
            lowerBound = upperBound + that.options.maxHeight - heightDelta;
            if (offsetTop < upperBound) {
                $(that.suggestionsContainer).scrollTop(offsetTop)
            } else if (offsetTop > lowerBound) {
                $(that.suggestionsContainer).scrollTop(offsetTop - that.options.maxHeight + heightDelta)
            }
            that.el.val(that.getValue(that.suggestions[index].value));
            that.signalHint(null)
        }, onSelect: function (index) {
            var that = this, onSelectCallback = that.options.onSelect, suggestion = that.suggestions[index];
            that.currentValue = that.getValue(suggestion.value);
            if (that.currentValue !== that.el.val()) {
                that.el.val(that.currentValue)
            }
            that.signalHint(null);
            that.suggestions = [];
            that.selection = suggestion;
            if ($.isFunction(onSelectCallback)) {
                onSelectCallback.call(that.element, suggestion)
            }
        }, getValue: function (value) {
            var that = this, delimiter = that.options.delimiter, currentValue, parts;
            if (!delimiter) {
                return value
            }
            currentValue = that.currentValue;
            parts = currentValue.split(delimiter);
            if (parts.length === 1) {
                return value
            }
            return currentValue.substr(0, currentValue.length - parts[parts.length - 1].length) + value
        }, dispose: function () {
            var that = this;
            that.el.off('.autocomplete').removeData('autocomplete');
            that.disableKillerFn();
            $(window).off('resize.autocomplete', that.fixPositionCapture);
            $(that.suggestionsContainer).remove()
        }
    };
    $.fn.autocomplete = function (options, args) {
        var dataKey = 'autocomplete';
        if (arguments.length === 0) {
            return this.first().data(dataKey)
        }
        return this.each(function () {
            var inputElement = $(this), instance = inputElement.data(dataKey);
            if (typeof options === 'string') {
                if (instance && typeof instance[options] === 'function') {
                    instance[options](args)
                }
            } else {
                if (instance && instance.dispose) {
                    instance.dispose()
                }
                instance = new Autocomplete(this, options);
                inputElement.data(dataKey, instance)
            }
        })
    }
}));
/*! goods like */
$(function () {
    $(".glike").click(function (event) {
        var _this = $(this);
        var _val = parseInt(_this.find("font").text());
        var goods_id = _this.attr("data_id");
        $.ajax({
            url: "/h-user-addFavorites.html",
            type: "POST",
            data: {goods_id: goods_id},
            dataType: "json",
            cache: false,
            success: function (res) {
                if (res == 1) {
                    _this.tip("Add to favorite succeed.", 15, 0, 2000);
                    _this.find("font").text(_val + 1)
                } else {
                    if (res == 2) {
                        _this.tip("Please Login first", 15, 0, 2000)
                    } else {
                        if (res == 3) {
                            _this.tip("Already added", 15, 0, 2000)
                        } else {
                            _this.tip("Add to favorite failed.", 15, 0, 2000)
                        }
                    }
                }
            },
            error: function () {
                alert("Connection failed, please refresh")
            },
            beforeSend: function () {
                createAjaxLoading()
            },
            complete: function () {
                removeAjaxLoading()
            }
        });
        event.stopPropagation()
    })
});

function checkUrl(value) {
    return /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value)
}

function checkEmail(value) {
    return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value)
}

function resizeimg(img, target_w, target_h) {
    var tarW = target_w ? target_w : 100;
    var tarH = target_h ? target_h : 100;
    var imgW = img.width;
    var imgH = img.height;
    var widthRatio = 1;
    var widthTag = false;
    var heightRatio = 1;
    var heightTag = false;
    var ratio = 1;
    if (imgW > tarW || imgH > tarH) {
        if (imgW > tarW) {
            widthRatio = tarW / imgW;
            widthTag = true
        }
        if (imgH > tarH) {
            heightRatio = tarH / imgH;
            heightTag = true
        }
        if (widthTag && heightTag) {
            if (widthRatio > heightRatio) {
                ratio = heightRatio
            } else {
                ratio = widthRatio
            }
        }
        if (!widthTag && heightTag) {
            ratio = heightRatio
        }
        if (widthTag && !heightTag) {
            ratio = widthTag
        }
    } else {
        if (tarW > tarH) {
            ratio = tarW / imgW
        } else {
            ratio = tarH / imgH
        }
    }
    img.width = imgW * ratio;
    img.heigt = imgH * ratio
}

var userFastLoginCallback = null;
var userFastLoginDialog = null;
var userFastLogin = function () {
    return {
        exec: function (callback) {
            userFastLoginCallback = callback;
            $.ajax({
                url: "/index.php?m=home&c=user&a=isLoginAjax",
                type: "GET",
                dataType: "json",
                cache: false,
                async: false,
                success: function (retdat) {
                    if (retdat.status == 1) {
                        userFastLoginCallback(retdat.user)
                    } else {
                        if (userFastLoginDialog == null) {
                            $.ajax({
                                url: "/index.php?m=home&c=user&a=loginAjax",
                                type: "GET",
                                dataType: "html",
                                cache: false,
                                success: function (retdat) {
                                    $("body").append(retdat);
                                    userFastLoginDialog = $("#dlgUserFastLogin").dialog({
                                        drag: "div.dlg-header",
                                        buttons: ".dlg-close"
                                    });
                                    userFastLoginDialog.open()
                                },
                                beforeSend: function () {
                                    createAjaxLoading()
                                },
                                complete: function () {
                                    removeAjaxLoading()
                                }
                            })
                        } else {
                            userFastLoginDialog.open()
                        }
                    }
                }
            })
        }
    }
}();
var shareGoodsDialog = null;
var shareGoods = function () {
    return {
        exec: function (goods_id) {
            if (shareGoodsDialog == null) {
                $.ajax({
                    url: "/h-product-share.html?goods_id=" + goods_id,
                    type: "GET",
                    dataType: "html",
                    cache: false,
                    success: function (retdat) {
                        $("body").append(retdat);
                        shareGoodsDialog = $("#goods_share_box").dialog({drag: ".share-header", buttons: ".close_box"});
                        shareGoodsDialog.open()
                    },
                    beforeSend: function () {
                        createAjaxLoading()
                    },
                    complete: function () {
                        removeAjaxLoading()
                    }
                })
            } else {
                shareGoodsDialog.open()
            }
        }
    }
}();

function createAjaxLoading() {
    $("<div/>").appendTo(document.body).addClass("ajax_loading");
    $(".ajax_loading").append('<div class="loading-container"><div></div></div>')
}

function removeAjaxLoading() {
    $(".ajax_loading").remove()
}

function pad(num, decimal, type) {
    if (decimal == 0) {
        return num
    }
    var type = type ? type : 0;
    var arrnum = num.split(".");
    if (arrnum[1]) {
        if (arrnum[1].length < decimal) {
            var str = "";
            for (var i = 0; i < decimal - arrnum[1].length; i++) {
                str += "0"
            }
            num = num + str
        }
    } else {
        if (type == 1) {
            if (num.length < decimal) {
                var str = "";
                for (var i = 0; i < decimal - num.length; i++) {
                    str += "0"
                }
                num = num + str
            }
        } else {
            var str = "";
            for (var i = 0; i < decimal; i++) {
                str += "0"
            }
            num = num + "." + str
        }
    }
    return num
}

function unique(arr) {
    var n = new Array();
    for (var i = 0; i < arr.length; i++) {
        if (n.indexOf(arr[i]) == -1) {
            n.push(arr[i])
        }
    }
    return n
}

function formatnumber(value, num) {
    value = parseFloat(value);
    value = value.toFixed(num);
    a = value.toString();
    b = a.indexOf(".");
    c = a.length;
    if (num) {
        num = parseInt(num)
    }
    if (num == 0) {
        if (b != -1) {
            a = a.substring(0, b)
        }
    } else {
        if (b == -1) {
            a = a + ".";
            for (i = 1; i <= num; i++) {
                a = a + "0"
            }
        } else {
            a = a.substring(0, b + num + 1);
            for (i = c; i <= b + num; i++) {
                a = a + "0"
            }
        }
    }
    return a
}

Number.prototype.toFixed = function (len) {
    var add = 0;
    var s, temp;
    var s1 = this + "";
    var start = s1.indexOf(".");
    if (start != -1) {
        if (s1.substr(start + len + 1, 1) >= 5) {
            add = 1
        }
        var temp = Math.pow(10, len);
        s = Math.floor(this.mul(temp)) + add;
        return s / temp
    } else {
        return this
    }
};
Number.prototype.mul = function (arg) {
    return accMul(arg, this)
};

function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

function format_keywords(keywords) {
    keywords = keywords.replace(/"/g, "");
    keywords = encodeURIComponent(keywords);
    keywords = keywords.replace(/%25/ig, "%2525");
    keywords = keywords.replace(/%3b/ig, "%253b");
    keywords = keywords.replace(/%2f/ig, "%252f");
    keywords = keywords.replace(/%40/ig, "%2540");
    keywords = keywords.replace(/%3a/ig, "%253a");
    keywords = keywords.replace(/%26/ig, "%2526");
    keywords = keywords.replace(/%3d/ig, "%253d");
    keywords = keywords.replace(/%2b/ig, "%252b");
    keywords = keywords.replace(/%24/ig, "%2524");
    keywords = keywords.replace(/%2c/ig, "%252c");
    keywords = keywords.replace(/%23/ig, "%2523");
    keywords = keywords.replace(/%3f/ig, "%253f");
    keywords = keywords.replace(/%20/g, "+");
    return keywords
}

function getImageThumbURL(pic, style, is_https, is_oss) {
    is_oss = true;
    pic = pic.replace(/thumb\/[\d]+x[\d]+\//gi, "");
    var el = document.createElement("a");
    el.href = pic;
    var info = el.pathname.split("/");
    if (pic && style && style != "0x0") {
        if (is_oss) {
            resize_w = 0;
            resize_h = 0;
            resize = style.split("x");
            resize_w = parseInt(resize[0]);
            if (typeof (resize[1]) != "undefined") {
                resize_h = parseInt(resize[1])
            }
            var url_query = "?x-oss-process=image/";
            if (typeof (ymcart_oss_default_query) !== "undefined" && ymcart_oss_default_query != "") {
                var reg = /quality\,Q_[\d]+/i;
                if (reg.test(ymcart_oss_default_query)) {
                    var matches = reg.exec(ymcart_oss_default_query);
                    url_query += matches[0];
                    var default_query = ymcart_oss_default_query.replace(/([\/]*)quality\,Q_[\d]+/i, "$1resize,m_lfit,w_" + resize_w + ",h_" + resize_h);
                    url_query += "/" + default_query
                } else {
                    url_query += 'resize,m_lfit,w_' + resize_w + ',h_' + resize_h + '/' + ymcart_oss_default_query
                }
            } else {
                url_query += "resize,m_lfit,w_" + resize_w + ",h_" + resize_h
            }
            info[info.length - 1] = info[info.length - 1] + url_query
        } else {
            info[1] = info[1] + "/thumb/" + style
        }
        return (is_https ? "https:" : "") + "//" + el.host + info.join("/")
    } else {
        if (pic && (style == "0x0" || style == "")) {
            return (is_https ? "https:" : "") + "//" + el.host + el.pathname
        } else {
            ftp_domain = str_replace("http:", "", "http://cn01.imgcdn.ymcart.com/");
            if (style) {
                return (is_https ? "https:" : "") + ftp_domain + "0/thumb/" + style + "/nopic.png"
            } else {
                return (is_https ? "https:" : "") + ftp_domain + "0/nopic.png"
            }
        }
    }
};
