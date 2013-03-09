window.NavigationView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        $(this.el).html(this.template());
        return this;
    },

    events: {
        "click .tags"   : "navigateTags",
        "click .notes" : "navigateNotes",
    },

    navigateTags: function(){
        app.navigate('tags/list', true);
    },

    navigateNotes: function(){        
        app.navigate('notes/list', true);      
    },

    selectMenuItem: function (menuItem) {
        this.showNavigation();
        $('.btn-group button').removeClass('active');
        if (menuItem) {
            $('.' + menuItem).addClass('active');
        }
    },

    hideNavigation: function(){
        $('.btn-group').hide();
    },

    showNavigation: function(){
        $('.btn-group').show();
    }
});