package com.nanuvem.metagui.server.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.Context;

@Component
public class ContextContainer {
	
	@Autowired
	private ContextRepository contextRepository;
	
	public Context getOrCreateContext(Context context) {
		List<Context> contexts = contextRepository.findByName(context.getName());
		Context contextStored = contexts.size() > 0 ? contexts.get(0) : null;
		if(contextStored == null) {
			contextStored = contextRepository.saveAndFlush(context);
		}
		return contextStored;
	}
	
}
