;(function(){
	'use strict';
    function Popup (id,option) {
        this.setOption(option);
        var _this = this;
        var box = $(id);
        var closeBtn = box.find('.closeBtn');
        var content = box.find('.popup-content');
        var clientH = document.documentElement.clientHeight;
        var clientW = document.documentElement.clientWidth;
        var scrollT = document.body.scrollTop || document.documentElement.scrollTop;
        var scrollL = document.body.scrollLeft || document.documentElement.scrollLeft;
        var ori = window.resize;
        var btn_cancle = box.find('.cancle');
        this.box = box;
        content.css({
            'width'  : this.option.width,
            'height' : this.option.height
        });
        content.css({
            'left'   : $(window).width()/2 - content.width()/2 + 'px',
            'top'    : $(window).height()/2 - content.height()/2 + 'px'
        });
        closeBtn.on('click',function(){
            // box.hide()
            _this.close();
        });

        if (!!btn_cancle) {
            btn_cancle.click(function(){
                _this.close();
            });
        }

        function resize(func){
            window.onresize = function(){
                ori && ori();
                func();
                window.setTimeout(function(){
                    resize(func);
                },100);
                window.onresize = null;
            }
        };
        resize(function(){      
            
        });

    }
    Popup.prototype = {
    	constructor : Popup,
    	open : function () {
    		var box = this.box;
            box.css('display','block');
            box.css('z-index','1003');
    	},
    	close :  function () {
            var box = this.box;
            var flag = true;
            var result = null;
            if(typeof this.option.onbeforeclose == "function"){
                result = this.option.onbeforeclose();
                flag = typeof result == "undefined" ? true : result;
            }
            if(flag){
                box.css('display','none');
                box.css('z-index','0');
            }
    	},
        setOption:function(option){
            this.option = {
                width : '640px',
                height : '480px',
                content : 'default content'
            };
            $.extend(this.option,option || {});
        }
    }
    window.Popup = Popup;
})();
