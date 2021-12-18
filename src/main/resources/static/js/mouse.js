var a_idx = 0;
jQuery(document).ready(function($) {
    $("body").click(function(e) {
        var a = new Array("富强👆", "民主👆", "文明👆", "和谐👆", "自由👆", "平等👆", "公正👆" ,"法治👆", "爱国👆", "敬业👆", "诚信👆", "友善👆 ");
        var $i = $("<span/>").text(a[a_idx]);
        a_idx = (a_idx + 1) % a.length;
        var x = e.pageX,
            y = e.pageY;
        $i.css({
            "z-index": 100000000,
            "top": y - 20,
            "left": x - 15,
            "position": "absolute",
            "font-weight": "bold",
            "color": "#00cccc"
        });
        $("body").append($i);
        $i.animate({
                "top": y - 180,
                "opacity": 0
            },
            1300,
            function() {
                $i.remove();
            });
    });
});