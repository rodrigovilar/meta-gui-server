(function() {
  var ListingTable,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  ListingTable = (function(_super) {

    __extends(ListingTable, _super);

    function ListingTable() {
      return ListingTable.__super__.constructor.apply(this, arguments);
    }

    ListingTable.prototype.render = function(view, entityType, entites, conf) {
      return this.drawTable(entityType, entites, view);
    };

    ListingTable.prototype.drawTable = function(entityType, entites, view) {
      var table;
      table = $("<table>");
      view.append(table);
      this.buildTableHead(entityType.properties, table);
      return this.buildTableBody(entityType.properties, entites, table);
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

    ListingTable.prototype.buildTableBody = function(properties, entites, table) {
      var tbody,
        _this = this;
      if (entites.length > 0) {
        tbody = $("<tbody>");
        tbody.attr("id", "instances");
        table.append(tbody);
        return entites.forEach(function(entity) {
          return _this.buildTableLine(entity, properties, tbody);
        });
      } else {
        return table.append("There are not instances");
      }
    };

    ListingTable.prototype.buildTableLine = function(entity, properties, tbody) {
      var trbody,
        _this = this;
      trbody = $("<tr>");
      trbody.attr("id", "instance_" + instance.id);
      tbody.append(trbody);
      return properties.forEach(function(property) {
        var td;
        td = $("<td>");
        td.attr("id", "entity_" + entity.id + "_property_" + property.name);
        td.append(entity[property.name]);
        return trbody.append(td);
      });
    };

    return ListingTable;

  })(EntitySetWidget);

  return new ListingTable;

}).call(this);
