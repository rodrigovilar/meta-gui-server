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
      var selectField,
        _this = this;
      selectField = $("<select>");
      this.selectField = selectField;
      view.append(this.selectField);
      return DataManager.getEntities(this.relationshipType.targetType.resource, function(entities) {
        return entities.forEach(function(entity) {
          var option;
          option = new Option(entity.id);
          if (this.configuration) {
            option = new Option(entity[this.configuration.propertyKey], entity.id);
          }
          return selectField.append(option);
        });
      });
    };

    return ComboBoxWidget;

  })(RelationshipWidget);

  return new ComboBoxWidget;

}).call(this);
