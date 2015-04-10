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
      var table, th,
        _this = this;
      table = $("<table>");
      view.append(table);
      th = $("<tr><th>Entities</th></tr>");
      th.attr("id", "entities");
      table.append(th);
      return entities.forEach(function(entity) {
        return _this.drawLine(table, entity);
      });
    };

    ListingTable.prototype.drawLine = function(table, entity) {
      var tr;
      tr = $("<tr><td>" + entity.name + "</td></tr>");
      tr.attr("id", "entity_" + entity.name);
      return table.append(tr);
    };

    return ListingTable;

  })(EntitySetWidget);

  return new TableRootRenderer;

}).call(this);
