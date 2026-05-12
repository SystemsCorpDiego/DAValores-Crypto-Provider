package com.davalores.crypto.provider.app.ripio.port.out;

import com.davalores.crypto.provider.domain.model.LoginTokenRipio;
import com.davalores.crypto.provider.domain.model.exception.LoginException;

public interface LoginRipioPortOut {

	public LoginTokenRipio run() throws LoginException;
	
}
