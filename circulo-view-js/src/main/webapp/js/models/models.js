window.User = Backbone.Model.extend({
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

	validateItem: function (key) {
        return (this.validators[key]) ? this.validators[key](this.get(key)) : {isValid: true};
    },

    // TODO: Implement Backbone's standard validate() method instead.
    validateAll: function () {

        var messages = {};

        for (var key in this.validators) {
            if(this.validators.hasOwnProperty(key)) {
                var check = this.validators[key](this.get(key));
                if (check.isValid === false) {
                    messages[key] = check.message;
                }
            }
        }

        return _.size(messages) > 0 ? {isValid: false, messages: messages} : {isValid: true};
    },

    defaults: {
        id: null,
        userName: "",
        password: "",
        email: ""
    }
});

window.UserCollection = Backbone.Collection.extend({
	model:User,
	url: "rest/users"
});