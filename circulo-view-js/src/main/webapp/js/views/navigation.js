window.NavigationView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        $(this.el).html(this.template());
        return this;
    },

    events: {
        "click .tags"       : "navigateTags",
        "click .notes"      : "navigateNotes",
        "click .byalpha"    : "sortAlpha",
        "click .bydate"     : "sortDate",
        "click .goback"     : "goBack"
    },

    navigateTags: function(){
        app.navigate('tags/list', true);
    },

    navigateNotes: function(){        
        app.navigate('notes/list', true);      
    },

    selectMenuItem: function (menu_item) {
        this.showNavigation();
        if (menu_item === 'tags'){
            $('.filter_2').hide();
        }else if (menu_item === 'notes'){
            $('.filter_2').show();
        }
        // $('.btn-group button').removeClass('active');
        $('.filter_1 button').removeClass('active');        
        if (menu_item) {
            $('.' + menu_item).addClass('active');
        }
    },

    hideNavigation: function(){
        $('.btn-group-navigation').hide();
    },

    showNavigation: function(){
        $('.btn-group-navigation').show();
    },

    sortAlpha: function(){
        $(".byalpha").addClass("active");
        $(".bydate").removeClass("active");
        
        app.navigate('notes/list/sort/alpha', true);
        //this.navigateNotes();       
        // Backbone.history.navigate('notes/list');
    },

    sortDate: function(){
        $(".byalpha").removeClass("active");
        $(".bydate").addClass("active");       
        app.navigate('notes/list/sort/date', true);
        //this.navigateNotes();
        // Backbone.history.navigate('notes/list');
    },

    getSelectedSorting: function(){
        if ($(".byalpha").hasClass("active")){
            return "alpha";
        }else if ($(".bydate").hasClass("active")){
            return "date";
        }
    },

    goBack: function(){
        window.history.back();
    }
});