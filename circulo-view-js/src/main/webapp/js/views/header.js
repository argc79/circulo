window.HeaderView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        $(this.el).html(this.template());
        $("#autocomplete", this.el).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "rest/search",
                    dataType: "json",
                    data: { query: request.term},
                    success: function( data ) {
                        response( $.map( data, function( item ) {
                            return {
                                key: item.id, 
                                label: item.type + ": " + item.name,
                                value: item.name,
                                type:  item.type
                            }
                        }));
                    }
                });
            },
        });

        $("#autocomplete", this.el).on( "autocompleteselect", function( event, ui ) {
            var item = ui['item'];
            if (item.type==="note"){
                app.navigate('notes/' + item.key, true);
            }else if (item.type==="tag"){
                app.navigate('tags/' + item.key, true);
            }
        });

        return this;
    },

    selectMenuItem: function (menuItem) {
        $('.nav li').removeClass('active');
        if (menuItem) {
            $('.' + menuItem).addClass('active');
        }
    }

});