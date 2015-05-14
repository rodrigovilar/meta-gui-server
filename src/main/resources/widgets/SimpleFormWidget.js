(function() {
  var SimpleFormWidget,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  SimpleFormWidget = (function(_super) {

    __extends(SimpleFormWidget, _super);

    function SimpleFormWidget() {
      return SimpleFormWidget.__super__.constructor.apply(this, arguments);
    }

    SimpleFormWidget.prototype.render = function(view) {
      var self,
        _this = this;
      self = this;
      if (this.entityID) {
        return DataManager.getEntity(this.entityType.resource, this.entityID, function(entity) {
          return self.draw(view, self.entityType, entity);
        });
      } else {
        return self.draw(view, this.entityType);
      }
    };

    SimpleFormWidget.prototype.draw = function(view, entityType, entity) {
      var self, submitButton, table, title, widgets;
      title = $("<h2>");
      title.append(entityType.name);
      view.append(title);
      table = $("<table>");
      view.append(table);
      widgets = [];
      entityType.propertyTypes.forEach(function(propertyType) {
        var td, tr, widget;
        if (propertyType.name !== "id") {
          tr = $("<tr>");
          td = $("<td>");
          td.append(propertyType.name);
          tr.append(td);
          td = $("<td>");
          widget = RenderingEngine.getPropertyWidget('field', entityType, propertyType);
          widget.propertyType = propertyType;
          if (entity) {
            widget.property = entity[propertyType.name];
          }
          widget.render(td);
          widgets.push(widget);
          tr.append(td);
          return view.append(tr);
        }
      });
      this.widgets = widgets;
      submitButton = $("<button>");
      self = this;
      if (entity) {
        submitButton.append("Update");
        submitButton.click(function() {
          var newEntityValues,
            _this = this;
          newEntityValues = self.getEntityValuesFromInput();
          newEntityValues["id"] = entity.id;
          return DataManager.updateEntity(entityType.resource, newEntityValues).done(function() {
            return RenderingEngine.popAndRender(View.emptyPage());
          }).fail(function() {
            return alert("Error");
          });
        });
      } else {
        submitButton.append("Create");
        submitButton.click(function() {
          var newEntityValues,
            _this = this;
          newEntityValues = self.getEntityValuesFromInput();
          return DataManager.createEntity(entityType.resource, newEntityValues).done(function() {
            return RenderingEngine.popAndRender(View.emptyPage());
          }).fail(function() {
            return alert("Error");
          });
        });
      }
      return view.append(submitButton);
    };

    SimpleFormWidget.prototype.getEntityValuesFromInput = function() {
      var entity;
      entity = {};
      this.widgets.forEach(function(widget) {
        return widget.injectValue(entity);
      });
      return entity;
    };

    return SimpleFormWidget;

  })(EntityWidget);

  return new SimpleFormWidget;

}).call(this);
