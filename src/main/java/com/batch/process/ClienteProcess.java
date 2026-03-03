package com.batch.process;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.dto.ClienteA;
import com.batch.dto.ClienteB;

@Component
public class ClienteProcess implements ItemProcessor<ClienteA, ClienteB>{

	@Override
	public ClienteB process(ClienteA clienteA) throws Exception {
		final String fullName = clienteA.getName() + " " + clienteA.getLastName();
		final String email = clienteA.getEmail().toUpperCase();
		final Boolean activo = clienteA.getActivo();
		
		if (!activo.equals(false)) {
			ClienteB clienteB = new ClienteB();
			clienteB.setFullName(fullName);
			clienteB.setEmail(email);
			clienteB.setActivo(activo);
			
			return clienteB;
		}
		
		return null;
		
	}
	
}
