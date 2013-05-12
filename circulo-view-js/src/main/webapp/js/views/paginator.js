window.Paginator = Backbone.View.extend({

    className: "pagination pagination-centered",

    initialize:function () {
        this.model.bind("reset", this.render, this);
        this.render();
    },

    render:function () {

        var items = this.model.models,
            len = items.length,
            pageCount = Math.ceil(len / 8),
            i;

        $(this.el).html('<ul />');

        for (i = 0; i < pageCount; i += 1) {
            //$('ul', this.el).append("<li" + ((i + 1) === this.options.page ? " class='active'" : "") + "><a href='#users/page/"+(i+1)+"'>" + (i+1) + "</a></li>");
            $('ul', this.el).append("<li" + ((i + 1) === this.options.page ? " class='active'" : "") + "><a href='#" + this.options.entityname + "/page/"+(i+1)+"'>" + (i+1) + "</a></li>");
        }

        return this;
    }
});

window.NoteByTagPaginator = Backbone.View.extend({

    className: "pagination pagination-centered",

    initialize:function () {
        this.model.bind("reset", this.render, this);
        this.render();
    },

    render:function () {

        var items = this.model.models,
            len = items.length,
            pageCount = Math.ceil(len / 8),
            i;

        $(this.el).html('<ul />');

        for (var i=0; i < pageCount; i++) {
            //$('ul', this.el).append("<li" + ((i + 1) === this.options.page ? " class='active'" : "") + "><a href='#users/page/"+(i+1)+"'>" + (i+1) + "</a></li>");
            $('ul', this.el).append("<li" + ((i + 1) === this.options.page ? " class='active'" : "") + "><a href='#" + this.options.entityname + "/" + this.options.id + "/page/"+(i+1)+"'>" + (i+1) + "</a></li>");
        }

        return this;
    }
});