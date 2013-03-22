window.TagEmpty = Backbone.View.extend({
	initialize: function () {
        this.render();
    },

    render: function () {
        var m = this.model.toJSON();
        $(this.el).html(this.template(m));
        return this;
    },
   
   events: {
        "click .delete"     : "deleteTag"
    },

    deleteTag: function () {
        this.model.destroy({
            success: function () {
                alert('Tag deleted successfully');
                window.history.back();
            }
        });
        return false;
    }
});