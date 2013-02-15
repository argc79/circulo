var AppRouter = Backbone.Router.extend({
	routes: {
		"" : "list" 
	},

	list: function(page) {
        var p = page ? parseInt(page, 10) : 1;
        var userList = new UserCollection();
        userList.fetch({success: function(){
            $("#content").html(new UserListView({model: userList, page: p}).el);
        }});
    }
});

utils.loadTemplate(['UserListItemView'], function() {
    app = new AppRouter();
    Backbone.history.start();
});

/*$.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
  // Modify options, control originalOptions, store jqXHR, etc
  options.url = "http://localhost:8080/circulo/" + options.url;
});*/
