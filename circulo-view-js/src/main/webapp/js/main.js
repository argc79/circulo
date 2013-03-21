utils.loadTemplate(['UserListItemView', 'UserView', 'HeaderView', 'NavigationView','TagListItemView', 'TagView', 'NoteView', 'NoteListItemView'], function() {
    app = circulo.AppRouter.instance();
    Backbone.history.start();
});

//$.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
//  // Modify options, control originalOptions, store jqXHR, etc
//  options.url = "http://localhost:8080/circulo/" + options.url;
//  //options.url = "http://rocket79.dyndns.org:83/circulo/" + options.url;
//});
