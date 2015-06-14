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
      var configuration, relationship, selectField,
        _this = this;
      this.selectField = $("<select>");
      view.append(this.selectField);
      selectField = this.selectField;
      configuration = this.configuration;
      relationship = this.relationship;
      return DataManager.getEntities(this.relationshipType.targetType.resource, function(entities) {
        entities.forEach(function(entity) {
          var option;
          option = new Option(entity.id);
          if (configuration) {
            option = new Option(entity[configuration.propertyKey], entity.id);
          }
          return selectField.append(option);
        });
        if (relationship) {
          return selectField[0].value = relationship.id;
        }
      });
    };

    ComboBoxWidget.prototype.injectValue = function(entity) {
      return entity[this.relationshipType.name] = {
        id: parseInt(this.selectField.val())
      };
    };

    return ComboBoxWidget;

  })(RelationshipWidget);

  return new ComboBoxWidget;

}).call(this);
