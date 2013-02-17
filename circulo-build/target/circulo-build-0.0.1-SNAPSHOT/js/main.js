var AppRouter = Backbone.Router.extend({
	routes: {
		""                    : "list" ,
		"users/page/:page"    : "list",
        "users/add"           : "addUser",
        "users/:id"           : "userDetails"
	},

    initialize: function () {
        this.headerView = new HeaderView();
        $('.header').html(this.headerView.el);
    },
    
	list: function(page) {
        var p = page ? parseInt(page, 10) : 1;
        var userList = new UserCollection();
        userList.fetch({success: function(){
            $("#content").html(new UserListView({model: userList, page: p}).el);
        }});
    },

    userDetails: function (id) {
        var user = new User({id: id});
        user.fetch({success: function(){
            $("#content").html(new UserView({model: user}).el);
        }});
        this.headerView.selectMenuItem();
    },

    addUser: function() {
        var user = new User();
        $('#content').html(new UserView({model: user}).el);
        this.headerView.selectMenuItem('add-menu');
    }
});

utils.loadTemplate(['UserListItemView', 'UserView', 'HeaderView'], function() {
    app = new AppRouter();    
    Backbone.history.start();
});

$.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
  // Modify options, control originalOptions, store jqXHR, etc
  options.url = "http://localhost:8080/circulo/" + options.url;
});
