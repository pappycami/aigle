(function($){
    $(document).ready(function () {
        $.fn.resizeiframe = function () {
            $(this).load(function () {
                $(this).height($(this).contents().find("body").height());
            });

            $(this).click(function () {
                $(this).height($(this).contents().find("body").height());
            });
        };
        $('iframe').resizeiframe();
    });
    
    /*
     * $(document).ready(() => {
        $(".btn").on('click', function(){
            alert("bouton clicker!!!");
        });
    });
     */
})(jQuery);