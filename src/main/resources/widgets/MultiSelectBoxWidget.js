(function() {
  var MultiSelectBoxWidget,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  MultiSelectBoxWidget = (function(_super) {

    __extends(MultiSelectBoxWidget, _super);

    function MultiSelectBoxWidget() {
      return MultiSelectBoxWidget.__super__.constructor.apply(this, arguments);
    }

    MultiSelectBoxWidget.prototype.render = function(view) {
      var configuration, multiSelectField, relationship,
        _this = this;
      this.multiSelectField = $("<select multiple>");
      view.append(this.multiSelectField);
      multiSelectField = this.multiSelectField;
      configuration = this.configuration;
      relationship = [];
      if (this.relationship) {
        this.relationship.forEach(function(entity) {
          return relationship.push(entity.id);
        });
      }
      return DataManager.getEntities(this.relationshipType.targetType.resource, function(entities) {
        return entities.forEach(function(entity) {
          var option;
          option = new Option(entity.id);
          if (configuration) {
            option = new Option(entity[configuration.propertyKey], entity.id);
          }
          multiSelectField.append(option);
          if (relationship.indexOf(entity.id) !== -1) {
            return option.selected = true;
          }
        });
      });
    };

    MultiSelectBoxWidget.prototype.injectValue = function(entity) {
      var value,
        _this = this;
      value = [];
      if (this.multiSelectField.val()) {
        this.multiSelectField.val().forEach(function(selected) {
          return value.push({
            id: parseInt(selected)
          });
        });
      }
      return entity[this.relationshipType.name] = value;
    };

    return MultiSelectBoxWidget;

  })(RelationshipWidget);

  return new MultiSelectBoxWidget;

}).call(this);
