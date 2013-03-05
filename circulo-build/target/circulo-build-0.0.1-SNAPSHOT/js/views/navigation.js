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
        console.log(menuItem);
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
    /*selectMenuItem: function (menuItem) {
        if (_.isEqual('tags', menuItem)){
            console.log('tags');
            $('.two').removeClass('active');
            $('.one').addClass('active');
            console.log($('.one').attr('class'));
            console.log($('.two').attr('class'));
        }
        if (_.isEqual('notes', menuItem)){
            console.log('notes');
            $('.one').removeClass('active');
            $('.two').addClass('active');
            console.log($('.one').attr('class'));
            console.log($('.two').attr('class'));
        }
    }*/
/*
    events: {
        "click .tags"   : "navigateTags",
        "click .notes" : "navigateNotes",
    },

    navigateTags: function(){
        app.navigate('tags/list', true);
    },

    navigateNotes: function(){        
        app.navigate('notes/list', true);      
    }
*/
});