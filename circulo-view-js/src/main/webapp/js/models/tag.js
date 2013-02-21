window.Tag = window.Model.extend({
	urlRoot: "rest/tags",

	initialize: function(){
		this.validators = {};

	    this.validators.name = function (value) {
	        return value.length > 0 ? {isValid: true} : {isValid: false, message: "You must enter a name for the tag"};
	    };
	},

	defaults: {
		id: null,
		name: ""
	}
});

window.TagCollection = Backbone.Collection.extend({
	model: Tag,
	url: "rest/tags"
});