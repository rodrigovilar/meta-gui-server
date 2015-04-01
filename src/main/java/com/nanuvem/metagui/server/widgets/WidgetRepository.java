package com.nanuvem.metagui.server.widgets;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.api.Widget;

public interface WidgetRepository extends JpaRepository<Widget, Long> {

}
