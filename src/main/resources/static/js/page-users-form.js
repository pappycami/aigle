(function($){
    $(document).ready(function () {
        $('input').on('blur', function () {
            let formData = {
                email: $('input[name="email"]').val(),
                password: $('input[name="password"]').val(),
                role: $('select[name="role"]').val()
            };

            $.ajax({
                url: '/aigle/users/validate',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function (errors) {
                    $('.error').text(''); // reset
                    for (let field in errors) {
                        $('[name="' + field + '"]').next('.error').text(errors[field]);
                    }
                },
                error: function (xhr) {
                    if (xhr.status !== 400) {
                        console.log("Une erreur est survenue.");
                    }
                }
            });
        });
    });
})(jQuery);
