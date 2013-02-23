var AppRouter = Backbone.Router.extend({
	routes: {
		""                    : "listUser" ,
        "users/list"          : "listUser",
        "users/page/:page"    : "listUser",
        "users/add"           : "addUser",
        "users/:id"           : "userDetails",
        "tags/list"           : "listTag",
        "tags/page/:page"     : "listTag",
        "tags/add"            : "addTag",
        "tags/:id"            : "tagDetails",
        "notes/list"           : "listNote",
        "notes/page/:page"     : "listNote",
        "notes/add"            : "addNote",
        "notes/:id"            : "noteDetails",
	},

    initialize: function () {
        this.headerView = new HeaderView();
        $('.header').html(this.headerView.el);
    },
    
	listUser: function(page) {
        var p = page ? parseInt(page, 10) : 1;
        var userList = new UserCollection();
        userList.fetch({success: function(){
            $("#content-app").html(new UserListView({model: userList, page: p}).el);
        }});
        this.headerView.selectMenuItem('add-menu-user');
    },

    userDetails: function (id) {
        var user = new User({id: id});
        user.fetch({success: function(){
            $("#content-app").html(new UserView({model: user}).el);
        }});
        this.headerView.selectMenuItem();
    },

    addUser: function() {
        var user = new User();
        $('#content-app').html(new UserView({model: user}).el);
        this.headerView.selectMenuItem('add-menu-user');
    },

    listTag: function(page) {
        var p = page ? parseInt(page, 10) : 1;
        var tagList = new TagCollection();
        tagList.fetch({success: function(){
            $("#content-app").html(new TagListView({model: tagList, page: p}).el);
        }});
        this.headerView.selectMenuItem('add-menu-tag');
    },

    tagDetails: function (id) {
        var tag = new Tag({id: id});
        tag.fetch({success: function(){
            $("#content-app").html(new TagView({model: tag}).el);
        }});
        this.headerView.selectMenuItem();
    },

    addTag: function() {
        var tag = new Tag();
        $('#content-app').html(new TagView({model: tag}).el);
        this.headerView.selectMenuItem('add-menu-tag');
    },

    listNote: function(page) {
        var p = page ? parseInt(page, 10) : 1;
        var noteList = new NoteCollection();
        noteList.fetch({success: function(){
            $("#content-app").html(new NoteListView({model: noteList, page: p}).el);
        }});
        this.headerView.selectMenuItem('add-menu-note');
    },

    noteDetails: function (id) {
        var note = new Note({id: id});
        note.fetch({success: function(){
            $("#content-app").html(new NoteView({model: note}).el);
        }});
        this.headerView.selectMenuItem();
    },

    addNote: function() {
        var note = new Note();
        $('#content-app').html(new NoteView({model: note}).el);
        this.headerView.selectMenuItem('add-menu-note');
    }
});