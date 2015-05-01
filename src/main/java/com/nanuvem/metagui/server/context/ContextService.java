package com.nanuvem.metagui.server.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanuvem.metagui.server.api.Context;

@Service
public class ContextService {
	
	@Autowired
	private ContextRepository contextRepository;
	
	public Context getContextByName(String name) {
		return contextRepository.findByName(name);
	}
	
	public Context createContext(Context context) {
		return contextRepository.saveAndFlush(context);
	}
	
}
