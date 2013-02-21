utils.loadTemplate(['UserListItemView', 'UserView', 'HeaderView', 'TagListItemView', 'TagView'], function() {
    app = new AppRouter();    
    Backbone.history.start();
});

/*$.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
  // Modify options, control originalOptions, store jqXHR, etc
  options.url = "http://localhost:8080/circulo/" + options.url;
});*/
