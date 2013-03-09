circulo.User = circulo.Model.extend({
	urlRoot: "rest/users",

    initialize: function () {
		this.validators = {};

	    this.validators.userName = function (value) {
	        return value.length > 0 ? {isValid: true} : {isValid: false, message: "You must enter a name"};
	    };

	    this.validators.password = function (value) {
	        return value.length > 0 ? {isValid: true} : {isValid: false, message: "You must enter a password"};
	    };

	    this.validators.email = function (value) {
	        return value.length > 0 ? {isValid: true} : {isValid: false, message: "You must enter a valid email address"};
	    };
	},

    defaults: {
        id: null,
        userName: "",
        password: "",
        email: ""
    }
});

circulo.UserCollection = Backbone.Collection.extend({
	model:circulo.User,
	url: "rest/users"
});