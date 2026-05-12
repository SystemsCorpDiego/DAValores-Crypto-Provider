package com.davalores.crypto.provider.app.port.in;

import com.davalores.crypto.provider.domain.model.Cotizacion;

public interface CotizacionObtenerPortIn {

	public Cotizacion run(String par);
	
}
