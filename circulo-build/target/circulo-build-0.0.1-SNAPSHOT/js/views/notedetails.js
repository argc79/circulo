window.NoteView = Backbone.View.extend({

    initialize: function () {
        this.render();
    },

    render: function () {
        //create               
        $(this.el).html(this.template(this.model.toJSON()));
        
        //initialize magicSuggest
        var taglist = this.model.get("tags");
        var tagavailable = this.options.tags.toJSON();
        var cleantaglist = [];

        //cleanup
        for (var i=0;i<tagavailable.length;i++){
            if (tagavailable[i].id !==null){
                cleantaglist.push(tagavailable[i]);
            }
        }        

        var selected = [];
        for (var i=0;i<taglist.length;i++){
            if (taglist[i].id !==null){
                selected.push(taglist[i].id);
            }
        }        
        this.msFilter = $('#ms-filter', this.el).magicSuggest({
                id: 'selectedTags',
                data: cleantaglist,
                value: selected,
                selectionPosition: 'right',
                expandOnFocus: true
        });
        // var taglist = this.model.get("tags");
        // var tagavailable = this.options.tags.toJSON();
        
        // //populate available tags
        // for (var i=0;i<tagavailable.length;i++){
        //     var checked = (this.isTagSelected(tagavailable[i].id)) ? "checked" : "";
        //     $("#tags-table", this.el).append("<tr><td>"+ tagavailable[i].name + "</td><td><input id='tag-table-id-" + tagavailable[i].id + "' type='checkbox' " + checked +"/></td></tr>");
        // }

        // //populate current tags
        // for (var i=0;i<taglist.length;i++){ 
        //     $("#tags-available", this.el).append("<span id='tag-id-"+ taglist[i].id +"' class='label label-info'>" +  taglist[i].name + "</span>");
        // }
        // $("#tags-available", this.el).append("<a id='add-tags' class='btn addTags'>Add Tags</a>");
        
        return this;
    },

    isTagSelected: function(id){
        var taglist = this.model.get("tags");
        for (var i=0;i<taglist.length;i++){ 
            if (taglist[i].id === id){
                return true;
            }
        }
        return false;
    },

    events: {
        "change"                : "change",
        "click .save"           : "beforeSave",
        "click .delete"         : "deleteNote",
        "drop #picture"         : "dropHandler",
        "click .addTags"        : "addTags",
        "click .btn-save-tags"  : "saveTags"
        // "keypress #tags"    : "listTags"
    },

    change: function (event) {
        // Remove any existing alert message
        utils.hideAlert();

        // Apply the change to the model
        var target = event.target;
        var change = {};
        change[target.name] = target.value;
        this.model.set(change);

        // Run validation rule (if any) on changed item
        var check = this.model.validateItem(target.id);
        if (check.isValid === false) {
            utils.addValidationError(target.id, check.message);
        } else {
            utils.removeValidationError(target.id);
        }
    },

    beforeSave: function () {
        var self = this;
        var check = this.model.validateAll();
        if (check.isValid === false) {
            utils.displayValidationErrors(check.messages);
            return false;
        }
        
        var newModelTags = [];
        var selected = this.msFilter.getSelectedItems();
        for (var i=0;i<selected.length;i++){ 
            if (selected[i].id === selected[i].name){
                selected[i].id = null;
            }
            newModelTags.push(selected[i]);
        }
        this.model.set({tags: newModelTags});
    
        //Get the content from CKEditor
        //console.debug("the model value is" + this.model.attributes.subject);
        this.model.attributes.content = CKEDITOR.instances['content'].getData();
        //console.debug("the model value is" + this.model.attributes.content);

        // Upload picture file if a new file was dropped in the drop area
        if (this.pictureFile) {
            this.model.set("picture", this.pictureFile.name);
            utils.uploadFile(this.pictureFile,
                function () {
                    self.saveNote();
                }
            );
        } else {
            this.saveNote();
        }
        return false;
    },

    saveNote: function () {
        var self = this;        
        this.model.save(null, {
            success: function (model) {
                self.render();
                app.navigate('notes/' + model.id, false);
                utils.showAlert('Success!', 'Note saved successfully', 'alert-success');
            },
            error: function () {
                utils.showAlert('Error', 'An error occurred while trying to delete this item', 'alert-error');
            }
        });
    },

    deleteNote: function () {
        this.model.destroy({
            success: function () {
                alert('Note deleted successfully');
                window.history.back();
            }
        });
        return false;
    },

    dropHandler: function (event) {
        event.stopPropagation();
        event.preventDefault();
        var e = event.originalEvent;
        e.dataTransfer.dropEffect = 'copy';
        this.pictureFile = e.dataTransfer.files[0];

        // Read the image file from the local file system and display it in the img tag
        var reader = new FileReader();
        reader.onloadend = function () {
            $('#picture').attr('src', reader.result);
        };
        reader.readAsDataURL(this.pictureFile);
    },

    addTags: function () {                                               
        // $('#myModal').modal();        
        // console.log("addTags");
        var selected = this.msFilter.getSelectedItems();
        for (var i=0;i<selected.length;i++){ 
            console.log(selected[i].id);
        }
        console.log(this.msFilter);
        console.log($("#selectedTags"));
    },

    saveTags: function() {
        var taglist = this.model.get("tags");
        //cleaning
        for (var i=0;i<taglist.length;i++){ 
            $("#tag-id-"+ taglist[i].id).remove();
        }
        $("#add-tags").remove();

        //create
        for (var i=0;i<taglist.length;i++){ 
            $("#tags-available").append("<span id='tag-id-"+ taglist[i].id +"' class='label label-info'>" +  taglist[i].name + "</span>");
        }
        $("#tags-available").append("<span id='tag-id-2222' class='label label-info'>Nintendo</span>");
        $("#tags-available").append("<a id='add-tags' class='btn addTags'>Add Tags</a>");
        
        $('#myModal').modal("hide");
    }

});