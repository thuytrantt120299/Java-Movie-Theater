$(document).ready(function() {
	$('#movieForm').validate({
		errorClass: 'error fail-alert',
		validClass: 'valid success-alert',

		rules: {
			movieNameEnglish: {
				required: true,
				minlength: 5,
				maxlength: 30
			},
			movieNameVn: {
				required: true,
				minlength: 5,
				maxlength: 30
			},
			fromDate: {
				required: true
			},

			toDate: {
				required: true
			},

			duration: {
				required: true,
				max: 500,
				min: 60
			}



		},
		messages: {
			movieNameEnglish: {
				required: "Please fill the english name of movie fields",
				minlength: "User name must contain at least 5 characters",
				maxlength: "User name must contain less than 30 characters"
			},
			movieNameVn: {
				required: "Please fill the vietnamese name of movie fields",
				minlength: "Email must contain at least 5 characters",
				maxlength: "Email must contain less than 30 characters"
			},
			fromDate: {
				required: "Please fill the start date of movie"
			},
			toDate: {
				required: "Please fill the end date of movie"
			},
			duration: {
				required: "Please fill the duration of movie",
				min: "Duration of the film must longer than 60 minutes",
				max: "Duration of the film must shorter than 500 minutes"
			},

		},
		submitHandler: function(form) {
			form.submit();
		}
	});
})