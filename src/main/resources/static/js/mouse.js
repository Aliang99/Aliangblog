var a_idx = 0;
jQuery(document).ready(function($) {
    $("body").click(function(e) {
        var a = new Array("ε―εΌΊπ", "ζ°δΈ»π", "ζζπ", "εθ°π", "θͺη±π", "εΉ³η­π", "ε¬ζ­£π" ,"ζ³ζ²»π", "η±ε½π", "ζ¬δΈπ", "θ―δΏ‘π", "εεπ ");
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