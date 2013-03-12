circulo.Tag = circulo.Model.extend({
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

circulo.TagCollection = Backbone.Collection.extend({
	model: circulo.Tag,
	url: "rest/tags"
});