(function() {
  var ComboBoxWidget,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  ComboBoxWidget = (function(_super) {

    __extends(ComboBoxWidget, _super);

    function ComboBoxWidget() {
      return ComboBoxWidget.__super__.constructor.apply(this, arguments);
    }

    ComboBoxWidget.prototype.render = function(view) {
      var configuration, selectField,
        _this = this;
      this.selectField = $("<select>");
      view.append(this.selectField);
      selectField = this.selectField;
      configuration = this.configuration;
      return DataManager.getEntities(this.relationshipType.targetType.resource, function(entities) {
        return entities.forEach(function(entity) {
          var option;
          option = new Option(entity.id);
          if (configuration) {
            option = new Option(entity[configuration.propertyKey], entity.id);
          }
          return selectField.append(option);
        });
      });
    };

    ComboBoxWidget.prototype.injectValue = function(entity) {
      return entity[this.relationshipType.name] = {
        id: parseInt(this.selectField[0].selectedOptions[0].value)
      };
    };

    return ComboBoxWidget;

  })(RelationshipWidget);

  return new ComboBoxWidget;

}).call(this);
