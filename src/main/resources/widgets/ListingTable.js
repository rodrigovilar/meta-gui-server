(function() {
  var ListingTable,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  ListingTable = (function(_super) {

    __extends(ListingTable, _super);

    function ListingTable() {
      return ListingTable.__super__.constructor.apply(this, arguments);
    }

    ListingTable.prototype.render = function(view, entityType, entities) {
      return this.drawTable(entityType, entities, view);
    };

    ListingTable.prototype.drawTable = function(entityType, entities, view) {
      var title;
      title = $("<h2>");
      title.append(entityType.name);
      view.append(title);
      this.table = $("<table>");
      view.append(this.table);
      this.buildTableHead(entityType.propertiesType, this.table);
      return this.buildTableBody(entityType, entities, this.table);
    };

    ListingTable.prototype.buildTableHead = function(properties, table) {
      var thead, trHead;
      thead = $("<thead>");
      table.append(thead);
      trHead = $("<tr>");
      trHead.attr("id", "properties");
      thead.append(trHead);
      return properties.forEach(function(property) {
        var thHead;
        thHead = $("<th>" + property.name + "</th>");
        return trHead.append(thHead);
      });
    };

    ListingTable.prototype.buildTableBody = function(entityType, entities, table) {
      var tbody,
        _this = this;
      if (entities.length > 0) {
        tbody = $("<tbody>");
        tbody.attr("id", "instances");
        table.append(tbody);
        return entities.forEach(function(entity) {
          return _this.buildTableLine(entity, entityType, tbody);
        });
      } else {
        return table.append("There are not instances");
      }
    };

    ListingTable.prototype.buildTableLine = function(entity, entityType, tbody) {
      var deleteButton, self, td, trbody,
        _this = this;
      trbody = $("<tr>");
      trbody.attr("id", "instance_" + entity.id);
      tbody.append(trbody);
      entityType.propertiesType.forEach(function(propertyType) {
        var td, widget;
        td = $("<td>");
        td.attr("id", "entity_" + entity.id + "_property_" + propertyType.name);
        widget = RederingEngine.getWidget(entityType, propertyType.type, 'property');
        widget.render(td, propertyType, entity[propertyType.name]);
        return trbody.append(td);
      });
      deleteButton = $("<button>");
      deleteButton.append("Delete");
      self = this;
      deleteButton.on("click", function() {
        var _this = this;
        return DataManager.deleteEntity(entityType.resource, entity.id, function(data) {
          return self.reloadData(entityType);
        }, function(status) {
          return alert("Ocorreu algum erro " + status);
        });
      });
      td = $("<td>");
      td.append(deleteButton);
      return trbody.append(td);
    };

    ListingTable.prototype.reloadData = function(entityType) {
      var _this = this;
      return DataManager.getEntities(entityType.resource, function(entities) {
        $("tbody").empty();
        return _this.buildTableBody(entityType, entities, _this.table);
      });
    };

    return ListingTable;

  })(EntitySetWidget);

  return new ListingTable;

}).call(this);
