window.User = Backbone.Model.extend({
	urlRoot: "rest/users"
})

window.UserCollection = Backbone.Collection.extend({
	model:User,
	url: "rest/users"
})