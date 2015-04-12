(function() {
  var ListingTable,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  ListingTable = (function(_super) {

    __extends(ListingTable, _super);

    function ListingTable() {
      return ListingTable.__super__.constructor.apply(this, arguments);
    }

    ListingTable.prototype.render = function(view, entityType, entites, configuration) {
      return this.drawTable(entityType, entites, view);
    };

    ListingTable.prototype.drawTable = function(entityType, entites, view) {
      var table, title;
      title = $("<h2>");
      title.append(entityType.name);
      view.append(title);
      table = $("<table>");
      view.append(table);
      this.buildTableHead(entityType.properties, table);
      return this.buildTableBody(entityType, entites, table);
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

    ListingTable.prototype.buildTableBody = function(entityType, entites, table) {
      var tbody,
        _this = this;
      if (entites.length > 0) {
        tbody = $("<tbody>");
        tbody.attr("id", "instances");
        table.append(tbody);
        return entites.forEach(function(entity) {
          return _this.buildTableLine(entity, entityType, tbody);
        });
      } else {
        return table.append("There are not instances");
      }
    };

    ListingTable.prototype.buildTableLine = function(entity, entityType, tbody) {
      var deleteButton, td, trbody,
        _this = this;
      trbody = $("<tr>");
      trbody.attr("id", "instance_" + entity.id);
      tbody.append(trbody);
      entityType.properties.forEach(function(property) {
        var td;
        td = $("<td>");
        td.attr("id", "entity_" + entity.id + "_property_" + property.name);
        td.append(entity[property.name]);
        return trbody.append(td);
      });
      deleteButton = $("<button>");
      deleteButton.append("Delete");
      deleteButton.on("click", function() {
        var _this = this;
        return DataManager.deleteEntity(entityType.resource, entity.id, function(data) {
          return RederingEngine.peformContext(View.emptyPage(), entityType, 'root');
        }, function(status) {
          return alert("Ocorreu algum erro " + status);
        });
      });
      td = $("<td>");
      td.append(deleteButton);
      return trbody.append(td);
    };

    return ListingTable;

  })(EntitySetWidget);

  return new ListingTable;

}).call(this);
